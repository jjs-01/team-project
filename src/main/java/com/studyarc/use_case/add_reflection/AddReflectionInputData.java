package com.studyarc.use_case.add_reflection;

public class AddReflectionInputData {
    private final String planTitle;
    private final String contents;

    public AddReflectionInputData(String planTitle, String contents) {
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
