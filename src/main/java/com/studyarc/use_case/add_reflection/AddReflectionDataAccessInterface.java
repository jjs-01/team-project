package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.StudyPlan;

/**
 * The DAO interface for the AddReflection Use Case.
 */
public interface AddReflectionDataAccessInterface {

    /**
     * Return the specific plan that wants to add reflection.
     * @param planName the plan title of this plan.
     * @return the plan that wants to add reflection
     */
    StudyPlan getPlan(String planName);

    /**
     * Saves the updated study plan.
     * @param username the current user
     * @param plan the updated study plan containing the new reflection
     */
    void savePlan(String username, StudyPlan plan);
}


