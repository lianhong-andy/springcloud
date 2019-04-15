package com.andy.service;

import com.andy.user.pojo.User;

import java.util.List;

public interface UserService {
    List<User> queryAll();

    int addUser(User user);

    User getUserById(Long id);
}

