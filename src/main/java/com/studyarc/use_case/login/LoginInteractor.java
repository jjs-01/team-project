package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.security.NoSuchAlgorithmException;

public class LoginInteractor implements LoginInputBoundary{
    private LoginDataAccessInterface dao;
    private LoginOutputBoundary loginPresenter;
    @Override
    public void login(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        User u = dao.getUser(username);
        if(u==null){
            loginPresenter.prepareView(new LoginOutputData(false, null));
        }
        try {
            boolean result = u.validateHash(password);
            if (result){
                loginPresenter.prepareView(new LoginOutputData(true, u));
            }
            loginPresenter.prepareView(new LoginOutputData(false, null));
        } catch (NoSuchAlgorithmException e) {
            loginPresenter.prepareView(new LoginOutputData(false, null));
        }
    }

}
