package com.dubatovka.app.service;

import com.dubatovka.app.entity.User;

public interface UserService {
    User authorizeUser(String email, String password);
}
