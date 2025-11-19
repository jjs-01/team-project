package com.studyarc.entity;

import java.util.ArrayList;
import java.util.List;

public class StudyPlan {
    private final List<Reflection> reflections = new ArrayList<>();



    public void addReflection(Reflection r) {
        reflections.add(r);
    }
}
