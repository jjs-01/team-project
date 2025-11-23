package com.studyarc.data_access;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

public class MockResearchPapersDataAccess implements ViewingResearchPapersDataAccessInterface {
    @Override
    public List<ResearchPaper> getAllPapers() {
        List<ResearchPaper> papers = new ArrayList<>();

        // Add some test data with all 5 required parameters: id, title, authors, abstractText, url
        papers.add(new ResearchPaper(
                "1",
                "Deep Learning for Computer Vision",
                "Smith, J., Johnson, A.",
                "This paper explores advanced deep learning techniques for image recognition and classification.",
                "http://example.com/paper1"
        ));

        papers.add(new ResearchPaper(
                "2",
                "Natural Language Processing with Transformers",
                "Williams, B., Brown, C.",
                "An investigation into transformer architectures for improved language understanding.",
                "http://example.com/paper2"
        ));

        papers.add(new ResearchPaper(
                "3",
                "Quantum Computing Applications",
                "Davis, M.",
                "Exploring practical applications of quantum computing in modern algorithms.",
                "http://example.com/paper3"
        ));

        return papers;
    }
}