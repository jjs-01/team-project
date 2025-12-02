package com.studyarc.view;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.job_postings.JobPostingsState;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.Color;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The View for when the user is on the job postings page.
 */
public class JobPostingsView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String VIEW_NAME = "job postings";

    private final transient JobPostingsViewModel jobPostingsViewModel;
    private static final String SELECT_FOCUS_TEXT = "Select Focus";
    private transient JobPostingsController jobPostingsController = null;

    private final JPanel allJobPostingsPanel = new JPanel();

    private final JComboBox<String> locationComboBox;
    private final JComboBox<String> salaryComboBox;
    private final JComboBox<String> sortComboBox;
    private final JComboBox<String> planComboBox;

    private final JLabel listingNumberLabel;
    private String listingNumber = "0";

    private final JButton search;

    transient List<String> planOptions = new ArrayList<>();

    public JobPostingsView(JobPostingsViewModel jobPostingsViewModel) {
        JPanel jobPostingsPanel = new JPanel();
        jobPostingsPanel.setLayout(new BoxLayout(jobPostingsPanel, BoxLayout.Y_AXIS));

        this.jobPostingsViewModel = jobPostingsViewModel;
        this.jobPostingsViewModel.addPropertyChangeListener(this);

        GridBagConstraints userChoices = new GridBagConstraints();
        GridBagConstraints topInfo = new GridBagConstraints();
        topInfo.gridx = 2;

        final JPanel titleAndSelections = new JPanel(new GridBagLayout());

        final JPanel selections = new JPanel(new GridLayout(2, 3, 10, 10));
        selections.setPreferredSize(new Dimension(800, 128));
        selections.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // adds the title of the page
        JLabel pageTitle = new JLabel(JobPostingsViewModel.TITLE_LABEL);
        pageTitle.setFont(Styling.getMainFont());
        titleAndSelections.add(pageTitle, topInfo);
        titleAndSelections.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        // creates the dropdown selection with label for selecting the focus/plan
        JLabel plan = new JLabel(JobPostingsViewModel.FOCUS);
        plan.setFont(Styling.getSubFont().deriveFont(14f));
        planOptions = new ArrayList<>(List.of(SELECT_FOCUS_TEXT));
        planComboBox = new JComboBox<>(planOptions.toArray(new String[0]));
        planComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        planComboBox.addActionListener(this);
        JPanel planSelectionPanel = new JPanel();
        planSelectionPanel.add(plan);
        planSelectionPanel.add(planComboBox);

        // creates the dropdown selection with label for selecting the location
        JLabel location = new JLabel(JobPostingsViewModel.LOCATION_LABEL);
        location.setFont(Styling.getSubFont().deriveFont(14f));
        String[] locationOptions = {"Select Country", "gb", "us", "ca"};
        locationComboBox = new JComboBox<>(locationOptions);
        locationComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        locationComboBox.addActionListener(this);
        JPanel locationSelectionPanel = new JPanel();
        locationSelectionPanel.add(location);
        locationSelectionPanel.add(locationComboBox);

        // creates the dropdown selection with label for selecting the minimum selection
        JLabel salary = new JLabel(JobPostingsViewModel.SALARAY_LABEL);
        salary.setFont(Styling.getSubFont().deriveFont(14f));
        String[] salaryOptions = {"Select Option", "$40,000", "$50,000", "$60,000", "$70,000",
                "$80,000", "$90,000", "$100,000"};
        salaryComboBox = new JComboBox<>(salaryOptions);
        salaryComboBox.setFont(Styling.getSubFont().deriveFont(12f));
        salaryComboBox.addActionListener(this);
        JPanel salarySelectionPanel = new JPanel();
        salarySelectionPanel.add(salary);

        // creates the dropdown selection with label for selecting the sorting
        JLabel sort = new JLabel(JobPostingsViewModel.SORT_LABEL);
        String[] sortOptions = {"Select Sort", "date", "salary", "relevance"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.addActionListener(this);
        JPanel sortSelectionPanel = new JPanel();
        sortSelectionPanel.add(sort);
        sortSelectionPanel.add(sortComboBox);

        listingNumberLabel = new JLabel("Showing Results: " + listingNumber);
        listingNumberLabel.setFont(Styling.getSubFont().deriveFont(12f));
        JPanel listingNumberPanel = new JPanel();
        listingNumberPanel.add(listingNumberLabel);

        search = new JButton("Search");
        search.setFont(Styling.getSubFont().deriveFont(12f));

        // adding all the combo boxes to the overall panel
        selections.add(planSelectionPanel, userChoices);
        selections.add(locationSelectionPanel, userChoices);
        selections.add(salarySelectionPanel, userChoices);
        salarySelectionPanel.add(salaryComboBox);
        selections.add(sortSelectionPanel);
        selections.add(search, userChoices);
        selections.add(listingNumberPanel, userChoices);

        // adding the panel with combo boxes to main panel
        titleAndSelections.add(selections, topInfo);

        jobPostingsPanel.add(titleAndSelections, BorderLayout.NORTH);

        // makes the pane scrollable once items are added
        allJobPostingsPanel.setLayout(new BoxLayout(allJobPostingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroller = new JScrollPane(allJobPostingsPanel);

        scroller.setPreferredSize(new Dimension(Styling.getWidth(), Styling.getHeight()));
        scroller.getVerticalScrollBar().setUnitIncrement(Styling.getScrollPace());
        scroller.setBorder(BorderFactory.createEmptyBorder());

        this.setLayout(new BorderLayout());
        this.add(titleAndSelections, BorderLayout.NORTH);
        this.add(scroller, BorderLayout.CENTER);

        search.addActionListener(
                e -> {
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
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JobPostingsState currentState = jobPostingsViewModel.getState();

        // sets the user selected info in the state that will be eventually sent to the controller
        if (e.getSource().equals(planComboBox)) {
            currentState.setFocus(Objects.requireNonNull(planComboBox.getSelectedItem()).toString());
            jobPostingsViewModel.setState(currentState);
        }
        if (e.getSource().equals(locationComboBox)) {
            currentState.setLocation(Objects.requireNonNull(locationComboBox.getSelectedItem()).toString());
            jobPostingsViewModel.setState(currentState);
        }
        if (e.getSource().equals(salaryComboBox)) {
            currentState.setMinSalary(Objects.requireNonNull(salaryComboBox.getSelectedItem()).toString());
            jobPostingsViewModel.setState(currentState);
        }
        if (e.getSource().equals(sortComboBox)) {
            currentState.setSort(Objects.requireNonNull(sortComboBox.getSelectedItem()).toString());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();
        List<JobListing> resetJobs = new ArrayList<>();

        // updates the focus selection combobox
        updateFocusSelection(jobPostingsState);

        // if an error appears, clear the panel and show the error
        if (!jobPostingsState.getListingError().isEmpty()) {
            JOptionPane.showMessageDialog(this, jobPostingsState.getListingError(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            allJobPostingsPanel.removeAll();
            allJobPostingsPanel.revalidate();
            allJobPostingsPanel.repaint();

            jobPostingsState.setListingError("");
            jobPostingsState.setJobListings(resetJobs);
            jobPostingsState.setNumberOfResults("0");
            showListingTotal(jobPostingsState);

        }

        // show the job listings if there are some
        if (!jobPostingsState.getJobListings().isEmpty()) {
            allJobPostingsPanel.removeAll();

            showListingTotal(jobPostingsState);

            createIndividualJobPostings(jobPostingsState);

            allJobPostingsPanel.revalidate();
            allJobPostingsPanel.repaint();

        }


    }

    private void updateFocusSelection(JobPostingsState jobPostingsState) {
        // saves previous selection
        String previousSelection = (String) planComboBox.getSelectedItem();

        ArrayList<String> usersFocuses = jobPostingsState.getFocuses();
        planOptions.clear();
        planOptions.add(SELECT_FOCUS_TEXT);
        // Adds the focuses
        for (String focus :  usersFocuses) {
            if (!planOptions.contains(focus)) {
                planOptions.add(focus);
            }
        }
        // resets the combo box with the updated selections
        planComboBox.setModel(new DefaultComboBoxModel<>(planOptions.toArray(new String[0])));

        // sets the previous selection
        if (previousSelection != null && planOptions.contains(previousSelection)) {
            planComboBox.setSelectedItem(previousSelection);
        }

        // if its empty
        if (usersFocuses.isEmpty()) {
            planComboBox.setSelectedItem(SELECT_FOCUS_TEXT);
        }
    }

    private void showListingTotal(JobPostingsState jobPostingsState) {
        listingNumber = jobPostingsState.getNumberOfResults();
        listingNumberLabel.setText("Showing Results: " + listingNumber);
    }

    private void createIndividualJobPostings(JobPostingsState jobPostingsState) {
        JLabel jobTitle;
        JLabel jobCompany;
        JLabel jobLocation;
        JLabel jobSalaryRange;
        JLabel jobDesc;
        for (JobListing jobListing : jobPostingsState.getJobListings()) {
            JPanel individualJobPostingsPanel = new JPanel();
            individualJobPostingsPanel.setLayout(new BoxLayout(individualJobPostingsPanel, BoxLayout.X_AXIS));

            int indJobCard = 750;
            individualJobPostingsPanel.setPreferredSize(new Dimension(indJobCard, 300));
            individualJobPostingsPanel.setMaximumSize(new Dimension(indJobCard, 300));
            individualJobPostingsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            JPanel jobInfo = new JPanel();
            jobInfo.setLayout(new BoxLayout(jobInfo, BoxLayout.Y_AXIS));
            jobInfo.setPreferredSize(new Dimension(indJobCard /3, 300));
            jobInfo.setMaximumSize(new Dimension(indJobCard /3, 300));
            jobInfo.add(Box.createRigidArea(new Dimension(10, 0)));
            jobInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            jobInfo.setBackground(Styling.getYellow());

            JPanel jobDescPanel = new JPanel();
            jobDescPanel.setLayout(new BoxLayout(jobDescPanel, BoxLayout.Y_AXIS));
            jobDescPanel.setPreferredSize(new Dimension(indJobCard * 2/3, 300));
            jobDescPanel.setMaximumSize(new Dimension(indJobCard * 2/3, 300));
            jobDescPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            jobDescPanel.setBackground(Styling.getGray());

            jobTitle = new JLabel("<html><div style='margin:0; padding:0;'>"
                    + jobListing.getTitle()
                    + "</div></html>");
            jobTitle.setMaximumSize(new Dimension(indJobCard /3, 90));
            jobTitle.setPreferredSize(new Dimension(indJobCard /3, 90));
            jobTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            jobTitle.setFont(Styling.getMainFont().deriveFont(Font.BOLD).deriveFont(16f));

            jobCompany = new JLabel(jobListing.getCompanyName());
            jobCompany.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
            jobCompany.setFont(Styling.getSubFont().deriveFont(14f));

            jobLocation = new JLabel(jobListing.getJobLoc());
            jobLocation.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
            jobLocation.setFont(Styling.getBodyFont().deriveFont(12f));

            jobSalaryRange = new JLabel(jobListing.getFormattedMin() + " - " + jobListing.getFormattedMax());
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

            String url = jobListing.getRedirectUrl();
            if (url != null) {
                individualJobPostingsPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                individualJobPostingsPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (Exception ex) {
                            // doesn't open link
                        }
                    }
                });
            }

            allJobPostingsPanel.add(individualJobPostingsPanel);

        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }
}
