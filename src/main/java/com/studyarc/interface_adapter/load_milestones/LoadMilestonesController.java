package com.studyarc.interface_adapter.load_milestones;

import com.studyarc.use_case.load_milestones.LoadMilestonesInputBoundary;
import com.studyarc.use_case.load_milestones.LoadMilestonesInputData;

public class LoadMilestonesController {
    private final LoadMilestonesInputBoundary loadMilestonesInteractor;

    public LoadMilestonesController(LoadMilestonesInputBoundary loadMilestonesInteractor) {
        this.loadMilestonesInteractor = loadMilestonesInteractor;
    }

    /**
     * Executes the MilestoneTasks Use Case
     * @param studyPlanName name of the StudyPlan that's being retrieved
     */
    public void execute(String studyPlanName) {
        final LoadMilestonesInputData loadMilestoneInputData = new LoadMilestonesInputData(studyPlanName);

        loadMilestonesInteractor.execute(loadMilestoneInputData);
    }
}
