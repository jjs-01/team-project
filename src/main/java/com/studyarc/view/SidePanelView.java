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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    //controller of TrackPlan usecase
    private TrackPlanController trackPlanController;

    public SidePanelView(SidebarViewModel sidebarViewModel) {
        this.sidebarViewModel = sidebarViewModel;
        this.sidebarViewModel.addPropertyChangeListener(this);

        seePlans = new JButton("New Plans");
        seePapers = new JButton("Papers");
        seeJobs = new JButton("Jobs");
        myPlans = new JButton("My Plans");

        this.setLayout(new BorderLayout());
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.Y_AXIS));

        this.add(logo, BorderLayout.NORTH);
        mainButtonPanel.add(seePlans);
        mainButtonPanel.add(seePapers);
        mainButtonPanel.add(seeJobs);
        mainButtonPanel.add(myPlans);

        this.add(mainButtonPanel, BorderLayout.CENTER);
        this.add(userLoggedIn, BorderLayout.SOUTH);

        seeJobs.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("clicked Jobs");
                        sidebarController.switchToJobBoard();
                    }
                }
        );

        seePlans.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("clicked Plans");
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(seeJobs)) {

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SidebarState state = sidebarViewModel.getState();
        System.out.println("state= " + state);

    }

    public void setSidebarController(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    public void setTrackPlanController(TrackPlanController trackPlanController) {
        this.trackPlanController = trackPlanController;
    }
}
