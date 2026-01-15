# java-automation-project

# Student Automation System

This project is a **Student Automation System** developed using **Spring Boot**.
It allows academicians to manage students, enter grades, track attendance,
and analyze student success status.

---

## Project Purpose

The main objectives of this project are:
- Managing student data digitally
- Tracking grades and attendance
- Automatically calculating pass/fail status
- Visualizing overall student success with charts

---

## Features

- User authentication system
- Password reset functionality
- Add / delete / update student information
- Midterm, final, and attendance input
- GPA and letter grade calculation
- Automatic failure based on attendance
- Student success visualization (pie chart)

---

## Technologies Used

- Java
- Spring Boot (MVC)
- Thymeleaf
- SQLite
- HTML
- CSS
- JavaScript
- Chart.js

---

## How to Run the Project

1. Open the project using IntelliJ IDEA or Eclipse
2. Wait for Maven dependencies to load
3. Run the application
4. Open the browser and navigate to:

http://localhost:8080/

---

## Login Credentials

- Username: `akademisyen`
- Password: `1234`

Password can be changed via the **Password Reset** screen.

---

## Database Information

- Database: SQLite
- File name: `ogrenci.db`
- Tables are created automatically on first run
- Missing columns are added automatically without data loss

---

## Grading System

- Midterm: 40%
- Final: 60%
- Average ≥ 50 → Passed
- Attendance ≥ 5 → Failed (Automatically)

---

## Chart System

A pie chart on the main dashboard displays:
- Number of passed students
- Number of failed students
- Number of students failed due to attendance
