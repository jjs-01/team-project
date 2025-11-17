package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.security.NoSuchAlgorithmException;

public class LoginInteractor implements LoginInputBoundary{
    private LoginDataAccessInterface dao;
    @Override
    public LoginOutputData login(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        User u = dao.getUser(username);
        if(u==null){
            return  new LoginOutputData(false, null);
        }
        try {
            boolean result = u.validateHash(password);
            if (result){
                return new LoginOutputData(true, u);
            }
            return new LoginOutputData(false, null);
        } catch (NoSuchAlgorithmException e) {
            return  new LoginOutputData(false, null);
        }
    }

}
