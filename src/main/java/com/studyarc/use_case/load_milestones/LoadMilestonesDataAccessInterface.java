package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

public interface LoadMilestonesDataAccessInterface {
    StudyPlan getPlan(String username, String planName);

    String getCurrentUsername();
}
