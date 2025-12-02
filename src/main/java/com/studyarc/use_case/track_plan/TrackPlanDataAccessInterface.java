package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.List;

/***
 * Data Access Interface for TrackPlan usecase
 */
public interface TrackPlanDataAccessInterface {

    List<StudyPlan> getPlans();

    void save();

    void saveAllPlansForUser(List<StudyPlan> plans);


}
