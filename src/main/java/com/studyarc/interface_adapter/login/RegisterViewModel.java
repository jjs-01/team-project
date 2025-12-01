package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewModel;

public class RegisterViewModel extends ViewModel<RegisterState> {
    public RegisterViewModel() {
        super("register");
        this.setState(new RegisterState());
    }
}
