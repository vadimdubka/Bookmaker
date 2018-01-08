package com.dubatovka.app.service;

import com.dubatovka.app.entity.Player;

import java.util.List;

public abstract class PlayerService extends AbstractService{
    
    public abstract List<Player> getAllPlayers();
    
    public abstract boolean registerPlayer(String email, String password, String fName, String mName, String lName, String birthDate);
    
    public abstract  void updatePlayerInfo(Player player);
}
