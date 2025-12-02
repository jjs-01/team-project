package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

/***
 * Output Data class for the Track Plan use case
 */
public class TrackPlanOutputData {
    private String username;
    private final ArrayList<StudyPlan> listofplan;

    public TrackPlanOutputData(String username, ArrayList<StudyPlan> plans) {
        this.username = username;
        this.listofplan = plans;
    }

    public ArrayList<StudyPlan> getListofplan() {
        return this.listofplan;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
