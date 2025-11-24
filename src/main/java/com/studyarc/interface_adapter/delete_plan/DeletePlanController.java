package com.studyarc.interface_adapter.delete_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.delete_plan.DeletePlanInputBoundary;
import com.studyarc.use_case.delete_plan.DeletePlanInputData;

public class DeletePlanController {
    private DeletePlanInputBoundary interactor;

    public  DeletePlanController(DeletePlanInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(StudyPlan plan){
        System.out.println("Deleted Controller executes with plan:" + plan.getTitle());
        DeletePlanInputData input = new DeletePlanInputData(plan);
        this.interactor.execute(input);
    }
}
