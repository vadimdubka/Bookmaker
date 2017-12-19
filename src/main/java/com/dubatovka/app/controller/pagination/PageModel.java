package com.dubatovka.app.controller.pagination;

import java.util.List;

public class PageModel<T> {
    private List<T> entityListOnPage;
    private int currentPage;
    private int amountOfPages;
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public List<T> getEntityListOnPage() {
        return entityListOnPage;
    }
    
    public void setEntityListOnPage(List<T> entityListOnPage) {
        this.entityListOnPage = entityListOnPage;
    }
    
    public int getAmountOfPages() {
        return amountOfPages;
    }
    
    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }
}
