package com.studyarc.interface_adapter.ui_sidebar;

public class SidebarState {

    private String userName;
    private boolean visible;

    public void setUserName(String userName) {
        this.userName = userName;
        this.visible = false;
    }

    public String getUserName() {
        return userName;
    }

}
