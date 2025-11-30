package com.studyarc.app;

import javax.swing.*;
import java.awt.*;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.entity.ReflectionFactory;
import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.add_plan.AddPlanController;
import com.studyarc.interface_adapter.add_plan.AddPlanPresenter;
import com.studyarc.interface_adapter.delete_plan.DeletePlanController;
import com.studyarc.interface_adapter.delete_plan.DeletePlanPresenter;
import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.job_postings.JobPostingsPresenter;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesController;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesPresenter;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesViewModel;
import com.studyarc.interface_adapter.login.*;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksController;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksPresenter;
import com.studyarc.interface_adapter.milestone_tasks.MilestoneTasksViewModel;
import com.studyarc.interface_adapter.add_reflection.AddReflectionController;
import com.studyarc.interface_adapter.add_reflection.AddReflectionPresenter;
import com.studyarc.interface_adapter.add_reflection.AddReflectionViewModel;
import com.studyarc.interface_adapter.track_plan.TrackPlanController;
import com.studyarc.interface_adapter.track_plan.TrackPlanPresenter;
import com.studyarc.interface_adapter.track_plan.TrackPlanViewModel;
import com.studyarc.interface_adapter.ui_sidebar.SidebarController;
import com.studyarc.interface_adapter.ui_sidebar.SidebarPresenter;
import com.studyarc.interface_adapter.ui_sidebar.SidebarViewModel;
import com.studyarc.use_case.add_plan.AddPlanInputBoundary;
import com.studyarc.use_case.add_plan.AddPlanInteractor;
import com.studyarc.use_case.add_plan.AddPlanOutputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionInputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionInteractor;
import com.studyarc.use_case.add_reflection.AddReflectionOutputBoundary;
import com.studyarc.use_case.delete_plan.DeletePlanInputBoundary;
import com.studyarc.use_case.delete_plan.DeletePlanInteractor;
import com.studyarc.use_case.delete_plan.DeletePlanOutputBoundary;
import com.studyarc.use_case.job_postings.JobPostingsInputBoundary;
import com.studyarc.use_case.job_postings.JobPostingsInteractor;
import com.studyarc.use_case.job_postings.JobPostingsOutputBoundary;
import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_keywords.LLMKeywordGenerator;
import com.studyarc.use_case.job_postings.generate_postings.AdzunaJobGenerator;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.load_milestones.LoadMilestonesInputBoundary;
import com.studyarc.use_case.load_milestones.LoadMilestonesInteractor;
import com.studyarc.use_case.load_milestones.LoadMilestonesOutputBoundary;
import com.studyarc.use_case.login.LoginInputBoundary;
import com.studyarc.use_case.login.LoginInteractor;
import com.studyarc.use_case.login.LoginOutputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInteractor;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputBoundary;
import com.studyarc.use_case.track_plan.*;
import com.studyarc.use_case.ui_sidebar.*;
import com.studyarc.view.MilestoneTasksView;
import com.studyarc.view.*;

public class AppBuilder {
    private final DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
    private final SidebarDataAccessInterface sidebarDataAccess = new SidebarDataAccessObject();

    private final JPanel overallPanel = new JPanel(new BorderLayout());
    private final JPanel cardPanel = new JPanel(new CardLayout());
    private CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

    private final BorderLayout borderLayout = new BorderLayout();
    private final JPanel mainUIPanel = new JPanel();
    private final JPanel usecasePanel = new JPanel();

    private SidebarViewModel sidebarViewModel;
    private SidePanelView sidePanelView;
    private JobPostingsViewModel jobPostingsViewModel;
    private JobPostingsView jobPostingsView;
    private RegisterView registerView;

    private final MilestoneTasksViewModel milestoneTasksViewModel = new MilestoneTasksViewModel();
    private MilestoneTasksView milestoneTaskView;
    final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private LoadMilestonesViewModel loadMilestonesViewModel;
    private LoadMilestonesView loadMilestonesView;

    final ReflectionFactory reflectionFactory = new ReflectionFactory();

    private TrackPlansView trackPlansView;
    private TrackPlanViewModel trackPlanViewModel;
    private AddReflectionViewModel addReflectionViewModel;

    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);


    public AppBuilder() {
    }

    public AppBuilder addSidePanel() {
        sidebarViewModel = new SidebarViewModel();
        sidePanelView = new SidePanelView(sidebarViewModel);

        overallPanel.add(sidePanelView, BorderLayout.WEST);
        return this;
    }

    public AppBuilder addTrackPlanView() {
        this.trackPlanViewModel = new TrackPlanViewModel();
        this.addReflectionViewModel = new AddReflectionViewModel();
        this.trackPlansView = TrackPlansView.getInstance(trackPlanViewModel, addReflectionViewModel);
        cardPanel.add(trackPlansView, trackPlansView.getViewName());

        return this;

    }

    public AppBuilder addTrackPlanUsecase() {
        TrackPlanOutputBoundary presenter = new TrackPlanPresenter(trackPlanViewModel, viewManagerModel);
        TrackPlanDataAccessinterface dataaccess = this.databaseAccess;

        // Add LoadMileStone Controller to TrackPlanView
        LoadMilestonesOutputBoundary loadpresenter = new LoadMilestonesPresenter(viewManagerModel,
                loadMilestonesViewModel);
        LoadMilestonesInputBoundary loadmilesIneractor = new LoadMilestonesInteractor(this.databaseAccess, loadpresenter);
        LoadMilestonesController loadMilestonesController = new LoadMilestonesController(loadmilesIneractor);
        this.trackPlansView.setLoadMilestonesController(loadMilestonesController);

        // Add SideBar Controller to TrackPlanView
        final SidebarOutputBoundary sidebarOutputBoundary = new SidebarPresenter(viewManagerModel,
                sidebarViewModel,
                jobPostingsViewModel,
                milestoneTasksViewModel,
                trackPlanViewModel,
                loginViewModel);
        final SidebarInputBoundary sidebarInteractor = new SidebarInteractor(sidebarDataAccess, sidebarOutputBoundary);
        final SidebarController sidebarController = new SidebarController(sidebarInteractor);
        this.trackPlansView.setSidebarController(sidebarController);

        // Add TrackPlan Controller to TrackPlanView
        TrackPlanInputBoundary interactor = new TrackPlanInteractor(presenter, dataaccess);
        TrackPlanController trackPlanController = new TrackPlanController(interactor);
        this.trackPlansView.setTrackPlanController(trackPlanController);
        sidePanelView.setTrackPlanController(trackPlanController);
        return this;
    }

    public AppBuilder addDeletePlanUsecase() {
        DeletePlanOutputBoundary presenter = new DeletePlanPresenter(this.trackPlanViewModel);
        DeletePlanInputBoundary interactor = new DeletePlanInteractor(presenter, this.databaseAccess);
        trackPlansView.setDeletePlanController(new DeletePlanController(interactor));
        return this;
    }

    public AppBuilder addJobPostingsView() {
        jobPostingsViewModel = new JobPostingsViewModel();
        jobPostingsView = new JobPostingsView(jobPostingsViewModel);

        cardPanel.add(jobPostingsView, jobPostingsView.getViewName());

        return this;
    }
    public AppBuilder addLoginView(){
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        registerViewModel = new RegisterViewModel();
        registerView = new RegisterView(registerViewModel);
        cardPanel.add(registerView, registerView.getViewName());
        return this;
    }
    public AppBuilder addMilestoneTasksPanel() {
        milestoneTaskView = new MilestoneTasksView(milestoneTasksViewModel);

        cardPanel.add(milestoneTaskView, milestoneTaskView.getViewName());


        return this;
    }

    public AppBuilder addLoadMilestonesPanel() {
        loadMilestonesViewModel = new LoadMilestonesViewModel();
        loadMilestonesView = new LoadMilestonesView(milestoneTasksViewModel, loadMilestonesViewModel);

        cardPanel.add(loadMilestonesView, loadMilestonesView.getViewName());
        overallPanel.add(cardPanel, BorderLayout.CENTER);

        return this;
    }

    public AppBuilder addSidebarUseCase() {
        final SidebarOutputBoundary sidebarOutputBoundary = new SidebarPresenter(viewManagerModel,
                sidebarViewModel,
                jobPostingsViewModel,
                milestoneTasksViewModel,
                trackPlanViewModel,
                loginViewModel);
        final SidebarInputBoundary sidebarInteractor = new SidebarInteractor(sidebarDataAccess, sidebarOutputBoundary);

        SidebarController sidebarController = new SidebarController(sidebarInteractor);
        sidePanelView.setSidebarController(sidebarController);
        registerView.setSideBarController(sidebarController);
        return this;
    }

    public AppBuilder addJobPostingsUseCase() {
        final JobPostingsOutputBoundary jobPostingsOutputBoundary = new JobPostingsPresenter(jobPostingsViewModel);

        KeywordGenerator keywordGenerator = new LLMKeywordGenerator();
        AdzunaJobGenerator jobGenerator = new AdzunaJobGenerator();

        final JobPostingsInputBoundary jobPostingsInteractor = new JobPostingsInteractor(databaseAccess, jobPostingsOutputBoundary, keywordGenerator, jobGenerator);

        JobPostingsController jobPostingsController = new JobPostingsController(jobPostingsInteractor);
        jobPostingsView.setJobPostingsController(jobPostingsController);
        sidePanelView.setJobPostingsController(jobPostingsController);
        return this;
    }

    public AppBuilder addAddReflectionUseCase() {
        AddReflectionOutputBoundary presenter = new AddReflectionPresenter(addReflectionViewModel,trackPlanViewModel);
        AddReflectionInputBoundary interactor = new AddReflectionInteractor(presenter, databaseAccess, reflectionFactory);
        AddReflectionController controller = new AddReflectionController(interactor);
        trackPlansView.setAddReflectionController(controller);
        return this;
    }

    public AppBuilder addMilestoneTasksUseCase() {
        final MilestoneTasksOutputBoundary milestonesOutputBoundary = new MilestoneTasksPresenter(viewManagerModel,
                milestoneTasksViewModel);
        final MilestoneTasksInputBoundary milestoneSaveInteractor = new MilestoneTasksInteractor(this.databaseAccess,
                milestonesOutputBoundary);

        MilestoneTasksController controller = new MilestoneTasksController(milestoneSaveInteractor);
        milestoneTaskView.setMilestoneTasksController(controller);
        return this;
    }

    public AppBuilder addLoadMilestonesUseCase() {
        final MilestoneTasksOutputBoundary milestonesOutputBoundary = new MilestoneTasksPresenter(viewManagerModel,
                milestoneTasksViewModel);
        final MilestoneTasksInputBoundary milestoneSaveInteractor = new MilestoneTasksInteractor(this.databaseAccess,
                milestonesOutputBoundary);
        MilestoneTasksController saveController = new MilestoneTasksController(milestoneSaveInteractor);

        final LoadMilestonesOutputBoundary loadMilestonesOutputBoundary = new LoadMilestonesPresenter(viewManagerModel,
                loadMilestonesViewModel) {
        };
        final LoadMilestonesInputBoundary loadMilestonesInteractor = new LoadMilestonesInteractor(this.databaseAccess,
                loadMilestonesOutputBoundary);

        LoadMilestonesController loadController = new LoadMilestonesController(loadMilestonesInteractor);
        loadMilestonesView.setLoadMilestonesController(loadController);
        loadMilestonesView.setMilestoneTasksController(saveController);

        loadMilestonesView.loadView();
        return this;
}

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loginViewModel, registerViewModel, viewManagerModel, trackPlanViewModel, milestoneTasksViewModel, sidebarViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(databaseAccess, loginOutputBoundary);

        loginView.setLoginController(new LoginController(loginInteractor));
        registerView.setRegisterController(new RegisterController(loginInteractor));
//        registerView.setSideBarController(new SidebarController());

        return this;
    }

    public AppBuilder addAddPlanUseCase() {
        final AddPlanOutputBoundary addPlanPresenter = new AddPlanPresenter(viewManagerModel, trackPlanViewModel);
        final AddPlanInputBoundary addPlanInteractor = new AddPlanInteractor(databaseAccess, addPlanPresenter);

        trackPlansView.setAddPlanController(new AddPlanController(addPlanInteractor));

        return this;
    }

    public JFrame build() {
        overallPanel.add(cardPanel, BorderLayout.CENTER);
        final JFrame application = new JFrame("Study Arc");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(overallPanel);
        application.setMinimumSize(new Dimension(1000, 800));

//        viewManagerModel.setState(loadMilestonesView.getViewName());
//        System.out.println(loadMilestonesView.getViewName());
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
