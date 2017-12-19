package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.service.PlayerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_PLAYERS;

public class GotoManagePlayersCommand implements Command {
    private static final PlayerService PLAYER_SERVICE = new PlayerService();
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        
        List<Player> players = PLAYER_SERVICE.getAllPlayers();
        request.setAttribute(ATTR_PLAYERS, players);
        
        return PageNavigator.FORWARD_PAGE_MANAGE_PLAYER;
    }
}
