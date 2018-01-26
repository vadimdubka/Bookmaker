package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.OutcomeDAO;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Outcome;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.PREFIX_FOR_OUTCOME_TYPE;

class OutcomeDAOImpl extends AbstractDBDAO implements OutcomeDAO {
    private static final String SQL_SELECT_OUTCOMES_BY_EVENT_ID =
            "SELECT event_id, type, coefficient " +
                    "FROM outcome " +
                    "WHERE event_id = ?";
    
    private static final String SQL_INSERT_OUTCOME = "INSERT INTO outcome (event_id, type, coefficient) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE coefficient = ?";
    
    
    OutcomeDAOImpl() {
    }
    
    OutcomeDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public Set<Outcome> getOutcomesByEventId(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OUTCOMES_BY_EVENT_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return buildOutcomeSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while reading outcome. " + e);
        }
    }
    
    @Override
    public boolean insertOutcome(Outcome outcome) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_OUTCOME)) {
            statement.setInt(1, outcome.getEventId());
            statement.setString(2, outcome.getType().getType());
            statement.setBigDecimal(3, outcome.getCoefficient());
            statement.setBigDecimal(4, outcome.getCoefficient());
            int rowUpd = statement.executeUpdate();
            return rowUpd > 0;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting outcome. " + e);
        }
    }
    
    private Set<Outcome> buildOutcomeSet(ResultSet resultSet) throws SQLException {
        Set<Outcome> outcomeSet = new HashSet<>();
        while (resultSet.next()) {
            Outcome outcome = new Outcome();
            outcome.setEventId(resultSet.getInt(EVENT_ID));
            outcome.setCoefficient(resultSet.getBigDecimal(COEFFICIENT));
            outcome.setType(Outcome.Type.valueOf(PREFIX_FOR_OUTCOME_TYPE +resultSet.getString(TYPE)));
            outcomeSet.add(outcome);
        }
        return outcomeSet;
    }
}