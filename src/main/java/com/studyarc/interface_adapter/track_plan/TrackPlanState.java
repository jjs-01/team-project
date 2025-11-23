package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

public class TrackPlanState {
    private ArrayList<StudyPlan> studyPlans = null;
    private String Username = "";

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setStudyPlans(ArrayList<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
