package com.gameshare.service;

import com.gameshare.entity.User;

public interface UserService {
    User login(String openid);
    User getUserInfo(Long userId);
}
