package com.dubatovka.app.dao;

import java.sql.Connection;

public abstract class AbstractDBDAO {
    protected Connection connection;
    
    protected AbstractDBDAO() {
        connection = DBConnection.getConnection();
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}