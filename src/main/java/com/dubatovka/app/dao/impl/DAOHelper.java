package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.*;
import com.dubatovka.app.db.ConnectionPool;
import com.dubatovka.app.db.ConnectionPoolException;
import com.dubatovka.app.db.WrappedConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * The class provides manager for DAO layer classes.
 *
 * @see AutoCloseable
 */
public final class DAOHelper implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(DAOHelper.class);
    /**
     * Field used to connect to database and do queries.
     *
     * @see WrappedConnection
     */
    private WrappedConnection connection;
    
    private BetDAO betDAO;
    private CategoryDAO categoryDAO;
    private DocumentDAO documentDAO;
    private EventDAO eventDAO;
    private OutcomeDAO outcomeDAO;
    private PlayerDAO playerDAO;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    private StandardDAO standardDAO; //TODO yдалить по окончанию проекта
    
    /**
     * Constructs DAOHelper object by taking {@link WrappedConnection} object from {@link ConnectionPool} collection.
     *
     * @see ConnectionPool
     */
    public DAOHelper() {
        try {
            connection = ConnectionPool.getInstance().takeConnection();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Database connection error.");
        }
    }
    
    /**
     * Constructs DAOHelper object by assigning 'connection' field definite {@link WrappedConnection} object.
     *
     * @param connection {@link WrappedConnection} to assign to 'connection' field
     */
    public DAOHelper(WrappedConnection connection) {
        this.connection = connection;
    }
    
    public BetDAO getBetDAO() {
        if (betDAO == null) {
            betDAO = new BetDAOImpl(connection);
        }
        return betDAO;
    }
    
    public CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAOImpl(connection);
        }
        return categoryDAO;
    }
    
    public DocumentDAO getDocumentDAO() {
        if (documentDAO == null) {
            documentDAO = new DocumentDAOImpl();
        }
        return documentDAO;
    }
    
    public EventDAO getEventDAO() {
        if (eventDAO == null) {
            eventDAO = new EventDAOImpl(connection);
        }
        return eventDAO;
    }
    
    public OutcomeDAO getOutcomeDAO() {
        if (outcomeDAO == null) {
            outcomeDAO = new OutcomeDAOImpl(connection);
        }
        return outcomeDAO;
    }
    
    public PlayerDAO getPlayerDAO() {
        if (playerDAO == null) {
            playerDAO = new PlayerDAOImpl(connection);
        }
        return playerDAO;
    }
    
    public TransactionDAO getTransactionDAO() {
        if (transactionDAO == null) {
            transactionDAO = new TransactionDAOImpl(connection);
        }
        return transactionDAO;
    }
    
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl(connection);
        }
        return userDAO;
    }
    
    public StandardDAO getStandardDAO() {
        if (standardDAO == null) {
            standardDAO = new StandardDAOImpl(connection);
        }
        return standardDAO;
    }
    
    /**
     * Starts transaction for multiple SQL queries.
     *
     * @throws SQLException if a database access error occurs, setAutoCommit(true) is called while participating in a
     *                      distributed transaction, or this method is called on a closed connection
     * @see WrappedConnection#setAutoCommit(boolean)
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }
    
    /**
     * Commits database changes made during transaction.
     *
     * @throws SQLException if a database access error occurs, this method is called while participating in a
     *                      distributed transaction, if this method is called on a closed connection or this
     *                      <code>Connection</code> object is in auto-commit mode
     * @see WrappedConnection#commit()
     */
    public void commit() throws SQLException {
        connection.commit();
    }
    
    /**
     * Rollbacks database changes made during transaction.
     *
     * @throws SQLException if a database access error occurs, this method is called while participating in a
     *                      distributed transaction, this method is called on a closed connection or this
     *                      <code>Connection</code> object is in auto-commit mode
     * @see WrappedConnection#rollback()
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }
    
    /**
     * Returns {@link #connection} to {@link ConnectionPool}.
     */
    @Override
    public void close() {
        ConnectionPool.getInstance().returnConnection(connection);
        connection = null;
    }
}