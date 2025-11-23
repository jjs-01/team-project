package com.studyarc.interface_adapter.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInteractor;

import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersViewModel {
    private ViewingResearchPapersInteractor interactor;
    private List<ResearchPaper> researchPapers;

    public void ViewResearchPapersViewModel(ViewingResearchPapersInteractor interactor) {
        this.interactor = interactor;
        this.researchPapers = new ArrayList<>();
    }


    public void loadResearchPapers() {
        List<ResearchPaper> papersFromInteractor = interactor.fetchResearchPapers();
        if (papersFromInteractor != null) {
            this.researchPapers = papersFromInteractor;
        } else {
            this.researchPapers = new ArrayList<>();
        }
    }

    /**
     * Returns the current list of research papers.
     * The view calls this when updating the table.
     */
    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }
}
}
