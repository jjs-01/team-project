package com.studyarc.app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSidePanel()
                .addLoginView()
                .addLoadMilestonesPanel()
                .addMilestoneTasksPanel()
                .addTrackPlanView()
                .addJobPostingsView()
                .addLoadMilestonesUseCase()
                .addAddPlanUseCase()
                .addTrackPlanUsecase()
                .addDeletePlanUsecase()
                .addSidebarUseCase()
                .addJobPostingsUseCase()
                .addMilestoneTasksUseCase()
                .addAddReflectionUseCase()
                .addLoginUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setSize(800, 400);
        application.setVisible(true);
    }
}
