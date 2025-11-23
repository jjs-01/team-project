package com.studyarc.view;

import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksController;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksState;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 *
 */
public class MilestoneTasksView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "milestones and tasks";
    private MilestoneTasksController milestoneTasksController;
    private final MilestoneTasksViewModel milestoneViewModel;

    private final JPanel milestoneView = new JPanel();
    private final JLabel planTitle;
    private final JLabel focuses;
    private final JButton addMilestone;
    private final JButton save;

    private final List<JPanel> milestones = new ArrayList<>();
    private final Map<Integer, List<JComponent[]>> milestoneToTaskComponents = new HashMap<>();

    public MilestoneTasksView(MilestoneTasksViewModel milestoneViewModel) {
        this.milestoneViewModel = milestoneViewModel;
        final JPanel topDetails = new JPanel();
        planTitle = new JLabel(MilestoneTasksViewModel.TITLE_LABEL);
        planTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        topDetails.add(planTitle);

        focuses = new JLabel("Focus: ");
        topDetails.add(focuses);

        save = new JButton("Save Changes");
        topDetails.add(save);

        save.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(save)) {
                            final MilestoneTasksState currentState = milestoneViewModel.getState();

                            milestoneTasksController.execute(
                                currentState.getMilestones(),
                                currentState.getMilestonestoTasks()
                            );
                        }
                    }
                }
        );

        milestoneView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        addMilestone = new JButton("+   add a milestone");
        addMilestone.setFont(new Font("SansSerif", Font.BOLD, 12));
        addMilestone.setForeground(new Color(75, 75, 95));
        addMilestone.setContentAreaFilled(false);

        milestoneView.add(addMilestone);

        addMilestone.addActionListener(
                new ActionListener() {

                    private void addMilestoneListenerHelper(JPanel milestone) {
                        final MilestoneTasksState currentState = milestoneViewModel.getState();
                        currentState.addMilestone(milestones.indexOf(milestone),
                                MilestoneTasksViewModel.BASE_MILESTONE_FIELDS[0],
                                MilestoneTasksViewModel.BASE_MILESTONE_FIELDS[1]);
                        milestoneViewModel.setState(currentState);
                    }

                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addMilestone)) {
                            // final LoginState currentState = loginViewModel.getState();

                            JPanel individualMilestone = new JPanel();
                            individualMilestone.setLayout(new GridBagLayout());
                            milestones.add(individualMilestone);
                            milestoneToTaskComponents.put(milestones.indexOf(individualMilestone), new ArrayList<>());

                            addMilestoneListenerHelper(individualMilestone);

                            GridBagConstraints constraints2 = new GridBagConstraints();

                            // Add milestone name textfield
                            constraints2.gridx = 0; constraints2.gridy = 0;
                            JTextField milestoneNameField = new JTextField(
                                    MilestoneTasksViewModel.BASE_MILESTONE_FIELDS[0], 20);
                            individualMilestone.add(milestoneNameField, constraints2);
                            addMilestoneNameListener(milestoneNameField, individualMilestone);

                            // Add completed check mark
                            constraints2.gridx = 1;
                            individualMilestone.add(new JCheckBox(), constraints2);

                            // Add dueDate textfield
                            constraints2.gridx = 2;
                            JTextField milestoneDateField = new JTextField(MilestoneTasksViewModel.BASE_MILESTONE_FIELDS[1], 10);
                            individualMilestone.add(milestoneDateField, constraints2);
                            addMilestoneDateListener(milestoneDateField, individualMilestone);

                            constraints2.gridx = 3;
                            individualMilestone.add(new JButton("x"));

                            // Define the add task button
                            constraints2.gridx = 0; constraints2.gridy = 1;
                            JButton addTask = new JButton("+   add a task");
                            addTaskListener(addTask, constraints2, individualMilestone);
                            individualMilestone.add(addTask, constraints2);

                            // Define the base labels for the task fields
                            constraints2.gridx = 1;
                            individualMilestone.add(new JLabel("Due Date"), constraints2);
                            constraints2.gridx = 2;
                            individualMilestone.add(new JLabel("Status"), constraints2);
                            constraints2.gridx = 3;
                            individualMilestone.add(new JLabel("Delete?"), constraints2);

                            // Adds the individual milestone to the bottom of the milestone
                            c.gridx = 0; c.gridy = GridBagConstraints.RELATIVE;
                            c.weightx = 2; c.weighty = 1;
                            milestoneView.add(individualMilestone, c);

                            milestoneView.revalidate();
                            c.weighty = 0.5;
                            c.weightx = 0.5;
                            // milestoneTasksController.execute();
                        }
                    }
                }
        );

        JScrollPane milestoneScrollPane = new JScrollPane(milestoneView);
        milestoneScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        milestoneScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        milestoneScrollPane.setBorder(null);

        this.setLayout(new BorderLayout());
        this.add(topDetails, BorderLayout.NORTH);
        this.add(milestoneScrollPane, BorderLayout.CENTER);
    }

    private void addMilestoneNameListener(JTextField milestoneNameField, JPanel individualMilestone) {
        milestoneNameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.setMilestoneName(milestones.indexOf(individualMilestone), milestoneNameField.getText());
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addMilestoneDateListener(JTextField dueDateField, JPanel individualMilestone) {
        dueDateField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.setMilestoneDate(milestones.indexOf(individualMilestone), dueDateField.getText());
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addTaskListener(JButton addTask, GridBagConstraints constraints2, JPanel individualMilestone) {
        addTask.setFont(new Font("SansSerif", Font.BOLD, 10));
        addTask.setForeground(new Color(95, 95, 105));
        addTask.setContentAreaFilled(false);

        int milestoneIndex = milestones.indexOf(individualMilestone);

        addTask.addActionListener(new ActionListener() {

            private void addTaskListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.addTask(milestoneIndex,
                        MilestoneTasksViewModel.BASE_TASK_FIELDS[0],
                        MilestoneTasksViewModel.BASE_TASK_FIELDS[1],
                        MilestoneTasksViewModel.BASE_TASK_FIELDS[2]);
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(addTask)) {
                    JComponent[] taskComponents = new JComponent[3];
                    milestoneToTaskComponents.get(milestoneIndex).add(taskComponents);
                    int taskIndex = milestoneToTaskComponents.get(milestoneIndex).indexOf(taskComponents);

                    // Creates the task name textfield
                    constraints2.gridx = 0; constraints2.gridy = GridBagConstraints.RELATIVE;
                    JTextField taskNameTextField = new JTextField(MilestoneTasksViewModel.BASE_TASK_FIELDS[0], 10);
                    individualMilestone.add(taskNameTextField, constraints2);
                    addTaskNameListener(taskNameTextField, milestoneIndex, taskIndex);
                    taskComponents[0] = taskNameTextField;

                    // Creates the due date task textfield
                    constraints2.gridx = 1;
                    JTextField taskDueDateField = new JTextField(MilestoneTasksViewModel.BASE_TASK_FIELDS[1], 7);
                    individualMilestone.add(taskDueDateField, constraints2);
                    addTaskDateListener(taskDueDateField, milestoneIndex, taskIndex);
                    taskComponents[1] = taskDueDateField;

                    // Creates status selection combobox
                    constraints2.gridx = 2;
                    JComboBox<String> statusOptionComboBox =
                            new JComboBox<>(MilestoneTasksViewModel.BASE_TASK_STATUS_OPTIONS);
                    individualMilestone.add(statusOptionComboBox, constraints2);
                    addTaskStatusListener(statusOptionComboBox, milestoneIndex, taskIndex);
                    taskComponents[2] = statusOptionComboBox;

                    // Creates delete button
                    constraints2.gridx = 3;
                    individualMilestone.add(new JButton("x"), constraints2);

                    addTaskListenerHelper();
                    individualMilestone.revalidate();
                }
            }
        });
    }

    private void addTaskNameListener(JTextField taskNameField, int milestoneIndex, int taskIndex) {
        taskNameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.setTaskName(milestoneIndex, taskIndex, taskNameField.getText());
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addTaskDateListener(JTextField taskDateField, int milestoneIndex, int taskIndex) {
        taskDateField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.setTaskDate(milestoneIndex, taskIndex, taskDateField.getText());
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addTaskStatusListener(JComboBox<String> statusBox, int milestoneIndex, int taskIndex) {
        statusBox.addItemListener(new ItemListener() {

            private void itemListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.setTaskStatus(milestoneIndex, taskIndex, (String) statusBox.getSelectedItem());
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemListenerHelper();
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MilestoneTasksState state = (MilestoneTasksState) evt.getNewValue();
//        if (state.getMilestoneNameError() != null) {
//            JOptionPane.showMessageDialog((this, state.get));
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Not implemented");
    }

    public String getViewName() { return viewName; }

    public void setMilestoneTasksController(MilestoneTasksController controller) {
        this.milestoneTasksController = controller;
    }
}