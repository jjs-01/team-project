package com.studyarc.interface_adapter.login;

import com.studyarc.use_case.login.LoginInputBoundary;
import com.studyarc.use_case.login.LoginInputData;
import com.studyarc.use_case.login.RegisterInputData;

public class RegisterController {
    private final LoginInputBoundary loginInteractor;
    public RegisterController(LoginInputBoundary loginInteractor){
        this.loginInteractor = loginInteractor;
    }
    public void execute(RegisterState state){
        final RegisterInputData inputData = new RegisterInputData(state.getUsername(), state.getPassword(), false);
        this.loginInteractor.register(inputData);
    }

    public void goToLogin(){
        this.loginInteractor.register(new RegisterInputData("", "", true));
    }
}
