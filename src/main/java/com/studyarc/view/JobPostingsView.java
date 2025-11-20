package com.studyarc.view;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.job_postings.JobPostingsState;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.Color;

/**
 * The View for when the user is on the job postings page.
 */
public class JobPostingsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "job postings";

    private final JobPostingsViewModel jobPostingsViewModel;
    private JobPostingsController jobPostingsController = null;

    private final JPanel jobPostingsPanel = new JPanel();
    private JPanel allJobPostingsPanel = new JPanel();

    private JPanel locationSelectionPanel = new JPanel();;
    private JPanel planSelectionPanel = new JPanel();;
    private JPanel salarySelectionPanel = new JPanel();;
    private JPanel sortSelectionPanel = new JPanel();;

    private JComboBox<String> locationComboBox;
    private JComboBox<String> salaryComboBox;
    private JComboBox<String> sortComboBox;
    private JComboBox<String> planComboBox;

    private JLabel pageTitle;
    private JLabel location;
    private JLabel plan;
    private JLabel salary;
    private JLabel sort;

    private JLabel jobTitle;
    private JLabel jobCompany;
    private JLabel jobLocation;
    private JLabel jobSalaryRange;
    private JLabel jobDesc;

    private JButton search;

    private String[] locationOptions = {"Select Country", "gb", "us", "ca"};
    private String[] planOptions = {"Select Plan", "Machine Learning"};
    private String[] salaryOptions = {"Select Option", "$40,000", "$50,000", "$60,000", "$70,000", "$80,000", "$90,000", "$100,000"};
    private String[] sortOptions = {"Select Sort", "default", "hybrid", "date", "salary"};

    private final int indJobCard = 750;
    private final Color jobInfoColor = new Color(255, 225, 143);
    private final Color jobDescColor = new Color(232, 231, 230);

    public JobPostingsView(JobPostingsViewModel jobPostingsViewModel) {
        jobPostingsPanel.setLayout(new BoxLayout(jobPostingsPanel, BoxLayout.Y_AXIS));

        this.jobPostingsViewModel = jobPostingsViewModel;
        this.jobPostingsViewModel.addPropertyChangeListener(this);

        GridBagConstraints userChoices = new GridBagConstraints();
        GridBagConstraints topInfo = new GridBagConstraints();
        topInfo.gridx = 2;

        final JPanel titleAndSelections = new JPanel(new GridBagLayout());

        final JPanel selections = new JPanel(new GridBagLayout());
        selections.setPreferredSize(new Dimension(800, 100));

        // adds the title of the page
        pageTitle = new JLabel(jobPostingsViewModel.TITLE_LABEL);
        pageTitle.setFont(Styling.getMainFont());
        titleAndSelections.add(pageTitle, topInfo);

        // creates the dropdown selection with label for selecting the focus/plan
        plan = new JLabel(jobPostingsViewModel.PLAN_LABEL);
        plan.setFont(Styling.getSubFont().deriveFont(14f));
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        planComboBox.addActionListener(this);
        planSelectionPanel.add(plan);
        planSelectionPanel.add(planComboBox);

        // creates the dropdown selection with label for selecting the location
        location = new JLabel(jobPostingsViewModel.LOCATION_LABEL);
        location.setFont(Styling.getSubFont().deriveFont(14f));
        locationComboBox = new JComboBox<>(locationOptions);
        locationComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        locationComboBox.addActionListener(this);
        locationSelectionPanel.add(location);
        locationSelectionPanel.add(locationComboBox);

        // creates the dropdown selection with label for selecting the minimum selection
        salary = new JLabel(jobPostingsViewModel.SALARAY_LABEL);
        salary.setFont(Styling.getSubFont().deriveFont(14f));
        salaryComboBox = new JComboBox<>(salaryOptions);
        salaryComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        salaryComboBox.addActionListener(this);
        salarySelectionPanel.add(salary);

        // creates the dropdown selection with label for selecting the sorting
//        sort = new JLabel(jobPostingsViewModel.SORT_LABEL);
//        sortComboBox = new JComboBox<>(sortOptions);
//        sortComboBox.addActionListener(this);
//        sortSelectionPanel.add(sort);
//        sortSelectionPanel.add(sortComboBox);
//        selections.add(sortSelectionPanel);

        search = new JButton("Search");
        search.setFont(Styling.getSubFont().deriveFont(12f));

        // adding all the combo boxes to the overall panel
        selections.add(planSelectionPanel, userChoices);
        selections.add(locationSelectionPanel, userChoices);
        selections.add(salarySelectionPanel, userChoices);
        salarySelectionPanel.add(salaryComboBox);
        selections.add(search, userChoices);

        // adding the panel with combo boxes to main panel
        titleAndSelections.add(selections, topInfo);

        jobPostingsPanel.add(titleAndSelections, BorderLayout.NORTH);

        // makes the pane scrollable once items are added
        allJobPostingsPanel.setLayout(new BoxLayout(allJobPostingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroller = new JScrollPane(allJobPostingsPanel);

//        JScrollPane scroller = new JScrollPane(jobPostingsPanel);
        scroller.setPreferredSize(new Dimension(800, 800));
        scroller.getVerticalScrollBar().setUnitIncrement(30);
        scroller.setBorder(BorderFactory.createEmptyBorder());

//        this.add(jobPostingsPanel);
        this.setLayout(new BorderLayout());
        this.add(titleAndSelections, BorderLayout.NORTH);
        this.add(scroller, BorderLayout.CENTER);
//        this.add(scroller);

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(search)) {
                            final JobPostingsState currentState = jobPostingsViewModel.getState();

                            jobPostingsController.execute(
                                    currentState.getFocus(),
                                    currentState.getLocation(),
                                    currentState.getMinSalary(),
                                    currentState.getSort()
                            );
                        }
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JobPostingsState currentState = jobPostingsViewModel.getState();

        // sets the user selected info in the state that will be eventually sent to the controller
        if (e.getSource().equals(planComboBox)) {
            currentState.setFocus(planComboBox.getSelectedItem().toString());
            jobPostingsViewModel.setState(currentState);
        }
        if (e.getSource().equals(locationComboBox)) {
            currentState.setLocation(locationComboBox.getSelectedItem().toString());
            jobPostingsViewModel.setState(currentState);
        }
        if (e.getSource().equals(salaryComboBox)) {
            currentState.setMinSalary(salaryComboBox.getSelectedItem().toString());
            jobPostingsViewModel.setState(currentState);
        }
//        if (e.getSource().equals(sortComboBox)) {
//            currentState.setSort(sortComboBox.getSelectedItem().toString());
//        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();

        if (!jobPostingsState.getListingError().equals("")) {
            JOptionPane.showMessageDialog(this, jobPostingsState.getListingError(), "Error", JOptionPane.ERROR_MESSAGE);
            jobPostingsState.setListingError("");
        }

        if (!jobPostingsState.getJobListings().isEmpty()) {
            allJobPostingsPanel.removeAll();

            createIndividualJobPostings(jobPostingsState);

            allJobPostingsPanel.revalidate();
            allJobPostingsPanel.repaint();

        }


    }

    private void createIndividualJobPostings(JobPostingsState jobPostingsState) {
        for (JobListing jobListing : jobPostingsState.getJobListings()) {
            JPanel individualJobPostingsPanel = new JPanel();
            individualJobPostingsPanel.setLayout(new BoxLayout(individualJobPostingsPanel, BoxLayout.X_AXIS));


            individualJobPostingsPanel.setPreferredSize(new Dimension(indJobCard, 300));
            individualJobPostingsPanel.setMaximumSize(new Dimension(indJobCard, 300));
            individualJobPostingsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            JPanel jobInfo = new JPanel();
            jobInfo.setLayout(new BoxLayout(jobInfo, BoxLayout.Y_AXIS));
            jobInfo.setPreferredSize(new Dimension(indJobCard/3, 300));
            jobInfo.setMaximumSize(new Dimension(indJobCard/3, 300));
            jobInfo.add(Box.createRigidArea(new Dimension(10, 0)));
            jobInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            jobInfo.setBackground(jobInfoColor);

            JPanel jobDescPanel = new JPanel();
            jobDescPanel.setLayout(new BoxLayout(jobDescPanel, BoxLayout.Y_AXIS));
            jobDescPanel.setPreferredSize(new Dimension(indJobCard * 2/3, 300));
            jobDescPanel.setMaximumSize(new Dimension(indJobCard * 2/3, 300));
//                jobDescPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            jobDescPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            jobDescPanel.setBackground(jobDescColor);

            jobTitle = new JLabel("<html><div style='margin:0; padding:0;'>"
                    + jobListing.getTitle()
                    + "</div></html>");
            jobTitle.setMaximumSize(new Dimension(indJobCard/3, 70));
            jobTitle.setPreferredSize(new Dimension(indJobCard/3, 70));
            jobTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            jobTitle.setFont(Styling.getMainFont().deriveFont(Font.BOLD).deriveFont(16f));

            jobCompany = new JLabel(jobListing.getCompanyName());
            jobCompany.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
            jobCompany.setFont(Styling.getSubFont().deriveFont(14f));

            jobLocation = new JLabel(jobListing.getJobLoc());
            jobLocation.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
            jobLocation.setFont(Styling.getBodyFont().deriveFont(12f));

            jobSalaryRange = new JLabel(jobListing.getSalaryMin() + " - " + jobListing.getSalaryMax());
            jobSalaryRange.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            jobSalaryRange.setFont(Styling.getBodyFont().deriveFont(12f));

            jobDesc = new JLabel("<html><div style='margin:0; padding:0; line-height: 35px;'>"
                    + jobListing.getJobDesc()
                    + "</div></html>");
            jobDesc.setMaximumSize(new Dimension(indJobCard * 2 / 3, 300));
            jobDesc.setPreferredSize(new Dimension(indJobCard * 2 / 3, 300));
            jobDesc.setFont(Styling.getBodyFont());

            jobInfo.add(jobTitle);
            jobInfo.add(jobCompany);
            jobInfo.add(jobLocation);
            jobInfo.add(jobSalaryRange);
            jobDescPanel.add(jobDesc);

            individualJobPostingsPanel.add(jobInfo);
            individualJobPostingsPanel.add(jobDescPanel);

            // adds padding between the postings
            allJobPostingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            allJobPostingsPanel.add(individualJobPostingsPanel);

        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }
}
