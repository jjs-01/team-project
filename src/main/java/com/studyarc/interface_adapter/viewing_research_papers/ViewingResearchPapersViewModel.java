package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.entity.StudyPlan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersViewModel {
    public static final String PLANS_PROPERTY = "studyPlans";
    public static final String HAS_PLANS_PROPERTY = "hasPlans";

    private String errorMessage;
    private final PropertyChangeSupport support;
    private List<StudyPlan> studyPlans;  // Changed from List<ResearchPaperState>
    private boolean hasPlans;

    public ViewingResearchPapersViewModel() {
        this.support = new PropertyChangeSupport(this);
        this.studyPlans = new ArrayList<>();
        this.hasPlans = false;
    }

    public List<StudyPlan> getStudyPlans() {
        return new ArrayList<>(studyPlans);
    }

    public void setStudyPlans(List<StudyPlan> studyPlans) {
        List<StudyPlan> oldPlans = this.studyPlans;
        this.studyPlans = new ArrayList<>(studyPlans);
        support.firePropertyChange(PLANS_PROPERTY, oldPlans, this.studyPlans);
    }

    public boolean hasPlans() {
        return hasPlans;
    }

    public void setHasPlans(boolean hasPlans) {
        boolean oldValue = this.hasPlans;
        this.hasPlans = hasPlans;
        support.firePropertyChange(HAS_PLANS_PROPERTY, oldValue, hasPlans);
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