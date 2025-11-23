package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
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
    public void viewPapers(ViewingResearchPapersInputData inputData) {
        List<ResearchPaper> papers = repository.getAllPapers();
        boolean hasPapers = !papers.isEmpty();
        ViewingResearchPapersOutputData outputData =
                new ViewingResearchPapersOutputData(papers, hasPapers);
        presenter.present(outputData);


    }
}
