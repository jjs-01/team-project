package com.studyarc.app;

import javax.swing.*;
import java.awt.*;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.interface_adapter.login.*;
import com.studyarc.use_case.login.LoginInputBoundary;
import com.studyarc.use_case.login.LoginInteractor;
import com.studyarc.use_case.login.LoginOutputBoundary;
import com.studyarc.view.*;

public class AppBuilder {
    private final DatabaseAccess dao = new DatabaseAccess();
    private final JPanel overallPanel = new JPanel();
    private final BorderLayout borderLayout = new BorderLayout();
    private final JPanel mainUIPanel = new JPanel();
    private final JPanel usecasePanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private SidePanelView sidePanelView;
    private MilestoneTasksView milestoneTaskView;

    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private RegisterViewModel registerViewModel;
    private RegisterView registerView;


    public AppBuilder() {
        overallPanel.setLayout(cardLayout); // includes login and other things
        mainUIPanel.setLayout(borderLayout); // for the side panel
        usecasePanel.setLayout(cardLayout);
        // another panel for switching between the panels
    }

    public AppBuilder addSidePanel() {
        sidePanelView = new SidePanelView();
        mainUIPanel.add(sidePanelView, BorderLayout.WEST);
        overallPanel.add(mainUIPanel);
        return this;
    }

    public AppBuilder addMilestoneTasksPanel() {
        milestoneTaskView = new MilestoneTasksView();
        mainUIPanel.add(milestoneTaskView, BorderLayout.CENTER);
        overallPanel.add(mainUIPanel);
        return this;
    }

    public AppBuilder addLoginView() {
        this.loginViewModel = new LoginViewModel("login");
        this.loginView = new LoginView(loginViewModel);
        overallPanel.add(loginView, "login");
        this.registerViewModel = new RegisterViewModel("register");
        this.registerView = new RegisterView(registerViewModel);
        overallPanel.add(loginView, "register");
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(this.loginViewModel, this.registerViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(dao, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        this.loginView.setLoginController(loginController);
        this.registerView.setRegisterController(new RegisterController(loginInteractor));
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Code Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(overallPanel);

        return application;
    }

}
