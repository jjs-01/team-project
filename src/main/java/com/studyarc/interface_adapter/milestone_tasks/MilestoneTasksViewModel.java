package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.interface_adapter.ViewModel;
import com.studyarc.interface_adapter.job_postings.JobPostingsState;

public class MilestoneTasksViewModel extends ViewModel<MilestoneTasksState> {

    public MilestoneTasksViewModel(){
        super("milestone tasks");
        setState(new MilestoneTasksState());
    }

}
