package com.studyarc.use_case.add_papers_to_plan;

import com.studyarc.entity.ResearchPaper;
import java.util.List;

public class AddPapersToPlanOutputData {
    private final String planName;
    private final List<ResearchPaper> addedPapers;
    private final boolean success;

    public AddPapersToPlanOutputData(String planName, List<ResearchPaper> addedPapers, boolean success) {
        this.planName = planName;
        this.addedPapers = addedPapers;
        this.success = success;
    }

    public String getPlanName() {
        return planName;
    }

    public List<ResearchPaper> getAddedPapers() {
        return addedPapers;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPaperCount() {
        return addedPapers != null ? addedPapers.size() : 0;
    }
}