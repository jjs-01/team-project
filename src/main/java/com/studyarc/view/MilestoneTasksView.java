package com.studyarc.view;

import com.studyarc.interface_adapter.*;
import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksController;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MilestoneTasksView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "milestone tasks";

    private final MilestoneTasksViewModel milestoneTasksViewModel;
    private MilestoneTasksController milestoneTasksController = null;

    private JPanel milestoneView = new JPanel();
    private JLabel planTitle;
    private JLabel focuses;
    private JButton addMilestone;
    private JButton save;

    // private List<> milestones = new ArrayList<JPanel>();

    public MilestoneTasksView(MilestoneTasksViewModel milestoneTasksViewModel) {

        this.milestoneTasksViewModel = milestoneTasksViewModel;
        this.milestoneTasksViewModel.addPropertyChangeListener(this);

        final JPanel topDetails = new JPanel();
        planTitle = new JLabel("HCI Study Plan");
        planTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        topDetails.add(planTitle);

        focuses = new JLabel("Focus: ");
        topDetails.add(focuses);

        save = new JButton("Save Plan");
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

                            c.gridx = 0; c.gridy = 0;
                            individualMilestone.add(new JTextField("Milestone name", 20), c);
                            c.gridx = 1;
                            individualMilestone.add(new JCheckBox(), c);
                            c.gridx = 2;
                            individualMilestone.add(new JTextField("XX/XX/XXXX", 10), c);

                            c.gridx = 0; c.gridy = 1;
                            JButton addTask = new JButton("+   add a task");
                            addTask.setFont(new Font("SansSerif", Font.BOLD, 10));
                            addTask.setForeground(new Color(95, 95, 105));
                            addTask.setContentAreaFilled(false);

                            individualMilestone.add(addTask, c);

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

    public String getViewName() {
        return viewName;
    }

    public void setMilestoneTasksController(MilestoneTasksController milestoneTasksController) {
        this.milestoneTasksController = milestoneTasksController;
    }

}
