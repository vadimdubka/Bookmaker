package com.dubatovka.app.controller;

import com.dubatovka.app.manager.QueryManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dubatovka.app.manager.ConfigConstant.*;

@WebServlet(name = MAIN_CONTROLLER, urlPatterns = {MAIN_CONTROLLER_URL})
public class MainController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.defineCommand(request);
        if (command != null) {
            PageNavigator navigator = command.execute(request);
            if (navigator != null) {
                processNavigator(request, response, navigator);
            } else {
                defaultProcessRequest(request, response);
            }
        } else {
            defaultProcessRequest(request, response);
        }
    }
    
    private void processNavigator(HttpServletRequest request, HttpServletResponse response, PageNavigator navigator) throws ServletException, IOException {
        String query = navigator.getQuery();
        if (PREV_QUERY.equals(query)) {
            query = QueryManager.takePreviousQuery(request);
        }
        String responseType = navigator.getResponseType();
        switch (responseType) {
            case FORWARD:
                ServletContext servletContext = getServletContext();
                RequestDispatcher dispatcher = servletContext.getRequestDispatcher(query);
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + query);
                break;
            default:
                defaultProcessRequest(request, response);
        }
    }
    
    private static void defaultProcessRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + PAGE_INDEX);
    }
}