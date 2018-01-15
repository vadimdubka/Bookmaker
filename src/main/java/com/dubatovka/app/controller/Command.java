package com.dubatovka.app.controller;

import com.dubatovka.app.service.ValidatorService;
import com.dubatovka.app.service.impl.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_ERROR_INVALID_REQUEST_PARAMETER;
import static com.dubatovka.app.manager.ConfigConstant.MESSAGE_SEPARATOR;

public interface Command {
    PageNavigator execute(HttpServletRequest request);
    
    default void validateRequestParams(StringBuilder errorMessage, String... params) {
        ValidatorService validatorService = ServiceFactory.getValidatorService();
        if (!validatorService.isValidRequestParam(params)) {
            errorMessage.append(MESSAGE_ERROR_INVALID_REQUEST_PARAMETER).append(MESSAGE_SEPARATOR);
        }
    }
}