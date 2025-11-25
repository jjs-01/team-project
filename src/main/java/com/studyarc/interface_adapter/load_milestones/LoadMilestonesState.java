package com.studyarc.interface_adapter.load_milestones;

import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksState;

public class LoadMilestonesState extends MilestoneTasksState {
    private boolean isLoaded = true;
    private String loadError = "";

    public String getLoadError() {
        return loadError;
    }

    public boolean getLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setLoadError(String loadError) {
        this.loadError = loadError;
    }
}
