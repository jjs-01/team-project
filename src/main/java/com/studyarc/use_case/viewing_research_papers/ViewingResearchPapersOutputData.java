package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import java.util.List;

public class ViewingResearchPapersOutputData {

    private List<ResearchPaper> papers;
    private boolean hasPapers;

    public void ViewPapersOutputData(List<ResearchPaper> papers, boolean hasPapers) {
        this.papers = papers;
        this.hasPapers = hasPapers;
    }

    public List<ResearchPaper> getPapers() { return papers; }
    public boolean hasPapers() { return hasPapers; }

}
