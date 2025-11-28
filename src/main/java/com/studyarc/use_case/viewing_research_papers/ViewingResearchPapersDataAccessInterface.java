package com.studyarc.use_case.viewing_research_papers;

import com.studyarc.entity.StudyPlan;
import java.util.ArrayList;

public interface ViewingResearchPapersDataAccessInterface {
    ArrayList<StudyPlan> getPlans(String username);
    ArrayList<StudyPlan> generateTestPlans(); // Optional, for testing
}