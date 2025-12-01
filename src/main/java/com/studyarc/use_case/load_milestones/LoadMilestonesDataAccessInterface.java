package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.StudyPlan;

/**
 * Data Access Interface for the Load Milestones use case
 */
public interface LoadMilestonesDataAccessInterface {
    StudyPlan getPlan(String planName);

    void save();
}
