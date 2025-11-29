package com.studyarc.app;

import javax.swing.*;
import java.awt.*;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.data_access.MilestoneTasksDataAccessObject;
import com.studyarc.entity.ReflectionFactory;
import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.interface_adapter.delete_plan.DeletePlanController;
import com.studyarc.interface_adapter.delete_plan.DeletePlanPresenter;
import com.studyarc.interface_adapter.job_postings.JobPostingsController;
import com.studyarc.interface_adapter.job_postings.JobPostingsPresenter;
import com.studyarc.interface_adapter.job_postings.JobPostingsViewModel;
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
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInteractor;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputBoundary;
import com.studyarc.use_case.track_plan.*;
import com.studyarc.use_case.ui_sidebar.*;
import com.studyarc.view.MilestoneTasksView;
import com.studyarc.view.*;

public class AppBuilder {
    private final DatabaseAccess databaseAccess = new DatabaseAccess();
    private final SidebarDataAccessInterface sidebarDataAccess = new SidebarDataAccessObject();
    private final MilestoneTasksDataAccessInterface milestoneDataAccessObject = new MilestoneTasksDataAccessObject();

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

    private MilestoneTasksViewModel milestoneTasksViewModel;
    private MilestoneTasksView milestoneTaskView;
    private MilestoneTasksViewModel milestoneViewModel;
    final ViewManagerModel viewManagerModel = new ViewManagerModel();

    final MilestoneTasksDataAccessObject singleUseCaseDAO = new MilestoneTasksDataAccessObject();
    final ReflectionFactory reflectionFactory = new ReflectionFactory();

    private TrackPlansView trackPlansView;
    private TrackPlanViewModel trackPlanViewModel;
    private AddReflectionViewModel addReflectionViewModel;

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
        cardPanel.add(trackPlansView, trackPlansView.getViewname());

        return this;

    }

    public AppBuilder addTrackPlanUsecase() {
        TrackPlanOutputBoundary presenter = new TrackPlanPresenter(trackPlanViewModel, viewManagerModel);
        TrackPlanDataAccessinterface dataaccess = new DatabaseAccess();//Siwtch to DataAccess later

        TrackPlanInputBoundary interactor = new TrackPlanInteractor(presenter, dataaccess);
        TrackPlanController trackPlanController = new TrackPlanController(interactor);
        sidePanelView.setTrackPlanController(trackPlanController);
        return this;
    }

    public AppBuilder addDeletePlanUsecase() {
        DeletePlanOutputBoundary presenter = new DeletePlanPresenter(this.trackPlanViewModel, this.viewManagerModel);
        DeletePlanInputBoundary interactor = new DeletePlanInteractor(presenter, new DatabaseAccess());
        trackPlansView.setDeletePlanController(new DeletePlanController(interactor));
        return this;
    }

    public AppBuilder addJobPostingsView() {
        jobPostingsViewModel = new JobPostingsViewModel();
        jobPostingsView = new JobPostingsView(jobPostingsViewModel);

        cardPanel.add(jobPostingsView, jobPostingsView.getViewName());

        return this;
    }

    public AppBuilder addMilestoneTasksPanel() {
        milestoneTasksViewModel = new MilestoneTasksViewModel();
        milestoneTaskView = new MilestoneTasksView(milestoneTasksViewModel);

        cardPanel.add(milestoneTaskView, milestoneTaskView.getViewName());
        overallPanel.add(cardPanel, BorderLayout.CENTER);

        return this;
    }

    public AppBuilder addSidebarUseCase() {
        final SidebarOutputBoundary sidebarOutputBoundary = new SidebarPresenter(viewManagerModel,
                sidebarViewModel,
                jobPostingsViewModel,
                milestoneTasksViewModel,
                trackPlanViewModel);
        final SidebarInputBoundary sidebarInteractor = new SidebarInteractor(sidebarDataAccess, sidebarOutputBoundary);

        SidebarController sidebarController = new SidebarController(sidebarInteractor);
        sidePanelView.setSidebarController(sidebarController);
        return this;
    }

    public AppBuilder addJobPostingsUseCase() {
        final JobPostingsOutputBoundary jobPostingsOutputBoundary = new JobPostingsPresenter(jobPostingsViewModel);

        KeywordGenerator keywordGenerator = new LLMKeywordGenerator();
        AdzunaJobGenerator jobGenerator = new AdzunaJobGenerator();

        final JobPostingsInputBoundary jobPostingsInteractor = new JobPostingsInteractor(databaseAccess, jobPostingsOutputBoundary, keywordGenerator, jobGenerator);

        JobPostingsController jobPostingsController = new JobPostingsController(jobPostingsInteractor);
        jobPostingsView.setJobPostingsController(jobPostingsController);
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
                milestoneViewModel);
        final MilestoneTasksInputBoundary milestoneSaveInteractor = new MilestoneTasksInteractor(singleUseCaseDAO,
                milestonesOutputBoundary);

        MilestoneTasksController controller = new MilestoneTasksController(milestoneSaveInteractor);
        milestoneTaskView.setMilestoneTasksController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Study Arc");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(overallPanel);
        application.setMinimumSize(new Dimension(1000, 800));

        viewManagerModel.setState(milestoneTaskView.getViewName());
        System.out.println(milestoneTaskView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
