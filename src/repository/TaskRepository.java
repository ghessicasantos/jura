package repository;

import enums.StatusTasks;
import model.Project;
import model.Task;
import model.User;
import service.ProjectService;
import service.UserService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private void save(Task task, String tableName) throws SQLException {
        String sql = """
                INSERT INTO %s (
                    task_title,
                    description,
                    start_date,
                    finish_date,
                    status,
                    assigned_user_email,
                    project_name
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """.formatted(tableName);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getTaskTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getFinishDate()));
            statement.setString(5, task.getStatus().name());
            statement.setString(6, task.getAssignedUser().getEmail());
            statement.setString(7, task.getProject().getProjectName());

            statement.executeUpdate();
        }
    }

    public void saveTask(Task task) throws SQLException {
        save(task, "tasks");
    }

    public void saveTaskHistory(Task task) throws SQLException {
        save(task, "tasks_history");
    }

    public List<Task> loadTask() throws SQLException {
        UserService userService = new UserService();
        ProjectService projectService = new ProjectService();

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User assignedUser = userService.findUserByEmail(resultSet.getString("assigned_user_email"));
                Project project = projectService.findProjectByName(resultSet.getString("project_name"));

                if (assignedUser == null || project == null) {
                    continue;
                }

                Task task = new Task(
                        resultSet.getString("task_title"),
                        resultSet.getString("description"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("finish_date").toLocalDate(),
                        StatusTasks.valueOf(resultSet.getString("status")),
                        assignedUser,
                        project
                );

                tasks.add(task);
            }
        }

        return tasks;
    }
}
