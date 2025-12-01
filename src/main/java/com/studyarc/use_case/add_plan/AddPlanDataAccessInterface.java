package com.studyarc.use_case.add_plan;

import com.studyarc.entity.StudyPlan;

/**
 * Data access interface for the Add Plan use case
 */
public interface AddPlanDataAccessInterface {
    void save();

    void addPlan(StudyPlan plan);
}
