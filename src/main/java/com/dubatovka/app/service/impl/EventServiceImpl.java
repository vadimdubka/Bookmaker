package com.dubatovka.app.service.impl;

import com.dubatovka.app.dao.EventDAO;
import com.dubatovka.app.entity.Event;
import com.dubatovka.app.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class EventServiceImpl extends AbstractService implements EventService {
    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    private final EventDAO eventDAO = daoFactory.getEventDAO();
    @Override
    public Set<Event> getActualEventsByCategoryId(String categoryId) {
        return null;
    }
}
