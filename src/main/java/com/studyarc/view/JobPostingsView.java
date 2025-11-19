package com.studyarc.view;

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

    private JButton search;

    private String[] locationOptions = {"Select Country", "gb", "us", "ca"};
    private String[] planOptions = {"Select Plan", "Machine Learning"};
    private String[] salaryOptions = {"Select Option", "$40,000", "$50,000", "$60,000", "$70,000", "$80,000", "$90,000", "$100,000"};
    private String[] sortOptions = {"Select Sort", "default", "hybrid", "date", "salary"};

    public JobPostingsView(JobPostingsViewModel jobPostingsViewModel) {

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

        // adding the panel with combo boxes to main panel
        titleAndSelections.add(selections, topInfo);

        this.add(titleAndSelections, BorderLayout.NORTH);

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
        }
        if (e.getSource().equals(locationComboBox)) {
            currentState.setLocation(locationComboBox.getSelectedItem().toString());
        }
        if (e.getSource().equals(salaryComboBox)) {
            currentState.setMinSalary(salaryComboBox.getSelectedItem().toString());
        }
        if (e.getSource().equals(sortComboBox)) {
            currentState.setSort(sortComboBox.getSelectedItem().toString());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();

        if (jobPostingsState.getListingError() != null) {
            JOptionPane.showMessageDialog(this, jobPostingsState.getListingError(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }
}
