package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.List;

/***
 * Output Data class for the Track Plan use case
 */
public class TrackPlanOutputData {
    private String username;
    private final List<StudyPlan> listOfPlan;

    public TrackPlanOutputData(String username, List<StudyPlan> plans) {
        this.username = username;
        this.listOfPlan = plans;
    }

    public List<StudyPlan> getListOfPlan() {
        return this.listOfPlan;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
