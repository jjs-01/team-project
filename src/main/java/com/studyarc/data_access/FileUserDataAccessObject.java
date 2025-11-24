package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements MilestoneTasksDataAccessInterface {

    private static final String HEADER = "username,password";

//    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

//    /**
//     * Construct this DAO for saving to and reading from a local file.
//     * @param csvPath the path of the file to save to
//     * @throws RuntimeException if there is an IOException when accessing the file
//     */
//    public FileUserDataAccessObject(String csvPath) {
//
//        csvFile = new File(csvPath);
//        headers.put("username", 0);
//        headers.put("password", 1);
//
//        if (csvFile.length() == 0) {
//            save();
//        }
//        else {
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
//                final String header = reader.readLine();
//
//                if (!header.equals(HEADER)) {
//                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
//                }
//
//                String row;
//                while ((row = reader.readLine()) != null) {
//                    final String[] col = row.split(",");
//                    final String username = String.valueOf(col[headers.get("username")]);
//                    final String password = String.valueOf(col[headers.get("password")]);
//                    final User user = userFactory.create(username, password);
//                    accounts.put(username, user);
//                }
//            }
//            catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    private void save() {
//        final BufferedWriter writer;
//        try {
//            writer = new BufferedWriter(new FileWriter(csvFile));
//            writer.write(String.join(",", headers.keySet()));
//            writer.newLine();
//
//            for (User user : accounts.values()) {
//                final String line = String.format("%s,%s",
//                        user.getName(), user.getPassword());
//                writer.write(line);
//                writer.newLine();
//            }
//
//            writer.close();
//
//        }
//        catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    @Override
//    public void save(User user) {
//        accounts.put(user.getName(), user);
//        this.save();
//    }

    public User get(String username) {
        return accounts.get(username);
    }

    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone) {
        return null;
    }

    @Override
    public ArrayList<Milestone> getMilestones(User user, StudyPlan plan) {
        return null;
    }

    @Override
    public void savePlan(User user, StudyPlan plan) {

    }

    @Override
    public ArrayList<StudyPlan> getPlans(User user) {
        return null;
    }

    @Override
    public User getUser(String username) {
        return null;
    }
}
