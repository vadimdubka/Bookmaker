package com.dubatovka.app.service;

import com.dubatovka.app.entity.User;

public abstract class UserService extends AbstractService{
    public abstract User authorizeUser(String email, String password);
}
