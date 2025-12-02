package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginInteractor implements LoginInputBoundary{
    private final LoginDataAccessInterface dao;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginDataAccessInterface dao, LoginOutputBoundary loginPresenter){
        this.dao = dao;
        this.loginPresenter = loginPresenter;
    }
    @Override
    public void login(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        if(loginInputData.isGoToRegister()){
            loginPresenter.prepareView(new LoginOutputData(false, true, username));
            return;
        }

        User u = dao.getUser(username);

        if(u==null){
            System.out.println("Got a null user");
            loginPresenter.prepareView(new LoginOutputData(false, false, username));
            return;
        }
        System.out.println("Got a real user");
        try {
            boolean result = u.validateHash(password);
            System.out.println(result);
            if (result){
                this.dao.setUser(u);
                loginPresenter.prepareView(new LoginOutputData(true, false, username));
                return;
            }
            loginPresenter.prepareView(new LoginOutputData(false, false, username));
        } catch (NoSuchAlgorithmException e) {
            loginPresenter.prepareView(new LoginOutputData(false, false, username));
        }
    }
    public void register(RegisterInputData registerInputData){
        String username = registerInputData.getUsername();
        String password = registerInputData.getPassword();
        if(registerInputData.isGoToLogin()){
            loginPresenter.prepareView(new RegisterOutputData(false, true, "", username));
            return;
        }

        User u = dao.getUser(username);
        if(username.isEmpty()){
            System.out.println("empty username for register");
            loginPresenter.prepareView(new RegisterOutputData(false, false, "Empty username!", username));
            return;
        }
        else if(u!=null){
            System.out.println("User already exists!");
            loginPresenter.prepareView(new RegisterOutputData(false, false, "User already exists!", username));
            return;
            // go back
        }
        this.dao.registerUser(username, password);
        loginPresenter.prepareView(new RegisterOutputData(true, false, "", username));

    }

}
