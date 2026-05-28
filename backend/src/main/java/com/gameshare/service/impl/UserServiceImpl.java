package com.gameshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameshare.entity.User;
import com.gameshare.mapper.UserMapper;
import com.gameshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        User user = userMapper.selectOne(wrapper);
        
        if (user == null) {
            // 新用户自动注册
            user = new User();
            user.setOpenid(openid);
            user.setNickname("用户" + System.currentTimeMillis() % 10000);
            userMapper.insert(user);
        }
        return user;
    }

    @Override
    public User getUserInfo(Long userId) {
        return userMapper.selectById(userId);
    }
}
