package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.BetDAO;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Outcome;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import static com.dubatovka.app.manager.ConfigConstant.WIN_BET_INFO_KEY_COUNT;
import static com.dubatovka.app.manager.ConfigConstant.WIN_BET_INFO_KEY_SUM;

public class BetDAOImpl extends AbstractDBDAO implements BetDAO {
    private static final String SQL_INSERT_BET = "INSERT INTO bet (player_id, event_id, type, date, coefficient, amount, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE_BET_STATUS_BY_TYPE = "UPDATE bet SET status=? WHERE event_id=? AND type=? AND status <>'paid'";
    
    private static final String SQL_UPDATE_BET_STATUS_BY_STATUS = "UPDATE bet SET status=? WHERE event_id=? AND status= ?";
    
    private static final String SQL_SELECT_BET_BY_EVENT_ID_AND_STATUS = "SELECT player_id, event_id, type, date, coefficient, amount, status FROM bet WHERE event_id = ? and status = ?";
    
    private static final String SQL_SELECT_BET_BY_PLAYER_ID = "SELECT player_id, event_id, type, date, coefficient, amount, status FROM bet WHERE player_id = ? ORDER BY date DESC";
    
    private static final String SQL_COUNT_BET_BY_PLAYER_ID = "SELECT COUNT(player_id) AS count FROM bet WHERE player_id = ?";
    
    private static final String SQL_SELECT_BET_BY_PLAYER_ID_LIMIT = "SELECT player_id, event_id, type, date, coefficient, amount, status FROM bet WHERE player_id = ? ORDER BY date DESC LIMIT ? OFFSET ?";
    
    //TODO сделать общий запрос по типу статуса, а не конкретный
    private static final String SQL_SELECT_WIN_BET_INFO_GROUP_BY_EVENT_ID =
            "SELECT event_id, COUNT(event_id) AS count, SUM(amount) AS sum FROM bet WHERE status='win' and event_id IN (SELECT id FROM event WHERE category_id=?) GROUP BY event_id;";
    
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
    public List<Bet> readBetListForPlayer(int playerId, int limit, int offset) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_PLAYER_ID_LIMIT)) {
            statement.setInt(1, playerId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            return buildBetList(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while reading bet. " + e);
        }
    }
    
    @Override
    public Set<Bet> readBetSetForEventAndStatus(int eventId, Bet.Status status) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_EVENT_ID_AND_STATUS)) {
            statement.setInt(1, eventId);
            statement.setString(2, status.getStatus());
            ResultSet resultSet = statement.executeQuery();
            return buildBetSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while reading bet. " + e);
        }
    }
    
    @Override
    public int countBetForPlayer(int playerId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT_BET_BY_PLAYER_ID);) {
            statement.setInt(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(COUNT);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while count bet. " + e);
        }
    }
    
    @Override
    public void updateBetStatus(int eventId, Outcome.Type type, Bet.Status status) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BET_STATUS_BY_TYPE)) {
            statement.setString(1, status.getStatus());
            statement.setInt(2, eventId);
            statement.setString(3, type.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database connection error while updating bet. " + e);
        }
    }
    
    @Override
    public int updateBetStatus(int eventId, Bet.Status oldStatus, Bet.Status newStatus) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BET_STATUS_BY_STATUS)) {
            statement.setString(1, newStatus.getStatus());
            statement.setInt(2, eventId);
            statement.setString(3, oldStatus.getStatus());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database connection error while updating bet. " + e);
        }
    }
    
    @Override
    public Map<String, Map<String, String>> readWinBetInfoMap(int categoryId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_WIN_BET_INFO_GROUP_BY_EVENT_ID)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            
            Map<String, String> winBetCount = new HashMap<>();
            Map<String, String> winBetSum = new HashMap<>();
            
            while (resultSet.next()) {
                int eventId = resultSet.getInt(EVENT_ID);
                int count = resultSet.getInt(COUNT);
                BigDecimal sum = resultSet.getBigDecimal(SUM);
                winBetCount.put(String.valueOf(eventId), String.valueOf(count));
                winBetSum.put(String.valueOf(eventId), String.valueOf(sum));
            }
            Map<String, Map<String, String>> winBetInfoMap = new HashMap<>();
            winBetInfoMap.put(WIN_BET_INFO_KEY_COUNT, winBetCount);
            winBetInfoMap.put(WIN_BET_INFO_KEY_SUM, winBetSum);
            return winBetInfoMap;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while reading bet. " + e);
        }
    }
    
    private List<Bet> buildBetList(ResultSet resultSet) throws SQLException {
        List<Bet> betList = new ArrayList<>();
        while (resultSet.next()) {
            Bet bet = buildBet(resultSet);
            betList.add(bet);
        }
        return betList;
    }
    
    private Set<Bet> buildBetSet(ResultSet resultSet) throws SQLException {
        Set<Bet> betSet = new HashSet<>();
        while (resultSet.next()) {
            Bet bet = buildBet(resultSet);
            betSet.add(bet);
        }
        return betSet;
    }
    
    private Bet buildBet(ResultSet resultSet) throws SQLException {
        Bet bet = new Bet();
        bet.setPlayerId(resultSet.getInt(PLAYER_ID));
        bet.setEventId(resultSet.getInt(EVENT_ID));
        bet.setOutcomeType(resultSet.getString(OUTCOME_TYPE));
        bet.setDate(resultSet.getTimestamp(DATE).toLocalDateTime());
        bet.setCoefficient(resultSet.getBigDecimal(COEFFICIENT));
        bet.setAmount(resultSet.getBigDecimal(AMOUNT));
        bet.setStatus(Bet.Status.valueOf(resultSet.getString(STATUS).toUpperCase()));
        return bet;
    }
}