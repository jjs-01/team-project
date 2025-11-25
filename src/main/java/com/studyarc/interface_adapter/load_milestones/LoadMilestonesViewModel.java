package com.studyarc.interface_adapter.load_milestones;

import com.studyarc.interface_adapter.ViewModel;

public class LoadMilestonesViewModel extends ViewModel<LoadMilestonesState> {

    public static final int TOP_PANEL_INDEX = 0;
    public static final int SCROLL_PANE_INDEX = 1;

    public LoadMilestonesViewModel() {
        super("load milestones");
        setState(new LoadMilestonesState());
    }
}
