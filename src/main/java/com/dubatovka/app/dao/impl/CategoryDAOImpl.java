package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.CategoryDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl extends AbstractDBDAO implements CategoryDAO {
    private static final String SQL_SELECT_SPORT_CATEGORIES = "SELECT id, name, parent_id " +
            "FROM category " +
            "WHERE parent_id IS NULL " +
            "ORDER BY id";
    
    @Override
    public List<Category> getSportCategories() throws DAOException {
        
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SPORT_CATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            return buildCategory(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while getting sport categories. " + e);
        }
    }
    
    private List<Category> buildCategory(ResultSet resultSet) throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt(ID));
            category.setName(resultSet.getString(NAME));
            category.setParentId(resultSet.getInt(PARENT_ID));
            categoryList.add(category);
        }
        return categoryList;
    }
    
    
    
    
}
