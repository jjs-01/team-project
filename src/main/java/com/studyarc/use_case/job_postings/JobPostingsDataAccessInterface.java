package com.studyarc.use_case.job_postings;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

/**
 * DAO interface for the Login Use Case.
 */
public interface JobPostingsDataAccessInterface {

    /**
     * Returns the user's focuses from all the plans they created
     * @return
     */
    ArrayList<String> getFocuses(ArrayList<StudyPlan> allStudyPlans);
}
