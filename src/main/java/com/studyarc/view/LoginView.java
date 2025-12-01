package com.studyarc.view;

import com.studyarc.interface_adapter.login.LoginController;
import com.studyarc.interface_adapter.login.LoginState;
import com.studyarc.interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName;

    private final JTextField usernameInput = new JTextField(18);
    private final JTextField passwordInput = new JPasswordField(18);

    private final JLabel errorField;
    private final JButton logInButton;
    private final JButton registerButton;
    private final LoginViewModel loginViewModel;
    private LoginController loginController = null;


    public LoginView(LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
        this.viewName = loginViewModel.getViewName();
        this.loginViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel title = new JLabel("Login");
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
        final JPanel button = new JPanel();
        this.add(button);
        logInButton = new JButton("Log In");
        this.registerButton = new JButton("Register");
        button.add(logInButton);
        button.add(registerButton);
        logInButton.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==logInButton){
            LoginState state = this.loginViewModel.getState();
            state.setUsername(this.usernameInput.getText());
            state.setPassword(this.passwordInput.getText());
            state.setErrorCode("");
            this.loginController.execute(state);
        }
        else if(e.getSource() == registerButton){
            this.loginController.goToRegister();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState newState = (LoginState) evt.getNewValue();
        this.usernameInput.setText(newState.getUsername());
        this.passwordInput.setText(newState.getPassword());
        this.errorField.setText(newState.getErrorCode());
    }

    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }

    public String getViewName() {
        return viewName;
    }
}
