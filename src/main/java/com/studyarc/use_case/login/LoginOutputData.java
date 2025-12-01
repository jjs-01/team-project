package com.studyarc.use_case.login;

import com.studyarc.entity.User;

public class LoginOutputData {
    private final boolean success;
    private final boolean goToRegister;
    private final String username;

    LoginOutputData(boolean success, boolean goToRegister,String username) {
        this.success = success;
        this.goToRegister = goToRegister;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isGoToRegister() {
        return goToRegister;
    }
}
