package com.dubatovka.app.dao;


import com.dubatovka.app.dao.exception.DAOException;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.entity.PlayerProfile;

import java.util.List;

public abstract class PlayerDAO extends AbstractDBDAO {
    
    protected static final String ID = "id";
    protected static final String FIRST_NAME = "fname";
    protected static final String MIDDLE_NAME = "mname";
    protected static final String LAST_NAME = "lname";
    protected static final String EMAIL = "email";
    
    protected PlayerDAO() {
    }
    
    public abstract String defineEmailById(int id) throws DAOException;
    
    public abstract PlayerProfile takeProfile(int id) throws DAOException;
    
    public abstract int insertUserPlayer(String email, String password) throws DAOException;
    
    public abstract boolean insertPlayer(int id, String fName, String mName, String lName) throws DAOException;
    
    public abstract List<Player> readPlayers() throws DAOException;
}