package com.studyarc.view;

import com.studyarc.interface_adapter.viewing_research_papers.ResearchPaperState;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersController;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewingResearchPapersView extends JPanel {
    private JLabel titleLabel;
    private JLabel errorLabel;  // NEW: Error message label
    private JTable papersTable;
    private DefaultTableModel tableModel;
    private ViewingResearchPapersViewModel viewModel;
    private ViewingResearchPapersController controller;

    public ViewingResearchPapersView(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;

        // Listen for changes in the ViewModel
        viewModel.addPropertyChangeListener(evt -> {
            if (ViewingResearchPapersViewModel.PAPERS_PROPERTY.equals(evt.getPropertyName())) {
                refreshTable();
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
        errorLabel.setVisible(false);  // Hidden by default
        topPanel.add(errorLabel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Title", "Authors", "URL"};
        tableModel = new DefaultTableModel(columns, 0);
        papersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(papersTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<ResearchPaperState> papers = viewModel.getResearchPapers();
        for (ResearchPaperState paper : papers) {
            Object[] row = {
                    paper.getTitle(),
                    paper.getAuthors(),
                    paper.getUrl()
            };
            tableModel.addRow(row);
        }

        // Hide error if we successfully loaded papers
        if (!papers.isEmpty()) {
            errorLabel.setVisible(false);
        }
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