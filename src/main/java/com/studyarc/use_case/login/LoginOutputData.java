package com.studyarc.use_case.login;

import com.studyarc.entity.User;

public class LoginOutputData {
    private final boolean success;
    private final boolean goToRegister;

    LoginOutputData(boolean success, boolean goToRegister) {
        this.success = success;
        this.goToRegister = goToRegister;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isGoToRegister() {
        return goToRegister;
    }
}
