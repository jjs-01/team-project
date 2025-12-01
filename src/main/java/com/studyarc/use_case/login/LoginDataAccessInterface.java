package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.util.List;

public interface LoginDataAccessInterface {
    boolean registerUser(String username, String password);
    User getUser(String username);
    void setUser(User u);
    void save();
}
