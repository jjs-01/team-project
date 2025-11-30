package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

/**
 * Data Access Interface for the Load Milestones use case
 */
public interface LoadMilestonesDataAccessInterface {
    StudyPlan getPlan(String username, String planName);

    String getCurrentUsername();
}
