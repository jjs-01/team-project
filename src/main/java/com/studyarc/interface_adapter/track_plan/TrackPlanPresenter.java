package com.studyarc.interface_adapter.track_plan;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.use_case.track_plan.TrackPlanOutputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanOutputData;

public class TrackPlanPresenter implements TrackPlanOutputBoundary {
    private final TrackPlanViewModel trackPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public TrackPlanPresenter(TrackPlanViewModel trackPlanViewModel,
                              ViewManagerModel viewManagerModel) {
        this.trackPlanViewModel = trackPlanViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareShowPlans(TrackPlanOutputData outputData) {
        //change the Plans in the TrackPlanState and notify the viewmodel to update the view.
        TrackPlanState state = trackPlanViewModel.getState();
        state.setStudyPlans(outputData.getListofplan());
        trackPlanViewModel.firePropertyChange();

        //update the viewname in the viewmanagermodel and invoke propertychange in view manager to switch view.
        this.viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();

    }

    @Override
    public void parepareShowRedirect() {
        trackPlanViewModel.firePropertyChange();

        //update the viewname in the viewmanagermodel and invoke propertychange in view manager to switch view.
        this.viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
