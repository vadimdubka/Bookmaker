package com.dubatovka.app.service;

import com.dubatovka.app.entity.Player;

import java.util.List;

public interface PlayerService {
    
    List<Player> getAllPlayers();
    
    //TODO добавить чтобы дата рождения тоже записывалась в БД и проверка даты, не младше 18 лет
    boolean registerPlayer(String email, String password, String fName, String mName, String lName);
    
    boolean updateProfileInfo(Player player);
}
