package com.studyarc.interface_adapter.login;

import com.studyarc.use_case.login.LoginInputBoundary;
import com.studyarc.use_case.login.LoginInputData;

public class LoginController {
    private final LoginInputBoundary loginInteractor;
    public LoginController(LoginInputBoundary loginInteractor){
        this.loginInteractor = loginInteractor;
    }
    public void execute(LoginState state){
        final LoginInputData inputData = new LoginInputData(state.getUsername(), state.getPassword(), false);
        this.loginInteractor.login(inputData);
    }

    public void goToRegister(){
        final LoginInputData inputData = new LoginInputData(null, null, true);
        this.loginInteractor.login(inputData);
    }
}
