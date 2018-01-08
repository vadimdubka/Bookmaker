package com.dubatovka.app.controller.command_impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_PLAYERS;

public class GotoManagePlayersCommand implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        List<Player> players;
        try (PlayerService playerService = ServiceFactory.getPlayerService()) {
            players = playerService.getAllPlayers();
        }
        request.setAttribute(ATTR_PLAYERS, players);
        
        return PageNavigator.FORWARD_PAGE_MANAGE_PLAYER;
    }
}
