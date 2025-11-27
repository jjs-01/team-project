package com.studyarc.use_case.login;

public class RegisterOutputData {
    private final boolean success;
    private final boolean goToLogin;

    public RegisterOutputData(boolean success, boolean goToLogin) {
        this.success = success;
        this.goToLogin = goToLogin;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isGoToLogin() {
        return goToLogin;
    }
}
