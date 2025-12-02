// New file: SearchResearchPapersDataAccessInterface.java
package com.studyarc.use_case.search_research_papers;

import com.studyarc.entity.ResearchPaper;
import java.util.List;

public interface SearchResearchPapersDataAccessInterface {


    SearchResult searchPapers(String query, int limit, int offset);


    SearchResult searchPapersByYear(String query, int yearFrom, int yearTo, int limit);

    
    ResearchPaper getPaperById(String paperId);

    class SearchResult {
        private final int totalHits;
        private final List<ResearchPaper> papers;

        public SearchResult(int totalHits, List<ResearchPaper> papers) {
            this.totalHits = totalHits;
            this.papers = papers;
        }

        public int getTotalHits() { return totalHits; }
        public List<ResearchPaper> getPapers() { return papers; }
    }
}
