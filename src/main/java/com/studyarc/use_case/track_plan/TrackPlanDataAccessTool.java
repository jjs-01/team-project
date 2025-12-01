package com.studyarc.use_case.track_plan;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface;

import java.util.ArrayList;
import java.util.Random;

public class TrackPlanDataAccessTool implements TrackPlanDataAccessinterface, ViewingResearchPapersDataAccessInterface {

    @Override
    public ArrayList<StudyPlan> getPlans(String username) {
        return generateTestPlans();
    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        return generateTestPlans();
    }

    @Override
    public ArrayList<StudyPlan> generateTestPlans() {
        ArrayList<StudyPlan> plans = new ArrayList<>();
        Random random = new Random();
        String date = "MM/DD/YYYY";
        String[] taskStatus = {"Not Started", "In Progress", "Completed"};

        // Plan 1 - Deep Learning & Computer Vision
        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>(), "Deep Learning");
        addResearchPapers(plan1, new String[][]{
                {"1", "Deep Learning for Computer Vision", "Smith, J., Johnson, A.", "http://example.com/paper1"},
                {"2", "Neural Networks Introduction", "Williams, B.", "http://example.com/paper2"},
                {"3", "Advanced CNN Architectures", "Brown, C.", "http://example.com/paper3"}
        });
        addMilestones(plan1, date, taskStatus, random);

        // Plan 2 - Natural Language Processing
        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>(), "Natural Language Processing");
        addResearchPapers(plan2, new String[][]{
                {"4", "Natural Language Processing with Transformers", "Davis, M., Garcia, R.", "http://example.com/paper4"},
                {"5", "Attention Mechanisms", "Martinez, L.", "http://example.com/paper5"},
                {"6", "BERT and GPT Models", "Anderson, K.", "http://example.com/paper6"}
        });
        addMilestones(plan2, date, taskStatus, random);

        // Plan 3 - Reinforcement Learning
        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>(), "Reinforcement Learning");
        addResearchPapers(plan3, new String[][]{
                {"7", "Introduction to Reinforcement Learning", "Taylor, P.", "http://example.com/paper7"},
                {"8", "Q-Learning and Deep Q-Networks", "Wilson, S.", "http://example.com/paper8"},
                {"9", "Policy Gradient Methods", "Moore, T.", "http://example.com/paper9"}
        });
        addMilestones(plan3, date, taskStatus, random);

        // Plan 4 - Computer Vision
        StudyPlan plan4 = new StudyPlan("Plan 4", new ArrayList<>(), "Computer Vision");
        addResearchPapers(plan4, new String[][]{
                {"10", "Object Detection with YOLO", "Harris, M.", "http://example.com/paper10"},
                {"11", "Image Segmentation Techniques", "Clark, L.", "http://example.com/paper11"},
                {"12", "Vision Transformers", "Lewis, R.", "http://example.com/paper12"}
        });
        addMilestones(plan4, date, taskStatus, random);

        // Plan 5 - Graph Neural Networks
        StudyPlan plan5 = new StudyPlan("Plan 5", new ArrayList<>(), "Graph Neural Networks");
        addResearchPapers(plan5, new String[][]{
                {"13", "Graph Neural Networks Overview", "Walker, J.", "http://example.com/paper13"},
                {"14", "Message Passing Networks", "Hall, A.", "http://example.com/paper14"},
                {"15", "Graph Attention Networks", "Young, C.", "http://example.com/paper15"}
        });
        addMilestones(plan5, date, taskStatus, random);

        // Plan 6 - Generative Models
        StudyPlan plan6 = new StudyPlan("Plan 6", new ArrayList<>(), "Generative Models");
        addResearchPapers(plan6, new String[][]{
                {"16", "Generative Adversarial Networks", "King, D.", "http://example.com/paper16"},
                {"17", "Variational Autoencoders", "Wright, N.", "http://example.com/paper17"},
                {"18", "Diffusion Models", "Lopez, E.", "http://example.com/paper18"}
        });
        addMilestones(plan6, date, taskStatus, random);

        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        plans.add(plan4);
        plans.add(plan5);
        plans.add(plan6);

        return plans;
    }

    private void addResearchPapers(StudyPlan plan, String[][] paperData) {
        for (String[] data : paperData) {
            plan.addResearchPaper(new ResearchPaper(
                    data[0],  // id
                    data[1],  // title
                    data[2],  // authors
                    "Abstract text here...",  // abstract
                    data[3]   // url
            ));
        }
    }

    private void addMilestones(StudyPlan plan, String date, String[] taskStatus, Random random) {
        Milestone m1 = new Milestone("Milestone 1");
        m1.getSubtasks().add(new Task("Do step1", date, taskStatus[random.nextInt(3)]));
        m1.getSubtasks().add(new Task("Do step2", date, taskStatus[random.nextInt(3)]));

        Milestone m2 = new Milestone("Milestone 2");
        m2.getSubtasks().add(new Task("Do step1", date, taskStatus[random.nextInt(3)]));
        m2.getSubtasks().add(new Task("Do step2", date, taskStatus[random.nextInt(3)]));

        plan.getMilestones().add(m1);
        plan.getMilestones().add(m2);
    }
}