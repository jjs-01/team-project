package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewModel;

public class RegisterViewModel extends ViewModel<RegisterState> {
    public RegisterViewModel(String viewName) {
        super(viewName);
        this.setState(new RegisterState());
    }
}
