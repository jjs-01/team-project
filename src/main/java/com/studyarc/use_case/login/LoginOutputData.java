package com.studyarc.use_case.login;

import com.studyarc.entity.User;

public class LoginOutputData {
    private final boolean success;
    private final User user;

    LoginOutputData(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }
}
