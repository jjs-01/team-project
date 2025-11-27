package com.studyarc.use_case.login;

public class RegisterInputData {
    private final String username;
    private final String password;
    private final String focus;
    private final boolean goToLogin;

    public RegisterInputData(String username, String password, String focus, boolean goToLogin){
        this.username = username;
        this.password = password;
        this.focus = focus;
        this.goToLogin = goToLogin;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFocus(){
        return focus;
    }
}
