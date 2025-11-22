package com.studyarc.interface_adapter.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.StudyPlan;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.add_reflection.AddReflectionOutputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionOutputData;

import java.util.List;
import java.util.Map;

public class AddReflectionPresenter implements AddReflectionOutputBoundary {
    private final AddReflectionViewModel reflectionViewModel;
    private final TrackPlanViewModel trackPlanViewModel;

    public AddReflectionPresenter(AddReflectionViewModel reflectionViewModel, TrackPlanViewModel trackPlanViewModel) {
        this.reflectionViewModel = reflectionViewModel;
        this.trackPlanViewModel = trackPlanViewModel;
    }

    @Override
    public void prepareSuccessView(AddReflectionOutputData outputData) {
        AddReflectionState state = reflectionViewModel.getState();
        state.setSuccess("Reflection added");
        state.setError(null);
        reflectionViewModel.firePropertyChange("add reflection");

        TrackPlanState tpState = trackPlanViewModel.getState();
        List<StudyPlan> plans = tpState.getStudyPlans();

        for (StudyPlan p : plans) {
            if (p.getTitle().equals(outputData.getPlanTitle())) {
                p.addReflection(outputData.getReflection());
                break;
            }
        }

        trackPlanViewModel.firePropertyChange("reflection_added");

    }

    @Override
    public void prepareFailView(String error) {
        AddReflectionState state = reflectionViewModel.getState();
        state.setError(error);
        state.setSuccess(null);
        reflectionViewModel.firePropertyChange("add reflection");

    }
}
