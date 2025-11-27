package com.studyarc.interface_adapter.ui_sidebar;

import com.studyarc.interface_adapter.ViewModel;

public class SidebarViewModel extends ViewModel<SidebarState> {
    public SidebarViewModel() {
        super("side panel");
        setState(new SidebarState());
    }
}
