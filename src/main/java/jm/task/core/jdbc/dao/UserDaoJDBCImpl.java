package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "create table if not exists users (id int auto_increment primary key, " +
                "name  varchar(45), lastname varchar(45), age tinyint)";
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Failed to create table");
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists mydb.users";
        try(Statement statement = new Util().getConnection().createStatement()){
            statement.executeUpdate(query);
            System.out.println("Table dropped successfully");
        } catch (SQLException e) {
            System.out.println("Failed to drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users (name, lastname, age) values (?, ?, ?)";
        try(PreparedStatement statement = new Util().getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("user " + name + " created successfully");
        } catch (SQLException e) {
            System.out.println("Failed to save table");
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?";
        try(PreparedStatement statement = new Util().getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Row removed successfully");
        } catch (SQLException e) {
            System.out.println("Failed to remove row");
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> users = new ArrayList<User>();
        try(Statement statement = new Util().getConnection().createStatement()) {
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

    public void cleanUsersTable() {
        String query = "delete from users";
        try(Statement statement = new Util().getConnection().createStatement()){
            statement.executeUpdate(query);
            System.out.println("Table cleaned successfully");
        } catch (SQLException e) {
            System.out.println("Failed to clean table");
        }
    }
}
