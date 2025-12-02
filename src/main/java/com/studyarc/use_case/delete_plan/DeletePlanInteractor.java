package com.studyarc.use_case.delete_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/***
 * Interactor class for the Delete Plan use case
 */


public class DeletePlanInteractor implements DeletePlanInputBoundary {
    private final DeletePlanOutputBoundary presenter;
    private final TrackPlanDataAccessInterface dataAccessTool;


    public DeletePlanInteractor(DeletePlanOutputBoundary presenter, TrackPlanDataAccessInterface dataAccessTool) {
        this.dataAccessTool = dataAccessTool;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeletePlanInputData input) {
        StudyPlan plan = input.getPlan();
        List<StudyPlan> plans = dataAccessTool.getPlans();
        plans.remove(plan);
        dataAccessTool.save();
        presenter.showPlans(new DeletePlanOutputData(plan));
    }
}
