package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class EventDAOImpl extends AbstractDBDAO implements EventDAO {
    private static final String SQL_SELECT_EVENT_BY_EVENT_ID =
            "SELECT id, date, category_id, participant1, participant2, result1, result2 " +
                    "FROM event WHERE id =?";
    
    private static final String SQL_SELECT_NEW_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND id NOT IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) > 0 " +
                    "AND result1 IS NULL";
    
    private static final String SQL_SELECT_ACTUAL_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND id IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) > 0 " +
                    "AND result1 IS NULL";
    
    private static final String SQL_SELECT_NOT_STARTED_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND (date - NOW()) > 0 " +
                    "AND result1 IS NULL";
    
    private static final String SQL_SELECT_STARTED_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND id IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) <= 0 " +
                    "AND result1 IS NULL";
    
    private static final String SQL_SELECT_FAILED_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND id NOT IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) <= 0";
    
    private static final String SQL_SELECT_CLOSED_EVENTS_BY_CATEGORY_ID =
            "SELECT id, category_id, date, participant1, participant2, result1, result2 " +
                    "FROM event WHERE category_id =? " +
                    "AND result1 IS NOT NULL";
    
    private static final String SQL_COUNT_NEW_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE id NOT IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) > 0 " +
                    "AND result1 IS NULL " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_ACTUAL_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE id IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) > 0 " +
                    "AND result1 IS NULL " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_NOT_STARTED_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE (date - NOW()) > 0 " +
                    "AND result1 IS NULL " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_STARTED_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE id IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) <= 0 " +
                    "AND result1 IS NULL " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_FAILED_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE id NOT IN (SELECT event_id FROM outcome GROUP BY event_id) " +
                    "AND (date - NOW()) <= 0 " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_CLOSED_EVENTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) AS count FROM event " +
                    "WHERE result1 IS NOT NULL " +
                    "GROUP BY category_id";
    
    private static final String SQL_COUNT_EVENTS_WITHOUT_RESULTS_GROUP_BY_CATEGORY_ID =
            "SELECT category_id, COUNT(category_id) FROM event WHERE result1 IS NULL GROUP BY category_id";
    
    EventDAOImpl() {
    }
    
    EventDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public Event getEvent(int eventId) throws DAOException {
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
    public Set<Event> readEvents(String categoryId, String eventQueryType) throws DAOException {
        Set<Event> eventSet;
        switch (eventQueryType) {
            case EVENT_QUERY_TYPE_NEW:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_NEW_EVENTS_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_ACTUAL:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_ACTUAL_EVENTS_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_NOT_STARTED:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_NOT_STARTED_EVENTS_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_STARTED:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_STARTED_EVENTS_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_FAILED:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_FAILED_EVENTS_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_CLOSED:
                eventSet = readEventsByQuery(categoryId, SQL_SELECT_CLOSED_EVENTS_BY_CATEGORY_ID);
                break;
            default:
                eventSet = new HashSet<>();
        }
        return eventSet;
    }
    
    @Override
    public Map<Integer, Integer> countEvents(String eventQueryType) throws DAOException {
        Map<Integer, Integer> eventCountMap;
        switch (eventQueryType) {
            case EVENT_QUERY_TYPE_NEW:
                eventCountMap = countEventsByQuery(SQL_COUNT_NEW_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_ACTUAL:
                eventCountMap = countEventsByQuery(SQL_COUNT_ACTUAL_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_NOT_STARTED:
                eventCountMap = countEventsByQuery(SQL_COUNT_NOT_STARTED_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_STARTED:
                eventCountMap = countEventsByQuery(SQL_COUNT_STARTED_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_FAILED:
                eventCountMap = countEventsByQuery(SQL_COUNT_FAILED_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            case EVENT_QUERY_TYPE_CLOSED:
                eventCountMap = countEventsByQuery(SQL_COUNT_CLOSED_EVENTS_GROUP_BY_CATEGORY_ID);
                break;
            default:
                eventCountMap = new HashMap<>(0);
        }
        return eventCountMap;
    }
    
    private Set<Event> readEventsByQuery(String categoryId, String sqlQuery) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            return buildEventSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport event. " + e);
        }
    }
    
    private Map<Integer, Integer> countEventsByQuery(String sqlQuery) throws DAOException {
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            Map<Integer, Integer> eventCountMap = new HashMap<>();
            while (resultSet.next()) {
                int categoryId = resultSet.getInt(CATEGORY_ID);
                int eventCount = resultSet.getInt(COUNT);
                eventCountMap.put(categoryId, eventCount);
            }
            return eventCountMap;
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