package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Interactor for the saving milestones use case
 */
public class MilestoneTasksInteractor implements MilestoneTasksInputBoundary {
    private final MilestoneTasksDataAccessInterface milestoneDataAccessObject;
    private final MilestoneTasksOutputBoundary milestonePresenter;

    public MilestoneTasksInteractor (MilestoneTasksDataAccessInterface milestoneDataAccessObject,
                                     MilestoneTasksOutputBoundary milestonePresenter) {
        this.milestoneDataAccessObject = milestoneDataAccessObject;
        this.milestonePresenter = milestonePresenter;
    }

    @Override
    public void execute(MilestoneTasksInputData milestoneInputData) {
        Set<String> set = new HashSet<>(milestoneInputData.getMilestoneNames());
        if (set.size() < milestoneInputData.getMilestoneNames().size()) {
            milestonePresenter.prepareFailView("Can't have more than one milestone with the same name");
        } else if (milestoneInputData.getMilestoneNames().contains("")) {
            milestonePresenter.prepareFailView("Can't save a study plan with an empty title");
        }
        else {
            ArrayList<Milestone> milestones = getMilestones(milestoneInputData);

            StudyPlan plan = milestoneDataAccessObject.getPlan(milestoneInputData.getStudyPlanName());
            plan.setMilestones(milestones);
            plan.setFocus(milestoneInputData.getFocus());
            milestoneDataAccessObject.save();

            final MilestoneTasksOutputData outputData = new MilestoneTasksOutputData(milestones.size(),
                    milestoneInputData.getStudyPlanName());
            milestonePresenter.prepareSuccessView(outputData);
        }
    }

    private static ArrayList<Milestone> getMilestones(MilestoneTasksInputData milestoneInputData) {
        ArrayList<Milestone> milestones = new ArrayList<>();

        for (int i = 0; i < milestoneInputData.getMilestoneNames().size(); i++) {
            List<String[]> taskInfoList = milestoneInputData.getMilestoneIndexToTasks().get(i);
            List<Task> tasksForCurrMilestone = new ArrayList<>();

            for (String[] taskInfo : taskInfoList) {
                Task newTask = new Task(taskInfo[0], taskInfo[1], taskInfo[2]);
                tasksForCurrMilestone.add(newTask);
            }

            Milestone newMilestone = new Milestone(milestoneInputData.getMilestoneNames().get(i),
                    milestoneInputData.getMilestoneDates().get(i),
                    tasksForCurrMilestone);
            milestones.add(newMilestone);
        }
        return milestones;
    }
}
