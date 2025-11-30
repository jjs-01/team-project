package com.studyarc.interface_adapter.add_plan;

import com.studyarc.use_case.add_plan.AddPlanInputBoundary;
import com.studyarc.use_case.add_plan.AddPlanInputData;

import java.util.List;

public class AddPlanController {
    private final AddPlanInputBoundary addPlanInteractor;

    public AddPlanController(AddPlanInputBoundary addPlanInteractor) {
        this.addPlanInteractor = addPlanInteractor;
    }

    /**
     * Executes the Add Plan use case
     * @param studyPlanTitles list of all the plan titles made so far by the user
     */
    public void execute(List<String> studyPlanTitles) {
        final AddPlanInputData addPlanInputData = new AddPlanInputData(studyPlanTitles);

        addPlanInteractor.execute(addPlanInputData);
    }
}
