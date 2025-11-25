package com.studyarc.view;

import com.studyarc.interface_adapter.load_milestones.LoadMilestonesController;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesState;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class LoadMilestonesView extends MilestoneTasksView implements ActionListener, PropertyChangeListener {
    private final LoadMilestonesViewModel loadViewModel;
    private LoadMilestonesController loadController;
    private final String viewName = "loaded milestones";

    private final JButton loadMilestones = new JButton("Load Milestones");
    private final JPanel topPanel;
    private final JPanel milestonePanel;

    public LoadMilestonesView(MilestoneTasksViewModel milestoneViewModel, LoadMilestonesViewModel loadViewModel) {
        super(milestoneViewModel);
        this.loadViewModel = loadViewModel;

        loadMilestones.addActionListener(this);

        topPanel = (JPanel) this.getComponents()[LoadMilestonesViewModel.TOP_PANEL_INDEX];
        topPanel.add(loadMilestones);

        milestonePanel = (JPanel) ((JScrollPane) this.getComponents()[LoadMilestonesViewModel.SCROLL_PANE_INDEX])
                .getViewport()
                .getView();

    }

    private void loadStudyPlan(List<String> milestoneNames,
                               List<String> milestoneDates,
                               List<List<String[]>> milestonesTaskList) {
        System.out.println("Not implemented yet");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == loadMilestones) {
            final LoadMilestonesState currentState = loadViewModel.getState();

            loadController.execute(currentState.getStudyPlanName());
        }
        super.actionPerformed(evt);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof LoadMilestonesState) {
            final LoadMilestonesState state = (LoadMilestonesState) evt.getNewValue();
            if (!state.getLoadError().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getLoadError());

                state.setLoadError("");
            } else if (!state.getLoaded()) {
                loadStudyPlan(state.getMilestoneNames(), state.getMilestoneDates(), state.getMilestoneIndexToTasks());

                topPanel.remove(loadMilestones);
                topPanel.revalidate();
                topPanel.repaint();
                state.setLoaded(true);
            }
        }
    }

    public void setLoadMilestonesController(LoadMilestonesController controller) {
        loadController = controller;
    }

    @Override
    public String getViewName() {
        return this.viewName;
    }
}
