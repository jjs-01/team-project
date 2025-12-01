package com.studyarc.use_case.add_reflection;

public class AddReflectionInputData {
    private final String username;
    private final String planTitle;
    private final String contents;

    public AddReflectionInputData(String username, String planTitle, String contents) {
        this.username = username;
        this.planTitle = planTitle;
        this.contents = contents;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public String getContents() {
        return contents;
    }

}
