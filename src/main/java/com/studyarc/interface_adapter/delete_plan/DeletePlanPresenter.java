package com.studyarc.interface_adapter.delete_plan;

import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.use_case.track_plan.TrackPlanOutputData;

public class DeletePlanPresenter {
    private TrackPlanOutputData outputData;
    private TrackPlanViewModel trackPlanViewModel;

    public DeletePlanPresenter(TrackPlanViewModel trackPlanViewModel){
        this.trackPlanViewModel = trackPlanViewModel;
    }
}
