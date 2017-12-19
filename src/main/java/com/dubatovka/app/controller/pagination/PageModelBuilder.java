package com.dubatovka.app.controller.pagination;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PageModelBuilder<T> {
    private final Logger logger = LogManager.getLogger(PageModelBuilder.class);
    
    private static final int ENTITY_AMOUNT_ON_PAGE_BY_DEFOULT = 5;
    
    private List<T> entityList;
    private int totalEntityAmount;
    private int entityAmountOnPage;
    private int amountOfPages;
    
    public final void buildPagination(List<T> entityList) {
        this.entityList = entityList;
        totalEntityAmount = entityList.size();
        this.entityAmountOnPage = ENTITY_AMOUNT_ON_PAGE_BY_DEFOULT;
        amountOfPages = countAmountOfPages();
    }
    
    public final PageModel<T> getPageModel(String numberOfPage) {
        PageModel<T> pageModel = new PageModel<>();
        
        int pageNumber = getPageNumber(numberOfPage);
        List<T> entityListOnPage = getSubListForPage(pageNumber);
        
        pageModel.setEntityListOnPage(entityListOnPage);
        pageModel.setCurrentPage(pageNumber);
        pageModel.setAmountOfPages(amountOfPages);
        
        return pageModel;
    }
    
    
    private int countAmountOfPages() {
        int amountOfPages;
        if (totalEntityAmount > 0) {
            int integerPart = totalEntityAmount / entityAmountOnPage;
            int remainder = totalEntityAmount % entityAmountOnPage;
            if (remainder == 0) {
                amountOfPages = integerPart;
            } else {
                amountOfPages = integerPart + 1;
            }
        } else {
            amountOfPages = 0;
        }
        return amountOfPages;
    }
    
    private int getPageNumber(String numberOfPage) {
        int pageNumber;
        try {
            pageNumber = Integer.valueOf(numberOfPage);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            pageNumber = 1;
        }
        
        if (pageNumber < 1) {
            pageNumber = 1;
        } else if (pageNumber > amountOfPages) {
            pageNumber = amountOfPages;
        }
        
        return pageNumber;
    }
    
    private List<T> getSubListForPage(int pageNumber) {
        int amountOfPreviousPages = pageNumber - 1;
        int fromEntity = amountOfPreviousPages * entityAmountOnPage;
        int toEntity = fromEntity + entityAmountOnPage;
        
        if (toEntity > totalEntityAmount) {
            toEntity = totalEntityAmount;
        }
        
        List<T> subList = entityList.subList(fromEntity, toEntity);
        return subList;
    }
    
    public int getEntityAmountOnPage() {
        return entityAmountOnPage;
    }
    
    public void setEntityAmountOnPage(int entityAmountOnPage) {
        this.entityAmountOnPage = entityAmountOnPage;
    }
    
}
