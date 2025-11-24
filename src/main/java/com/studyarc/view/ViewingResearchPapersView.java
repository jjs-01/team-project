package com.studyarc.view;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.interface_adapter.viewing_research_papers.ResearchPaperState;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersController;
import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewingResearchPapersView extends JPanel {
    private JLabel titleLabel;
    private JTable papersTable;
    private DefaultTableModel tableModel;
    private ViewingResearchPapersViewModel viewModel;
    private ViewingResearchPapersController controller;
    private boolean dataLoaded = false;

    public ViewingResearchPapersView(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());
        viewModel.addPropertyChangeListener(evt -> {
            if (ViewingResearchPapersViewModel.PAPERS_PROPERTY.equals(evt.getPropertyName())) {
                refreshTable();
            }
        });
        JPanel topPanel = new JPanel();
        titleLabel = new JLabel("Research Papers");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanel.add(titleLabel);
        this.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Title", "Authors", "URL"};
        tableModel = new DefaultTableModel(columns, 0);
        papersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(papersTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public String getViewName() {
        return "viewing research papers";
    }

    public void setViewingResearchPapersController(ViewingResearchPapersController controller) {
        this.controller = controller;
    }



    @Override
    public void addNotify() {
        super.addNotify();
        if (controller != null && !dataLoaded && this.isShowing()) {
            controller.handleViewingResearchPapers();
            dataLoaded = true;
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && controller != null) {
            controller.handleViewingResearchPapers();
        }
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
    }
}