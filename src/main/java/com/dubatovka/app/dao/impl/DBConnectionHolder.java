package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.db.ConnectionPool;
import com.dubatovka.app.dao.db.ConnectionPoolException;
import com.dubatovka.app.dao.db.WrappedConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class DBConnectionHolder {
    private static final Logger logger = LogManager.getLogger(DBConnectionHolder.class);
    
    /**
     * Field used to connect to database and do queries.
     *
     * @see WrappedConnection
     */
    protected WrappedConnection connection;
    
    /**
     * Constructs DAO object by taking {@link WrappedConnection} object from {@link ConnectionPool}
     * collection.
     */
    protected DBConnectionHolder() {
        try {
            connection = ConnectionPool.getInstance().takeConnection();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Database connection error.");
        }
    }
    
    /**
     * Constructs DAO object by assigning {@link DBConnectionHolder#connection} field definite
     * {@link WrappedConnection} object.
     *
     * @param connection {@link WrappedConnection} to assign to {@link DBConnectionHolder#connection}
     *                   field
     */
    protected DBConnectionHolder(WrappedConnection connection) {
        this.connection = connection;
    }
    
    /**
     * {@link DBConnectionHolder#connection} field getter.
     *
     * @return {@link #connection}
     */
    protected WrappedConnection getConnection() {
        return connection;
    }
    
    /**
     * {@link DBConnectionHolder#connection} field setter.
     *
     * @param connection {@link WrappedConnection} to assign to {@link DBConnectionHolder#connection}
     *                   field
     */
    protected void setConnection(WrappedConnection connection) {
        this.connection = connection;
    }
}