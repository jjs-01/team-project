package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

public class TrackPlanSavingInputData {
    private String username;
    private ArrayList<StudyPlan> plans;

    public TrackPlanSavingInputData(ArrayList<StudyPlan> plans, String username){
        this.username = username;
        this.plans = plans;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<StudyPlan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<StudyPlan> plans) {
        this.plans = plans;
    }
}
