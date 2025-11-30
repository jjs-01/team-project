package com.studyarc.entity;

import java.io.Serializable;

public class Reflection implements Serializable {
    private final String contents;

    public Reflection(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}

