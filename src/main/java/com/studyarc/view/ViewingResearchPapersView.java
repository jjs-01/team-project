package com.studyarc.view;

import com.studyarc.entity.ResearchPaper;
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

    public ViewingResearchPapersView(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

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

    public void refreshTable() {
        tableModel.setRowCount(0);

        List<ResearchPaper> papers = viewModel.getResearchPapers();
        for (ResearchPaper paper : papers) {
            Object[] row = {
                    paper.getTitle(),
                    paper.getAuthors(),
                    paper.getUrl()
            };
            tableModel.addRow(row);
        }
    }
}