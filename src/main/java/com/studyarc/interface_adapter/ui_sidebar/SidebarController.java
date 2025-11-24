package com.studyarc.interface_adapter.ui_sidebar;

import com.studyarc.use_case.ui_sidebar.SidebarInputBoundary;

public class SidebarController {
    private final SidebarInputBoundary sidebarUseCaseInteractor;

    public SidebarController(SidebarInputBoundary sidebarUseCaseInteractor) {
        this.sidebarUseCaseInteractor = sidebarUseCaseInteractor;
    }

    public void switchToJobBoard() {
        sidebarUseCaseInteractor.switchToJobBoard();
    }

    public void switchToMilestone() {
        sidebarUseCaseInteractor.switchToMilestone();
    }

    public void switchToTrackPlan(){sidebarUseCaseInteractor.switchToTrackPlan();}
}
