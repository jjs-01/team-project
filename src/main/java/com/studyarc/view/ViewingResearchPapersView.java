package com.studyarc.view;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
import com.studyarc.interface_adapter.add_papers_to_plan.AddPapersToPlanController;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersController;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.List;

public class ViewingResearchPapersView extends JPanel {
    private JLabel titleLabel;
    private JLabel errorLabel;
    private JLabel successLabel;
    private JPanel plansContainer;
    private ViewingResearchPapersViewModel viewModel;
    private ViewingResearchPapersController controller;
    private AddPapersToPlanController addPapersController;

    public ViewingResearchPapersView(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(evt -> {
            System.out.println("Property change detected: " + evt.getPropertyName());

            if (ViewingResearchPapersViewModel.PLANS_PROPERTY.equals(evt.getPropertyName())) {
                refreshPlansView();
            } else if ("errorMessage".equals(evt.getPropertyName())) {
                updateErrorMessage();
            } else if ("successMessage".equals(evt.getPropertyName())) {
                updateSuccessMessage();
            } else if (ViewingResearchPapersViewModel.REFRESH_PROPERTY.equals(evt.getPropertyName())) {
                // CRITICAL: Refresh the view when papers are added
                System.out.println("Refresh triggered - reloading plans from database");
                if (controller != null) {
                    // Use SwingUtilities.invokeLater to ensure UI update happens on EDT
                    SwingUtilities.invokeLater(() -> {
                        System.out.println("Executing refresh on EDT");
                        controller.handleViewingResearchPapers();
                    });
                } else {
                    System.err.println("Controller is null - cannot refresh!");
                }
            }
        });

        this.setLayout(new BorderLayout());

        // Top panel with title and messages
        JPanel topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Research Papers");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Message panel for error and success messages
        JPanel messagePanel = new JPanel(new GridLayout(2, 1));

        // Error message label (initially hidden)
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVisible(false);
        messagePanel.add(errorLabel);

        // Success message label (initially hidden)
        successLabel = new JLabel();
        successLabel.setForeground(new Color(0, 128, 0)); // Green
        successLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setVisible(false);
        messagePanel.add(successLabel);

        topPanel.add(messagePanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

        // Plans container with scroll
        plansContainer = new JPanel();
        plansContainer.setLayout(new BoxLayout(plansContainer, BoxLayout.Y_AXIS));
        plansContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(plansContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshPlansView() {
        plansContainer.removeAll();
        List<StudyPlan> plans = viewModel.getStudyPlans();
        if (plans == null || plans.isEmpty()) {
            JLabel emptyLabel = new JLabel("No plans available");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            plansContainer.add(Box.createVerticalGlue());
            plansContainer.add(emptyLabel);
            plansContainer.add(Box.createVerticalGlue());
            errorLabel.setVisible(false);
        } else {
            for (StudyPlan plan : plans) {
                JPanel planPanel = createPlanPanel(plan);
                plansContainer.add(planPanel);
                plansContainer.add(Box.createVerticalStrut(20));
            }
            errorLabel.setVisible(false);
        }

        plansContainer.revalidate();
        plansContainer.repaint();
    }

    private JPanel createPlanPanel(StudyPlan plan) {
        JPanel planPanel = new JPanel();
        planPanel.setLayout(new BorderLayout(10, 10));
        planPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        planPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        planPanel.setBackground(Color.WHITE);

        // Top section: Title and "Add Papers" button
        JPanel topSection = new JPanel(new BorderLayout());

        JLabel planTitle = new JLabel(plan.getTitle());
        planTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        topSection.add(planTitle, BorderLayout.WEST);

        // Add "Search Papers" button
        JButton addPapersButton = new JButton("+ Search Papers");
        addPapersButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        addPapersButton.addActionListener(e -> showSearchPapersDialog(plan));
        topSection.add(addPapersButton, BorderLayout.EAST);

        planPanel.add(topSection, BorderLayout.NORTH);

        // Research papers cards container
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        cardsPanel.setBackground(Color.WHITE);

        if (plan.getResearchPapers().isEmpty()) {
            JLabel noPapersLabel = new JLabel("No research papers yet. Click 'Search Papers' to add some!");
            noPapersLabel.setForeground(Color.GRAY);
            cardsPanel.add(noPapersLabel);
        } else {
            for (ResearchPaper paper : plan.getResearchPapers()) {
                JPanel card = createPaperCard(paper, plan);
                cardsPanel.add(card);
            }
        }

        planPanel.add(cardsPanel, BorderLayout.CENTER);

        return planPanel;
    }

    private void showSearchPapersDialog(StudyPlan plan) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Search Research Papers", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Plan name label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel planLabel = new JLabel("Adding papers to: " + plan.getTitle());
        planLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        contentPanel.add(planLabel, gbc);

        // Search query label
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel queryLabel = new JLabel("Search Query:");
        contentPanel.add(queryLabel, gbc);

        // Search query field
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField queryField = new JTextField();
        queryField.setToolTipText("Enter keywords to search for research papers");
        contentPanel.add(queryField, gbc);

        // Number of papers label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel limitLabel = new JLabel("Number of papers:");
        contentPanel.add(limitLabel, gbc);

        // Number of papers spinner
        gbc.gridx = 1;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 1, 10, 1);
        JSpinner limitSpinner = new JSpinner(spinnerModel);
        contentPanel.add(limitSpinner, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton searchButton = new JButton("Search & Add");
        JButton cancelButton = new JButton("Cancel");

        searchButton.addActionListener(e -> {
            String query = queryField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a search query",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int limit = (Integer) limitSpinner.getValue();

            // Call the controller
            if (addPapersController != null) {
                addPapersController.execute(plan.getTitle(), query, limit);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Add Papers feature not configured",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createPaperCard(ResearchPaper paper, StudyPlan plan) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(200, 140));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(250, 250, 250));

        // Top panel with title and delete button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(250, 250, 250));

        // Paper name (title)
        JLabel nameLabel = new JLabel("<html><div style='width: 150px;'>" + paper.getTitle() + "</div></html>");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        topPanel.add(nameLabel, BorderLayout.CENTER);

        // Delete button (top-right) - small red circle with X
        JButton deleteButton = new JButton("×");
        deleteButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        deleteButton.setPreferredSize(new Dimension(16, 16));
        deleteButton.setBorder(null);
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setOpaque(false);
        deleteButton.setToolTipText("Remove this paper");
        deleteButton.setForeground(new Color(200, 50, 50));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    card,
                    "Remove '" + paper.getTitle() + "' from this plan?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Remove the paper from the plan
                plan.getResearchPapers().remove(paper);

                // Save the plan to persist the change
                controller.handleViewingResearchPapers();
                viewModel.setSuccessMessage("Paper removed successfully");
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setForeground(new Color(255, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setForeground(new Color(200, 50, 50));
            }
        });

        topPanel.add(deleteButton, BorderLayout.EAST);
        card.add(topPanel, BorderLayout.NORTH);

        // Link at bottom
        JLabel linkLabel = new JLabel("<html><u style='color: blue;'>link</u></html>");
        linkLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(paper.getUrl()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(card,
                            "Could not open link: " + paper.getUrl(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkLabel.setText("<html><u style='color: darkblue;'>link</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkLabel.setText("<html><u style='color: blue;'>link</u></html>");
            }
        });

        card.add(linkLabel, BorderLayout.SOUTH);

        return card;
    }

    private void updateErrorMessage() {
        String error = viewModel.getErrorMessage();
        if (error != null && !error.isEmpty()) {
            errorLabel.setText("⚠ " + error);
            errorLabel.setVisible(true);
            successLabel.setVisible(false);
        } else {
            errorLabel.setVisible(false);
        }
    }

    private void updateSuccessMessage() {
        String success = viewModel.getSuccessMessage();
        if (success != null && !success.isEmpty()) {
            successLabel.setText("✓ " + success);
            successLabel.setVisible(true);
            errorLabel.setVisible(false);

            // Hide success message after 5 seconds
            Timer timer = new Timer(5000, e -> successLabel.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        } else {
            successLabel.setVisible(false);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && controller != null) {
            controller.handleViewingResearchPapers();
        }
    }

    public String getViewName() {
        return "viewing research papers";
    }

    public void setViewingResearchPapersController(ViewingResearchPapersController controller) {
        this.controller = controller;
        System.out.println("Controller set: " + (controller != null));
    }

    public void setAddPapersController(AddPapersToPlanController controller) {
        this.addPapersController = controller;
        System.out.println("AddPapersController set: " + (controller != null));
    }
}