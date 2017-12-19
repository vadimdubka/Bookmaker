package com.dubatovka.app.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Create database connection
 */

public class DBConnection {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    
    private static final String DB_PROPERTIES = "database";
    
    /**
     * Database property-file keys.
     */
    private static final String DB_DRIVER = "driver";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";
    private static final String DB_URL = "url";
    
    private static Connection connection;
    
    private DBConnection() {
    }
    
    public static Connection getConnection() {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES);
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, "Invalid resource path to database *.properties file");
            throw new RuntimeException();
        }
        String driver = resourceBundle.getString(DB_DRIVER);
        String user = resourceBundle.getString(DB_USER);
        String password = resourceBundle.getString(DB_PASSWORD);
        String url = resourceBundle.getString(DB_URL);
        
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, e + " Driver wasn't found.");
            //TODO может сделать не фатал, а обработать ошибку для польователя
            throw new RuntimeException(e);
        } catch (SQLException e) {
            //TODO может сделать не фатал, а обработать ошибку для польователя
            logger.log(Level.FATAL, e + " Connection initializing was interrupted.");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
