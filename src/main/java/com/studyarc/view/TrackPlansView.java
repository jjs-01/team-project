package com.studyarc.view;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.interface_adapter.delete_plan.DeletePlanController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import java.util.List;

public class TrackPlansView extends JPanel implements PropertyChangeListener, ActionListener, DocumentListener {
    private static TrackPlansView instance;

    final String viewname = "track plan";
    final BorderLayout borderLayout = new BorderLayout();
    final String[] TaskStatus = {"Not Started", "In Progress", "Completed"};

    final JPanel trackPlansPanel;
    final JPanel titlePanel;
    final JLabel title = new JLabel("MY PLANS");
    private final JButton saveButton = new JButton("Save");

    private final TrackPlanViewModel trackPlanViewModel;
    private DeletePlanController deletePlanController = null;

    private HashMap<JButton, StudyPlan> buttonToPlanMap;
    private HashMap<JTextField, StudyPlan> titleToPlanMap;

    private final AddReflectionViewModel addReflectionViewModel;
    private AddReflectionController addReflectionController = null;

    private String currentSelectedPlanTitle;
    private ShowReflectionView showReflectionView;

    public static TrackPlansView getInstance(TrackPlanViewModel trackPlanViewModel,
                                             AddReflectionViewModel addReflectionViewModel) {
        if (instance == null) {
            instance = new TrackPlansView(trackPlanViewModel, addReflectionViewModel);
        }
        return instance;
    }

    private TrackPlansView(TrackPlanViewModel trackPlanViewModel, AddReflectionViewModel addReflectionViewModel) {
        this.buttonToPlanMap = new HashMap<>();
        this.titleToPlanMap = new HashMap<>();

        this.trackPlanViewModel = trackPlanViewModel;
        this.trackPlanViewModel.addPropertyChangeListener(this);
        this.setLayout(borderLayout);

        titlePanel = SetTitlePanel();
        trackPlansPanel = new JPanel();

        trackPlansPanel.setLayout(new BoxLayout(trackPlansPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(this.trackPlansPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);

        this.addReflectionViewModel = addReflectionViewModel;
        this.addReflectionViewModel.addPropertyChangeListener(this);

    }

    @NotNull
    private JPanel SetTitlePanel() {
        final JPanel titlePanel;
        titlePanel = new JPanel();
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        saveButton.addActionListener(this);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(saveButton, BorderLayout.EAST);
        return titlePanel;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // get the current plans in the TrackPlanState and show them in the view accordingly.
        if (!(evt.getNewValue() instanceof TrackPlanState)) {
            return;
        }

        if (evt.getPropertyName().equals("reflection_added")) {
            updateReflectionsUI();
        }

        TrackPlanState currentstate = (TrackPlanState) evt.getNewValue();
        ArrayList<StudyPlan> current_Plans = currentstate.getStudyPlans();
        ArrayList<StudyPlan> Test_Plans = this.generateTestPlans();
//        if (current_Plans.isEmpty()) {
//            System.out.println("YOU HAVE NO PLANS！");
//            //notify user you have no plan
//        } else {
//            this.showPlansinView(current_Plans);
//        }
        String result = "";
        for (StudyPlan currentPlan : current_Plans) {
            result += currentPlan.getTitle() + " ";
        }
        System.out.println("Current Plan in the View: " + result);
        this.showPlansinView(current_Plans);
    }



    private void showPlansinView(ArrayList<StudyPlan> plans) {
        this.buttonToPlanMap = new HashMap<>();
        this.trackPlansPanel.removeAll();
        if (plans == null || plans.isEmpty()) {
            trackPlansPanel.add(new JLabel("You have no plans yet."));
        } else {
            for (StudyPlan plan : plans) {
                JPanel planPanel = createPlanPanel(plan);
                trackPlansPanel.add(planPanel);
                trackPlansPanel.add(Box.createVerticalStrut(15)); // space between plans
            }
        }
        trackPlansPanel.repaint();
        trackPlansPanel.revalidate();


    }

    private JPanel createPlanPanel(StudyPlan plan) {
        JPanel planPanel = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                Dimension size = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, size.height);
            }
        };
        planPanel.setLayout(new BorderLayout());
        planPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Head Part of each plan
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
        JLabel planLabel = new JLabel("Plan : ");

        // Text Field for Plan Title
        JTextField planTitleTextField = new JTextField();
        planTitleTextField.getDocument().addDocumentListener(this);
        planTitleTextField.setText(plan.getTitle());
        planLabel.setFont(planLabel.getFont().deriveFont(Font.BOLD, 16f));
        this.titleToPlanMap.put(planTitleTextField, plan);//Add the title textfield in the map for each plan.

        // Delete Button for each plan
        JButton deleteButton = new JButton("❌");
        deleteButton.addActionListener(this);
        this.buttonToPlanMap.put(deleteButton, plan);//Add the delete button in the map for each plan.

        headPanel.add(planLabel);
        headPanel.add(planTitleTextField);
        headPanel.add(deleteButton);



        // Milestones of each plan
        JPanel milestonesPanel = new JPanel();
        milestonesPanel.setLayout(new BoxLayout(milestonesPanel, BoxLayout.Y_AXIS));

        ArrayList<Milestone> milestones = plan.getMilestones();

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

                String d = t.getDuedate();

                JLabel dueLabel = new JLabel("Due: " + d + "   ");

                JComboBox statusComboBox = new JComboBox(TaskStatus);
                statusComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedStatus = (String) statusComboBox.getSelectedItem();
                        System.out.println(selectedStatus);
                    }
                });
                taskRow.add(taskLabel);
                taskRow.add(dueLabel);
                taskRow.add(statusComboBox);

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
            tasksPanel.setVisible(true);

            milestonePanel.add(milestoneHeader);
            milestonePanel.add(tasksPanel);

            milestonesPanel.add(milestonePanel);
            milestonesPanel.add(Box.createVerticalStrut(10));
        }

        // reflection log part.
        JPanel reflectionPanel = new JPanel();
        reflectionPanel.setLayout(new BorderLayout());
        reflectionPanel.setBorder(BorderFactory.createTitledBorder("Reflection Log"));

        JPanel reflectionHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addReflectionButton = new JButton("Add");
        JButton showAllReflectionsButton = new JButton("Show All");

        reflectionHeader.add(addReflectionButton);
        reflectionHeader.add(showAllReflectionsButton);

        reflectionPanel.add(reflectionHeader, BorderLayout.NORTH);

        addReflectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReflectionView dialog = new AddReflectionView(
                        SwingUtilities.getWindowAncestor(TrackPlansView.this),
                        addReflectionViewModel,
                        addReflectionController,
                        plan.getTitle()
                );

                dialog.setVisible(true);
            }
        });

        showAllReflectionsButton.addActionListener(e -> {
            currentSelectedPlanTitle = plan.getTitle();

            if (showReflectionView == null) {
                showReflectionView = new ShowReflectionView(
                        SwingUtilities.getWindowAncestor(TrackPlansView.this)
                );
            }

            showReflectionView.refresh(plan.getReflections());
            showReflectionView.setVisible(true);
        });


        planPanel.add(headPanel, BorderLayout.NORTH);
        planPanel.add(milestonesPanel, BorderLayout.CENTER);
        planPanel.add(reflectionPanel, BorderLayout.SOUTH);

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
        String date = "MM/DD/YYYY";
        p1m1.getTasks().add(new Task("Do step1", date));
        p1m1.getTasks().add(new Task("Do step2", date));

        Milestone p1m2 = new Milestone("Milesone 2");
        p1m2.getTasks().add(new Task("Do step1", date));
        p1m2.getTasks().add(new Task("Do step2", date));

        plan1.getMilestones().add(p1m1);
        plan1.getMilestones().add(p1m2);

        // Plan 2
        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>());

        Milestone p2m1 = new Milestone("Milestone 1");
        p2m1.getTasks().add(new Task("Do step1", date));
        p2m1.getTasks().add(new Task("Do step2", date));

        Milestone p2m2 = new Milestone("Milestone 2");
        p2m2.getTasks().add(new Task("Do step1", date));
        p2m2.getTasks().add(new Task("Do step2", date));

        plan2.getMilestones().add(p2m1);
        plan2.getMilestones().add(p2m2);

        // Plan 3
        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>());

        Milestone p3m1 = new Milestone("Milestone 1");
        p3m1.getTasks().add(new Task("Do step1", date));
        p3m1.getTasks().add(new Task("Do step2", date));

        Milestone p3m2 = new Milestone("Milestone 2");
        p3m2.getTasks().add(new Task("Do step1", date));
        p3m2.getTasks().add(new Task("Do step2", date));

        plan3.getMilestones().add(p3m1);
        plan3.getMilestones().add(p3m2);

        // plan 4
        StudyPlan plan4 = new StudyPlan("Plan 4", new ArrayList<>());

        Milestone p4m1 = new Milestone("Milestone 1");
        p3m1.getTasks().add(new Task("Do step1", date));
        p3m1.getTasks().add(new Task("Do step2", date));

        Milestone p4m2 = new Milestone("Milestone 2");
        p3m2.getTasks().add(new Task("Do step1", date));
        p3m2.getTasks().add(new Task("Do step2", date));

        plan4.getMilestones().add(p4m1);
        plan4.getMilestones().add(p4m2);
        // Plan 5
        StudyPlan plan5 = new StudyPlan("Plan 5", new ArrayList<>());

        Milestone p5m1 = new Milestone("Milestone 1");
        p5m1.getTasks().add(new Task("Do step1", date));
        p5m1.getTasks().add(new Task("Do step2", date));

        Milestone p5m2 = new Milestone("Milestone 2");
        p5m2.getTasks().add(new Task("Do step1", date));
        p5m2.getTasks().add(new Task("Do step2", date));

        plan5.getMilestones().add(p5m1);
        plan5.getMilestones().add(p5m2);

        //plan 6
        StudyPlan plan6 = new StudyPlan("Plan 6", new ArrayList<>());

        Milestone p6m1 = new Milestone("Milestone 1");
        p6m1.getTasks().add(new Task("Do step1", date));
        p6m1.getTasks().add(new Task("Do step2", date));

        Milestone p6m2 = new Milestone("Milestone 2");
        p6m2.getTasks().add(new Task("Do step1", date));
        p6m2.getTasks().add(new Task("Do step2", date));

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

    public void setDeletePlanController(DeletePlanController deletePlanController) {
        this.deletePlanController = deletePlanController;
    }

    // Delete button triggers here
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (this.buttonToPlanMap.containsKey(button)) {
            StudyPlan plan = this.buttonToPlanMap.get(button);
            System.out.println("Trying to delete: " + plan.getTitle());
            this.deletePlanController.execute(plan);
        } else {

            System.out.println("click save button");
        }
    }


// DocumentListener for the plan title input field
    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println("insert");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        System.out.println("remove");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("changed");
    }

    public void setAddReflectionController(AddReflectionController controller) {
        this.addReflectionController = controller;
    }

    private void updateReflectionsUI() {
        if (currentSelectedPlanTitle == null) {
            return;
        }

        TrackPlanState tpState = trackPlanViewModel.getState();
        List<StudyPlan> plans = tpState.getStudyPlans();

        StudyPlan current = null;
        for (StudyPlan p : plans) {
            if (p.getTitle().equals(currentSelectedPlanTitle)) {
                current = p;
                break;
            }
        }

        if (current == null) {
            System.out.println("No matching plan found for updateReflectionsUI()");
            return;
        }

        if (showReflectionView != null && showReflectionView.isVisible()) {
            showReflectionView.refresh(current.getReflections());
        }
    }


}
