package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.util.Set;

public interface EventService {
    Set<Event> getActualEventsByCategoryId(String categoryId);
}
