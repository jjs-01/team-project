package com.studyarc.use_case.ui_sidebar;

public class SidebarInteractor implements SidebarInputBoundary {
    private final SidebarDataAccessInterface userDataAccessObject;
    private final SidebarOutputBoundary userPresenter;

    public SidebarInteractor(SidebarDataAccessInterface sidebarDataAcessInterface, SidebarOutputBoundary sidebarOutputBoundary) {

        this.userDataAccessObject = sidebarDataAcessInterface;
        this.userPresenter = sidebarOutputBoundary;
    }

    @Override
    public void switchToJobBoard() {
        userPresenter.switchToJobBoard();
    }

    @Override
    public void switchToMilestone() {
        userPresenter.switchToMilestone();
    }

    @Override
    public void switchToTrackPlan() { userPresenter.switchToTrackPlan();}


}
