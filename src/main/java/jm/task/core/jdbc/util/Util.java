package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL = "jdbc:mysql://localhost:3306/mydb";
    private final String USER = "root";
    private final String PASSWORD = "springcourse";
    private Connection connection;
    private SessionFactory sessionFactory;

    public Util() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        }
        try {
            Configuration configuration = getConfiguration();
            sessionFactory = configuration.buildSessionFactory();

        } catch (HibernateException e) {
            System.out.println("Failed to create session factory");
        }


    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("hibernate.connection.username", USER);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public Connection getConnection() {
        return connection;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
