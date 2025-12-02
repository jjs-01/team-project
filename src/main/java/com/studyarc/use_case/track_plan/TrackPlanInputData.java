package com.studyarc.use_case.track_plan;

/***
 * Input Data for TrackPlan usecase
 */
public class TrackPlanInputData {
    final String username;

    public TrackPlanInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
