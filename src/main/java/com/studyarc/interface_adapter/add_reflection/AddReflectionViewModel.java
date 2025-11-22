package com.studyarc.interface_adapter.add_reflection;

import com.studyarc.interface_adapter.ViewModel;

public class AddReflectionViewModel extends ViewModel<AddReflectionState> {

    public AddReflectionViewModel() {
        super("add reflection");
        setState(new AddReflectionState());
    }
}