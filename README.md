# Multi-User-Academic-Timetable-System

## Features

### 1. Authentication System
- Role-based login (Admin / Teacher / Student)
- Sign-up functionality for new users
- Data persistence using local file-based databases

### 2. Admin Portal
- Manage and view classrooms, teachers, and subjects
- Initialize classroom infrastructure (e.g., labs, conference rooms)
- Assign teachers to subjects
- Allocate classrooms and schedule timeslots

### 3. Teacher Portal
- View assigned classes in a timetable format
- Weekly schedule with subjects grouped by day and time
- Automatically populated based on subjects taught

### 4. Student Portal
- View and register for available courses
- Track enrolled subjects and total credits (max 25)
- Detect and prevent time conflicts
- View personalized weekly timetable

### 5. Timetable Scheduler
- Generate all possible non-conflicting schedules based on selected courses
- Navigate through different schedule suggestions
- Save preferred timetable

---

## Project Structure

\`\`\`bash
TimetableManagementSystem/
├── AdminPortal.java
├── Classroom.java
├── DatabaseManager.java
├── DataManager.java
├── LoginGUI.java
├── Student.java
├── StudentPortal.java
├── Subject.java
├── Teacher.java
├── TeacherPortal.java
├── TimetableScheduler.java
├── User.java
└── resources/
    ├── admin_users.txt
    ├── teacher_users.txt
    └── student_users.txt
\`\`\`

---

## Prerequisites

- Java JDK 8 or above
- IDE like IntelliJ IDEA, Eclipse, or NetBeans

---

## How to Run

```bash
# Clone the repository
git clone https://github.com/yourusername/TimetableManagementSystem.git
cd TimetableManagementSystem

# Open the project in your IDE, compile and run
