package com.studyarc.view;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.interface_adapter.add_plan.AddPlanController;
import com.studyarc.interface_adapter.delete_plan.DeletePlanController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionViewModel;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesController;
import com.studyarc.interface_adapter.track_plan.TrackPlanController;
import com.studyarc.interface_adapter.track_plan.TrackPlanState;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.*;

/***
 * TrackPlan view for Track plan usecase, can only have one instance throughout the application.
 *
 */


public class TrackPlansView extends JPanel implements PropertyChangeListener, ActionListener, DocumentListener {
    private static TrackPlansView instance;

    static final String VIEW_NAME = "track plan";
    final BorderLayout borderLayout = new BorderLayout();

    final JPanel trackPlansPanel;
    final JPanel titlePanel;
    final JLabel title = new JLabel("MY PLANS");
    private final JButton saveButton = new JButton("Save");
    private final JButton newPlan = new JButton("🌟Create A New Plan🌟");
    private final TrackPlanViewModel trackPlanViewModel;

    private TrackPlanController trackPlanController = null;
    private DeletePlanController deletePlanController = null;
    private LoadMilestonesController loadMilestonesController = null;
    private AddPlanController addPlanController;


    private HashMap<JButton, StudyPlan> buttonToPlanMap;
    private HashMap<JTextField, StudyPlan> titleToPlanMap;
    private HashMap<JButton, StudyPlan> editButtonToPlanMap;

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

    private TrackPlansView(TrackPlanViewModel trackPlanViewModel,
                           AddReflectionViewModel addReflectionViewModel) {
        this.buttonToPlanMap = new HashMap<>();
        this.titleToPlanMap = new HashMap<>();
        this.editButtonToPlanMap = new HashMap<>();

        this.trackPlanViewModel = trackPlanViewModel;
        this.trackPlanViewModel.addPropertyChangeListener(this);
        this.setLayout(borderLayout);
        this.setBackground(Color.DARK_GRAY);

        titlePanel = setTitlePanel();
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 5, 25));

        titlePanel.setBackground(Styling.getYellow());
        trackPlansPanel = new JPanel();
        trackPlansPanel.setLayout(new BoxLayout(trackPlansPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(this.trackPlansPanel);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(Styling.getScrollPace());
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);

        jScrollPane.getViewport().setBackground(Styling.getYellow());
        jScrollPane.setBackground(Styling.getYellow());

        this.addReflectionViewModel = addReflectionViewModel;
        this.addReflectionViewModel.addPropertyChangeListener(this);

    }

    @NotNull
    private JPanel setTitlePanel() {
        GridBagConstraints topInfo = new GridBagConstraints();
        topInfo.gridx = 2;

        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        title.setFont(Styling.getMainFont());

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


        newPlan.addActionListener(this);
        buttonPanel.add(newPlan);

        saveButton.addActionListener(this);
        saveButton.setFont(Styling.getSubFont().deriveFont(12f));
        buttonPanel.add(saveButton);

        buttonPanel.setBackground(Styling.getYellow());

        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(buttonPanel, BorderLayout.EAST);
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

        TrackPlanState currentState = (TrackPlanState) evt.getNewValue();
        ArrayList<StudyPlan> currentPlans = currentState.getStudyPlans();

        if (!currentState.getSavingMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, currentState.getSavingMessage());
            return;
        }
        if (!currentPlans.isEmpty() || evt.getPropertyName().equals("added plan")) {
            this.showPlansInView(currentPlans);
        } else {
            this.showRedirectButton();
        }
    }

    private void showRedirectButton() {
        trackPlansPanel.removeAll();
        JLabel message = new JLabel("You have no Plans! Go Create New Plans!");
        message.setFont(Styling.getSubFont());
        trackPlansPanel.add(message);
        trackPlansPanel.repaint();
        trackPlansPanel.revalidate();
    }


    private void showPlansInView(ArrayList<StudyPlan> plans) {
        this.buttonToPlanMap = new HashMap<>();
        this.titleToPlanMap = new HashMap<>();
        this.editButtonToPlanMap = new HashMap<>();

        this.trackPlansPanel.removeAll();
        for (StudyPlan plan : plans) {
            JPanel planPanel = buildPlanPanel(plan);
            trackPlansPanel.add(planPanel);
            trackPlansPanel.add(Box.createVerticalStrut(15));
        }
        trackPlansPanel.repaint();
        trackPlansPanel.revalidate();
    }

    private JPanel buildPlanPanel(StudyPlan plan) {
        //get the main panel for each plan
        JPanel planPanel = creatPlanPanel();

        // Head Part of each plan
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
        JLabel planLabel = new JLabel("Plan : ");
        headPanel.setBackground(Styling.getGray());

        headPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 5, 25));

        // Text Field for Plan Title
        JTextField planTitleTextField = new JTextField();
        planTitleTextField.getDocument().addDocumentListener(this);
        planTitleTextField.setText(plan.getTitle());
        planLabel.setFont(planLabel.getFont().deriveFont(Font.BOLD, 16f));
        this.titleToPlanMap.put(planTitleTextField, plan);

        // Delete and Edit Buttons for each plan
        setUpDeleteAndEditButton(plan, headPanel, planLabel, planTitleTextField);

        // Milestones of each plan
        JPanel milestonesPanel = new JPanel();
        milestonesPanel.setLayout(new BoxLayout(milestonesPanel, BoxLayout.Y_AXIS));
        milestonesPanel.setBackground(Styling.getGray());
        milestonesPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setMileStonePanel(plan, milestonesPanel);

        // reflection log part.
        JPanel reflectionPanel = new JPanel();
        reflectionPanel.setLayout(new BorderLayout());
        reflectionPanel.setBorder(BorderFactory.createTitledBorder("Reflection Log"));

        JPanel reflectionHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addReflectionButton = new JButton("Add");
        addReflectionButton.setFont(Styling.getSubFont().deriveFont(12f));
        JButton showAllReflectionsButton = new JButton("Show All");
        showAllReflectionsButton.setFont(Styling.getSubFont().deriveFont(12f));

        reflectionHeader.add(addReflectionButton);
        reflectionHeader.add(showAllReflectionsButton);

        reflectionPanel.add(reflectionHeader, BorderLayout.NORTH);

        String username = trackPlanViewModel.getState().getUsername();
        addReflectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReflectionView dialog = new AddReflectionView(
                        SwingUtilities.getWindowAncestor(TrackPlansView.this),
                        addReflectionViewModel,
                        addReflectionController,
                        plan.getTitle(),
                        username
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

    @NotNull
    private JPanel creatPlanPanel() {
        JPanel planPanel = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                Dimension size = getPreferredSize();
                return new Dimension(750, size.height);
            }
        };
        planPanel.setLayout(new BorderLayout());
        planPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        planPanel.setBackground(Styling.getGray());
        return planPanel;
    }

    private void setUpDeleteAndEditButton(StudyPlan plan, JPanel headPanel, JLabel planLabel,
                                          JTextField planTitleTextField) {
        JButton deleteButton = new JButton("Delete " + "❌");
        deleteButton.setFont(Styling.getSubFont().deriveFont(12f));
        JButton editButton = new JButton("Edit");
        deleteButton.addActionListener(this);
        this.buttonToPlanMap.put(deleteButton, plan);
        editButton.addActionListener(this);

        this.buttonToPlanMap.put(deleteButton, plan);
        this.editButtonToPlanMap.put(editButton, plan);

        headPanel.add(planLabel);
        headPanel.add(planTitleTextField);
        headPanel.add(editButton);
        headPanel.add(deleteButton);
    }

    private void setMileStonePanel(StudyPlan plan, JPanel milestonesPanel) {
        List<Milestone> milestones = plan.getMilestones();

        for (int i = 0; i < milestones.size(); i++) {
            Milestone m = milestones.get(i);

            JPanel milestonePanel = new JPanel();
            milestonePanel.setBackground(Styling.getGray());
            milestonePanel.setLayout(new BoxLayout(milestonePanel, BoxLayout.Y_AXIS));

            // MilestoneHeadPanel setup
            JPanel milestoneHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
            milestoneHeader.setBackground(Styling.getGray());
            JButton upButton = new JButton("▲");
            JButton downButton = new JButton("▼");
            upButton.setFont(Styling.getSubFont().deriveFont(12f));
            downButton.setFont(Styling.getSubFont().deriveFont(12f));
            JLabel milestoneLabel = new JLabel("milestone " + (i + 1) + " : " + m.getTitle());

            // Check if all subtasks are completed
            JLabel milestoneCompleted = new JLabel(" ✅ ");
            List<Task> subtasks = m.getSubtasks();
            milestoneCompleted.setVisible(taskAllDone(subtasks));

            milestoneHeader.add(upButton);
            milestoneHeader.add(downButton);
            milestoneHeader.add(milestoneLabel);
            milestoneHeader.add(milestoneCompleted);

            // SubTask Panel for each Milestone
            JPanel tasksPanel = new JPanel();
            tasksPanel.setBackground(Styling.getGray());
            tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
            //If the milestone is completed, hide it. Show it if it's not
            tasksPanel.setVisible(!milestoneCompleted.isVisible());

            upButton.addActionListener(e -> {
                tasksPanel.setVisible(false);              // hide tasks
                milestonePanel.revalidate();
                milestonePanel.repaint();
            });

            downButton.addActionListener(e -> {
                //show the task panel
                tasksPanel.setVisible(true);
                milestonePanel.revalidate();
                milestonePanel.repaint();
            });

            List<Task> tasks = m.getSubtasks();
            for (int j = 0; j < tasks.size(); j++) {
                JPanel taskRow = getTaskRowPanel(tasks, j);
                tasksPanel.add(taskRow);
            }

            milestonePanel.add(milestoneHeader);
            milestonePanel.add(tasksPanel);

            milestonesPanel.add(milestonePanel);
            milestonesPanel.add(Box.createVerticalStrut(10));
        }
    }

    private  boolean taskAllDone(List<Task> subtasks) {
        boolean allCompleted = !subtasks.isEmpty();

        for (Task subtask : subtasks) {
            String status = subtask.getStatus();
            if (!"Done".equals(status)) {
                allCompleted = false;
                break;
            }
        }
        return allCompleted;
    }

    @NotNull
    private JPanel getTaskRowPanel(List<Task> tasks, int j) {
        Task t = tasks.get(j);

        JPanel taskRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskRow.setBackground(Styling.getGray());
        JLabel taskLabel = new JLabel("Task " + (j + 1) + ": " + t.getName() + "    ");
        String d = t.getDueDate();
        JLabel dueLabel = new JLabel("Due: " + d + "   ");

        //Later on could change String color based on the status.
        JLabel statusLabel = new JLabel("Status: " + t.getStatus());

        taskRow.add(taskLabel);
        taskRow.add(dueLabel);
        taskRow.add(statusLabel);
        return taskRow;
    }

    public String getViewName() {
        return VIEW_NAME;
    }


    public void setDeletePlanController(DeletePlanController deletePlanController) {
        this.deletePlanController = deletePlanController;
    }

    // Button Actions
    @Override
    public void actionPerformed(ActionEvent e) {
        TrackPlanState state = this.trackPlanViewModel.getState();
        JButton button = (JButton) e.getSource();

        if (this.buttonToPlanMap.containsKey(button)) {
            this.deletePlanController.execute(this.buttonToPlanMap.get(button));
        } else if (e.getSource() == newPlan) {
            addPlanController.execute(state.getStudyPlanTitles());
        } else if (this.editButtonToPlanMap.containsKey(button)) {
            System.out.println("EditPlan: " + this.editButtonToPlanMap.get(button).getTitle());
            this.loadMilestonesController.execute(this.editButtonToPlanMap.get(button).getTitle());

        } else if (e.getSource() == saveButton) {
            this.trackPlanController.execute(state.getStudyPlans(), state.getUsername());
        }
    }

    // DocumentListener for the plan title input field
    @Override
    public void insertUpdate(DocumentEvent e) {
        handleTypingEvent(e);

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleTypingEvent(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleTypingEvent(e);
    }

    private void handleTypingEvent(DocumentEvent e) {
        Document doc = e.getDocument();
        Set<JTextField> textFieldSet = titleToPlanMap.keySet();
        for (JTextField textField : textFieldSet) {
            if (textField.getDocument() == doc) {
                titleToPlanMap.get(textField).setTitle(textField.getText());
                System.out.println("Find the field of the Plan: " + titleToPlanMap.get(textField).getTitle());
                System.out.println(titleToPlanMap.get(textField).getTitle());
                break;
            }
        }
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

    public void setLoadMilestonesController(LoadMilestonesController loadMilestonesController) {
        this.loadMilestonesController = loadMilestonesController;
    }


    public void setTrackPlanController(TrackPlanController trackPlanController) {
        this.trackPlanController = trackPlanController;
    }

    public void setAddPlanController(AddPlanController controller) {
        this.addPlanController = controller;
    }
}
