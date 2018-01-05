package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl extends AbstractDBDAO implements PlayerDAO {
    private static final String SQL_SELECT_ALL_PLAYERS = "SELECT * FROM player ORDER BY fname";
    
    private static final String SQL_DEFINE_EMAIL_BY_ID = "SELECT email FROM user WHERE id=?";
    
    private static final String SQL_SELECT_PROFILE = "SELECT fname, mname, lname FROM player WHERE id=?";
    
    private static final String SQL_INSERT_USER = "INSERT INTO user (email, password, role, registration_date) " +
            "VALUES (?, ?, 'player', NOW())";
    
    private static final String SQL_INSERT_PLAYER = "INSERT INTO player (id, fname, mname, lname, birthday) " +
            "VALUES (?, ?, ?, ?, ?)";
    
    
    public PlayerDAOImpl() {
    }
    
    @Override
    public String defineEmailById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DEFINE_EMAIL_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getString(EMAIL) : null;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while defining e-mail by id. " + e);
        }
    }
    
    @Override
    public PlayerProfile takeProfile(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PROFILE)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return buildPlayerProfile(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while taking player profile. " + e);
        }
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
    public boolean insertPlayer(int id, String fName, String mName, String lName, String birthDate) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PLAYER)) {
            statement.setInt(1, id);
            statement.setString(2, fName);
            statement.setString(3, mName);
            statement.setString(4, lName);
            statement.setString(5, birthDate);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting player. " + e);
        }
    }
    
    private PlayerProfile buildPlayerProfile(ResultSet resultSet) throws SQLException {
        PlayerProfile profile = null;
        if (resultSet.next()) {
            profile = new PlayerProfile();
            profile.setFirstName(resultSet.getString(FIRST_NAME));
            profile.setMiddleName(resultSet.getString(MIDDLE_NAME));
            profile.setLastName(resultSet.getString(LAST_NAME));
        }
        return profile;
    }
    
    
    @Override
    public List<Player> readPlayers() throws DAOException {
        List<Player> playerList;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PLAYERS);
            playerList = createUserList(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while read users. " + e);
        }
        return playerList;
    }
    
    private Player createUser(ResultSet resultSet) throws SQLException {
        Player player = null;
        if (resultSet.next()) {
            player = new Player();
            player.setId(resultSet.getInt(ID));
            PlayerProfile playerProfile = new PlayerProfile();
            playerProfile.setFirstName(resultSet.getString(FIRST_NAME));
            playerProfile.setMiddleName(resultSet.getString(MIDDLE_NAME));
            playerProfile.setLastName(resultSet.getString(LAST_NAME));
            player.setProfile(playerProfile);
        }
        return player;
    }
    
    private List<Player> createUserList(ResultSet resultSet) throws SQLException {
        List<Player> playerList = new ArrayList<>();
        Player player;
        do {
            player = createUser(resultSet);
            if (player != null) {
                playerList.add(player);
            }
        } while (player != null);
        
        return playerList;
    }
}