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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.List;
import java.util.ArrayList;


public class MilestoneTasksView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "milestones and tasks";
    private MilestoneTasksController milestoneTasksController;
    private MilestoneTasksViewModel milestoneViewModel;

    private JPanel milestoneView = new JPanel();
    private JLabel planTitle;
    private JLabel focuses;
    private JButton addMilestone;
    private JButton save;

    private List<JPanel> milestones = new ArrayList<>();

    public MilestoneTasksView(MilestoneTasksViewModel milestoneViewModel) {
        this.milestoneViewModel = milestoneViewModel;
        final JPanel topDetails = new JPanel();
        planTitle = new JLabel("Study Plan");
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
                            individualMilestone.add(new JTextField(
                                    MilestoneTasksViewModel.BASE_MILESTONE_FIELDS[1], 10), constraints2);
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

    private void addTaskListener(JButton addTask, GridBagConstraints constraints2, JPanel individualMilestone) {
        addTask.setFont(new Font("SansSerif", Font.BOLD, 10));
        addTask.setForeground(new Color(95, 95, 105));
        addTask.setContentAreaFilled(false);

        addTask.addActionListener(new ActionListener() {

            private void addTaskListenerHelper() {
                final MilestoneTasksState currentState = milestoneViewModel.getState();
                currentState.addTask(milestones.indexOf(individualMilestone),
                        MilestoneTasksViewModel.BASE_TASK_STATUS_OPTIONS);
                milestoneViewModel.setState(currentState);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(addTask)) {

                    constraints2.gridx = 0; constraints2.gridy = GridBagConstraints.RELATIVE;
                    JTextField taskNameTextField = new JTextField(MilestoneTasksViewModel.BASE_TASK_FIELDS[0], 10);
                    individualMilestone.add(taskNameTextField, constraints2);

                    constraints2.gridx = 1;
                    JTextField taskDueDateField = new JTextField(MilestoneTasksViewModel.BASE_TASK_FIELDS[2], 7);
                    individualMilestone.add(taskDueDateField, constraints2);

                    constraints2.gridx = 2;
                    JComboBox statusOptionComboBox = new JComboBox(MilestoneTasksViewModel.BASE_TASK_STATUS_OPTIONS);
                    individualMilestone.add(statusOptionComboBox, constraints2);

                    constraints2.gridx = 3;
                    individualMilestone.add(new JButton("x"), constraints2);

                    addTaskListenerHelper();
                    individualMilestone.revalidate();
                }
            }
        });
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

    private void addTaskListener() {
        // probably needs some parameter to specify which milestone to add the task to
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // final milestoneTaskState state =(miles)
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
        this.milestoneTasksController = controller;}
}
