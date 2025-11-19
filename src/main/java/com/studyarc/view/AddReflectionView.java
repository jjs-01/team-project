package com.studyarc.view;

import com.studyarc.interface_adapter.reflection_log.AddReflectionController;
import com.studyarc.interface_adapter.reflection_log.AddReflectionState;
import com.studyarc.interface_adapter.reflection_log.AddReflectionViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddReflectionView extends JDialog implements PropertyChangeListener {

    private final AddReflectionViewModel viewModel;
    private final AddReflectionController controller;
    private final String planName;

    private final JTextArea content = new JTextArea(6, 20);
    private final JButton saveButton = new JButton("Save");

    public AddReflectionView(Frame owner,
                             AddReflectionViewModel viewModel,
                             AddReflectionController controller,
                             String planName) {

        super(owner, "Add Reflection", true);

        this.viewModel = viewModel;
        this.controller = controller;
        this.planName = planName;

        this.viewModel.addPropertyChangeListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Reflection:"), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(content);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(saveButton, BorderLayout.SOUTH);
        this.add(panel);

        saveButton.addActionListener(e -> save());

        this.setSize(350, 250);
        this.setLocationRelativeTo(owner);
    }

    private void save() {
        String contents = content.getText().trim();

        if (contents.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Reflection cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        controller.execute(planName, contents);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AddReflectionState state = viewModel.getState();

        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getSuccessMessage(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            this.dispose();
        }
    }
}