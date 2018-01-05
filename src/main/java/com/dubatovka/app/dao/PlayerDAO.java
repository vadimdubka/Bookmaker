package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerProfile;

import java.util.List;

public interface PlayerDAO {
    
    String ID = "id";
    String EMAIL = "email";
    String FIRST_NAME = "fname";
    String MIDDLE_NAME = "mname";
    String LAST_NAME = "lname";
    
    
    String defineEmailById(int id) throws DAOException;
    
    PlayerProfile takeProfile(int id) throws DAOException;
    
    int insertUser(String email, String password) throws DAOException;
    
    boolean insertPlayer(int id, String fName, String mName, String lName, String birthDate) throws DAOException;
    
    List<Player> readPlayers() throws DAOException;
}