package com.dubatovka.app.service.impl;

import com.dubatovka.app.service.PaginationService;

public class PaginationServiceImpl implements PaginationService {
    private static final int DEFAULT_LIMIT_ON_PAGE = 5;
    private static final int DEFAULT_TOTAL_ENTITY_AMOUNT = 0;
    private static final int DEFAULT_AMOUNT_OF_PAGES = 0;
    private static final int DEFAULT_CURRENT_PAGE = 1;
    
    private int limitOnPage = DEFAULT_LIMIT_ON_PAGE;
    private int totalEntityAmount = DEFAULT_TOTAL_ENTITY_AMOUNT;
    private int amountOfPages = DEFAULT_AMOUNT_OF_PAGES;
    private int currentPage = DEFAULT_CURRENT_PAGE;
    
    private boolean isStateValid = false;
    
    @Override
    public int getLimitOnPage() {
        return limitOnPage;
    }
    
    @Override
    public int getTotalEntityAmount() {
        return totalEntityAmount;
    }
    
    @Override
    public int getAmountOfPages() {
        return amountOfPages;
    }
    
    @Override
    public int getCurrentPage() {
        return currentPage;
    }
    
    @Override
    public int getOffsetForPage(int pageNumber) {
        if (!isStateValid) {
            throw new IllegalStateException("Pagination service is not built.");
        }
        setCurrentPage(pageNumber);
        
        int amountOfPreviousPages = currentPage - 1;
        if (amountOfPreviousPages < 0) {
            amountOfPreviousPages = 0;
        }
        int offset = amountOfPreviousPages * limitOnPage;
        
        return offset;
    }
    
    @Override
    public void buildService(int totalEntityAmount, int limitOnPage, int currentPage) {
        this.totalEntityAmount = totalEntityAmount;
        this.limitOnPage = limitOnPage;
        countAmountOfPages();
        setCurrentPage(currentPage);
        isStateValid = true;
    }
    
    private void countAmountOfPages() {
        if (totalEntityAmount > 0) {
            int integerPart = totalEntityAmount / limitOnPage;
            int remainder = totalEntityAmount % limitOnPage;
            if (remainder == 0) {
                amountOfPages = integerPart;
            } else {
                amountOfPages = integerPart + 1;
            }
        } else {
            amountOfPages = 0;
        }
    }
    
    private void setCurrentPage(int currentPage) {
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > amountOfPages) {
            currentPage = amountOfPages;
        }
        this.currentPage = currentPage;
    }
}
