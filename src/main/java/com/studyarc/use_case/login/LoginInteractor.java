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
        if(loginInputData.isGoToRegister()){
            loginPresenter.prepareView(new LoginOutputData(false, true));
            return;
        }
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        User u = dao.getUser(username);
        if(u==null){
            System.out.println("Got a null user");
            loginPresenter.prepareView(new LoginOutputData(false, false));
            return;
        }
        System.out.println("Got a real user");
        try {
            boolean result = u.validateHash(password);
            System.out.println(result);
            if (result){
                this.dao.setUser(u);
                loginPresenter.prepareView(new LoginOutputData(true, false));
                return;
            }
            loginPresenter.prepareView(new LoginOutputData(false, false));
        } catch (NoSuchAlgorithmException e) {
            loginPresenter.prepareView(new LoginOutputData(false, false));
        }
    }
    public void register(RegisterInputData registerInputData){
        if(registerInputData.isGoToLogin()){
            loginPresenter.prepareView(new RegisterOutputData(false, true, ""));
            return;
        }
        String username = registerInputData.getUsername();
        String password = registerInputData.getPassword();
        User u = dao.getUser(username);
        if(u!=null){
            System.out.println("User already exists!");
            loginPresenter.prepareView(new RegisterOutputData(false, false, "User already exists!"));
            return;
            // go back
        } else if (username.isEmpty()) {
            loginPresenter.prepareView(new RegisterOutputData(false, false, "emptyUsername"));
            return;
        }
        try {
            List<User> alluser = this.dao.getAllUsers();
            User newuser = new User(username, password);
                this.dao.setUser(newuser);
                alluser.add(newuser);
                this.dao.save();
                loginPresenter.prepareView(new RegisterOutputData(true, false, ""));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

    }

}
