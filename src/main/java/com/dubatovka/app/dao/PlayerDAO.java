package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface PlayerDAO {
    
    String ID = "id";
    String EMAIL = "email";
    String FIRST_NAME = "fname";
    String MIDDLE_NAME = "mname";
    String LAST_NAME = "lname";
    String BIRTHDAY = "birthday";
    String STATUS = "status";
    String BALANCE = "balance";
    String BET_LIMIT = "bet_limit";
    String WITHDRAWAL_LIMIT = "withdrawal_limit";
    String THIS_MONTH_WITHDRAWAL = "month_withdrawal";
    String VERIFICATION_STATUS = "verification_status";
    String PASSPORT = "passport";
    
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
     */
    int insertPlayer(int id, String fName, String mName, String lName, String birthDate) throws DAOException;
    
    List<Player> readAllPlayers() throws DAOException;
    
    Player readPlayerById(int id) throws DAOException;
    
    /**
     * Updates definite {@link Player} balance by adding/subtracting definite value to it.
     *
     * @param id     id of {@link Player} whose data is updating
     * @param amount amount of money to add/subtract to current balance value
     * @param type   type of balance changing
     * @return true if update proceeded successfully
     * @throws DAOException if {@link SQLException} occurred while working with database
     * @see by.sasnouskikh.jcasino.entity.bean.Transaction.TransactionType
     */
    boolean changeBalance(int id, BigDecimal amount, Transaction.TransactionType type) throws DAOException;
}