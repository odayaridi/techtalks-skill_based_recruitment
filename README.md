<img width="1254" height="1254" alt="Project Logo" src="https://github.com/user-attachments/assets/dde26535-f14d-4871-a100-6f59e385838b" />


## About the Project

The **Skill-Based Recruitment and Internship Platform** is a backend application designed to modernize recruitment by evaluating candidates based on their **demonstrated technical skills** rather than relying solely on resumes or years of experience.

Candidates create professional profiles showcasing their technical skills, projects, GitHub repositories, certifications, and resumes. Recruiters create job postings with weighted required skills, and the platform uses an **AI-assisted evidence analysis engine** to evaluate candidate evidence and automatically calculate an objective compatibility score between candidates and job requirements.

The platform provides secure authentication, role-based authorization, automated candidate ranking, application management, and recruiter tools to streamline the hiring process.

---

# Problem Statement

Traditional recruitment platforms primarily evaluate candidates based on resumes, academic qualifications, and previous work experience. This approach often disadvantages students, fresh graduates, and junior developers who possess strong practical skills but have limited professional experience.

Recruiters also spend considerable time manually reviewing applications without an effective way to verify whether a candidate's claimed skills accurately represent their real technical abilities.

This platform addresses these challenges by:

- Evaluating candidates using real evidence such as projects, GitHub repositories, and certifications.
- Using AI-assisted skill extraction and scoring to analyze candidate evidence.
- Automatically calculating compatibility scores between candidates and job requirements.
- Ranking applicants based on demonstrated technical skills.
- Reducing recruiter effort through automated candidate analysis and ranking.
- Providing a secure and scalable recruitment workflow with authentication, authorization, validation, pagination, and rate limiting.

---

# Key Features

## Authentication & Security

- JWT Authentication (Access Token & Refresh Token)
- Role-Based Authorization (Candidate / Recruiter)
- Spring Security integration
- Password hashing using BCrypt
- Refresh Token support
- Password Reset via Gmail SMTP
- JWT-based Password Reset Tokens
- Global Exception Handling
- Sliding Window Rate Limiting
- Secure API access

---

## Candidate Features

- Candidate Profile Management
- Resume Management
- Technical Skills Management
- Project Portfolio Management
- Certification Management
- GitHub Profile Integration
- LinkedIn Profile Integration

---

## Recruiter & Company Features

- Company Management
- Recruiter Management
- Company-Recruiter Management
- Recruiter Dashboard
- Candidate Discovery

---

## Job Management

- Create Jobs
- Update Jobs
- Delete Jobs
- Manage Required Job Skills
- Weighted Skill Requirements
- Recruiter Job Listings

---

## Application Management

- Apply for Jobs
- Application Tracking
- Application Status Management
- Recruiter Application Review

---

## AI Skill Matching Engine

- AI-assisted Project Analysis
- AI-assisted Certification Analysis
- Skill Evidence Generation
- Candidate Skill Score Calculation
- Weighted Match Score Calculation
- Automatic Candidate Ranking
- Missing Skill Identification

---

## Platform Features

- Feature-Based Modular Architecture
- RESTful APIs
- DTO Pattern
- Mapper Pattern
- Repository Pattern
- Bean Validation
- Pagination Support
- Database Indexing
- Global Exception Handling
- Role-Based Endpoint Protection
- Postman API Testing

---

# Main Modules

- User
- Role
- Candidate Profile
- Candidate Resume
- Candidate Skill
- Candidate Skill Score
- Project
- Project Skill
- Certification
- Company
- Company Recruiter
- Recruiter Profile
- Job
- Job Skill
- Application
- Skill
- Skill Evidence
- Match Score
- Security (JWT & Authorization)
- Rate Limiting
- Common (Pagination, Exceptions, Responses)

---

# Software Architecture

The backend follows a **Feature-Based (Modular) Architecture**, where every business domain is implemented as an independent feature module. Each module contains its own controllers, services, repositories, DTOs, mappers, and related classes, making the system easier to maintain, scale, and develop collaboratively.

### Architecture Components

- Feature-Based (Modular) Architecture
- Spring Boot REST API
- Spring Data JPA & Hibernate
- DTO Pattern
- Mapper Pattern
- Repository Pattern
- Dependency Injection (Spring IoC)
- JWT Authentication
- Refresh Token Authentication
- Password Reset via Gmail SMTP
- Role-Based Authorization
- Sliding Window Rate Limiting
- Bean Validation
- Global Exception Handling
- Pagination Support

---

# AI Matching Workflow

1. Recruiters create job postings and assign weighted required skills.
2. Candidates upload projects, certifications, resumes, and technical profiles.
3. The AI analyzes candidate projects and certifications to extract relevant skills and assign confidence scores.
4. Candidate skill scores are calculated based on the analyzed evidence.
5. The system compares candidate skills against job requirements.
6. A weighted match percentage is calculated automatically.
7. Recruiters receive a ranked list of applicants ordered by compatibility score, along with insights into matched and missing skills.

---

# Tech Stack

| Layer | Technology |
|---------|------------|
| Frontend | React |
| Backend | Spring Boot (REST API) |
| Database | MariaDB |
| ORM | Spring Data JPA & Hibernate |
| Authentication | Spring Security + JWT |
| Authorization | Role-Based Access Control |
| Validation | Jakarta Bean Validation |
| Email Service | Gmail SMTP (JavaMailSender) |
| AI Integration | AI-assisted Project & Certification Analysis |
| API Testing | Postman |
| Version Control | Git & GitHub |
| Project Management | Jira |

---

# Project Structure

```
src
└── main
    └── java
        └── org.example.techtalksskillbasedrecruitment
            ├── common
            ├── modules
            │   ├── application
            │   ├── candidateprofile
            │   ├── candidateresume
            │   ├── candidateskill
            │   ├── candidateskillscore
            │   ├── certification
            │   ├── company
            │   ├── companyrecruiter
            │   ├── job
            │   ├── jobskill
            │   ├── project
            │   ├── projectskill
            │   ├── recruiterprofile
            │   ├── role
            │   ├── skill
            │   ├── skillevidence
            │   ├── matchscore
            │   └── user
            ├── ratelimit
            └── security
```

---

# Future Enhancements

- GitHub Repository Synchronization
- Email Notifications
- Interview Scheduling
- Recruiter Analytics Dashboard
- Candidate Skill Recommendations
- Advanced Search & Filtering
- File Storage Integration (AWS S3 / Cloudinary)
- Docker Deployment
- CI/CD Pipeline
- Audit Logging

---

# Sprint Reports

## Sprint 1

https://app.notion.com/p/Sprint-1-Report-385961e002c780c98942ff213d21bd1f

---

## Sprint 2

https://app.notion.com/p/Sprint-2-Report-394961e002c7807ba5b4e0e5222344d1?source=copy_link

---

## Sprint 3

https://app.notion.com/p/Sprint-3-Report-39a961e002c780b3be4cd7681f1c3025?source=copy_link

---

## Sprint 4

https://app.notion.com/p/Sprint-4-Report-3a1961e002c7804f86b0f40a68f47897?source=copy_link

---

## Sprint 5

https://app.notion.com/p/Sprint-5-Report-3a8961e002c7806fb381c63b47b6a979?source=copy_link

---

## Sprint 6

https://app.notion.com/p/Sprint-6-Report-3ac961e002c7807880ffccb55a2c58ee?source=copy_link

---

## Internship Learnings

https://app.notion.com/p/TECHTALKS-INTERNSHIP-3ac3cdb345f1800db282cdab75f555e0

---

# Jira Task Screenshots

> Paste your Jira screenshots below.

<img width="1917" height="877" alt="image" src="https://github.com/user-attachments/assets/1ab4b2fa-a0ff-4b53-b6fe-fd2b38597d43" />
<img width="1911" height="957" alt="image" src="https://github.com/user-attachments/assets/a8063d79-2625-4ef8-93b8-817192e67734" />
<img width="1917" height="607" alt="image" src="https://github.com/user-attachments/assets/108eeb80-c496-4c8c-8dd0-f5aec81971ff" />
