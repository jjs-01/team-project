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

    public JobPostingsView(JobPostingsViewModel jobPostingsViewModel) {
        jobPostingsPanel.setLayout(new BoxLayout(jobPostingsPanel, BoxLayout.Y_AXIS));

        this.jobPostingsViewModel = jobPostingsViewModel;
        this.jobPostingsViewModel.addPropertyChangeListener(this);

        GridBagConstraints userChoices = new GridBagConstraints();
        GridBagConstraints topInfo = new GridBagConstraints();
        topInfo.gridx = 2;

        final JPanel titleAndSelections = new JPanel(new GridBagLayout());
//        titleAndSelections.setLayout(new BoxLayout(titleAndSelections, BoxLayout.Y_AXIS));
//        titleAndSelections.setBackground(Color.GRAY);

        final JPanel selections = new JPanel(new GridBagLayout());
        selections.setPreferredSize(new Dimension(800, 100));
//        selections.setBackground(Color.GRAY);

        // adds the title of the page
        pageTitle = new JLabel(jobPostingsViewModel.TITLE_LABEL);
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleAndSelections.add(pageTitle, topInfo);

        // creates the dropdown selection with label for selecting the focus/plan
        plan = new JLabel(jobPostingsViewModel.PLAN_LABEL);
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.addActionListener(this);
        planSelectionPanel.add(plan);
        planSelectionPanel.add(planComboBox);

        // creates the dropdown selection with label for selecting the location
        location = new JLabel(jobPostingsViewModel.LOCATION_LABEL);
        locationComboBox = new JComboBox<>(locationOptions);
        locationComboBox.addActionListener(this);
        locationSelectionPanel.add(location);
        locationSelectionPanel.add(locationComboBox);

        // creates the dropdown selection with label for selecting the minimum selection
        salary = new JLabel(jobPostingsViewModel.SALARAY_LABEL);
        salaryComboBox = new JComboBox<>(salaryOptions);
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

        // adding all the combo boxes to the overall panel
        selections.add(planSelectionPanel, userChoices);
        selections.add(locationSelectionPanel, userChoices);
        selections.add(salarySelectionPanel, userChoices);
        salarySelectionPanel.add(salaryComboBox);
        selections.add(search, userChoices);

//        selections.setBackground(Color.LIGHT_GRAY);

        // adding the panel with combo boxes to main panel
        titleAndSelections.add(selections, topInfo);

//        titleAndSelections.setBackground(Color.blue);

        jobPostingsPanel.add(titleAndSelections);
//        jobPostingsPanel.setBackground(Color.gray);

        // makes the pane scrollable once items are added
        allJobPostingsPanel.setLayout(new BoxLayout(allJobPostingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroller = new JScrollPane(allJobPostingsPanel);

        jobPostingsPanel.add(scroller);
        scroller.setVisible(true);

        this.add(jobPostingsPanel);

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

        System.out.println("TESTING TO SEE JOB LISTINGS: " + jobPostingsState.getJobListings());

        if (!jobPostingsState.getListingError().equals("")) {
            JOptionPane.showMessageDialog(this, jobPostingsState.getListingError(), "Error", JOptionPane.ERROR_MESSAGE);
            jobPostingsState.setListingError("");
        }

        if (!jobPostingsState.getJobListings().isEmpty()) {
            allJobPostingsPanel.removeAll();

//            System.out.println(jobPostingsState.getJobListings());
//            JLabel response = new JLabel("GOT A POSTIVE RESOPNSE");
//            jobPostingsPanel.add(response);

            // setting the layout for the main panel of the job listings
//            allJobPostingsPanel.setLayout(new BoxLayout(allJobPostingsPanel, BoxLayout.Y_AXIS));
//            JScrollPane scroller = new JScrollPane(allJobPostingsPanel);


//            jobInfo.setLayout(new GridLayout(1, 4));

            for (JobListing jobListing : jobPostingsState.getJobListings()) {
                JPanel individualJobPostingsPanel = new JPanel();
                individualJobPostingsPanel.setLayout(new BoxLayout(individualJobPostingsPanel, BoxLayout.Y_AXIS));


                individualJobPostingsPanel.setPreferredSize(new Dimension(500, 100));
                individualJobPostingsPanel.setBackground(Color.LIGHT_GRAY);
                individualJobPostingsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                JPanel jobInfo = new JPanel(new GridBagLayout());
                jobInfo.setPreferredSize(new Dimension(500, 10));

                GridBagConstraints jobInfoConst = new GridBagConstraints();
//                jobInfoConst.gridx = 1;
//                jobInfoConst.gridy = 4
                jobInfoConst.gridwidth = 4;
//                jobInfoConst.fill = GridBagConstraints.HORIZONTAL;
                jobInfoConst.insets = new Insets(0, 10, 0, 10);

                jobTitle = new JLabel(jobListing.getTitle() + ";");
                jobCompany = new JLabel(jobListing.getCompanyName() + ";");
                jobLocation = new JLabel(jobListing.getJobLoc() + ";");
                jobSalaryRange = new JLabel(jobListing.getSalaryMin() + " - " + jobListing.getSalaryMax());
                jobDesc = new JLabel(jobListing.getJobDesc());

                jobInfo.add(jobTitle, jobInfoConst);
                jobInfo.add(jobCompany, jobInfoConst);
                jobInfo.add(jobLocation, jobInfoConst);
                jobInfo.add(jobSalaryRange, jobInfoConst);

                individualJobPostingsPanel.add(jobInfo);
                individualJobPostingsPanel.add(jobDesc);

                // adds padding between the postings
                allJobPostingsPanel.add(Box.createRigidArea(new Dimension(20, 10)));

                allJobPostingsPanel.add(individualJobPostingsPanel);

            }


//            jobPostingsPanel.add(allJobPostingsPanel);
//            jobPostingsPanel.revalidate();
//            jobPostingsPanel.repaint();

            allJobPostingsPanel.revalidate();
            allJobPostingsPanel.repaint();

        }


    }

    public String getViewName() {
        return viewName;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }
}
