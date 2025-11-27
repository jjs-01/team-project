package com.studyarc.use_case.login;

public interface LoginOutputBoundary {

    void prepareView(LoginOutputData loginOutputData);
    void prepareView(RegisterOutputData registerOutputData);
}
