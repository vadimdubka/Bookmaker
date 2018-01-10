package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.StandardDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

//TODO удалить по завершению проекта
public class StandardDAOImpl extends AbstractDBDAO implements StandardDAO {
    private static final String SQL_SELECT_X = "SELECT X";
    
    StandardDAOImpl() {
    }
    
    StandardDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public Set<Object> mockMethod() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_X)) {
            ResultSet resultSet = statement.executeQuery();
            return buildX(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport categories. " + e);
        }
    }
    
    private Set<Object> buildX(ResultSet resultSet) throws SQLException {
        Set<Object> xSet = new HashSet<>();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt(ID));
            xSet.add(category);
        }
        return xSet;
    }
}