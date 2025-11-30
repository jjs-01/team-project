package com.studyarc.use_case.add_plan;

import com.studyarc.entity.StudyPlan;

public interface AddPlanDataAccessInterface {
    String getCurrentUsername();

    void addPlan(String username, StudyPlan plan);
}
