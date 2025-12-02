package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

/***
 * Data Access Interface for TrackPlan usecase
 */
public interface TrackPlanDataAccessinterface {

    ArrayList<StudyPlan> getPlans();

    void save();

    void saveAllPlansForUser(ArrayList<StudyPlan> plans);


}
