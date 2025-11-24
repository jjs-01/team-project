package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.interface_adapter.ViewModel;

public class MilestoneTasksViewModel extends ViewModel<MilestoneTasksState> {

    public static final String TITLE_LABEL = "Study Plan View";
    public static final String FONT = "SansSerif";

    public static final String BASE_MILESTONE_NAME = "Milestone Name";
    public static final String BASE_MILESTONE_DATE = "XX/XX/XXXX";

    public static final String BASE_TASK_STATUS_1 = "Not started";
    public static final String BASE_TASK_STATUS_2 = "In progress";
    public static final String BASE_TASK_STATUS_3 = "Done";

    public static final String BASE_TASK_NAME = "Task Name";
    public static final String BASE_TASK_DATE = "XX/XX/XXXX";

    public static final String MILESTONE_INDEX_KEY = "milestone index";
    public static final String TASK_INDEX_KEY = "task index";


    public MilestoneTasksViewModel() {
        super("milestones and tasks");
        setState(new MilestoneTasksState());
    }
}
