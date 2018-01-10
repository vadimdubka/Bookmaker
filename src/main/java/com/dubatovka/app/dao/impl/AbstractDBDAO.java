package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.db.ConnectionPool;
import com.dubatovka.app.dao.db.ConnectionPoolException;
import com.dubatovka.app.dao.db.WrappedConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractDBDAO {
    private static final Logger logger = LogManager.getLogger(AbstractDBDAO.class);
    
    /**
     * Field used to connect to database and do queries.
     *
     * @see WrappedConnection
     */
    protected WrappedConnection connection;
    
    /**
     * Constructs DAO object by taking {@link WrappedConnection} object from {@link ConnectionPool} collection.
     */
    protected AbstractDBDAO() {
        try {
            connection = ConnectionPool.getInstance().takeConnection();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Database connection error.");
        }
    }
    
    /**
     * Constructs DAO object by assigning {@link AbstractDAO#connection} field definite
     * {@link WrappedConnection} object.
     *
     * @param connection {@link WrappedConnection} to assign to {@link AbstractDAO#connection} field
     */
    protected AbstractDBDAO(WrappedConnection connection) {
        this.connection = connection;
    }
    
    /**
     * {@link AbstractDAO#connection} field getter.
     *
     * @return {@link #connection}
     */
    protected WrappedConnection getConnection() {
        return connection;
    }
    
    /**
     * {@link AbstractDAO#connection} field setter.
     *
     * @param connection {@link WrappedConnection} to assign to {@link AbstractDAO#connection} field
     */
    protected void setConnection(WrappedConnection connection) {
        this.connection = connection;
    }
}