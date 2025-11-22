package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import java.util.List;

public class ViewingResearchPapersInteractor implements com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInputBoundary {
    private com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface repository;
    private com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary presenter;

    public void ViewingResearchPapersInteractor(com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface repository,
                                                com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void viewPapers(com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInputData inputData) {
        List<ResearchPaper> papers = repository.getAllPapers();
        boolean hasPapers = !papers.isEmpty();
        com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData outputData =
                new com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData(papers, hasPapers);


        presenter.present(outputData);


    }
}
