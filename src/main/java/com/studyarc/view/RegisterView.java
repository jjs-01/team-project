package com.studyarc.view;

import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.login.*;
import com.studyarc.interface_adapter.ui_sidebar.SidebarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.Arrays;

public class RegisterView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName;
    private static final String[] FOCUS_LIST = new String[]{"Artificial Intelligence", "Data Science", "Game Design", "Human Computer Interaction", "Web and Internet Technologies"};

    private final JTextField usernameInput = new JTextField(18);
    private final JTextField passwordInput = new JPasswordField(18);

    private final JLabel errorField;
    private final JButton registerButton;
    private final JButton loginButton;
    private final JComboBox<String> focuses;
    private final RegisterViewModel registerViewModel;
    private RegisterController registerController = null;

    // controller for job postings controller
    private SidebarController sideBarController;

    public RegisterView(RegisterViewModel registerViewModel){
        this.registerViewModel = registerViewModel;
        this.viewName = registerViewModel.getViewName();
        this.registerViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel title = new JLabel("Register");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInput);
        this.add(usernameInfo);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInput);
        this.add(passwordInfo);
        this.errorField = new JLabel("");
        this.errorField.setForeground(new Color(255, 0, 0));
        this.errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(errorField);
        this.focuses = new JComboBox<>(FOCUS_LIST);
        this.add(focuses);
        final JPanel button = new JPanel();
        this.add(button);
        this.registerButton = new JButton("Register");
        this.loginButton = new JButton("Log In");
        button.add(registerButton);
        button.add(loginButton);
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Performed action");

        if(e.getSource()== registerButton){
            RegisterState state = this.registerViewModel.getState();
            state.setUsername(this.usernameInput.getText());
            state.setPassword(this.passwordInput.getText());
            state.setErrorCode("");
            state.setFocus((String) this.focuses.getSelectedItem());

            sideBarController.setUser(state.getUsername());
//            registerController.showSidebar();

            this.registerController.execute(state);
        } else if(e.getSource() == loginButton){
            this.registerController.goToLogin();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RegisterState newState = (RegisterState) evt.getNewValue();
        this.usernameInput.setText(newState.getUsername());
        this.passwordInput.setText(newState.getPassword());
        this.errorField.setText(newState.getErrorCode());
        this.focuses.setSelectedIndex(Arrays.binarySearch(FOCUS_LIST, newState.getFocus()));
    }

    public void setRegisterController(RegisterController registerController){
        this.registerController = registerController;
    }

    public void setSideBarController(SidebarController sideBarController) {
        this.sideBarController = sideBarController;
    }

    public String getViewName() {
        return viewName;
    }
}
