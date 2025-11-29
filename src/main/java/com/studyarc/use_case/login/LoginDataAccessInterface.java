package com.studyarc.use_case.login;

import com.studyarc.entity.User;

public interface LoginDataAccessInterface {
    boolean registerUser(User u);
    User getUser(String username);
    void setUser(User u);
}
