package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

public class TrackPlanState {
    private ArrayList<StudyPlan> studyPlans;
    private String Username = "";
    private String savingMessage = "";


    public ArrayList<StudyPlan> getStudyPlans() {
        return this.studyPlans;
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

    public void setSavingMessage(String savingMessage) {
        this.savingMessage = savingMessage;
    }

    public String getSavingMessage(){
        return this.savingMessage;
    }
}

