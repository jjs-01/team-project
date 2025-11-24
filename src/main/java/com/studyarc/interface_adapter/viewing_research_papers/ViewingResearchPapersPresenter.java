package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewingResearchPapersPresenter implements ViewingResearchPapersOutputBoundary {
    private final ViewingResearchPapersViewModel viewModel;

    public ViewingResearchPapersPresenter(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewingResearchPapersOutputData outputData) {
        // Convert ResearchPaper entities to ResearchPaperState objects
        // This maintains Clean Architecture by keeping entities out of the ViewModel
        List<ResearchPaperState> paperStates = outputData.getPapers().stream()
                .map(paper -> new ResearchPaperState(
                        paper.getId(),
                        paper.getTitle(),
                        paper.getAuthors(),
                        paper.getAbstractText(),
                        paper.getUrl()
                ))
                .collect(Collectors.toList());

        viewModel.setResearchPapers(paperStates);
        viewModel.setHasPapers(outputData.hasPapers());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error loading papers: " + errorMessage);
        // Set a user-friendly error message for the GUI
        viewModel.setErrorMessage("Unable to load research papers. Please try again later.");
        viewModel.setResearchPapers(new ArrayList<>());
        viewModel.setHasPapers(false);
    }
}