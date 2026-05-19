package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import enums.ProfileType;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    
    private void save(User user, String tableName) throws SQLException {
        String sql = """
            INSERT INTO %s (
                full_name,
                cpf,
                email,
                cargo,
                login,
                password,
                profile_name,
                profile_type
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """.formatted(tableName);

        try(
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getCpf());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getCargo());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getProfileName());
            statement.setString(8, user.getProfileType().name());

            statement.executeUpdate();
        }

    }

    public void saveUser(User user) throws SQLException {
        save(user, "users");
    }

    public void saveUserHistory(User user) throws SQLException {
        save(user, "users_history");
    }

    public void updateUserPassword(User user) throws SQLException {
        String sql = """
                UPDATE users
                SET password = ?
                WHERE email = ?
                """;

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, user.getPassword());
            statement.setString(2, user.getEmail());

            statement.executeUpdate();
        }
    }

    public List<User> loadUsers() throws SQLException{

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("cpf"),
                        resultSet.getString("email"),
                        resultSet.getString("cargo"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("profile_name"),
                        ProfileType.valueOf(resultSet.getString("profile_type"))
                        );
                users.add(user);
            }
        }
        return users;
    }

}

