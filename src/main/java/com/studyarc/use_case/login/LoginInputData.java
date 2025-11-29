package com.studyarc.use_case.login;

public class LoginInputData {
    private final String username;
    private final String password;
    private boolean goToRegister;

    public LoginInputData(String username, String password, boolean goToRegister){
        this.username = username;
        this.password = password;
        this.goToRegister = goToRegister;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGoToRegister() {
        return goToRegister;
    }
}
