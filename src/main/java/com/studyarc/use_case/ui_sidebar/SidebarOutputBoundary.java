package com.studyarc.use_case.ui_sidebar;

public interface SidebarOutputBoundary {

    /**
     * Switches to the Login View.
     */
    void switchToJobBoard();

    void switchToTrackPlan();

    void switchToPapers();

    void switchToLogin();

    void setUser(String username);
}
