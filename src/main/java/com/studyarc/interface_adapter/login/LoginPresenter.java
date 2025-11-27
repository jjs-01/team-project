package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.use_case.login.LoginOutputBoundary;
import com.studyarc.use_case.login.LoginOutputData;
import com.studyarc.use_case.login.RegisterOutputData;
import com.studyarc.view.LoginView;
import com.studyarc.view.ViewManager;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final RegisterViewModel registerViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(LoginViewModel loginViewModel, RegisterViewModel registerViewModel, ViewManagerModel viewManagerModel){
        this.loginViewModel = loginViewModel;
        this.registerViewModel = registerViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareView(LoginOutputData loginOutputData) {
        if(!loginOutputData.isSuccess()){
            System.out.println(loginViewModel);
            loginViewModel.getState().setPassword("");
            loginViewModel.getState().setErrorCode("Incorrect username or password.");
            loginViewModel.firePropertyChange();
        } else if(loginOutputData.isGoToRegister()){
            loginViewModel.setState(new LoginState());
        }
    }

    public void prepareView(RegisterOutputData registerOutputData){

    }
}
