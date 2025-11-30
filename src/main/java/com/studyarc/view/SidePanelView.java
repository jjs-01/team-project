package com.studyarc.view;

import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanController;
import com.studyarc.interface_adapter.ui_sidebar.SidebarController;
import com.studyarc.interface_adapter.ui_sidebar.SidebarState;
import com.studyarc.interface_adapter.ui_sidebar.SidebarViewModel;
import com.studyarc.use_case.track_plan.TrackPlanInputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanInteractor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SidePanelView extends JPanel implements ActionListener, PropertyChangeListener  {
    private final SidebarViewModel sidebarViewModel;
    private SidebarController sidebarController = null;

    private final JPanel mainButtonPanel = new JPanel();
    private final JLabel logo = new JLabel("Study Arc");
    private final JLabel userLoggedIn = new JLabel("Logged In User");
    private final JButton seePlans;
    private final JButton seePapers;
    private final JButton seeJobs;
    private final JButton myPlans;
    private final JButton logout;

    private String userName;

    private final Color mainColor = new Color(232, 231, 230);
    //controller of TrackPlan usecase
    private TrackPlanController trackPlanController;
    // controller for job postings controller
    private JobPostingsController jobPostingsController;

    public SidePanelView(SidebarViewModel sidebarViewModel) {
        this.sidebarViewModel = sidebarViewModel;
        this.sidebarViewModel.addPropertyChangeListener(this);

        logo.setFont(Styling.getMainFont().deriveFont(Font.BOLD));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        userLoggedIn.setFont(Styling.getSubFont());
        userLoggedIn.setHorizontalAlignment(SwingConstants.CENTER);

        seePlans = new JButton("New Plans");
        seePapers = new JButton("Papers");
        seeJobs = new JButton("Jobs");
        myPlans = new JButton("My Plans");
        logout = new JButton("logout");

        final JButton[] buttons = {seePlans, seePapers, seeJobs, myPlans, logout};

        this.setLayout(new BorderLayout());
        mainButtonPanel.setLayout(new GridBagLayout());

        this.setPreferredSize(new Dimension(180, 800));
        this.setMaximumSize(new Dimension(180, 800));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));
        this.setBackground(mainColor);

        GridBagConstraints mainButtonLayout = new GridBagConstraints();
        mainButtonLayout.fill = GridBagConstraints.HORIZONTAL;
        mainButtonLayout.gridx = 0;
        mainButtonLayout.insets = new Insets(50,0,0,0);
        mainButtonLayout.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < buttons.length; i++) {
            mainButtonLayout.gridy = i;
            mainButtonPanel.add(buttons[i], mainButtonLayout);
            buttons[i].setFont(Styling.getSubFont().deriveFont(14f));
        }

        mainButtonPanel.setBackground(mainColor);

        this.add(logo, BorderLayout.NORTH);
        this.add(mainButtonPanel, BorderLayout.CENTER);
        this.add(userLoggedIn, BorderLayout.SOUTH);

        this.setVisible(false);
        seeJobs.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // TODO: use the username that is logged in
                        jobPostingsController.retrieveAvailableFocuses("");
                        sidebarController.switchToJobBoard();

                    }
                }
        );

        seePlans.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        sidebarController.switchToMilestone();
                    }
                }
        );
        myPlans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //entered for a randomusername for test, need to change later
                trackPlanController.execute("qyz");
                sidebarController.switchToTrackPlan();

            }
        });
        logout.addActionListener(this);
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logout){
            this.sidebarController.switchToLogin();
            this.setVisible(false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setVisible(true);
        SidebarState state = sidebarViewModel.getState();
        System.out.println("state= " + state);

        userName = state.getUserName();
        this.userLoggedIn.setText("Welcome, " + userName);
        this.setVisible(true);

    }

    public void setSidebarController(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    public void setTrackPlanController(TrackPlanController trackPlanController) {
        this.trackPlanController = trackPlanController;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }

    public void setLoggedInUser(String username) {
        SidebarState state = sidebarViewModel.getState();
        state.setUserName(username);

//        this.userLoggedIn.setText(username);
    }

    public String getLoggedInUserName() {
        SidebarState state = sidebarViewModel.getState();
        return state.getUserName();
    }
}
