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

    private String[] locationOptions = {"gb", "us", "ca"};
    private String[] planOptions = {"Machine Learning"};
    private String[] salaryOptions = {"$40,000", "$50,000", "$60,000", "$70,000", "$80,000", "$90,000", "$100,000"};
    private String[] sortOptions = {"default", "hybrid", "date", "salary"};



    public JobPostingsView(JobPostingsViewModel jobPostingsViewModel) {

        this.jobPostingsViewModel = jobPostingsViewModel;
        this.jobPostingsViewModel.addPropertyChangeListener(this);

        final JPanel titleAndSelections = new JPanel();
        titleAndSelections.setLayout(new BoxLayout(titleAndSelections, BoxLayout.Y_AXIS));

        final JPanel selections = new JPanel();

        // adds the title of the page
        pageTitle = new JLabel(jobPostingsViewModel.TITLE_LABEL);
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleAndSelections.add(pageTitle);

        // creates the dropdown selection with label for selecting the focus/plan
        plan = new JLabel(jobPostingsViewModel.PLAN_LABEL);
        planComboBox = new JComboBox<>(planOptions);
        planSelectionPanel.add(plan);
        planSelectionPanel.add(planComboBox);
        selections.add(planSelectionPanel);

        // creates the dropdown selection with label for selecting the location
        location = new JLabel(jobPostingsViewModel.LOCATION_LABEL);
        locationComboBox = new JComboBox<>(locationOptions);
        locationSelectionPanel.add(location);
        locationSelectionPanel.add(locationComboBox);
        selections.add(locationSelectionPanel);

        // creates the dropdown selection with label for selecting the minimum selection
        salary = new JLabel(jobPostingsViewModel.SALARAY_LABEL);
        salaryComboBox = new JComboBox<>(salaryOptions);
        salarySelectionPanel.add(salary);
        salarySelectionPanel.add(salaryComboBox);
        selections.add(salarySelectionPanel);

        // creates the dropdown selection with label for selecting the sorting
        sort = new JLabel(jobPostingsViewModel.SORT_LABEL);
        sortComboBox = new JComboBox<>(sortOptions);
        sortSelectionPanel.add(sort);
        sortSelectionPanel.add(sortComboBox);
        selections.add(sortSelectionPanel);

        search = new JButton("Search");
        selections.add(search);

        titleAndSelections.add(selections);

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
            currentState.setFocus(locationComboBox.getSelectedItem().toString());
        }
        if (e.getSource().equals(salaryComboBox)) {
            currentState.setFocus(salaryComboBox.getSelectedItem().toString());
        }
        if (e.getSource().equals(sortComboBox)) {
            currentState.setFocus(sortComboBox.getSelectedItem().toString());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

    public void setJobPostingsController(JobPostingsController jobPostingsController) {
        this.jobPostingsController = jobPostingsController;
    }
}
