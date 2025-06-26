import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String ADMIN_DB = "admin_users.txt";
    private static final String TEACHER_DB = "teacher_users.txt";
    private static final String STUDENT_DB = "student_users.txt";
    
    private List<User> adminUsers;
    private List<User> teacherUsers;
    private List<User> studentUsers;
    private List<Teacher> teachers;
    private List<Subject> subjects;

    public DatabaseManager() {
        adminUsers = new ArrayList<>();
        teacherUsers = new ArrayList<>();
        studentUsers = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
        
        // Initialize with sample teachers
        Teacher teacher1 = new Teacher("Dr. Sarah Johnson", "Computer Science", "sarah.j@university.edu", "123-456-7890");
        teacher1.addSubject("Java Programming");
        teacher1.addSubject("Data Structures");
        teacher1.addSubject("Algorithms");
        teacher1.addSubject("Database Systems");
        teachers.add(teacher1);

        // Create and add subjects with proper lecturer assignment
        Subject javaProg = new Subject("Java Programming", "CS101", 3);
        javaProg.addLecturer("Dr. Sarah Johnson");
        javaProg.setTiming("Monday 8:00 AM - 9:00 AM");
        javaProg.setClassroom("CS Lab 1");
        subjects.add(javaProg);

        Subject dataStruct = new Subject("Data Structures", "CS102", 3);
        dataStruct.addLecturer("Dr. Sarah Johnson");
        dataStruct.setTiming("Tuesday 9:00 AM - 10:00 AM");
        dataStruct.setClassroom("CS Lab 2");
        subjects.add(dataStruct);

        Subject algorithms = new Subject("Algorithms", "CS103", 3);
        algorithms.addLecturer("Dr. Sarah Johnson");
        algorithms.setTiming("Wednesday 10:00 AM - 11:00 AM");
        algorithms.setClassroom("CS Lab 3");
        subjects.add(algorithms);

        Subject dbSystems = new Subject("Database Systems", "CS104", 3);
        dbSystems.addLecturer("Dr. Sarah Johnson");
        dbSystems.setTiming("Thursday 11:00 AM - 12:00 PM");
        dbSystems.setClassroom("CS Lab 1");
        subjects.add(dbSystems);

        Teacher teacher2 = new Teacher("Prof. Michael Chen", "Mathematics", "m.chen@university.edu", "123-456-7891");
        // Add more sample teachers as needed
        loadUsers();
        // Debug: Print loaded users
        System.out.println("Loaded Admin Users:");
        for (User user : adminUsers) {
            System.out.println("Username: " + user.getUsername());
        }
    }

    private void loadUsers() {
        // First ensure all database files exist
        createDefaultUsers(ADMIN_DB, "Admin");
        createDefaultUsers(TEACHER_DB, "Teacher");
        createDefaultUsers(STUDENT_DB, "Student");
        
        // Then load all users
        loadUsersFromFile(ADMIN_DB, adminUsers, "Admin");
        loadUsersFromFile(TEACHER_DB, teacherUsers, "Teacher");
        loadUsersFromFile(STUDENT_DB, studentUsers, "Student");
    }

    private void loadUsersFromFile(String filename, List<User> userList, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userList.add(new User(parts[0].trim(), parts[1].trim(), role));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users from " + filename + ": " + e.getMessage());
        }
    }

    private void createDefaultUsers(String filename, String role) {
        File file = new File(filename);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                switch (role) {
                    case "Admin":
                        writer.println("admin1,Admin@123");
                        writer.println("admin2,Admin@456");
                        writer.println("superadmin,Super@789");
                        break;
                    case "Teacher":
                        writer.println("teacher1,Teach@123");
                        writer.println("teacher2,Teach@456");
                        writer.println("teacher3,Teach@789");
                        writer.println("professor1,Prof@123");
                        break;
                    case "Student":
                        writer.println("student1,Stud@123");
                        writer.println("student2,Stud@456");
                        writer.println("student3,Stud@789");
                        writer.println("student4,Stud@101");
                        writer.println("student5,Stud@102");
                        break;
                }
                System.out.println("Created default users for " + role + " in " + filename);
            } catch (IOException e) {
                System.out.println("Error creating " + filename + ": " + e.getMessage());
            }
        }
    }

    public String[] authenticateUser(String username, String password, String role) {
        String[] result = new String[2];
        result[0] = "false";
        result[1] = "";

        if (username == null || password == null || role == null) {
            result[1] = "Please provide all credentials";
            return result;
        }

        username = username.trim();
        password = password.trim();
        
        List<User> users = null;
        switch (role) {
            case "Admin":
                users = adminUsers;
                break;
            case "Teacher":
                users = teacherUsers;
                break;
            case "Student":
                users = studentUsers;
                break;
            default:
                result[1] = "Invalid role selected";
                return result;
        }

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        result[0] = "true";
                        return result;
                    } else {
                        result[1] = "Wrong password. The correct password is: " + user.getPassword();
                        return result;
                    }
                }
            }
            result[1] = "Username '" + username + "' not found for role '" + role + "'";
        } else {
            result[1] = "No users found for role '" + role + "'";
        }
        return result;
    }

    public String signupUser(String username, String password, String role) {
        if (username == null || password == null || role == null) {
            return "Please provide all credentials";
        }

        username = username.trim();
        password = password.trim();

        // Check if username already exists in the selected role
        List<User> users = null;
        String filename = null;
        switch (role) {
            case "Admin":
                users = adminUsers;
                filename = ADMIN_DB;
                break;
            case "Teacher":
                users = teacherUsers;
                filename = TEACHER_DB;
                break;
            case "Student":
                users = studentUsers;
                filename = STUDENT_DB;
                break;
            default:
                return "Invalid role selected";
        }

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "Username already exists for role '" + role + "'";
            }
        }

        // Add new user to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(username + "," + password);
            // Add to in-memory list
            users.add(new User(username, password, role));
            return "Signup successful!";
        } catch (IOException e) {
            return "Error during signup: " + e.getMessage();
        }
    }

    public Teacher getTeacher(String username) {
        // First check if we already have a teacher with this username
        for (Teacher teacher : teachers) {
            String teacherUsername = teacher.getName().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (teacherUsername.equals(username.toLowerCase())) {
                return teacher;
            }
        }
        
        // If not found in teachers list, check teacherUsers list
        for (User user : teacherUsers) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                // Find the corresponding teacher by name
                for (Teacher teacher : teachers) {
                    String teacherUsername = teacher.getName().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                    if (teacherUsername.equals(username.toLowerCase())) {
                        return teacher;
                    }
                }
            }
        }
        
        // If still not found, try to find a teacher with a similar name
        for (Teacher teacher : teachers) {
            String teacherName = teacher.getName().toLowerCase();
            String usernameLower = username.toLowerCase();
            
            // Check if the username is part of the teacher's name
            if (teacherName.contains(usernameLower) || usernameLower.contains(teacherName.replaceAll("[^a-zA-Z0-9]", ""))) {
                return teacher;
            }
        }
        
        return null;
    }

    public void createTeacherLoginCredentials() {
        // Clear existing teacher users
        teacherUsers.clear();
        
        // Create a new file for teacher users
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEACHER_DB))) {
            // Add default teachers
            writer.println("teacher1,Teach@123");
            writer.println("teacher2,Teach@456");
            writer.println("teacher3,Teach@789");
            writer.println("professor1,Prof@123");
            
            // Add credentials for all teachers in the AdminPortal
            for (Teacher teacher : teachers) {
                // Create a username from the teacher's name (remove spaces and special characters)
                String username = teacher.getName().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                // Create a password based on the teacher's department
                String password = teacher.getDepartment().replaceAll("[^a-zA-Z0-9]", "") + "@123";
                
                // Add to file
                writer.println(username + "," + password);
                
                // Add to in-memory list
                teacherUsers.add(new User(username, password, "Teacher"));
                
                System.out.println("Created login credentials for teacher: " + teacher.getName() + 
                                   " (Username: " + username + ", Password: " + password + ")");
            }
        } catch (IOException e) {
            System.out.println("Error creating teacher login credentials: " + e.getMessage());
        }
    }
}