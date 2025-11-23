package com.studyarc.view;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.interface_adapter.delete_plan.DeletePlanController;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.interface_adapter.ui_sidebar.SidebarViewModel;
import com.studyarc.use_case.delete_plan.DeletePlanInputData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TrackPlansView extends JPanel implements PropertyChangeListener, ActionListener {
    final String viewname = "track plan";
    final BorderLayout borderLayout = new BorderLayout();

    final JPanel trackPlansPanel;
    final JPanel TitlePanel;
    final JLabel title = new JLabel("MY PLANS");

    private final TrackPlanViewModel trackPlanViewModel;
    private DeletePlanController deletePlanController = null;

    public  HashMap<JButton, StudyPlan> buttonToPlanMap;

    public TrackPlansView(TrackPlanViewModel trackPlanViewModel) {
        this.buttonToPlanMap = new HashMap<>();
        this.trackPlanViewModel = trackPlanViewModel;
        this.trackPlanViewModel.addPropertyChangeListener(this);
        this.setLayout(borderLayout);

        TitlePanel = new JPanel();
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        TitlePanel.add(title);
        trackPlansPanel = new JPanel();

        trackPlansPanel.setLayout(new BoxLayout(trackPlansPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(this.trackPlansPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(TitlePanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // get the current plans in the TrackPlanState and show them in the view accordingly.
        TrackPlanState currentstate = (TrackPlanState) evt.getNewValue();
        ArrayList<StudyPlan> current_Plans = currentstate.getStudyPlans();
        ArrayList<StudyPlan> Test_Plans = this.generateTestPlans();
//        if (current_Plans.isEmpty()) {
//            System.out.println("YOU HAVE NO PLANS！");
//            //notify user you have no plan
//        } else {
//            this.showPlansinView(current_Plans);
//        }
        this.showPlansinView(Test_Plans);
    }

    private void showPlansinView(ArrayList<StudyPlan> plans) {

        if (plans == null || plans.isEmpty()) {
            trackPlansPanel.add(new JLabel("You have no plans yet."));
        } else {
            for (StudyPlan plan : plans) {
                JPanel planPanel = createPlanPanel(plan);
                trackPlansPanel.add(planPanel);
                trackPlansPanel.add(Box.createVerticalStrut(15)); // space between plans
            }
        }


    }

    private JPanel createPlanPanel(StudyPlan plan) {
        JPanel planPanel = new JPanel();
        planPanel.setLayout(new BorderLayout());
        planPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Head Part of each plan
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
        JLabel planLabel = new JLabel("Plan : ");
        JTextField planTitleTextField = new JTextField();
        planTitleTextField.setText(plan.getTitle());
        planLabel.setFont(planLabel.getFont().deriveFont(Font.BOLD, 16f));

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("❌");

        deleteButton.addActionListener(this);

        this.buttonToPlanMap.put(deleteButton, plan);

        headPanel.add(planLabel);
        headPanel.add(planTitleTextField);
        headPanel.add(deleteButton);
        headPanel.add(saveButton);
//        headPanel.add(planLabel, BorderLayout.WEST);
//        headPanel.add(deleteButton, BorderLayout.EAST);
//        headPanel.add(planTitleTextField, BorderLayout.NORTH);

        // Milestones of each plan
        JPanel milestonesPanel = new JPanel();
        milestonesPanel.setLayout(new BoxLayout(milestonesPanel, BoxLayout.Y_AXIS));

        ArrayList<Milestone> milestones = plan.getMilestones();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        for (int i = 0; i < milestones.size(); i++) {
            Milestone m = milestones.get(i);

            JPanel milestonePanel = new JPanel();
            milestonePanel.setLayout(new BoxLayout(milestonePanel, BoxLayout.Y_AXIS));

            // MilestoneHeadPanel setup
            JPanel milestoneHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton upButton = new JButton("Hide");
            JButton downButton = new JButton("Show");
            JLabel milestoneLabel = new JLabel("milestone " + (i + 1) + " : " + m.getTitle());

            milestoneHeader.add(upButton);
            milestoneHeader.add(downButton);
            milestoneHeader.add(milestoneLabel);


            // SubTask Panel for each Milestone
            JPanel tasksPanel = new JPanel();
            tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));

            ArrayList<Task> tasks = m.getTasks();
            for (int j = 0; j < tasks.size(); j++) {
                Task t = tasks.get(j);

                JPanel taskRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel taskLabel = new JLabel("Task " + (j + 1) + ": " + t.getName() + "    ");

//              JTextField taskNameField = new JTextField(15);
//              taskNameField.setText(t.getName());

                Date d = t.getDuedate();

//              JTextField dueField = new JTextField(10);

//              if (d != null) {
//                  dueField.setText(format.format(d));
//              }
                JLabel dueLabel = new JLabel("Due: " + format.format(d) + "   ");

                JCheckBox doneCheckBox = new JCheckBox("Done");
                doneCheckBox.setSelected(t.isCompleted());

                taskRow.add(taskLabel);
//                taskRow.add(taskNameField);
                taskRow.add(dueLabel);
//                taskRow.add(dueField);
                taskRow.add(doneCheckBox);

                tasksPanel.add(taskRow);
            }

            upButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tasksPanel.setVisible(false);              // hide tasks
                    milestonePanel.revalidate();
                    milestonePanel.repaint();
                }
            });

            downButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show the task panel
                    tasksPanel.setVisible(true);
                    milestonePanel.revalidate();
                    milestonePanel.repaint();
                }
            });
            // tasks are originally invisible
            tasksPanel.setVisible(false);

            milestonePanel.add(milestoneHeader);
            milestonePanel.add(tasksPanel);

            milestonesPanel.add(milestonePanel);
            milestonesPanel.add(Box.createVerticalStrut(10));
        }

        planPanel.add(headPanel, BorderLayout.NORTH);
        planPanel.add(milestonesPanel, BorderLayout.CENTER);
        return planPanel;
    }

    public String getViewname() {
        return viewname;
    }

    private ArrayList<StudyPlan> generateTestPlans() {
        ArrayList<StudyPlan> plans = new ArrayList<>();

        // Plan 1
        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>());

        Milestone p1m1 = new Milestone("Milestone 1");
        p1m1.getTasks().add(new Task("Do step1", new Date()));
        p1m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p1m2 = new Milestone("Milesone 2");
        p1m2.getTasks().add(new Task("Do step1", new Date()));
        p1m2.getTasks().add(new Task("Do step2", new Date()));

        plan1.getMilestones().add(p1m1);
        plan1.getMilestones().add(p1m2);

        // Plan 2
        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>());

        Milestone p2m1 = new Milestone("Milestone 1");
        p2m1.getTasks().add(new Task("Do step1", new Date()));
        p2m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p2m2 = new Milestone("Milestone 2");
        p2m2.getTasks().add(new Task("Do step1", new Date()));
        p2m2.getTasks().add(new Task("Do step2", new Date()));

        plan2.getMilestones().add(p2m1);
        plan2.getMilestones().add(p2m2);

        // Plan 3
        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>());

        Milestone p3m1 = new Milestone("Milestone 1");
        p3m1.getTasks().add(new Task("Do step1", new Date()));
        p3m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p3m2 = new Milestone("Milestone 2");
        p3m2.getTasks().add(new Task("Do step1", new Date()));
        p3m2.getTasks().add(new Task("Do step2", new Date()));

        plan3.getMilestones().add(p3m1);
        plan3.getMilestones().add(p3m2);

        // plan 4
        StudyPlan plan4 = new StudyPlan("Plan 4", new ArrayList<>());

        Milestone p4m1 = new Milestone("Milestone 1");
        p3m1.getTasks().add(new Task("Do step1", new Date()));
        p3m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p4m2 = new Milestone("Milestone 2");
        p3m2.getTasks().add(new Task("Do step1", new Date()));
        p3m2.getTasks().add(new Task("Do step2", new Date()));

        plan4.getMilestones().add(p4m1);
        plan4.getMilestones().add(p4m2);
        // Plan 5
        StudyPlan plan5 = new StudyPlan("Plan 5", new ArrayList<>());

        Milestone p5m1 = new Milestone("Milestone 1");
        p5m1.getTasks().add(new Task("Do step1", new Date()));
        p5m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p5m2 = new Milestone("Milestone 2");
        p5m2.getTasks().add(new Task("Do step1", new Date()));
        p5m2.getTasks().add(new Task("Do step2", new Date()));

        plan5.getMilestones().add(p5m1);
        plan5.getMilestones().add(p5m2);

        //plan 6
        StudyPlan plan6 = new StudyPlan("Plan 6", new ArrayList<>());

        Milestone p6m1 = new Milestone("Milestone 1");
        p6m1.getTasks().add(new Task("Do step1", new Date()));
        p6m1.getTasks().add(new Task("Do step2", new Date()));

        Milestone p6m2 = new Milestone("Milestone 2");
        p6m2.getTasks().add(new Task("Do step1", new Date()));
        p6m2.getTasks().add(new Task("Do step2", new Date()));

        plan6.getMilestones().add(p6m1);
        plan6.getMilestones().add(p6m2);

        //add all plans to plans
        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        plans.add(plan4);
        plans.add(plan5);
        plans.add(plan6);

        return plans;

    }

    // Delete button triggers here
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        StudyPlan plan = this.buttonToPlanMap.get(button);
//        this.deletePlanController.execute(plan);
        System.out.println("Trying to delete: " + plan.getTitle());
    }
}
