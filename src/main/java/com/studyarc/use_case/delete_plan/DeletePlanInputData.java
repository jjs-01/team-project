package com.studyarc.use_case.delete_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;

import java.util.ArrayList;

public class DeletePlanInputData {
    private StudyPlan plan;
    private String userName;


    public DeletePlanInputData(StudyPlan plan){
        this.plan = plan;
    }

    public StudyPlan getPlan() {
        return this.plan;
    }

    public void setPlan(StudyPlan plan){
        this.plan = plan;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
