package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String query = "create table if not exists users (id int auto_increment primary key, " +
                "name varchar(45), lastname varchar(45), age tinyint)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Failed to create table");
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "drop table if exists users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table dropped successfully");
        } catch (SQLException e) {
            System.out.println("Failed to drop table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "insert into users (name, lastname, age) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("user " + name + " created successfully");
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Failed to save table");
        }

    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String query = "delete from users where id = ?";

        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Row removed successfully");
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Failed to remove row");
        }

    }

    @Override
    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> users = new ArrayList<User>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            System.out.println("Users loaded successfully");
        } catch (SQLException e) {
            System.out.println("Failed to get all users");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "delete from users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table cleaned successfully");
        } catch (SQLException e) {
            System.out.println("Failed to clean table");
        }
    }
}
