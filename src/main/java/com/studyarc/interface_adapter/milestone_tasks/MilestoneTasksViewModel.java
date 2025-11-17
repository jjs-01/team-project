package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.entity.Milestone;
import com.studyarc.interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksViewModel extends ViewModel<MilestoneTasksState> {

    public static final String TITLE_LABLE = "Study Plan View";
    public static final List<Milestone> MILESTONES = new ArrayList<>();

    public MilestoneTasksViewModel(String viewName) {
        super("");
        setState(new MilestoneTasksState());
    }
}
