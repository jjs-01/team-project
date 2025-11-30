package com.studyarc.view;

import java.awt.*;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import com.studyarc.interface_adapter.add_reflection.AddReflectionController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionState;
import com.studyarc.interface_adapter.add_reflection.AddReflectionViewModel;

public class AddReflectionView extends JDialog implements PropertyChangeListener {

    private final AddReflectionViewModel viewModel;
    private final AddReflectionController controller;
    private final String planTitle;
    private final String username;

    private final JTextArea content = new JTextArea(6, 20);
    private final JButton saveButton = new JButton("Save");

    public AddReflectionView(Window owner,
                             AddReflectionViewModel viewModel,
                             AddReflectionController controller,
                             String planTitle,
                             String username) {

        super(owner, "Add Reflection", ModalityType.APPLICATION_MODAL);

        this.viewModel = viewModel;
        this.controller = controller;
        this.planTitle = planTitle;
        this.username = username;

        this.viewModel.addPropertyChangeListener(this);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Reflection:"), BorderLayout.NORTH);

        final JScrollPane scrollPane = new JScrollPane(content);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(saveButton, BorderLayout.SOUTH);
        this.add(panel);

        saveButton.addActionListener(e -> save());

        this.setSize(350, 250);
        this.setLocationRelativeTo(owner);
    }

    private void save() {
        final String contents = content.getText().trim();

        if (contents.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Reflection cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        controller.execute(username, planTitle, contents);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddReflectionState state = viewModel.getState();

        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            viewModel.removePropertyChangeListener(this);
            this.dispose();
        }

        if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getSuccessMessage(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            viewModel.removePropertyChangeListener(this);
            this.dispose();
        }
    }
}
