package com.studyarc.use_case.milestone_tasks;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class MilestoneTasksInteractor implements MilestoneTasksInputBoundary {
    private final MilestoneTasksDataAccessInterface milestoneDataAccessObject;
    private final MilestoneTasksOutputBoundary milestonePresenter;
    private final DatabaseAccess userData = new DatabaseAccess();

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
            User user = userData.getUser("");

            ArrayList<Milestone> milestones = getMilestones(milestoneInputData);

            StudyPlan plan = milestoneDataAccessObject.getPlan(user, milestoneInputData.getStudyPlanName());
            plan.setMilestones(milestones);
            milestoneDataAccessObject.savePlan(userData.getUser(""), plan);
            plan.setFocus(milestoneInputData.getFocus());
            milestoneDataAccessObject.savePlan(user, plan);

            // hardcoded for now (output data should be with the studyplan name)
            final MilestoneTasksOutputData outputData;
            System.out.println(milestones.size());
            if (milestones.isEmpty()) {
                outputData = new MilestoneTasksOutputData("empty milestones" + " focus: " + plan.getFocus());
            } else {
                outputData = new MilestoneTasksOutputData(milestones.get(0).getTitle() + " focus: " + plan.getFocus());
            }
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
