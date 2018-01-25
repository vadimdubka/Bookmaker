package com.dubatovka.app.service;

public interface PaginationService {
    
    int getLimitOnPage();
    
    int getTotalEntityAmount();
    
    int getAmountOfPages();
    
    int getCurrentPage();
    
    int getOffset();
    
    void buildService(int totalEntityAmount, int limitOnPage, int currentPage);
}
