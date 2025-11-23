package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData;

public class ViewingResearchPapersPresenter implements ViewingResearchPapersOutputBoundary {
    private final ViewingResearchPapersViewModel viewModel;

    public ViewingResearchPapersPresenter(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewingResearchPapersOutputData outputData) {
        viewModel.setResearchPapers(outputData.getPapers());
        viewModel.setHasPapers(outputData.hasPapers());
    }
}