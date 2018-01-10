package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.entity.Bet;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BetDAOImpl extends AbstractDBDAO implements BetDAO {
    private static final String SQL_INSERT_BET = "INSERT INTO bet (player_id, event_id, type, date, coefficient, amount, status) VALUES (?, ?, ?, NOW(), ?, ?,'new')";
    
    private static final String SQL_SELECT_BET_BY_PLAYER_ID = "SELECT player_id, event_id, type, date, coefficient, amount, status FROM bet WHERE player_id = ? ORDER BY date DESC";
    
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
    
    @Override
    public List<Bet> readBetListForPlayer(int playerId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_PLAYER_ID)) {
            statement.setInt(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            return buildBetList(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting bet. " + e);
        }
    }
    
    private List<Bet> buildBetList(ResultSet resultSet) throws SQLException {
        List<Bet> betList = new ArrayList<>();
        while (resultSet.next()) {
            Bet bet = new Bet();
            bet.setPlayerId(resultSet.getInt(PLAYER_ID));
            bet.setEventId(resultSet.getInt(EVENT_ID));
            bet.setOutcomeType(resultSet.getString(OUTCOME_TYPE));
            bet.setDate(resultSet.getTimestamp(DATE).toLocalDateTime());
            bet.setCoefficient(resultSet.getBigDecimal(COEFFICIENT));
            bet.setAmount(resultSet.getBigDecimal(AMOUNT));
            bet.setStatus(Bet.Status.valueOf(resultSet.getString(STATUS).toUpperCase()));
            betList.add(bet);
        }
        return betList;
    }
}