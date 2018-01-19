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

public class OutcomeDAOImpl extends AbstractDBDAO implements OutcomeDAO {
    private static final String SQL_SELECT_OUTCOMES_BY_EVENT_ID =
            "SELECT event_id, type, coefficient " +
                    "FROM outcome " +
                    "WHERE event_id = ?";
    private static final String SQL_SELECT_ALL_OUTCOME_TYPES = "SELECT type, description FROM outcome_type";
    
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
            throw new DAOException("Database connection error while getting outcome. " + e);
        }
    }
    
    @Override
    public void insertOutcome(Outcome outcome) throws DAOException {
        sdfsdfsdf
    }
    
    private Set<Outcome> buildOutcomeSet(ResultSet resultSet) throws SQLException {
        Set<Outcome> outcomeSet = new HashSet<>();
        while (resultSet.next()) {
            Outcome outcome = new Outcome();
            outcome.setEventId(resultSet.getInt(EVENT_ID));
            outcome.setCoefficient(resultSet.getBigDecimal(COEFFICIENT));
            outcome.setType(Outcome.Type.valueOf(resultSet.getString(TYPE)));
            outcomeSet.add(outcome);
        }
        return outcomeSet;
    }
}