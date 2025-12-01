package com.studyarc.interface_adapter.login;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanController;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.interface_adapter.ui_sidebar.SidebarViewModel;
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
    private final SidebarViewModel sidebarViewModel;
    private final TrackPlanController trackPlanController;

    public LoginPresenter(LoginViewModel loginViewModel,
                          RegisterViewModel registerViewModel,
                          ViewManagerModel viewManagerModel,
                          TrackPlanViewModel trackPlanViewModel,
                          MilestoneTasksViewModel milestoneTasksViewModel,
                          SidebarViewModel sidebarViewModel,
                          TrackPlanController c
    ){
        this.loginViewModel = loginViewModel;
        this.registerViewModel = registerViewModel;
        this.viewManagerModel = viewManagerModel;
        this.trackPlanViewModel = trackPlanViewModel;
        this.milestoneTasksViewModel = milestoneTasksViewModel;
        this.sidebarViewModel = sidebarViewModel;
        this.trackPlanController = c;
    }
    @Override
    public void prepareView(LoginOutputData loginOutputData) {
        String username = loginOutputData.getUsername();
        if(!loginOutputData.isSuccess() && !loginOutputData.isGoToRegister()){
            loginViewModel.getState().setPassword("");
            loginViewModel.getState().setErrorCode("Incorrect username or password.");
        } else if(loginOutputData.isGoToRegister()){
            loginViewModel.setState(new LoginState());
            viewManagerModel.setState(registerViewModel.getViewName());
        } else if(loginOutputData.isSuccess()){
            loginViewModel.setState(new LoginState());
            sidebarViewModel.getState().setUserName(username);
            sidebarViewModel.firePropertyChange();
            trackPlanController.execute(username);
            viewManagerModel.setState(trackPlanViewModel.getViewName());
        }
        loginViewModel.firePropertyChange();
        viewManagerModel.firePropertyChange();

    }

    public void prepareView(RegisterOutputData registerOutputData){
        if(registerOutputData.isGoToLogin()){
            registerViewModel.setState(new RegisterState());
            registerViewModel.firePropertyChange();
            viewManagerModel.setState(loginViewModel.getViewName());
            viewManagerModel.firePropertyChange();

        } else if(registerOutputData.isSuccess()){
            registerViewModel.setState(new RegisterState());
            registerViewModel.firePropertyChange();
            viewManagerModel.setState(trackPlanViewModel.getViewName());
            sidebarViewModel.getState().setUserName(registerOutputData.getUsername());
            sidebarViewModel.firePropertyChange();

            trackPlanController.execute(registerOutputData.getUsername());

            viewManagerModel.firePropertyChange();
        } else {
            System.out.println("presenter : user exists!");
            registerViewModel.getState().setErrorCode(registerOutputData.getErrorMessage());
            registerViewModel.getState().setPassword("");
            registerViewModel.firePropertyChange();
            sidebarViewModel.firePropertyChange();
        }

    }
}
