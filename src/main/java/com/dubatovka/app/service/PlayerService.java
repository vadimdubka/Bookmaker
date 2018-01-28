package com.dubatovka.app.service;

import com.dubatovka.app.dao.impl.DAOProvider;
import com.dubatovka.app.entity.Player;

import java.util.List;

public abstract class PlayerService extends AbstractService {
    protected PlayerService() {
    }
    
    protected PlayerService(DAOProvider daoProvider) {
        super(daoProvider);
    }
    
    public abstract List<Player> getAllPlayers();
    
    //TODO возвращать игрока а не булен
    public abstract boolean registerPlayer(String email, String password, String fName, String mName, String lName, String birthDate);
    
    public abstract void updatePlayerInfo(Player player);
    
    
}
