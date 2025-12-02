package com.studyarc.interface_adapter.track_plan;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.use_case.track_plan.TrackPlanOutputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanOutputData;
/***
 * Presenter class for the Track Plan use case
 * This class is responsible for updating the TrackPlanViewModel with the output data from the Track Plan use case.
 */

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

        TrackPlanState state = trackPlanViewModel.getState();
        state.setStudyPlans(outputData.getListOfPlan());
        trackPlanViewModel.firePropertyChange();

        this.viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();

    }

    @Override
    public void prepareShowRedirect() {
        trackPlanViewModel.firePropertyChange();

        this.viewManagerModel.setState(trackPlanViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareShowSavingResult(String message) {
        TrackPlanState state = trackPlanViewModel.getState();
        state.setSavingMessage(message);
        trackPlanViewModel.firePropertyChange();

        trackPlanViewModel.getState().setSavingMessage("");
    }
}
