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

        logo.setFont(Styling.getMainFont().deriveFont(20f).deriveFont(Font.BOLD));

        seePlans = new JButton("New Plans");
        seePapers = new JButton("Papers");
        seeJobs = new JButton("Jobs");
        myPlans = new JButton("My Plans");

        this.setLayout(new BorderLayout());
//        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.Y_AXIS));
        mainButtonPanel.setLayout(new GridBagLayout());

        this.setMinimumSize(new Dimension(700, 400));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));

        GridBagConstraints mainButtonLayout = new GridBagConstraints();
        mainButtonLayout.gridx = 4;
        mainButtonLayout.ipady = 15;
        mainButtonLayout.insets = new Insets(50,0,0,0);
//        mainButtonLayout.gridy = 2;
        mainButtonLayout.fill = GridBagConstraints.HORIZONTAL;

        mainButtonPanel.add(seePlans, mainButtonLayout);
        mainButtonPanel.add(seePapers, mainButtonLayout);
        mainButtonPanel.add(seeJobs, mainButtonLayout);
        mainButtonPanel.add(myPlans, mainButtonLayout);

        this.add(logo, BorderLayout.NORTH);
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
