package com.study.week1.service;

import com.study.week1.domain.User;

public interface UserService {
    void registerUser();
    User findByName();
}
