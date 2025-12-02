package com.studyarc.use_case.add_papers_to_plan;

public interface AddPapersToPlanOutputBoundary {
    void prepareSuccessView(AddPapersToPlanOutputData outputData);
    void prepareFailView(String errorMessage);
}