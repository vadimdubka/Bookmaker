package com.dubatovka.app.controller.command_impl.navigation;

import com.dubatovka.app.controller.Command;
import com.dubatovka.app.controller.PageNavigator;
import com.dubatovka.app.entity.Bet;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.entity.Player;
import com.dubatovka.app.manager.MessageManager;
import com.dubatovka.app.service.BetService;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.EventService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoPlayerStateCommand implements Command {
    public static final String MESSAGE_ERROR_PLAYER_NOT_DEFINED = "Невозможно перейти на страницу, т.к. игрок не определен.";
    public static final String ATTR_BET_LIST = "bet_list";
    public static final String ATTR_BET_INFO_MAP = "bet_info_map";
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        
        Player player = (Player) session.getAttribute(PLAYER);
        
        validateParams(player, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (BetService betService = ServiceFactory.getBetService(); EventService eventService = ServiceFactory.getEventService(); CategoryService categoryService = ServiceFactory.getCategoryService()) {
                List<Bet> betList = betService.getBetList(player.getId());
                Map<Bet, Map<String, Object>> betInfoMap = new LinkedHashMap<>(betList.size());
                betList.forEach(bet -> {
                    Event event = eventService.getEventById(bet.getEventId());
                    Category category = categoryService.getCategoryById(event.getCategoryId());
                    Category parentCategory = categoryService.getCategoryById(category.getParentId());
                    Map<String, Object> betInfo = new HashMap<>();
                    betInfo.put("event", event);
                    betInfo.put("category", category);
                    betInfo.put("sportCategory", parentCategory);
                    betInfoMap.put(bet, betInfo);
                });
                request.setAttribute(ATTR_BET_LIST, betList);
                request.setAttribute(ATTR_BET_INFO_MAP, betInfoMap);
                navigator = PageNavigator.FORWARD_PAGE_PLAYER_STATE;
            }
        } else {
            request.setAttribute(ATTR_ERROR_MESSAGE, errorMessage.toString().trim());
            navigator = PageNavigator.FORWARD_PREV_QUERY;
        }
        return navigator;
    }
    
    private void validateParams(Player player, StringBuilder errorMessage) {
        if (player == null) {
            errorMessage.append(MESSAGE_ERROR_PLAYER_NOT_DEFINED);
        }
    }
}
