package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public LoginViewModel() {
        super("login");
        this.setState(new LoginState());
    }
}
