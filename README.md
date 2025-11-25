# StudyArc

### Instructions 
This project requires a `.env` file for configuration.
Create a copy of `env.example` and name it `.env`:
<br>
You will need to generate your own api keys from [Cohere AI](https://dashboard.cohere.com/welcome/login?redirect_uri=%2Fapi-keys), [Adzuna API](https://developer.adzuna.com/).

<br>
<br>

**StudyArc** is an educational support platform designed to help students explore industry connections and deepen their 
understanding of computer science focus areas. The application bridges the gap between academic learning and real-world 
applications through personalized study plans, research discovery, job exploration, and reflective learning tools.

StudyArc allows users to:
- Create personalized study plans based on their chosen CS focus and research interests
- Retrieve relevant research papers using the CORE API
- Discover real-world job listings tied to their topics of interest via the Adzuna API
- Track progress across multiple study plans with milestones and subtasks
- Add reflections linked to specific milestones
- Securely log in and access private, user-specific data

# Team Contract

## User Stories & Responsibilities

| User Story # | Description | Team Member |
|--------------|-------------|-------------|
| 1 | As a user, I want my credentials to be secure so my privacy is respected. | Tom Philip |
| 2 | As a user, I want to add reflection logs to specific milestones. | An Chen |
| 3 | As a user, I want to see real-world applications (job listings) for my topics. | Sofia Borodaenko |
| 4 | As a user, I want to create a personalized study plan based on my focus and interests. | Julia Sinclair |
| 5 | As a user, I want to track and edit my study plans for flexible studying. | Yizhou Qian |
| 6 | As a user, I want to view recommended or past research papers. | Henry Morton |


## APIs Used

1. Cohere Chat API
- Purpose: Generate keywords for user's job name selection
- Usage: Helps generate the call to Adzuna's Job Search API.


2. Adzuna Job Search API
- Purpose: Retrieves live job listings based on expanded keywords from the user’s topic.
- Usage: Shows users how their academic interests connect to industry careers.

3. CORE Services API
- Purpose: Fetches publicly available academic research papers.
- Usage: Supplies research papers used to build study plans and milestones.

