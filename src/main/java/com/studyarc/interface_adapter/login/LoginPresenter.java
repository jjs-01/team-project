package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.login.LoginOutputBoundary;
import com.studyarc.use_case.login.LoginOutputData;
import com.studyarc.use_case.login.RegisterOutputData;
import com.studyarc.view.LoginView;
import com.studyarc.view.ViewManager;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final RegisterViewModel registerViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TrackPlanViewModel trackPlanViewModel;
    private final MilestoneTasksViewModel milestoneTasksViewModel;

    public LoginPresenter(LoginViewModel loginViewModel, RegisterViewModel registerViewModel, ViewManagerModel viewManagerModel, TrackPlanViewModel trackPlanViewModel, MilestoneTasksViewModel milestoneTasksViewModel){
        this.loginViewModel = loginViewModel;
        this.registerViewModel = registerViewModel;
        this.viewManagerModel = viewManagerModel;
        this.trackPlanViewModel = trackPlanViewModel;
        this.milestoneTasksViewModel = milestoneTasksViewModel;
    }
    @Override
    public void prepareView(LoginOutputData loginOutputData) {
        if(!loginOutputData.isSuccess() && !loginOutputData.isGoToRegister()){
            System.out.println(loginViewModel);
            loginViewModel.getState().setPassword("");
            loginViewModel.getState().setErrorCode("Incorrect username or password.");
        } else if(loginOutputData.isGoToRegister()){
            loginViewModel.setState(new LoginState());
            viewManagerModel.setState(registerViewModel.getViewName());
        } else if(loginOutputData.isSuccess()){
            loginViewModel.setState(new LoginState());
            viewManagerModel.setState(trackPlanViewModel.getViewName());
        }
        viewManagerModel.firePropertyChange();
    }

    public void prepareView(RegisterOutputData registerOutputData){
        if(registerOutputData.isGoToLogin()){
            registerViewModel.setState(new RegisterState());
            viewManagerModel.setState(loginViewModel.getViewName());
        } else if(registerOutputData.isSuccess()){
            registerViewModel.setState(new RegisterState());
            viewManagerModel.setState(milestoneTasksViewModel.getViewName());
        }
        viewManagerModel.firePropertyChange();
    }
}
