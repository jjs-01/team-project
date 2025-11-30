package com.studyarc.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class User implements Serializable {
    private final byte[] salt;
    private final byte[] passwordHash;
    private final String username;
    private final ArrayList<StudyPlan> studyPlans = new ArrayList<>();

    private String focus;

    public User(String username, String password) throws NoSuchAlgorithmException {
        Random rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        this.username = username;
        this.salt = salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        this.passwordHash = md.digest();
    }

    public User(String username, String password, byte[] salt) throws NoSuchAlgorithmException {
        this.username = username;
        this.salt = salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        this.passwordHash = md.digest();
    }

    public boolean validateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        return Arrays.equals(md.digest(), this.passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }
}
