package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersInteractor implements ViewingResearchPapersInputBoundary {
    private ViewingResearchPapersDataAccessInterface repository;
    private ViewingResearchPapersOutputBoundary presenter;

    public ViewingResearchPapersInteractor(ViewingResearchPapersDataAccessInterface repository,
                                           ViewingResearchPapersOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewingResearchPapersInputData inputData) {
        try {
            List<ResearchPaper> papers = repository.getAllPapers();
            boolean hasPapers = !papers.isEmpty();
            ViewingResearchPapersOutputData outputData =
                    new ViewingResearchPapersOutputData(papers, hasPapers);
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Failed to load research papers: " + e.getMessage());
        }
    }
}