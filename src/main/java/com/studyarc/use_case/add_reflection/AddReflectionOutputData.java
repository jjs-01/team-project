package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.Reflection;

public class AddReflectionOutputData {
    private final String planTitle;
    private final Reflection reflection;

    public AddReflectionOutputData(String planTitle, Reflection reflection) {
        this.planTitle = planTitle;
        this.reflection = reflection;
    }

    public String getPlanTitle() {
        return planTitle;
    }
    public Reflection getReflection() {
        return reflection;
    }
}
