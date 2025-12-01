package com.studyarc.interface_adapter.load_milestones;

import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksState;

/**
 * State for the loading milestones use case
 */
public class LoadMilestonesState extends MilestoneTasksState {
    private String loadError = "";

    public String getLoadError() {
        return loadError;
    }

    public void setLoadError(String loadError) {
        this.loadError = loadError;
    }
}
