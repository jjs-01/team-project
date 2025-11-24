package com.studyarc.interface_adapter.login;

import com.studyarc.use_case.login.LoginOutputBoundary;
import com.studyarc.use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private LoginViewModel loginViewModel;

    @Override
    public void prepareView(LoginOutputData loginOutputData) {
        if(!loginOutputData.isSuccess()){
            loginViewModel.getState().setPassword("");
            loginViewModel.getState().setErrorCode("Incorrect username or password.");
            loginViewModel.firePropertyChange();
        }
    }
}
