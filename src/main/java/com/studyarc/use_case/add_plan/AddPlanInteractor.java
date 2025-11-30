package com.studyarc.use_case.add_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddPlanInteractor implements AddPlanInputBoundary {
    private final AddPlanDataAccessInterface addPlanDataAccessObject;
    private final AddPlanOutputBoundary addPlanPresenter;

    public AddPlanInteractor(AddPlanDataAccessInterface addPlanDataAccessInterface,
                             AddPlanOutputBoundary addPlanPresenter) {
        this.addPlanDataAccessObject = addPlanDataAccessInterface;
        this.addPlanPresenter = addPlanPresenter;
    }

    @Override
    public void execute(AddPlanInputData addPlanInputData) {
        // loads a new studyplan with default name
        String uniqueDefaultTitle = getNextUniqueDefaultTitle(addPlanInputData.getStudyPlanTitles());
        StudyPlan newStudyPlan = new StudyPlan(uniqueDefaultTitle,
                new ArrayList<>(),
                "Artificial Intelligence");

        addPlanDataAccessObject.addPlan(addPlanDataAccessObject.getCurrentUsername(), newStudyPlan);

        AddPlanOutputData outputData = new AddPlanOutputData(newStudyPlan);
        addPlanPresenter.prepareSuccessView(outputData);

        // need to decide if we need a fail view
    }

    private String getNextUniqueDefaultTitle(List<String> studyPlanTitles) {
        Set<Integer> seenIndexes = new HashSet<>();
        for (String title : studyPlanTitles) {
            if (title.contains("Untitled Plan ")) {
                try {
                    seenIndexes.add(Integer.parseInt(title.substring(14)));
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
}
