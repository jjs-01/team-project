package com.studyarc.use_case.delete_plan;

import com.studyarc.entity.StudyPlan;

/***
 * Output Data class for the Delete Plan use case
 */
public class DeletePlanOutputData {
    private StudyPlan plan;

    public DeletePlanOutputData(StudyPlan plan) {
        this.plan = plan;
    }

    public StudyPlan getPlan() {
        return this.plan;
    }

    public void setPlan(StudyPlan plan){
        this.plan = plan;
    }
}

