package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

public interface TrackPlanDataAccessinterface {

    ArrayList<StudyPlan> getPlans();
    ArrayList<StudyPlan> generateTestPlans();
    void save();
    void saveAllPlansForUser(ArrayList<StudyPlan> plans);

}
