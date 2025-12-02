package com.studyarc.interface_adapter.ui_sidebar;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;
import com.studyarc.interface_adapter.login.LoginState;
import com.studyarc.interface_adapter.login.LoginViewModel;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersViewModel;
import com.studyarc.use_case.ui_sidebar.SidebarOutputBoundary;

public class SidebarPresenter implements SidebarOutputBoundary {
    private final SidebarViewModel sidebarViewModel;
    private final JobPostingsViewModel jobPostingsViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TrackPlanViewModel trackPlanViewModel;
    private final ViewingResearchPapersViewModel viewingResearchPapersViewModel;
    private final LoginViewModel loginViewModel;

    public SidebarPresenter(ViewManagerModel viewManagerModel,
                            SidebarViewModel sidebarViewModel,
                            JobPostingsViewModel jobPostingsViewModel,
                            TrackPlanViewModel trackPlanViewModel,
                            ViewingResearchPapersViewModel viewingResearchPapersViewModel,
                            LoginViewModel loginViewModel) {
        this.sidebarViewModel = sidebarViewModel;
        this.jobPostingsViewModel = jobPostingsViewModel;
        this.viewManagerModel = viewManagerModel;
        this.trackPlanViewModel = trackPlanViewModel;
        this.viewingResearchPapersViewModel = viewingResearchPapersViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void switchToJobBoard() {
        viewManagerModel.setState(jobPostingsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToTrackPlan() {
        viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToPapers() {
        viewManagerModel.setState(viewingResearchPapersViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToLogin() {
        // Reset the login state
        loginViewModel.setState(new LoginState());
        // Reset the sidebar username
        sidebarViewModel.getState().setUserName("");
        // Reset track plan state
        trackPlanViewModel.setState(new TrackPlanState());
        loginViewModel.firePropertyChange();
        viewManagerModel.setState(loginViewModel.getViewName());
        sidebarViewModel.firePropertyChange();
        trackPlanViewModel.firePropertyChange();
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void setUser(String username) {
        final SidebarState sidebarState = sidebarViewModel.getState();
        sidebarState.setUserName(username);
        sidebarViewModel.firePropertyChange();
    }
}