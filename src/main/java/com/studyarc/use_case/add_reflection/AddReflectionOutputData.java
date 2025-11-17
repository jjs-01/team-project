package com.studyarc.use_case.add_reflection;

import java.time.LocalDate;

public class AddReflectionOutputData {
    private final String contents;
    private final LocalDate date;

    public AddReflectionOutputData(String contents, LocalDate date) {
        this.contents = contents;
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public LocalDate getDate() {
        return date;
    }
}
