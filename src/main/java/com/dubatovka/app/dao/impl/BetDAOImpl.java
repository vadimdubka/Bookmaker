package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Outcome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDAOImpl extends AbstractDBDAO implements BetDAO {
    private static final String SQL_INSERT_BET = "INSERT INTO bet (player_id, event_id, type, date, coefficient, amount, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE_BET_STATUS = "UPDATE bet SET status=? WHERE event_id=? AND type=? AND status is NOT ?";
    
    private static final String SQL_SELECT_BET_BY_PLAYER_ID = "SELECT player_id, event_id, type, date, coefficient, amount, status FROM bet WHERE player_id = ? ORDER BY date DESC";
    
    BetDAOImpl() {
    }
    
    BetDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public boolean insertBet(Bet bet) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_BET)) {
            statement.setInt(1, bet.getPlayerId());
            statement.setInt(2, bet.getEventId());
            statement.setString(3, bet.getOutcomeType());
            statement.setTimestamp(4, Timestamp.valueOf(bet.getDate()));
            statement.setBigDecimal(5, bet.getCoefficient());
            statement.setBigDecimal(6, bet.getAmount());
            statement.setString(7, bet.getStatus().getStatus());
            int rowUpd = statement.executeUpdate();
            return rowUpd == 1;
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
            throw new DAOException("Database connection error while reading bet. " + e);
        }
    }
    
    @Override
    public void updateBetStatus(int eventId, Outcome.Type type, Bet.Status status) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BET_STATUS)) {
            statement.setString(1, status.getStatus());
            statement.setInt(2, eventId);
            statement.setString(3, type.getType());
            statement.setString(4, Bet.Status.PAID.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting bet. " + e);
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