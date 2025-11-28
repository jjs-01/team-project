package com.studyarc.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class User {
    private byte[] salt;
    private byte[] passwordHash;
    private String username;

    private String focus;

    public User(String username, String password, String focus) throws NoSuchAlgorithmException {
        Random rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        this.username = username;
        this.salt = salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        this.passwordHash = md.digest();
        this.focus = focus;
    }

    public User(String username, String password, byte[] salt, String focus) throws NoSuchAlgorithmException {
        this.username = username;
        this.salt = salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        md.update(this.salt);
        this.passwordHash = md.digest();
        this.focus = focus;
    }

    private ArrayList<StudyPlan> studyPlans;

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

}
