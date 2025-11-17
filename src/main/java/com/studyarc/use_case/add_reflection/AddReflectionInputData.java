package com.studyarc.use_case.add_reflection;

public class AddReflectionInputData {
    private final String planName;
    private final String contents;

    public AddReflectionInputData(String planName, String contents) {
        this.planName = planName;
        this.contents = contents;
    }

    public String getPlanName() {
        return planName;
    }

    public String getContents() {
        return contents;
    }

}
