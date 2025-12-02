package com.studyarc.use_case.viewing_research_papers;

import com.studyarc.entity.StudyPlan;
import java.util.List;

public interface ViewingResearchPapersDataAccessInterface {
    /**
     * Get all study plans for the current logged-in user
     * @return List of study plans with their research papers
     */
    List<StudyPlan> getPlans();  // NO PARAMETER
}