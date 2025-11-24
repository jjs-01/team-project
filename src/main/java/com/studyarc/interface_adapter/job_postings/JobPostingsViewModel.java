package com.studyarc.interface_adapter.job_postings;

import com.studyarc.interface_adapter.ViewModel;

import java.beans.PropertyChangeSupport;

/**
 * The View Model for the Job Postings View.
 */
public class JobPostingsViewModel extends ViewModel<JobPostingsState> {

    public static final String TITLE_LABEL = "Job Board";
    public static final String PLAN_LABEL = "Plan:";
    public static final String LOCATION_LABEL = "Location:";
    public static final String SALARAY_LABEL = "Salary Minimum:";
    public static final String SORT_LABEL = "Sort:";

    public JobPostingsViewModel() {
        super("job postings");
        setState(new JobPostingsState());
    }


}
