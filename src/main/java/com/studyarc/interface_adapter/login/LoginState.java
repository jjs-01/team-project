package com.studyarc.interface_adapter.login;

public class LoginState {
    private String username;
    private String password;
    private String errorCode;
    public LoginState(){
        this.username = "";
        this.password = "";
        this.errorCode = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
