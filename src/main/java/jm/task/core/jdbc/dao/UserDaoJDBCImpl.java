package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection con = Util.getConnection();
    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Statement statement = con.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = con.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users WHERE id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM users")){
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        try (Statement statement = con.createStatement()){
            statement.executeUpdate("Truncate table users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
