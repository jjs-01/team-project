package com.studyarc.interface_adapter.ui_sidebar;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.ui_sidebar.SidebarOutputBoundary;

public class SidebarPresenter implements SidebarOutputBoundary {
    private final SidebarViewModel sidebarViewModel;
    private final JobPostingsViewModel jobPostingsViewModel;
    private final MilestoneTasksViewModel milestoneTasksViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TrackPlanViewModel trackPlanViewModel;

    public SidebarPresenter(ViewManagerModel viewManagerModel, SidebarViewModel sidebarViewModel,
                            JobPostingsViewModel jobPostingsViewModel, MilestoneTasksViewModel milestoneTasksViewModel,
                            TrackPlanViewModel trackPlanViewModel) {
        this.sidebarViewModel = sidebarViewModel;
        this.jobPostingsViewModel = jobPostingsViewModel;
        this.viewManagerModel = viewManagerModel;
        this.milestoneTasksViewModel = milestoneTasksViewModel;
        this.trackPlanViewModel = trackPlanViewModel;
    }

    @Override
    public void switchToJobBoard() {
        viewManagerModel.setState(jobPostingsViewModel.getViewName());
        viewManagerModel.firePropertyChange();

    }

    @Override
    public void switchToMilestone() {
        viewManagerModel.setState(milestoneTasksViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToTrackPlan() {
        viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }


}
