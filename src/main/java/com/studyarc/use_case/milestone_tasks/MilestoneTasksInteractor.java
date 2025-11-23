package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

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
        if (true) {
            milestonePresenter.prepareFailView("Can't save a plan yet");
        } else {
            User user = milestoneDataAccessObject.getUser("");

            String targetPlanTitle = milestoneInputData.getStudyPlanName();
            for (StudyPlan plan : milestoneDataAccessObject.getPlans(user)) {
                if (plan.getTitle().equals(targetPlanTitle)) {
                    StudyPlan targetPlan = plan;
                    targetPlan.setMilestones(milestoneInputData.getMilestones());
                    milestoneDataAccessObject.savePlan(user, targetPlan);
                    break;
                }
            }
            final MilestoneTasksOutputData outputData = new MilestoneTasksOutputData("user");
            milestonePresenter.prepareSuccessView(outputData);
        }
    }
}
