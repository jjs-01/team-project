package com.studyarc.interface_adapter.reflection_log;

public class AddReflectionState {
    private String error = "";
    private String success = "";

    public String getErrorMessage() {
        return error;
    }

    public String getSuccessMessage() {
        return success;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public void setSuccess(String successMessage) {
        this.success = successMessage;
    }
}
