package repository;

import enums.StatusTasks;
import enums.StatusProjects;
import model.TaskStatusReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportRepository {

    public Map<StatusProjects, Integer> countProjectsByStatus() throws SQLException {
        Map<StatusProjects, Integer> projectsByStatus = new LinkedHashMap<>();

        for (StatusProjects status : StatusProjects.values()) {
            projectsByStatus.put(status, 0);
        }

        String sql = """
                SELECT status, COUNT(*) AS total
                FROM projects
                WHERE active = true
                GROUP BY status
                ORDER BY status
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                StatusProjects status = StatusProjects.valueOf(resultSet.getString("status"));
                int total = resultSet.getInt("total");

                projectsByStatus.put(status, total);
            }
        }

        return projectsByStatus;
    }

    public Map<StatusTasks, Integer> countTasksByStatus() throws SQLException {
        Map<StatusTasks, Integer> tasksByStatus = new LinkedHashMap<>();

        for (StatusTasks status : StatusTasks.values()) {
            tasksByStatus.put(status, 0);
        }

        String sql = """
                SELECT status, COUNT(*) AS total
                FROM tasks
                WHERE active = true
                GROUP BY status
                ORDER BY status
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                StatusTasks status = StatusTasks.valueOf(resultSet.getString("status"));
                int total = resultSet.getInt("total");

                tasksByStatus.put(status, total);
            }
        }

        return tasksByStatus;
    }

    public Map<String, TaskStatusReport> countTasksByStatusAndProject() throws SQLException {
        Map<String, TaskStatusReport> reports = new LinkedHashMap<>();

        String sql = """
                SELECT project_name, status, COUNT(*) AS total
                FROM tasks
                WHERE active = true
                GROUP BY project_name, status
                ORDER BY project_name, status
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String projectName = resultSet.getString("project_name");
                StatusTasks status = StatusTasks.valueOf(resultSet.getString("status"));
                int total = resultSet.getInt("total");

                TaskStatusReport report = reports.get(projectName);
                if (report == null) {
                    report = new TaskStatusReport(projectName);
                    reports.put(projectName, report);
                }

                report.setStatusTotal(status, total);
            }
        }

        return reports;
    }

    public TaskStatusReport countTasksByStatusForProject(String projectName) throws SQLException {
        TaskStatusReport report = new TaskStatusReport(projectName);

        String sql = """
                SELECT status, COUNT(*) AS total
                FROM tasks
                WHERE project_name = ?
                AND active = true
                GROUP BY status
                ORDER BY status
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, projectName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    StatusTasks status = StatusTasks.valueOf(resultSet.getString("status"));
                    int total = resultSet.getInt("total");

                    report.setStatusTotal(status, total);
                }
            }
        }

        return report;
    }
}
