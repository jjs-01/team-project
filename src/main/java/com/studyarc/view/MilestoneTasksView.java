package com.studyarc.view;

import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class MilestoneTasksView extends JPanel implements ActionListener, PropertyChangeListener {
    private MilestoneTasksController milestoneTasksController;

    private JPanel milestoneView = new JPanel();
    private JLabel planTitle;
    private JLabel focuses;
    private JButton addMilestone;
    private JButton save;

    private Map<JButton, JPanel> addTasksToMilestones = new HashMap<>();

    public MilestoneTasksView() {
        final JPanel topDetails = new JPanel();
        planTitle = new JLabel("Study Plan");
        planTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        topDetails.add(planTitle);

        focuses = new JLabel("Focus: ");
        topDetails.add(focuses);

        save = new JButton("Save Changes");
        topDetails.add(save);

        milestoneView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        addMilestone = new JButton("+   add a milestone");
        addMilestone.setFont(new Font("SansSerif", Font.BOLD, 12));
        addMilestone.setForeground(new Color(75, 75, 95));
        addMilestone.setContentAreaFilled(false);

        milestoneView.add(addMilestone);

        addMilestone.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addMilestone)) {
                            // final LoginState currentState = loginViewModel.getState();

                            JPanel individualMilestone = new JPanel();
                            individualMilestone.setLayout(new GridBagLayout());

                            GridBagConstraints constraints2 = new GridBagConstraints();
                            constraints2.gridx = 0; constraints2.gridy = 0;
                            individualMilestone.add(new JTextField("Milestone name", 20), constraints2);
                            constraints2.gridx = 1;
                            individualMilestone.add(new JCheckBox(), constraints2);
                            constraints2.gridx = 2;
                            individualMilestone.add(new JTextField("XX/XX/XXXX", 10), constraints2);
                            constraints2.gridx = 3;
                            individualMilestone.add(new JButton("x"));

                            constraints2.gridx = 0; constraints2.gridy = 1;
                            JButton addTask = new JButton("+   add a task");
                            addTask.setFont(new Font("SansSerif", Font.BOLD, 10));
                            addTask.setForeground(new Color(95, 95, 105));
                            addTask.setContentAreaFilled(false);

                            addTask.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (e.getSource().equals(addTask)) {
                                        constraints2.gridx = 0; constraints2.gridy = GridBagConstraints.RELATIVE;
                                        individualMilestone.add(new JTextField("Task Name", 10), constraints2);
                                        constraints2.gridx = 1;
                                        individualMilestone.add(new JTextField("XX/XX/XXXX", 7), constraints2);
                                        constraints2.gridx = 2;
                                        individualMilestone.add(new JComboBox<>(new String[]{"Not Started",
                                                "In progress", "Done"}), constraints2);
                                        constraints2.gridx = 3;
                                        individualMilestone.add(new JButton("x"), constraints2);

                                        individualMilestone.revalidate();
                                    }
                                }
                            });

                            individualMilestone.add(addTask, constraints2);
                            constraints2.gridx = 1;
                            individualMilestone.add(new JLabel("Due Date"), constraints2);
                            constraints2.gridx = 2;
                            individualMilestone.add(new JLabel("Status"), constraints2);
                            constraints2.gridx = 3;
                            individualMilestone.add(new JLabel("Delete?"), constraints2);

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

    private void addMilestoneListener() {

    }

    private void addTaskListener() {
        // probably needs some parameter to specify which milestone to add the task to
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // final milestoneTaskState state =(miles)
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
