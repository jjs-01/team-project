package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TrackPlanState {
    private ArrayList<StudyPlan> studyPlans;
    private String Username = "";
    private String savingMessage = "";

    public String getNextDefaultTitle() {
        Set<Integer> seenIndexes = new HashSet<>();
        for (StudyPlan plan : studyPlans) {
            if (plan.getTitle().contains("Untitled Plan ")) {
                try {
                    seenIndexes.add(Integer.parseInt(plan.getTitle().substring(14)));
                } catch(NumberFormatException ignored){
                    // If it doesn't have a number starting at index 14, then it
                    // doesn't follow the Untitled Plan structure and thus doesn't need to be
                    // added.
                }
            }
        }

        int untitledIndex = 0;
        for (int i = 0; i < seenIndexes.size(); i++) {
            if (seenIndexes.contains(untitledIndex)) {
                untitledIndex++;
            }
        }

        return "Untitled Plan " + untitledIndex;
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return this.studyPlans;
    }

    public void setStudyPlans(ArrayList<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setSavingMessage(String savingMessage) {
        this.savingMessage = savingMessage;
    }

    public String getSavingMessage(){
        return this.savingMessage;
    }
}

