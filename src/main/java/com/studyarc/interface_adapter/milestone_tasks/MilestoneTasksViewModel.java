package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.interface_adapter.ViewModel;

public class MilestoneTasksViewModel extends ViewModel<MilestoneTasksState> {

    public static final String TITLE_LABEL = "Study Plan View";

    public static final String[] BASE_MILESTONE_FIELDS = {"Milestone Name", "XX/XX/XXXX"};
    public static final String[] BASE_TASK_STATUS_OPTIONS = {"Not started", "In progress", "Done"};
    public static final String[] BASE_TASK_FIELDS = {"Task Name", "XX/XX/XXXX", BASE_TASK_STATUS_OPTIONS[0]};

    public static final String MILESTONE_INDEX_KEY = "milestone index";
    public static final String TASK_INDEX_KEY = "task index";


    public MilestoneTasksViewModel() {
        super("milestones and tasks");
        setState(new MilestoneTasksState());
    }
}
