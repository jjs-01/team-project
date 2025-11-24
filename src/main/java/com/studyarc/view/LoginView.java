package com.studyarc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";

    private final JTextField usernameInput = new JTextField(18);
    private final JTextField passwordInput = new JPasswordField(18);

    private final JButton logInButton;

    public LoginView(){
        final JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInput);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInput);
        final JPanel button = new JPanel();
        logInButton = new JButton("Log In");
        button.add(logInButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logInButton){

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
