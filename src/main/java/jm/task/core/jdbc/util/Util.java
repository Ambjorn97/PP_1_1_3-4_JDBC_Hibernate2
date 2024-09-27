package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASSWORD = "springcourse";
    private Connection connection;
    public Util(){
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("Failed to connect to database");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
