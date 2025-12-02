package com.studyarc.interface_adapter.ui_sidebar;

import com.studyarc.use_case.ui_sidebar.SidebarInputBoundary;

/**
 * Controller for the sidebar (switching between view options on the side bar) use case
 */
public class SidebarController {
    private final SidebarInputBoundary sidebarUseCaseInteractor;

    public SidebarController(SidebarInputBoundary sidebarUseCaseInteractor) {
        this.sidebarUseCaseInteractor = sidebarUseCaseInteractor;
    }

    public void switchToJobBoard() {
        sidebarUseCaseInteractor.switchToJobBoard();
    }

    public void switchToLogin() {
        sidebarUseCaseInteractor.switchToLogin();
    }

    public void switchToTrackPlan() {
        sidebarUseCaseInteractor.switchToTrackPlan();
    }

    public void switchToPapers() {
        sidebarUseCaseInteractor.switchToPapers();
    }

    public void setUser(String username) {
        sidebarUseCaseInteractor.setUser(username);
    }
}