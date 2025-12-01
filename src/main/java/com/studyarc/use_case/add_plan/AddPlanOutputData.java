package com.studyarc.use_case.add_plan;

import com.studyarc.entity.StudyPlan;

/**
 * Output Data class for the Add Plan use case
 *
 */
public class AddPlanOutputData {
    private final StudyPlan plan;

    public AddPlanOutputData(StudyPlan plan) {
        this.plan = plan;
    }

    public StudyPlan getPlan() {
        return this.plan;
    }
}
