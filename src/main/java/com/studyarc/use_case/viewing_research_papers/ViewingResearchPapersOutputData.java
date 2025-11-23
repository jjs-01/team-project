package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import java.util.List;

public class ViewingResearchPapersOutputData {
    private List<ResearchPaper> papers;
    private boolean hasPapers;

    // Fix the constructor - remove the empty one and use this:
    public ViewingResearchPapersOutputData(List<ResearchPaper> papers, boolean hasPapers) {
        this.papers = papers;
        this.hasPapers = hasPapers;
    }

    // Remove the ViewPapersOutputData method - it's not needed

    public List<ResearchPaper> getPapers() { return papers; }
    public boolean hasPapers() { return hasPapers; }
}