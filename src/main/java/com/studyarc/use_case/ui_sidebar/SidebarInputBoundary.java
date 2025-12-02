package com.studyarc.use_case.ui_sidebar;

/**
 * Input boundary interface for the sidebar use case
 */
public interface SidebarInputBoundary {

    /**
     * Executes the switch to job postings use case.
     */
    void switchToJobBoard();

    void switchToTrackPlan();

    void switchToPapers();

    void switchToLogin();

    void setUser(String username);
}
