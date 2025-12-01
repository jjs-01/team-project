package com.studyarc.use_case.load_milestones;

/**
 * Input Data object for the load milestones use case
 */
public class LoadMilestonesInputData {
    private final String studyPlanName;

    public LoadMilestonesInputData(String studyPlanName) {
        this.studyPlanName = studyPlanName;
    }

    public String getStudyPlanName() {
        return studyPlanName;
    }
}
