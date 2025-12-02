package com.studyarc.use_case.ui_sidebar;

/**
 * Class for the interactor for the sidebar (switching between views on the sidebar) use case
 */
public class SidebarInteractor implements SidebarInputBoundary {
    private final SidebarOutputBoundary userPresenter;

    public SidebarInteractor(SidebarOutputBoundary sidebarOutputBoundary) {
        this.userPresenter = sidebarOutputBoundary;
    }

    @Override
    public void switchToJobBoard() {
        userPresenter.switchToJobBoard();
    }

    @Override
    public void switchToTrackPlan() {
        userPresenter.switchToTrackPlan();
    }

    @Override
    public void switchToLogin() {
        userPresenter.switchToLogin();
    }

    @Override
    public void switchToPapers() {
        userPresenter.switchToPapers();
    }

    @Override
    public void setUser(String username) {
        userPresenter.setUser(username);
    }
}