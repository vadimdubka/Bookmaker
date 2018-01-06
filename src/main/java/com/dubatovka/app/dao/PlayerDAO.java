package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;

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
    
    int insertPlayer(int id, String fName, String mName, String lName, String birthDate) throws DAOException;
    
    List<Player> readAllPlayers() throws DAOException;
    
    Player readPlayerById(int id) throws DAOException;
}