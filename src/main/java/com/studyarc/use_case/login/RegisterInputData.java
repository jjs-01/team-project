package com.studyarc.use_case.login;

public class RegisterInputData {
    private final String username;
    private final String password;
    private final boolean goToLogin;

    public RegisterInputData(String username, String password, boolean goToLogin){
        this.username = username;
        this.password = password;
        this.goToLogin = goToLogin;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGoToLogin() {
        return goToLogin;
    }
}
