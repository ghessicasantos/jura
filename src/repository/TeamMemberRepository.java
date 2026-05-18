package repository;

import model.Team;
import model.TeamMember;
import model.User;
import service.TeamService;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberRepository {

    private UserService userService;

    private TeamService teamService;

    public TeamMemberRepository(UserService userService) {
        this.userService = userService;
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    private void save(TeamMember teamMember, String tableName) throws SQLException {
        String sql = """
                INSERT INTO %s (
                    team_member_email,
                    team_name,
                    member_status
                ) VALUES (?, ?, ?)
                """.formatted(tableName);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, teamMember.getUser().getEmail());
            statement.setString(2, teamMember.getTeam().getTeamName());
            statement.setBoolean(3, teamMember.isActive());

            statement.executeUpdate();
        }
    }

    public void saveTeamMember(TeamMember teamMember) throws SQLException {
        save(teamMember, "team_members");
    }

    public void saveTeamMemberHistory(TeamMember teamMember) throws SQLException {
        save(teamMember, "team_members_history");
    }

    public List<TeamMember> loadTeamMembers() throws SQLException {
        List<TeamMember> teamMembers = new ArrayList<>();

        String sql = "SELECT * FROM team_members";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User member = userService.findUserByEmail(resultSet.getString("team_member_email"));
                Team team = teamService.findTeamByName(resultSet.getString("team_name"));

                if (member == null || team == null) {
                    continue;
                }

                TeamMember teamMember = new TeamMember(
                        member,
                        team,
                        resultSet.getBoolean("member_status")
                );

                teamMembers.add(teamMember);
            }
        }

        return teamMembers;
    }
}
