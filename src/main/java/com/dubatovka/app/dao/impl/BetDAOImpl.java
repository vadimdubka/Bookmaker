package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.db.WrappedConnection;
import com.dubatovka.app.entity.Category;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BetDAOImpl extends AbstractDBDAO implements BetDAO {
    private static final String SQL_INSERT_BET = "INSERT INTO bet (player_id, event_id, type, date, coefficient, amount, status) VALUES (?, ?, ?, NOW(), ?, ?,'new')";
    
    BetDAOImpl() {
    }
    
    BetDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public void insertBet(int playerId, int eventId, String outcomeType, BigDecimal coefficient, BigDecimal betAmount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_BET)) {
            statement.setInt(1, playerId);
            statement.setInt(2, eventId);
            statement.setString(3, outcomeType);
            statement.setBigDecimal(4, coefficient);
            statement.setBigDecimal(5, betAmount);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting bet. " + e);
        }
    }
    
    private Set<Object> buildX(ResultSet resultSet) throws SQLException {
        Set<Object> xSet = new HashSet<>();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt(1));
            xSet.add(category);
        }
        return xSet;
    }
}