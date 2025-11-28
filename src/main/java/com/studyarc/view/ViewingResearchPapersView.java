package com.studyarc.view;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
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
    private JPanel plansContainer;
    private ViewingResearchPapersViewModel viewModel;
    private ViewingResearchPapersController controller;

    public ViewingResearchPapersView(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;

        // Listen for changes in the ViewModel
        viewModel.addPropertyChangeListener(evt -> {
            if (ViewingResearchPapersViewModel.PLANS_PROPERTY.equals(evt.getPropertyName())) {
                refreshPlansView();
            } else if ("errorMessage".equals(evt.getPropertyName())) {
                updateErrorMessage();
            }
        });

        this.setLayout(new BorderLayout());

        // Top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Research Papers");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Error message label (initially hidden)
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVisible(false);
        topPanel.add(errorLabel, BorderLayout.SOUTH);

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
        System.out.println("refreshPlansView called");
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
        planPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        planPanel.setBackground(Color.WHITE);

        // Plan title
        JLabel planTitle = new JLabel(plan.getTitle());
        planTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        planPanel.add(planTitle, BorderLayout.NORTH);

        // Research papers cards container
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        cardsPanel.setBackground(Color.WHITE);

        for (ResearchPaper paper : plan.getResearchPapers()) {
            JPanel card = createPaperCard(paper);
            cardsPanel.add(card);
        }

        planPanel.add(cardsPanel, BorderLayout.CENTER);

        return planPanel;
    }

    private JPanel createPaperCard(ResearchPaper paper) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(250, 250, 250));

        // Paper name (title)
        JLabel nameLabel = new JLabel("<html><div style='width: 180px;'>" + paper.getTitle() + "</div></html>");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(nameLabel, BorderLayout.NORTH);

        // Link
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
        } else {
            errorLabel.setVisible(false);
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
    }




}