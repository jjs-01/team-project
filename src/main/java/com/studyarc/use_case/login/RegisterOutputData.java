package com.studyarc.use_case.login;

public class RegisterOutputData {
    private final boolean success;
    private final boolean goToLogin;
    private final String errorMessage;

    public RegisterOutputData(boolean success, boolean goToLogin, String errorMessage) {
        this.success = success;
        this.goToLogin = goToLogin;
        this.errorMessage = errorMessage;
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
}
