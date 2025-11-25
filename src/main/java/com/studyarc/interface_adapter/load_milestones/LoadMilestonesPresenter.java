package com.studyarc.interface_adapter.load_milestones;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.use_case.load_milestones.LoadMilestonesOutputBoundary;
import com.studyarc.use_case.load_milestones.LoadMilestonesOutputData;

import java.util.List;

public class LoadMilestonesPresenter implements LoadMilestonesOutputBoundary {
    private final LoadMilestonesViewModel loadMilestonesViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoadMilestonesPresenter(ViewManagerModel viewManagerModel,
                                   LoadMilestonesViewModel loadMilestonesViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadMilestonesViewModel = loadMilestonesViewModel;
    }

    @Override
    public void prepareSuccessView(LoadMilestonesOutputData response) {
        final LoadMilestonesState loadMilestonesState = loadMilestonesViewModel.getState();

        List<String[]> milestoneInfoList = response.getMilestoneInfo();

        // populate milestones
        for (int i = 0; i < milestoneInfoList.size(); i++) {
            String[] milestoneInfo = milestoneInfoList.get(i);
            loadMilestonesState.addMilestone(i, milestoneInfo[0], milestoneInfo[1]);
        }

        // populate tasks
        List<List<String[]>> milestoneToTaskInfoList = response.getTaskInfo();
        loadMilestonesState.setMilestoneIndexToTasks(milestoneToTaskInfoList);

        loadMilestonesViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoadMilestonesState loadMilestonesState = loadMilestonesViewModel.getState();
        loadMilestonesState.setLoadError(error);
        loadMilestonesViewModel.firePropertyChange();
    }
}
