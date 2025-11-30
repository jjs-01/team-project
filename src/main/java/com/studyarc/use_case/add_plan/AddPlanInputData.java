package com.studyarc.use_case.add_plan;

import java.util.List;

public class AddPlanInputData {
    private final List<String> studyPlanTitles;

    public AddPlanInputData(List<String> studyPlanTitles) {
        this.studyPlanTitles = studyPlanTitles;
    }

    public List<String> getStudyPlanTitles() {
        return this.studyPlanTitles;
    }
}
