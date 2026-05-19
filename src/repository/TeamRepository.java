package repository;

import model.Team;
import model.User;
import service.UserService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private UserService userService;

    public TeamRepository(UserService userService) {
        this.userService = userService;
    }

    private void save(Team team, String tableName) throws SQLException {
        String sql = """
                INSERT INTO %s (
                    team_name,
                    description,
                    team_owner_email,
                    created_at
                ) VALUES (?, ?, ?, ?)
                """.formatted(tableName);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, team.getTeamName());
            statement.setString(2, team.getDescription());
            statement.setString(3, team.getTeamOwner().getEmail());
            statement.setDate(4, Date.valueOf(team.getCreatedAt()));

            statement.executeUpdate();
        }
    }

    public void saveTeam(Team team) throws SQLException {
        save(team, "teams");
    }

    public void saveTeamHistory(Team team) throws SQLException {
        save(team, "teams_history");
    }

    public List<Team> loadTeams() throws SQLException {
        List<Team> teams = new ArrayList<>();

        String sql = "SELECT * FROM teams";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User teamOwner = userService.findUserByEmail(resultSet.getString("team_owner_email"));

                if (teamOwner == null) {
                    continue;
                }

                Team team = new Team(
                        resultSet.getString("team_name"),
                        resultSet.getString("description"),
                        teamOwner,
                        resultSet.getDate("created_at").toLocalDate()
                );

                teams.add(team);
            }
        }

        return teams;
    }
}
