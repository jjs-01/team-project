package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.entity.ResearchPaper;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersViewModel {
    public static final String PAPERS_PROPERTY = "researchPapers";
    public static final String HAS_PAPERS_PROPERTY = "hasPapers";

    private final PropertyChangeSupport support;
    private List<ResearchPaper> researchPapers;
    private boolean hasPapers;

    public ViewingResearchPapersViewModel() {
        this.support = new PropertyChangeSupport(this);
        this.researchPapers = new ArrayList<>();
        this.hasPapers = false;
    }


    public List<ResearchPaper> getResearchPapers() {
        return new ArrayList<>(researchPapers);
    }


    public void setResearchPapers(List<ResearchPaper> researchPapers) {
        List<ResearchPaper> oldPapers = this.researchPapers;
        this.researchPapers = new ArrayList<>(researchPapers);
        support.firePropertyChange(PAPERS_PROPERTY, oldPapers, this.researchPapers);
    }


    public boolean hasPapers() {
        return hasPapers;
    }


    public void setHasPapers(boolean hasPapers) {
        boolean oldValue = this.hasPapers;
        this.hasPapers = hasPapers;
        support.firePropertyChange(HAS_PAPERS_PROPERTY, oldValue, hasPapers);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public String getViewName() {
        return "viewing research papers";
    }

}