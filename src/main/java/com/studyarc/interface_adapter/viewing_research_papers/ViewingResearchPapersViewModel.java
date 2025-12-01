package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.entity.StudyPlan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersViewModel {
    public static final String PLANS_PROPERTY = "studyPlans";
    public static final String HAS_PLANS_PROPERTY = "hasPlans";
    public static final String REFRESH_PROPERTY = "refresh";

    private String errorMessage;
    private String successMessage;
    private final PropertyChangeSupport support;
    private List<StudyPlan> studyPlans;
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
        System.out.println("setStudyPlans called with " + studyPlans.size() + " plans");

        // CRITICAL: Always create a new list to ensure property change fires
        this.studyPlans = new ArrayList<>(studyPlans);

        System.out.println("Firing property change for PLANS_PROPERTY");

        // Fire property change with null old value to FORCE it to fire
        support.firePropertyChange(PLANS_PROPERTY, null, this.studyPlans);

        System.out.println("Property change fired for PLANS_PROPERTY");
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
        System.out.println("PropertyChangeListener added to ViewModel");
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
        this.successMessage = null; // Clear success message when showing error
        support.firePropertyChange("errorMessage", oldMessage, errorMessage);
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        String oldMessage = this.successMessage;
        this.successMessage = successMessage;
        this.errorMessage = null; // Clear error message when showing success
        support.firePropertyChange("successMessage", oldMessage, successMessage);
    }

    /**
     * Trigger a refresh of the view data.
     * This fires the REFRESH_PROPERTY change which tells the view to reload plans.
     */
    public void firePropertyChange() {
        System.out.println("firePropertyChange() called - triggering REFRESH_PROPERTY");
        // Fire with different objects to ensure it's always detected as a change
        support.firePropertyChange(REFRESH_PROPERTY, null, new Object());
        System.out.println("REFRESH_PROPERTY fired");
    }
}