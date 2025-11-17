package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

public interface AddReflectionDataAccessInterface {
    User getCurrentUser();
    StudyPlan getPlan(User user, String planName);
    void savePlan(User user, StudyPlan plan);
}


