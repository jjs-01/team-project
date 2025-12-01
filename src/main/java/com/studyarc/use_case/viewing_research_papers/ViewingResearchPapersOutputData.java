package com.studyarc.use_case.viewing_research_papers;

import com.studyarc.entity.StudyPlan;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersOutputData {
    private final List<StudyPlan> plans;
    private final boolean hasPlans;

    public ViewingResearchPapersOutputData(List<StudyPlan> plans, boolean hasPlans) {
        this.plans = plans;
        this.hasPlans = hasPlans;
    }

    public List<StudyPlan> getPlans() {
        return plans;
    }

    public boolean hasPlans() {
        return hasPlans;
    }
}