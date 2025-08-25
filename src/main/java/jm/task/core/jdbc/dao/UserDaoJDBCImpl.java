package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users(id TINYINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(100), age TINYINT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Таблица создана успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу!");
        }
    }

    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Таблица удалена успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу!");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь добавлен успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось добавить пользователя!");
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Пользователь с ID=" + id + " удален успешно!");
            } else {
                System.out.println("Пользователь с ID=" + id + " не найден!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя!");
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            System.out.println("Данные получены успешно! Найдено " + userList.size() + " пользователей.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить данные!");
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Таблица очищена успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу!");
        }
    }
}
