package com.studyarc.use_case.delete_plan;

import com.studyarc.entity.StudyPlan;


/***
 * Input Data class for the Delete Plan use case
 */

public class DeletePlanInputData {
    private StudyPlan plan;


    public DeletePlanInputData(StudyPlan plan) {
        this.plan = plan;
    }

    public StudyPlan getPlan() {
        return this.plan;
    }

    public void setPlan(StudyPlan plan) {
        this.plan = plan;
    }

}
