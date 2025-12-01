package com.studyarc.entity;

public class ReflectionFactory {

    public Reflection create(String contents)  {
        return new Reflection(contents);
    }
}
