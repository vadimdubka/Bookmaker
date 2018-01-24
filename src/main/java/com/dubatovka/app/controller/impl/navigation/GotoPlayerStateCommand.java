package com.dubatovka.app.controller.impl.navigation;

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
import com.dubatovka.app.service.PaginationService;
import com.dubatovka.app.service.PlayerService;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dubatovka.app.manager.ConfigConstant.*;

public class GotoPlayerStateCommand implements Command {
    private static final int PAGE_LIMIT = 5;
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageNavigator navigator = PageNavigator.FORWARD_PREV_QUERY;
        HttpSession session = request.getSession();
        
        String locale = (String) session.getAttribute(ATTR_LOCALE);
        MessageManager messageManager = MessageManager.getMessageManager(locale);
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder infoMessage = new StringBuilder();
        
        Player player = (Player) session.getAttribute(PLAYER);
        String pageNumberStr = request.getParameter(PARAM_PAGE_NUMBER);
        
        validateCommand(player, errorMessage);
        if (errorMessage.toString().trim().isEmpty()) {
            try (BetService betService = ServiceFactory.getBetService(); CategoryService categoryService = ServiceFactory.getCategoryService(); EventService eventService = ServiceFactory.getEventService(); PlayerService playerService = ServiceFactory.getPlayerService()) {
                ValidatorService validatorService = ServiceFactory.getValidatorService();
                int pageNumber = validatorService.isValidId(pageNumberStr) ? Integer.parseInt(pageNumberStr) : 1;
                int totalEntityAmount = betService.countBetsForPlayer(player.getId());
                PaginationService paginationService = getPaginationService(pageNumber, totalEntityAmount);
                int limit = paginationService.getLimitOnPage();
                int offset = paginationService.getOffsetForPage(pageNumber);
                List<Bet> betList = betService.getBetListForPlayer(player.getId(), limit, offset);
                Map<Bet, Event> eventMap = new HashMap<>(betList.size());
                Map<Bet, Category> categoryMap = new HashMap<>(betList.size());
                Map<Bet, Category> sportMap = new HashMap<>(betList.size());
                betList.forEach(bet -> {
                    Event event = eventService.getEvent(bet.getEventId());
                    Category category = categoryService.getCategoryById(event.getCategoryId());
                    Category parentCategory = categoryService.getCategoryById(category.getParentId());
                    eventMap.put(bet, event);
                    categoryMap.put(bet, category);
                    sportMap.put(bet, parentCategory);
                });
                playerService.updatePlayerInfo(player);
                session.setAttribute(ATTR_PLAYER, player);
                request.setAttribute(ATTR_BET_LIST, betList);
                request.setAttribute(ATTR_EVENT_MAP, eventMap);
                request.setAttribute(ATTR_CATEGORY_MAP, categoryMap);
                request.setAttribute(ATTR_SPORT_MAP, sportMap);
                request.setAttribute(ATTR_PAGINATION, paginationService);
                navigator = PageNavigator.FORWARD_PAGE_PLAYER_STATE;
            }
        }
        
        setErrorMessagesToRequest(errorMessage, request);
        setInfoMessagesToRequest(infoMessage, request);
        return navigator;
    }
    
    private PaginationService getPaginationService(int pageNumber, int totalEntityAmount) {
        PaginationService paginationService = ServiceFactory.getPaginationService();
        paginationService.buildService(totalEntityAmount, PAGE_LIMIT, pageNumber);
        return paginationService;
    }
    
    private void validateCommand(Player player, StringBuilder errorMessage) {
        if (errorMessage.toString().trim().isEmpty()) {
            if (player == null) {
                errorMessage.append(MESSAGE_ERROR_PLAYER_NOT_DEFINED);
            }
        }
    }
}
