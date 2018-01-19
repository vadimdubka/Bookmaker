package com.dubatovka.app.controller;

import com.dubatovka.app.entity.Event;
import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERROR_INVALID_EVENT_ID;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERROR_INVALID_REQUEST_PARAMETER;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_SEPARATOR;

@FunctionalInterface
public interface Command {
    PageNavigator execute(HttpServletRequest request);
    
    default void validateRequestParams(StringBuilder errorMessage, String... params) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidRequestParam(params)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_REQUEST_PARAMETER).append(MESSAGE_SEPARATOR);
        }
    }
    
    default void validateEventNotNull(Event event, StringBuilder errorMessage) {
        if (event == null) {
            errorMessage.append(MESSAGE_ERROR_INVALID_EVENT_ID).append(MESSAGE_SEPARATOR);
        }
    }
}