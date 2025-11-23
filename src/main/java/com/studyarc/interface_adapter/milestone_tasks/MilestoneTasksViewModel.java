package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.entity.Milestone;
import com.studyarc.interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksViewModel extends ViewModel<MilestoneTasksState> {

    public static final String TITLE_LABLE = "Study Plan View";
    public static final List<Milestone> MILESTONES = new ArrayList<>();

    public static final String[] BASE_MILESTONE_FIELDS = {"Milestone Name", "XX/XX/XXXX"};
    public static final String[] BASE_TASK_STATUS_OPTIONS = {"Not started", "In progress", "Done"};
    public static final String[] BASE_TASK_FIELDS = {"Task Name", "XX/XX/XXXX", BASE_TASK_STATUS_OPTIONS[0]};


    public MilestoneTasksViewModel() {
        super("milestones and tasks");
        setState(new MilestoneTasksState());
    }
}
