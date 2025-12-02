package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.List;

/***
 * Input Data class for the Track Plan Saving use case
 */

public class TrackPlanSavingInputData {
    private String username;
    private List<StudyPlan> plans;

    public TrackPlanSavingInputData(List<StudyPlan> plans, String username){
        this.username = username;
        this.plans = plans;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<StudyPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<StudyPlan> plans) {
        this.plans = plans;
    }
}
