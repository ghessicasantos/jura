package repository;

import enums.StatusTasks;
import model.TaskStatusReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportRepository {

    public Map<String, TaskStatusReport> countTasksByStatusAndProject() throws SQLException {
        Map<String, TaskStatusReport> reports = new LinkedHashMap<>();

        String sql = """
                SELECT project_name, status, COUNT(*) AS total
                FROM tasks
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
