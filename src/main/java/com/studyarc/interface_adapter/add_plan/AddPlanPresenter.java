package com.studyarc.interface_adapter.add_plan;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.add_plan.AddPlanOutputBoundary;
import com.studyarc.use_case.add_plan.AddPlanOutputData;

/**
 * Presenter for the Add Plan use case
 */
public class AddPlanPresenter implements AddPlanOutputBoundary {

    private final TrackPlanViewModel trackPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddPlanPresenter(ViewManagerModel viewManagerModel, TrackPlanViewModel trackPlanViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.trackPlanViewModel = trackPlanViewModel;

    }

    @Override
    public void prepareSuccessView(AddPlanOutputData response) {
        trackPlanViewModel.firePropertyChange("added plan");
    }
}
