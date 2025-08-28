package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static SessionFactory sessionFactory;

    // Метод для JDBC соединения
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("JDBC Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("JDBC Connection ERROR");
        }
        return connection;
    }

    // Метод для получения SessionFactory Hibernate
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Настройки Hibernate
                configuration.setProperty("hibernate.connection.driver_class", DB_DRIVER);
                configuration.setProperty("hibernate.connection.url", DB_URL);
                configuration.setProperty("hibernate.connection.username", DB_USER);
                configuration.setProperty("hibernate.connection.password", DB_PASSWORD);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.current_session_context_class", "thread");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop"); // Изменено для тестов
                // Добавляем аннотированный класс
                configuration.addAnnotatedClass(User.class);

                sessionFactory = (SessionFactory) configuration.buildSessionFactory();
                System.out.println("Hibernate SessionFactory created successfully");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Hibernate SessionFactory creation failed");
            }
        }
        return sessionFactory;
    }

    // Метод для получения новой сессии
    public static Session openSession() {
        return getSessionFactory().openSession();
    }

    // Метод для закрытия SessionFactory
    public static void shutdown() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Session shutdown failed");
        }
    }
}
