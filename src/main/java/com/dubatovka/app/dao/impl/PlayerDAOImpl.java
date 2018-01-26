package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.PlayerDAO;
import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.dao.db.WrappedConnection;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerAccount;
import com.dubatovka.app.entity.PlayerProfile;
import com.dubatovka.app.entity.PlayerStatus;
import com.dubatovka.app.entity.PlayerVerification;
import com.dubatovka.app.entity.Transaction;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class PlayerDAOImpl extends AbstractDBDAO implements PlayerDAO {
    private static final String SQL_INSERT_PLAYER = "INSERT INTO player (id, fname, mname, lname, birthday) " +
            "VALUES (?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_PLAYER_BY_ID = "SELECT fname, mname, lname, birthday, status, balance, bet_limit, withdrawal_limit, " +
            "IFNULL((SELECT ABS(SUM(amount)) FROM transaction WHERE player.id=player_id AND amount < 0 AND MONTH(date)=MONTH(NOW()) AND YEAR(date)=YEAR(NOW())), 0) AS month_withdrawal, verification_status, passport " +
            "FROM player LEFT JOIN player_status ON player.player_status=player_status.status WHERE player.id=?;";
    
    private static final String SQL_SELECT_ALL_PLAYERS = "SELECT fname, mname, lname, birthday, player_status, balance, month_withdrawal, verification_status FROM player ORDER BY fname";
    
    /**
     * Updates definite player balance by adding definite value to it.
     */
    private static final String SQL_UPDATE_ACCOUNT_BALANCE = "UPDATE player " +
            "SET balance=balance+? " +
            "WHERE id=?";
    
    PlayerDAOImpl() {
    }
    
    PlayerDAOImpl(WrappedConnection connection) {
        super(connection);
    }
    
    /**
     * Inserts {@link Player} data into 'player' table on registration.
     *
     * @param id        id of {@link Player} whose data is inserting
     * @param fName     first name of {@link Player} whose data is inserting
     * @param mName     middle name of {@link Player} whose data is inserting
     * @param lName     last name of {@link Player} whose data is inserting
     * @param birthDate birthdate of {@link Player} whose data is inserting
     * @return true if insertion proceeded successfully
     * @throws DAOException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     */
    @Override
    public int insertPlayer(int id, String fName, String mName, String lName, String birthDate) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PLAYER)) {
            statement.setInt(1, id);
            statement.setString(2, fName);
            statement.setString(3, mName);
            statement.setString(4, lName);
            statement.setString(5, birthDate);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Database connection error while inserting player. " + e);
        }
    }
    
    @Override
    public Player readPlayerById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PLAYER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return buildPlayer(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while taking player profile. " + e);
        }
    }
    
    @Override
    public List<Player> readAllPlayers() throws DAOException {
        List<Player> playerList;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PLAYERS);
            playerList = createUserList(resultSet);
        } catch (SQLException e) {
            throw new DAOException("Database connection error while read users. " + e);
        }
        return playerList;
    }
    
    /**
     * Updates definite {@link Player} balance by adding/subtracting definite value to it.
     *
     * @param id     id of {@link Player} whose data is updating
     * @param amount amount of money to add/subtract to current balance value
     * @param type   type of balance changing
     * @return true if update proceeded successfully
     * @throws DAOException if {@link SQLException} occurred while working with database
     * @see Transaction.TransactionType
     * @see PreparedStatement
     */
    @Override
    public boolean updateBalance(int id, BigDecimal amount, Transaction.TransactionType type) throws DAOException {
        if (type == Transaction.TransactionType.WITHDRAW) {
            amount = amount.negate();
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BALANCE)) {
            statement.setBigDecimal(1, amount);
            statement.setInt(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException("Database connection error while changing balance. " + e);
        }
    }
    
    private static List<Player> createUserList(ResultSet resultSet) throws SQLException {
        List<Player> playerList = new ArrayList<>();
        Player player;
        do {
            player = buildPlayer(resultSet);
            if (player != null) {
                playerList.add(player);
            }
        } while (player != null);
        
        return playerList;
    }
    
    private static Player buildPlayer(ResultSet resultSet) throws SQLException {
        Player player = null;
        if (resultSet.next()) {
            player = new Player();
            
            player.setProfile(buildPlayerProfile(resultSet));
            player.setAccount(buildPlayerAccount(resultSet));
            player.setVerification(buildPlayerVerification(resultSet));
        }
        return player;
    }
    
    private static PlayerProfile buildPlayerProfile(ResultSet resultSet) throws SQLException {
        PlayerProfile profile = new PlayerProfile();
        profile.setFirstName(resultSet.getString(FIRST_NAME));
        profile.setMiddleName(resultSet.getString(MIDDLE_NAME));
        profile.setLastName(resultSet.getString(LAST_NAME));
        profile.setBirthDate(resultSet.getDate(BIRTHDAY).toLocalDate());
        return profile;
    }
    
    private static PlayerAccount buildPlayerAccount(ResultSet resultSet) throws SQLException {
        PlayerAccount account = new PlayerAccount();
        account.setBalance(resultSet.getBigDecimal(BALANCE));
        account.setThisMonthWithdrawal(resultSet.getBigDecimal(THIS_MONTH_WITHDRAWAL));
        PlayerStatus status = new PlayerStatus();
        status.setStatus(PlayerStatus.Status.valueOf(resultSet.getString(STATUS).toUpperCase()));
        status.setBetLimit(resultSet.getBigDecimal(BET_LIMIT));
        status.setWithdrawalLimit(resultSet.getBigDecimal(WITHDRAWAL_LIMIT));
        account.setStatus(status);
        return account;
    }
    
    private static PlayerVerification buildPlayerVerification(ResultSet resultSet) throws SQLException {
        PlayerVerification verification = new PlayerVerification();
        verification.setPassport(resultSet.getString(PASSPORT));
        verification.setVerificationStatus(PlayerVerification.VerificationStatus.valueOf(resultSet.getString(VERIFICATION_STATUS).toUpperCase()));
        return verification;
    }
}