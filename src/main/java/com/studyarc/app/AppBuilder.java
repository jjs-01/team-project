package com.studyarc.app;

import javax.swing.*;
import java.awt.*;

import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.view.*;

public class AppBuilder {
    private final JPanel overallPanel = new JPanel();
    private final BorderLayout borderLayout = new BorderLayout();
    private final JPanel mainUIPanel = new JPanel();
    private final JPanel usecasePanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private SidePanelView sidePanelView;
    private MilestoneTasksView milestoneTaskView;
    private MilestoneTasksViewModel milestoneViewModel;


    public AppBuilder() {
        overallPanel.setLayout(cardLayout); // includes login and other things
        mainUIPanel.setLayout(borderLayout); // for the side panel
        usecasePanel.setLayout(cardLayout);
        // another panel for switching between the panels
    }

    public AppBuilder addSidePanel() {
        sidePanelView = new SidePanelView();
        mainUIPanel.add(sidePanelView, BorderLayout.WEST);
        overallPanel.add(mainUIPanel);
        return this;
    }

    public AppBuilder addMilestoneTasksPanel() {
        milestoneViewModel = new MilestoneTasksViewModel();
        milestoneTaskView = new MilestoneTasksView(milestoneViewModel);
        mainUIPanel.add(milestoneTaskView, BorderLayout.CENTER);
        overallPanel.add(mainUIPanel);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Code Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(overallPanel);

        return application;
    }

}
