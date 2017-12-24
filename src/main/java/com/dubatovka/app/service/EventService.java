package com.dubatovka.app.service;

import com.dubatovka.app.entity.Event;

import java.util.Map;
import java.util.Set;

public interface EventService {
    Set<Event> getActualEventsByCategoryId(String categoryId);
    
    Set<Event> getAllEventsByCategoryId(String categoryId);
    
    Map<String, Map<String, String>> getOutcomeColumnMaps(Set<Event> eventSet);
}
