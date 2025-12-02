package com.studyarc.use_case.ui_sidebar;

/**
 * Interface for the output boundary for the sidebar (switching views using the sidebar) use case
 */
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
