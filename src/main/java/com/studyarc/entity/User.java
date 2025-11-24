package com.studyarc.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private byte[] salt;
    private byte[] passwordHash;


    private String username;
    private String password;
    private ArrayList<StudyPlan> studyPlans;

    public boolean validateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        return md.digest() == this.passwordHash;
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
