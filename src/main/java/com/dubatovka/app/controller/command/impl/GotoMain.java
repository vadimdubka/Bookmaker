package com.dubatovka.app.controller.command.impl;

import com.dubatovka.app.controller.command.Command;
import com.dubatovka.app.controller.command.PageNavigator;
import com.dubatovka.app.entity.Category;
import com.dubatovka.app.manager.QueryManager;
import com.dubatovka.app.service.CategoryService;
import com.dubatovka.app.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.dubatovka.app.controller.command.PageNavigator.FORWARD_PAGE_MAIN;
import static com.dubatovka.app.manager.ConfigConstant.ATTR_SPORT_LIST;

public class GotoMain implements Command {
    
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
    
        CategoryService categoryService = ServiceFactory.getInstance().getCategoryService();
        List<Category> sportList = categoryService.getSportCategories();
        session.setAttribute(ATTR_SPORT_LIST, sportList);
    
        //TODO убрать ниже
        QueryManager.saveQueryToSession(request);
        return FORWARD_PAGE_MAIN;
    }
}
