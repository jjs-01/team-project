package com.studyarc.interface_adapter.track_plan;

import com.studyarc.interface_adapter.ViewModel;

/***
 * View Model class for the Track Plan use case
 */
public class TrackPlanViewModel extends ViewModel<TrackPlanState> {
    public TrackPlanViewModel() {
        super("track plan");
        this.setState(new TrackPlanState());
    }
}
