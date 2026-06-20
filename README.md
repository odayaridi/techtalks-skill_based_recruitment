# Skill-Based Recruitment and Internship Platform

## About the Project

The Skill-Based Recruitment and Internship Platform is a web-based recruitment system that connects companies with candidates based on **verified technical skills** rather than resumes alone. Candidates showcase their abilities through real projects, GitHub repositories, and certifications, while recruiters define required skills with weighted importance for each job. The platform then calculates an evidence-based compatibility score to rank candidates objectively.

## Problem It Solves

Traditional recruitment platforms rely heavily on resumes, education, and years of experience — which puts students, fresh graduates, and junior developers at a disadvantage, even when they have strong practical skills. Recruiters also spend significant time manually reviewing applications, with no reliable way to verify whether a candidate's claimed skills are real.

This platform solves that by:
- Replacing self-reported skills with **verifiable evidence** (projects, GitHub repos, certifications)
- Automatically **calculating a match score** between candidates and job requirements
- **Ranking applicants** based on demonstrated ability instead of resume keywords
- Reducing manual screening time for recruiters

## Key Features

- **Candidate Profiles** — skills, resume, GitHub/LinkedIn links
- **Project Portfolio** — projects linked to specific skills as proof of ability
- **Certification Management** — verified certificates as additional skill evidence
- **Company & Job Management** — job postings with weighted required skills
- **Application Tracking** — full lifecycle from "Applied" to "Offer"
- **Skill Matching Engine** — AI-assisted analysis of evidence to generate a candidate–job match percentage
- **Candidate Ranking** — recruiters see top-matching applicants first, with missing skills highlighted

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React |
| Backend | Node.js / Express (REST API) |
| Database | PostgreSQL |
| Caching | Redis |
| Authentication | JWT |
| File Storage | AWS S3 (resumes, certificates, project files) |
| AI / ML | AI-based analysis for project & certificate evidence scoring |
| External Integration | GitHub API (project/repository verification) |

> Tech stack is the planned implementation and may evolve as development progresses.
