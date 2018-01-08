package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.UserDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.db.WrappedConnection;
import com.dubatovka.app.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserDAOImpl extends AbstractDBDAO implements UserDAO {
    private static final String SQL_INSERT_USER = "INSERT INTO user (email, password, role, registration_date) " +
            "VALUES (?, ?, 'player', NOW())";
    
    private static final String SQL_AUTH = "SELECT id, email, role, registration_date " +
            "FROM user " +
            "WHERE email=? AND password=?";
    
    
    UserDAOImpl() {
    }
    
    UserDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public int insertUser(String email, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting user player. " + e);
        }
    }
    
    @Override
    public User authorizeUser(String email, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_AUTH)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while authorizing user. " + e);
        }
    }
    
    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt(ID));
            user.setEmail(resultSet.getString(EMAIL));
            String role = resultSet.getString(ROLE).toUpperCase();
            user.setRole(User.UserRole.valueOf(role));
            LocalDate registrationDate = resultSet.getDate(REGISTRATION_DATE).toLocalDate();
            user.setRegistrationDate(registrationDate);
        }
        return user;
    }
    
}