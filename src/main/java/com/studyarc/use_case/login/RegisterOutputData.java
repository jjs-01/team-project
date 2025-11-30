package com.studyarc.use_case.login;

public class RegisterOutputData {
    private final boolean success;
    private final boolean goToLogin;
    private final String errorMessage;
    private final String username;

    public RegisterOutputData(boolean success, boolean goToLogin, String errorMessage, String username) {
        this.success = success;
        this.goToLogin = goToLogin;
        this.errorMessage = errorMessage;
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isGoToLogin() {
        return goToLogin;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUsername() {
        return username;
    }
}
