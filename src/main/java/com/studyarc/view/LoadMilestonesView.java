package com.studyarc.view;

import com.studyarc.interface_adapter.load_milestones.LoadMilestonesState;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View for loading milestones usecase
 */
public class LoadMilestonesView extends MilestoneTasksView implements PropertyChangeListener {
    private final LoadMilestonesViewModel loadViewModel;
    private final MilestoneTasksViewModel milestoneViewModel;
    private static final String VIEW_NAME = "loaded milestones";

    private final JPanel milestonePanel;

    public LoadMilestonesView(MilestoneTasksViewModel milestoneViewModel, LoadMilestonesViewModel loadViewModel) {
        super(milestoneViewModel);
        this.milestoneViewModel = milestoneViewModel;
        this.loadViewModel = loadViewModel;
        this.loadViewModel.addPropertyChangeListener(this);

        milestonePanel = (JPanel) ((JScrollPane) this.getComponents()[LoadMilestonesViewModel.SCROLL_PANE_INDEX])
                .getViewport()
                .getView();
    }

    private void loadStudyPlan(String focus,
                               List<String> milestoneNames,
                               List<String> milestoneDates,
                               List<List<String[]>> milestonesTaskList) {
        List<JPanel> milestones = super.getMilestones();
        Map<JPanel, List<JComponent[]>> milestoneToTaskComponents = super.getMilestoneToTaskComponents();
        GridBagConstraints milestonePanelConstraints = super.getMilestonePanelConstraints();

        // first remove all current milestones:
        for (JPanel individualMilestone : milestones) {
            milestonePanel.remove(individualMilestone);
            milestonePanel.revalidate();
            milestonePanel.repaint();
        }
        milestones.clear();
        milestoneToTaskComponents.clear();


        super.getFocusSelector().setSelectedItem(focus);
        // then add all the milestones that are saved
        for (int i = 0; i < milestoneNames.size(); i++) {
            assert milestoneNames.size() == milestoneDates.size();

            JPanel individualMilestone = new JPanel();
            individualMilestone.setLayout(new GridBagLayout());
            milestones.add(individualMilestone);
            milestoneToTaskComponents.put(individualMilestone, new ArrayList<>());

            GridBagConstraints individualMilestoneConstraints = new GridBagConstraints();

            // Add milestone name textfield
            individualMilestoneConstraints.gridx = 0; individualMilestoneConstraints.gridy = 0;
            JTextField milestoneNameField = new JTextField(milestoneNames.get(i), 20);
            individualMilestone.add(milestoneNameField, individualMilestoneConstraints);
            super.addMilestoneNameListener(milestoneNameField, individualMilestone);

            // Add dueDate textfield
            individualMilestoneConstraints.gridx = 2;
            JTextField milestoneDateField = new JTextField(milestoneDates.get(i), 10);
            individualMilestone.add(milestoneDateField, individualMilestoneConstraints);
            super.addMilestoneDateListener(milestoneDateField, individualMilestone);

            // Basic add the rest of the milestone
            individualMilestoneConstraints.gridx = 3;
            JButton deleteMilestoneButton = new JButton("x");
            individualMilestone.add(deleteMilestoneButton);
            super.addDeleteMilestoneListener(deleteMilestoneButton, individualMilestone);

            // Define the add task button
            individualMilestoneConstraints.gridx = 0; individualMilestoneConstraints.gridy = 1;
            JButton addTask = new JButton("+   add a task");
            addTask.setFont(new Font(MilestoneTasksViewModel.FONT, Font.BOLD, 10));
            addTask.setForeground(new Color(95, 95, 105));
            addTask.setContentAreaFilled(false);
            addTaskListener(addTask, individualMilestoneConstraints, individualMilestone);
            individualMilestone.add(addTask, individualMilestoneConstraints);

            // Define the base labels for the task fields
            individualMilestoneConstraints.gridx = 2;
            individualMilestone.add(new JLabel("Status"), individualMilestoneConstraints);
            individualMilestoneConstraints.gridx = 1;
            individualMilestone.add(new JLabel("Due Date"), individualMilestoneConstraints);
            individualMilestoneConstraints.gridx = 3;
            individualMilestone.add(new JLabel("Delete?"), individualMilestoneConstraints);

            loadTasksForMilestone(individualMilestone,
                    individualMilestoneConstraints,
                    milestonesTaskList.get(i),
                    milestoneToTaskComponents);
            individualMilestone.revalidate();

            // Adds the individual milestone to the bottom of the milestone
            milestonePanelConstraints.weightx = 2; milestonePanelConstraints.weighty = 1;
            milestonePanelConstraints.gridx = 0; milestonePanelConstraints.gridy = GridBagConstraints.RELATIVE;
            milestonePanel.add(individualMilestone, milestonePanelConstraints);

            milestonePanelConstraints.weighty = 0.5;
            milestonePanelConstraints.weightx = 0.5;
        }
        milestonePanel.revalidate();
        System.out.println(milestones.size());
    }

    private void loadTasksForMilestone(JPanel individualMilestone,
                                       GridBagConstraints constraints,
                                       List<String[]> tasksInfoList,
                                       Map<JPanel, List<JComponent[]>> milestoneToTaskComponents) {

        for (String[] taskInfo : tasksInfoList) {
            JComponent[] taskComponents = new JComponent[4];
            milestoneToTaskComponents.get(individualMilestone).add(taskComponents);

            // Creates the task name textfield
            constraints.gridx = 0;
            constraints.gridy = GridBagConstraints.RELATIVE;
            JTextField taskNameTextField = new JTextField(taskInfo[0], 10);
            individualMilestone.add(taskNameTextField, constraints);
            super.addTaskNameListener(taskNameTextField, individualMilestone, taskComponents);
            taskComponents[0] = taskNameTextField;

            // Creates the due date task textfield
            constraints.gridx = 1;
            JTextField taskDueDateField = new JTextField(taskInfo[1], 7);
            individualMilestone.add(taskDueDateField, constraints);
            super.addTaskDateListener(taskDueDateField, individualMilestone, taskComponents);
            taskComponents[1] = taskDueDateField;

            // Creates status selection combobox
            constraints.gridx = 2;
            JComboBox<String> statusOptionComboBox =
                    new JComboBox<>(new String[]{MilestoneTasksViewModel.BASE_TASK_STATUS_1,
                            MilestoneTasksViewModel.BASE_TASK_STATUS_2,
                            MilestoneTasksViewModel.BASE_TASK_STATUS_3});
            statusOptionComboBox.setSelectedItem(taskInfo[2]);

            individualMilestone.add(statusOptionComboBox, constraints);
            super.addTaskStatusListener(statusOptionComboBox, individualMilestone, taskComponents);
            taskComponents[2] = statusOptionComboBox;

            // Creates delete button
            constraints.gridx = 3;
            JButton deleteButton = new JButton("x");
            individualMilestone.add(deleteButton, constraints);
            taskComponents[3] = deleteButton;
            super.addDeleteTaskButtonListener(deleteButton, individualMilestone, taskComponents);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("load plan")) {
            super.propertyChange(evt);
            final LoadMilestonesState state = (LoadMilestonesState) evt.getNewValue();
            if (!state.getLoadError().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getLoadError());

                state.setLoadError("");
            } else if (evt.getPropertyName().equals("load plan")) {
                loadStudyPlan(state.getFocus(),
                        state.getMilestoneNames(),
                        state.getMilestoneDates(),
                        state.getMilestoneIndexToTasks());

                milestoneViewModel.setState(state);
            }
        }
    }

    @Override
    public String getViewName() {
        return this.VIEW_NAME;
    }
}
