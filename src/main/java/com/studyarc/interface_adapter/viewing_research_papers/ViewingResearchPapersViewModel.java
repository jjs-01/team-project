package com.studyarc.interface_adapter.viewing_research_papers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersViewModel {
    public static final String PAPERS_PROPERTY = "researchPapers";
    public static final String HAS_PAPERS_PROPERTY = "hasPapers";

    private String errorMessage;
    private final PropertyChangeSupport support;
    private List<ResearchPaperState> researchPapers;
    private boolean hasPapers;

    public ViewingResearchPapersViewModel() {
        this.support = new PropertyChangeSupport(this);
        this.researchPapers = new ArrayList<>();
        this.hasPapers = false;
    }

    public List<ResearchPaperState> getResearchPapers() {
        return new ArrayList<>(researchPapers);
    }

    public void setResearchPapers(List<ResearchPaperState> researchPapers) {
        List<ResearchPaperState> oldPapers = this.researchPapers;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        String oldMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldMessage, errorMessage);
    }
}