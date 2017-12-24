package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_PLAYERS;

public class GotoManagePlayersCommand implements Command {
    private static final ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PlayerService playerService = serviceFactoryInstance.getPlayerService();
        
        List<Player> players = playerService.getAllPlayers();
        request.setAttribute(ATTR_PLAYERS, players);
        
        return PageNavigator.FORWARD_PAGE_MANAGE_PLAYER;
    }
}
