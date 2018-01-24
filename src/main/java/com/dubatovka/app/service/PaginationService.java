package com.dubatovka.app.service;

public interface PaginationService {
    
    int getLimitOnPage();
    
    int getTotalEntityAmount();
    
    int getAmountOfPages();
    
    int getCurrentPage();
    
    int getOffsetForPage(int pageNumber);
    
    void buildService(int totalEntityAmount, int limitOnPage, int currentPage);
}
