package com.studyarc.use_case.login;

import com.studyarc.entity.User;

import java.security.NoSuchAlgorithmException;

public class LoginInteractor implements LoginInputBoundary{
    private LoginDataAccessInterface dao;
    @Override
    public LoginResult login(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        User u = dao.getUser(username);
        if(u==null){
            return  new LoginResult(false, null);
        }
        try {
            boolean result = u.validateHash(password);
            if (result){
                return new LoginResult(true, u);
            }
            return new LoginResult(false, null);
        } catch (NoSuchAlgorithmException e) {
            return  new LoginResult(false, null);
        }
    }

}
