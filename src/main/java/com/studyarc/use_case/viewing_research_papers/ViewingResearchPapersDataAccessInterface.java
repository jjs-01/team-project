package com.studyarc.use_case.viewing_research_papers;
import com.studyarc.entity.ResearchPaper;
import java.util.List;

public interface ViewingResearchPapersDataAccessInterface {
    List<ResearchPaper> getAllPapers();
}