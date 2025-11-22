package com.studyarc.interface_adapter.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.use_case.add_reflection.AddReflectionOutputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionOutputData;

import java.util.List;
import java.util.Map;

public class AddReflectionPresenter implements AddReflectionOutputBoundary {
    private final AddReflectionViewModel reflectionViewModel;

    public AddReflectionPresenter(AddReflectionViewModel reflectionViewModel) {
        this.reflectionViewModel = reflectionViewModel;
    }

    @Override
    public void prepareSuccessView(AddReflectionOutputData outputData) {
        AddReflectionState state = reflectionViewModel.getState();
        state.setSuccess("Reflection added");
        state.setError(null);
        reflectionViewModel.firePropertyChange("add reflection");

    }

    @Override
    public void prepareFailView(String error) {
        AddReflectionState state = reflectionViewModel.getState();
        state.setError(error);
        state.setSuccess(null);
        reflectionViewModel.firePropertyChange("add reflection");

    }
}
