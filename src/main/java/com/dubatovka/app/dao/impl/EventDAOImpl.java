package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.entity.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EventDAOImpl extends AbstractDBDAO implements EventDAO {
    private static final String SQL_SELECT_EVENT_BY_EVENT_ID =
            "SELECT id, date, category_id, participant1, participant2, result1, result2 " +
                    "FROM event WHERE id =?";
    
    private static final String SQL_SELECT_ALL_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event " +
                    "WHERE category_id =?";
    private static final String SQL_SELECT_ACTUAL_EVENTS_CATEGORY_BY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event " +
                    "WHERE category_id =? AND (date - NOW()) > 0";
    
    EventDAOImpl() {
    }
    
    EventDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public Event getEventById(int eventId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EVENT_BY_EVENT_ID)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            Event event = null;
            if (resultSet.next()) {
                event = buildEvent(resultSet);
            }
            return event;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport event. " + e);
        }
    }
    
    @Override
    public Set<Event> getAllEventsByCategoryId(String categoryId) throws DAOException {
        Set<Event> eventSet = getEventsByCategoryId(categoryId, SQL_SELECT_ALL_EVENTS_BY_CATEGORY_ID);
        return eventSet;
    }
    
    @Override
    public Set<Event> getActualEventsByCategoryId(String categoryId) throws DAOException {
        Set<Event> eventSet = getEventsByCategoryId(categoryId, SQL_SELECT_ACTUAL_EVENTS_CATEGORY_BY_ID);
        return eventSet;
    }
    
    private Set<Event> getEventsByCategoryId(String categoryId, String sqlQuery) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            return buildEventSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport event. " + e);
        }
    }
    
    private Set<Event> buildEventSet(ResultSet resultSet) throws SQLException {
        Set<Event> eventSet = new HashSet<>();
        while (resultSet.next()) {
            Event event = buildEvent(resultSet);
            eventSet.add(event);
        }
        return eventSet;
    }
    
    private Event buildEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt(ID));
        event.setCategoryId(resultSet.getInt(CATEGORY_ID));
        event.setDate(resultSet.getTimestamp(DATE).toLocalDateTime());
        event.setParticipant1(resultSet.getString(PARTICIPANT1));
        event.setParticipant2(resultSet.getString(PARTICIPANT2));
        event.setResult1(resultSet.getString(RESULT1));
        event.setResult2(resultSet.getString(RESULT2));
        return event;
    }
}