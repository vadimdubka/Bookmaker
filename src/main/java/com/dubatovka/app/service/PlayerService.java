package com.dubatovka.app.service;

import com.dubatovka.app.entity.Player;

import java.util.List;

public interface PlayerService {
    
    List<Player> getAllPlayers();
    
    boolean registerPlayer(String email, String password, String fName, String mName, String lName, String birthDate);
    
    boolean updateProfileInfo(Player player);
}
