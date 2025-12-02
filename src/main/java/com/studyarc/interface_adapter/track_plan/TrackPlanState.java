package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.List;

/***
 * State class for the Track Plan use case
 * This class represents the state of the Track Plan use case,
 * including the list of study plans and the username of the user.
 */
public class TrackPlanState {
    private List<StudyPlan> studyPlans = new ArrayList<>();
    private String username = "";
    private String savingMessage = "";

    public List<StudyPlan> getStudyPlans() {
        return this.studyPlans;
    }

    public List<String> getStudyPlanTitles() {
        List<String> planTitles = new ArrayList<>();
        if (studyPlans != null) {
            for (StudyPlan plan : studyPlans) {
                planTitles.add(plan.getTitle());
            }
        }
        return planTitles;
    }

    public void setStudyPlans(List<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSavingMessage(String savingMessage) {
        this.savingMessage = savingMessage;
    }

    public String getSavingMessage() {
        return this.savingMessage;
    }
}