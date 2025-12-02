package com.studyarc.interface_adapter.delete_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.delete_plan.DeletePlanOutputBoundary;
import com.studyarc.use_case.delete_plan.DeletePlanOutputData;

import java.util.List;

/***
 * Presenter class for the Delete Plan use case
 * This class is responsible for updating the TrackPlanViewModel with the output data from the Delete Plan use case.
 */

public class DeletePlanPresenter implements DeletePlanOutputBoundary {
    private final TrackPlanViewModel trackPlanViewModel;

    public DeletePlanPresenter(TrackPlanViewModel trackPlanViewModel) {
        this.trackPlanViewModel = trackPlanViewModel;
    }

    @Override
    public void showPlans(DeletePlanOutputData outputData) {

        StudyPlan deletedPlan = outputData.getPlan();
        List<StudyPlan> plans = trackPlanViewModel.getState().getStudyPlans();
        plans.remove(deletedPlan);
        trackPlanViewModel.firePropertyChange();
    }
}
