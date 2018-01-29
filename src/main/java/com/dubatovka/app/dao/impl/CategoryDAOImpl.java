package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

class CategoryDAOImpl extends DBConnectionHolder implements CategoryDAO {
    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT id, name, parent_id " +
            "FROM category " +
            "WHERE id=?";
    
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT id, name, parent_id " +
            "FROM category " +
            "ORDER BY id";
    
    CategoryDAOImpl() {
    }
    
    CategoryDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    @Override
    public Category readCategoryById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Category category = null;
            if (resultSet.next()) {
                category = buildCategory(resultSet);
            }
            return category;
        }catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport category. " + e);
        }
    }
    
    @Override
    public Set<Category> readAllCategories() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            return buildCategorySet(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport categories. " + e);
        }
    }
    
    
    
    private Set<Category> buildCategorySet(ResultSet resultSet) throws SQLException {
        Set<Category> categorySet = new HashSet<>();
        while (resultSet.next()) {
            Category category = buildCategory(resultSet);
            categorySet.add(category);
        }
        return categorySet;
    }
    
    private Category buildCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt(ID));
        category.setName(resultSet.getString(NAME));
        category.setParentId(resultSet.getInt(PARENT_ID));
        return category;
    }
}