package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

/**
 * The DAO interface for the AddReflection Use Case.
 */
public interface AddReflectionDataAccessInterface {

    /**
     * Returns the user of the current user of the application.
     * @return the username of the current user
     */
    User getUser(String username);

    /**
     * Return the specific plan that wants to add reflection.
     * @param user the current user.
     * @param planName the plan title of this plan.
     * @return the plan that wants to add reflection
     */
    StudyPlan getPlan(User user, String planName);

    /**
     * Saves the updated study plan.
     * @param user the current user
     * @param plan the updated study plan containing the new reflection
     */
    void savePlan(User user, StudyPlan plan);
}


