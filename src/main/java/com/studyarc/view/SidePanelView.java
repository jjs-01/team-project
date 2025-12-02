package com.studyarc.view;

import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.track_plan.TrackPlanController;
import com.studyarc.interface_adapter.ui_sidebar.SidebarController;
import com.studyarc.interface_adapter.ui_sidebar.SidebarState;
import com.studyarc.interface_adapter.ui_sidebar.SidebarViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SidePanelView extends JPanel implements ActionListener, PropertyChangeListener {
    private final SidebarViewModel sidebarViewModel;
    private SidebarController sidebarController = null;

    private final JLabel userLoggedIn = new JLabel("Logged In User");
    private final JButton logout;

    // Controllers
    private TrackPlanController trackPlanController;
    private JobPostingsController jobPostingsController;

    public SidePanelView(SidebarViewModel sidebarViewModel) {
        this.sidebarViewModel = sidebarViewModel;
        this.sidebarViewModel.addPropertyChangeListener(this);

        JLabel logo = new JLabel("Study Arc");
        logo.setFont(Styling.getMainFont().deriveFont(Font.BOLD));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        userLoggedIn.setFont(Styling.getSubFont());
        userLoggedIn.setHorizontalAlignment(SwingConstants.CENTER);

        JButton seePapers = new JButton("Papers");
        JButton seeJobs = new JButton("Jobs");
        JButton myPlans = new JButton("My Plans");
        logout = new JButton("Logout");

        final JButton[] buttons = {seePapers, seeJobs, myPlans, logout};

        this.setLayout(new BorderLayout());
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new GridBagLayout());

        this.setPreferredSize(new Dimension(180, 800));
        this.setMaximumSize(new Dimension(180, 800));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));
        Color mainColor = new Color(232, 231, 230);
        this.setBackground(mainColor);

        GridBagConstraints mainButtonLayout = new GridBagConstraints();
        mainButtonLayout.fill = GridBagConstraints.HORIZONTAL;
        mainButtonLayout.gridx = 0;
        mainButtonLayout.insets = new Insets(50, 0, 0, 0);

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

        // Button action listeners
        seeJobs.addActionListener(evt -> {
            if (jobPostingsController != null) {
                jobPostingsController.retrieveAvailableFocuses();
            }
            sidebarController.switchToJobBoard();
        });

        seePapers.addActionListener(evt -> sidebarController.switchToPapers());

        myPlans.addActionListener(e -> {
            trackPlanController.execute(sidebarViewModel.getState().getUserName());
            sidebarController.switchToTrackPlan();
        });

        logout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout) {
            this.sidebarController.switchToLogin();
            this.setVisible(false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SidebarState state = sidebarViewModel.getState();
        String username = state.getUserName();
        boolean loggedIn = username != null && !username.trim().isEmpty();

        this.setVisible(loggedIn);
        if (loggedIn) {
            userLoggedIn.setText("Welcome, " + username);
        } else {
            userLoggedIn.setText("");
        }
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
}