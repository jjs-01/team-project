package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface LoginDataAccessInterface {
    boolean registerUser(User u);
    User getUser(String username);
    void setUser(User u);
    void save();
    List<User> getAllUsers();
}
