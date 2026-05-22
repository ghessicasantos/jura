package repository;

import enums.StatusProjects;
import model.Project;
import model.Team;
import model.User;
import service.TeamService;
import service.UserService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private void save(Project project, String tableName) throws SQLException {
        String sql = """
                INSERT INTO %s (
                    project_name,
                    description,
                    start_date,
                    finish_date,
                    status,
                    project_manager_email,
                    team_owner_name
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """.formatted(tableName);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Date.valueOf(project.getStartDate()));
            statement.setDate(4, Date.valueOf(project.getFinishDate()));
            statement.setString(5, project.getStatus().name());
            statement.setString(6, project.getProjectManager().getEmail());
            statement.setString(7, project.getTeamOwner().getTeamName());

            statement.executeUpdate();
        }
    }

    public void saveProject(Project project) throws SQLException {
        save(project, "projects");
    }

    public void saveProjectHistory(Project project) throws SQLException {
        save(project, "project_history");
    }

    public List<Project> loadProject() throws SQLException {
        UserRepository userRepository = new UserRepository();
        TeamService teamService = new TeamService();

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User projectManager = userRepository.findUserByEmail(resultSet.getString("project_manager_email"));
                Team projectTeam = teamService.findTeamByName(resultSet.getString("team_owner_name"));

                if (projectManager == null || projectTeam == null) {
                    continue;
                }

                Project project = new Project(
                        resultSet.getString("project_name"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("finish_date").toLocalDate(),
                        StatusProjects.valueOf(resultSet.getString("status")),
                        projectManager,
                        projectTeam
                );

                projects.add(project);
            }
        }

        return projects;
    }
}
