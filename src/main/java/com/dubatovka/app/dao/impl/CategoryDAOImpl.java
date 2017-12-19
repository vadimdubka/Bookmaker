package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CategoryDAOImpl extends AbstractDBDAO implements CategoryDAO {
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT id, name, parent_id " +
            "FROM category " +
            "ORDER BY id";
    
    @Override
    public Set<Category> getAllCategories() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            return buildCategory(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport categories. " + e);
        }
    }
    
    private Set<Category> buildCategory(ResultSet resultSet) throws SQLException {
        Set<Category> categorySet = new HashSet<>();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt(ID));
            category.setName(resultSet.getString(NAME));
            category.setParentId(resultSet.getInt(PARENT_ID));
            categorySet.add(category);
        }
        return categorySet;
    }
}