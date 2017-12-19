package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.UserDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDAOImpl extends UserDAO {
    private static final String SQL_AUTH = "SELECT id, email, role, registration_date " +
            "FROM user " +
            "WHERE email=? AND password=?";
    
    public UserDAOImpl() {
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