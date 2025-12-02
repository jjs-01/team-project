package com.studyarc.interface_adapter.add_papers_to_plan;

import com.studyarc.use_case.add_papers_to_plan.AddPapersToPlanInputBoundary;
import com.studyarc.use_case.add_papers_to_plan.AddPapersToPlanInputData;

public class AddPapersToPlanController {
    private final AddPapersToPlanInputBoundary interactor;

    public AddPapersToPlanController(AddPapersToPlanInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String planName, String searchQuery, int limit) {
        AddPapersToPlanInputData inputData = new AddPapersToPlanInputData(planName, searchQuery, limit);
        interactor.execute(inputData);
    }
}