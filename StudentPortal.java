import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class StudentPortal extends JFrame {
    private String studentUsername;
    private Student student;
    private List<Subject> availableSubjects;
    private List<Teacher> teachers;
    private DataManager dataManager;
    
    private JTable availableSubjectsTable;
    private JTable enrolledSubjectsTable;
    private JTable timetableTable;
    
    private DefaultTableModel availableSubjectsModel;
    private DefaultTableModel enrolledSubjectsModel;
    private DefaultTableModel timetableModel;
    
    private JLabel welcomeLabel;
    private JLabel enrolledCountLabel;
    private JLabel totalCreditsLabel;
    private JLabel scheduleCounterLabel;
    
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final String[] TIME_SLOTS = {
        "8:00 AM - 9:00 AM",
        "9:00 AM - 10:00 AM",
        "10:00 AM - 11:00 AM",
        "11:00 AM - 12:00 PM",
        "12:00 PM - 1:00 PM",
        "1:00 PM - 2:00 PM",
        "2:00 PM - 3:00 PM",
        "3:00 PM - 4:00 PM",
        "4:00 PM - 5:00 PM"
    };

    private TimetableScheduler timetableScheduler;
    private JButton generateSuggestionsButton;
    private JButton nextSuggestionButton;
    private JButton previousSuggestionButton;
    private JButton saveSuggestionButton;

    public StudentPortal(String username) {
        this.studentUsername = username;
        this.student = new Student(username, "Student " + username, username + "@university.edu");
        this.dataManager = DataManager.getInstance();
        this.availableSubjects = new ArrayList<>();
        this.teachers = new ArrayList<>();
        initializeData();  // This populates availableSubjects after scheduler is created
        this.timetableScheduler = new TimetableScheduler(availableSubjects);  // Empty list!
        setupGUI();
    }

    private void initializeData() {
        // Get teachers and subjects from DataManager
        teachers = dataManager.getTeachers();
        availableSubjects = dataManager.getSubjects();

        // If no subjects are available, add some sample data
        if (availableSubjects.isEmpty()) {
            // Add sample teachers
            Teacher t1 = new Teacher("John Doe", "Computer Science", "john@example.com", "123-456-7890");
            t1.addSubject("Java Programming");
            t1.addSubject("Data Structures");
            teachers.add(t1);

            Teacher t2 = new Teacher("Jane Smith", "Mathematics", "jane@example.com", "123-456-7891");
            t2.addSubject("Calculus");
            t2.addSubject("Linear Algebra");
            teachers.add(t2);

            // Add sample subjects
            Subject s1 = new Subject("Java Programming", "CS101", 3);
            s1.addLecturer("John Doe");
            s1.setClassroom("101");
            s1.setTiming("Monday 8:00 AM - 9:00 AM");
            availableSubjects.add(s1);

            Subject s2 = new Subject("Data Structures", "CS102", 3);
            s2.addLecturer("John Doe");
            s2.setClassroom("102");
            s2.setTiming("Monday 9:00 AM - 10:00 AM");
            availableSubjects.add(s2);

            Subject s3 = new Subject("Calculus", "MATH101", 3);
            s3.addLecturer("Jane Smith");
            s3.setClassroom("103");
            s3.setTiming("Tuesday 8:00 AM - 9:00 AM");
            availableSubjects.add(s3);

            Subject s4 = new Subject("Linear Algebra", "MATH102", 3);
            s4.addLecturer("Jane Smith");
            s4.setClassroom("104");
            s4.setTiming("Tuesday 9:00 AM - 10:00 AM");
            availableSubjects.add(s4);

            // Save sample data to DataManager
            for (Teacher teacher : teachers) {
                dataManager.addTeacher(teacher);
            }
            for (Subject subject : availableSubjects) {
                dataManager.addSubject(subject);
            }
        }
    }

    private void setupGUI() {
        setTitle("Student Portal - Welcome " + studentUsername);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 50, 50));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Add welcome label
        welcomeLabel = new JLabel("Welcome, " + student.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.setMargin(new Insets(5, 10, 5, 10));
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create tabs
        tabbedPane.addTab("Course Registration", createCourseRegistrationPanel());
        tabbedPane.addTab("My Timetable", createTimetablePanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        
        // Add enrolled count and total credits labels
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        enrolledCountLabel = new JLabel("Enrolled Courses: 0/7");
        totalCreditsLabel = new JLabel("Total Credits: 0");
        infoPanel.add(enrolledCountLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(totalCreditsLabel);
        
        statusBar.add(infoPanel, BorderLayout.WEST);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createCourseRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        // Create available subjects panel
        JPanel availableSubjectsPanel = new JPanel(new BorderLayout());
        availableSubjectsPanel.setBorder(BorderFactory.createTitledBorder("Available Courses"));
        
        // Create table model for available subjects
        String[] columns = {"Name", "Code", "Lecturer", "Classroom", "Timing", "Credits"};
        availableSubjectsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        availableSubjectsTable = new JTable(availableSubjectsModel);
        availableSubjectsTable.setRowHeight(25);
        availableSubjectsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane availableScrollPane = new JScrollPane(availableSubjectsTable);
        
        // Add data to available subjects table
        for (Subject subject : availableSubjects) {
            availableSubjectsModel.addRow(new Object[]{
                subject.getName(),
                subject.getCode(),
                String.join(", ", subject.getLecturers()),
                subject.getClassroom(),
                subject.getTiming(),
                subject.getCredits()
            });
        }
        
        // Add enroll button
        JButton enrollButton = new JButton("Enroll in Selected Course");
        enrollButton.addActionListener(e -> enrollSelectedSubject());
        
        availableSubjectsPanel.add(availableScrollPane, BorderLayout.CENTER);
        availableSubjectsPanel.add(enrollButton, BorderLayout.SOUTH);
        
        // Create enrolled subjects panel
        JPanel enrolledSubjectsPanel = new JPanel(new BorderLayout());
        enrolledSubjectsPanel.setBorder(BorderFactory.createTitledBorder("My Enrolled Courses"));
        
        // Create table model for enrolled subjects
        enrolledSubjectsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        enrolledSubjectsTable = new JTable(enrolledSubjectsModel);
        enrolledSubjectsTable.setRowHeight(25);
        enrolledSubjectsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane enrolledScrollPane = new JScrollPane(enrolledSubjectsTable);
        
        // Add drop button
        JButton dropButton = new JButton("Drop Selected Course");
        dropButton.addActionListener(e -> dropSelectedSubject());
        
        enrolledSubjectsPanel.add(enrolledScrollPane, BorderLayout.CENTER);
        enrolledSubjectsPanel.add(dropButton, BorderLayout.SOUTH);
        
        // Add panels to split pane
        splitPane.setTopComponent(availableSubjectsPanel);
        splitPane.setBottomComponent(enrolledSubjectsPanel);
        splitPane.setDividerLocation(300);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createTimetablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table model for timetable
        String[] columns = new String[DAYS.length + 1];
        columns[0] = "Time";
        System.arraycopy(DAYS, 0, columns, 1, DAYS.length);
        
        timetableModel = new DefaultTableModel(columns, TIME_SLOTS.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Set time slots in the first column
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            timetableModel.setValueAt(TIME_SLOTS[i], i, 0);
        }
        
        timetableTable = new JTable(timetableModel);
        timetableTable.setRowHeight(60);
        timetableTable.getTableHeader().setReorderingAllowed(false);
        
        // Customize table appearance
        timetableTable.setGridColor(Color.LIGHT_GRAY);
        timetableTable.setShowVerticalLines(true);
        timetableTable.setShowHorizontalLines(true);
        timetableTable.setIntercellSpacing(new Dimension(5, 5));
        
        // Make the table header bold
        timetableTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < timetableTable.getColumnCount(); i++) {
            timetableTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(timetableTable);
        
        // Create scheduler panel
        JPanel schedulerPanel = new JPanel(new BorderLayout());
        schedulerPanel.setBorder(BorderFactory.createTitledBorder("Timetable Scheduler"));
        
        // Input panel for course selection
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField courseInputField = new JTextField(30);
        JButton generateButton = new JButton("Generate Schedules");
        inputPanel.add(new JLabel("Enter course names (comma-separated):"));
        inputPanel.add(courseInputField);
        inputPanel.add(generateButton);
        
        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save Schedule");
        JLabel scheduleCounterLabel = new JLabel("Schedule 0 of 0");
        
        navPanel.add(prevButton);
        navPanel.add(scheduleCounterLabel);
        navPanel.add(nextButton);
        navPanel.add(saveButton);
        
        // Add action listeners
        generateButton.addActionListener(e -> {
            String input = courseInputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter course names", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<String> courseNames = Arrays.asList(input.split(","));
            timetableScheduler.generateSchedules(courseNames);
            
            if (timetableScheduler.getTotalSchedules() > 0) {
                showCurrentSchedule();
                prevButton.setEnabled(true);
                nextButton.setEnabled(true);
                saveButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No valid schedules found for the specified courses. Try different courses or check course names.", 
                    "No Schedules Found", JOptionPane.INFORMATION_MESSAGE);
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });
        
        prevButton.addActionListener(e -> {
            List<Subject> schedule = timetableScheduler.getPreviousSchedule();
            if (schedule != null) {
                showCurrentSchedule();
            }
        });
        
        nextButton.addActionListener(e -> {
            List<Subject> schedule = timetableScheduler.getNextSchedule();
            if (schedule != null) {
                showCurrentSchedule();
            }
        });
        
        saveButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter a name for this schedule:", "Save Schedule", JOptionPane.QUESTION_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                timetableScheduler.saveCurrentSchedule(name.trim());
                JOptionPane.showMessageDialog(this, "Schedule saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Initially disable navigation buttons
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        saveButton.setEnabled(false);
        
        // Add components to scheduler panel
        schedulerPanel.add(inputPanel, BorderLayout.NORTH);
        schedulerPanel.add(navPanel, BorderLayout.SOUTH);
        
        // Create main content panel with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(scrollPane);
        splitPane.setBottomComponent(schedulerPanel);
        splitPane.setDividerLocation(400);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private String findDayForSubject(Subject subject) {
        // Get the subject's timing
        String timing = subject.getTiming();
        if (timing == null || timing.isEmpty()) {
            return null;
        }

        // Extract the day from the timing string
        // Format is "Day HH:MM AM/PM - HH:MM AM/PM"
        String[] parts = timing.split(" ");
        if (parts.length >= 1) {
            String possibleDay = parts[0];
            for (String day : DAYS) {
                if (day.equalsIgnoreCase(possibleDay)) {
                    return day;
                }
            }
        }

        // If no day is found, try to match the time slot
        String timeSlot = timing;
        if (parts.length > 1) {
            // Reconstruct time slot without the day
            timeSlot = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        }

        for (int i = 0; i < TIME_SLOTS.length; i++) {
            if (TIME_SLOTS[i].equals(timeSlot)) {
                // Assign days based on the time slot index to distribute classes evenly
                return DAYS[i % DAYS.length];
            }
        }

        return null;
    }
    
    private int getDayColumnIndex(String day) {
        for (int i = 0; i < DAYS.length; i++) {
            if (DAYS[i].equals(day)) {
                return i + 1; // +1 because first column is time
            }
        }
        return -1;
    }
    
    private void enrollSelectedSubject() {
        int selectedRow = availableSubjectsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to enroll", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get the selected subject
        Subject selectedSubject = availableSubjects.get(selectedRow);
        
        // Check if student can enroll more subjects
        if (!student.canEnrollMoreSubjects()) {
            JOptionPane.showMessageDialog(this, "You cannot enroll in more than 7 courses", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check for scheduling conflicts
        if (student.hasSchedulingConflict(selectedSubject)) {
            JOptionPane.showMessageDialog(this, 
                "Scheduling conflict: You already have a course at " + selectedSubject.getTiming(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if student is already enrolled in this subject
        if (student.hasSubject(selectedSubject)) {
            JOptionPane.showMessageDialog(this, "You are already enrolled in this course", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if adding this subject would exceed credit limit
        if (student.getTotalCredits() + selectedSubject.getCredits() > 25) {
            JOptionPane.showMessageDialog(this, 
                "Cannot enroll: Adding this course would exceed the maximum credit limit of 25 credits", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Enroll the student in the subject
        student.enrollSubject(selectedSubject);
        
        // Update enrolled subjects table
        enrolledSubjectsModel.addRow(new Object[]{
            selectedSubject.getName(),
            selectedSubject.getCode(),
            String.join(", ", selectedSubject.getLecturers()),
            selectedSubject.getClassroom(),
            selectedSubject.getTiming(),
            selectedSubject.getCredits()
        });
        
        // Update status labels
        updateStatusLabels();
        
        // Update timetable
        updateTimetable();
        
        JOptionPane.showMessageDialog(this, "Successfully enrolled in " + selectedSubject.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void dropSelectedSubject() {
        int selectedRow = enrolledSubjectsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to drop", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get the selected subject
        Subject selectedSubject = student.getEnrolledSubjects().get(selectedRow);
        
        // Drop the subject
        student.dropSubject(selectedSubject);
        
        // Remove from enrolled subjects table
        enrolledSubjectsModel.removeRow(selectedRow);
        
        // Update status labels
        updateStatusLabels();
        
        // Update timetable
        updateTimetable();
        
        JOptionPane.showMessageDialog(this, "Successfully dropped " + selectedSubject.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateStatusLabels() {
        enrolledCountLabel.setText("Enrolled Courses: " + student.getEnrolledSubjectCount() + "/7");
        totalCreditsLabel.setText("Total Credits: " + student.getTotalCredits());
    }
    
    private void updateTimetable() {
        // Clear the timetable
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            for (int j = 1; j <= DAYS.length; j++) {
                timetableModel.setValueAt("", i, j);
            }
        }
        
        // Fill in enrolled subjects
        for (Subject subject : student.getEnrolledSubjects()) {
            String timing = subject.getTiming();
            if (timing != null && !timing.isEmpty()) {
                String[] parts = timing.split(" ", 2);
                if (parts.length == 2) {
                    String day = parts[0];
                    String timeRange = parts[1];
                    
                    // Find the day column
                    int dayIndex = -1;
                    for (int i = 0; i < DAYS.length; i++) {
                        if (DAYS[i].equalsIgnoreCase(day)) {
                            dayIndex = i + 1; // +1 because first column is time
                            break;
                        }
                    }
                    
                    if (dayIndex != -1) {
                        // Find the time slot row
                        String[] timeParts = timeRange.split(" - ");
                        if (timeParts.length == 2) {
                            String startTime = timeParts[0].trim();
                            for (int i = 0; i < TIME_SLOTS.length; i++) {
                                if (TIME_SLOTS[i].startsWith(startTime)) {
                                    String lecturers = String.join(", ", subject.getLecturers());
                                    if (lecturers.length() > 20) {
                                        lecturers = lecturers.substring(0, 17) + "...";
                                    }
                                    String cellContent = String.format("<html><center><b>%s</b><br>Room: %s<br>%s</center></html>", 
                                        subject.getName(), 
                                        subject.getClassroom(),
                                        lecturers);
                                    timetableModel.setValueAt(cellContent, i, dayIndex);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void showCurrentSchedule() {
        List<Subject> currentSchedule = timetableScheduler.getCurrentSchedule();
        if (currentSchedule != null) {
            // Clear the timetable
            for (int i = 0; i < TIME_SLOTS.length; i++) {
                for (int j = 1; j <= DAYS.length; j++) {
                    timetableModel.setValueAt("", i, j);
                }
            }
            
            // Fill in the timetable data
            for (Subject subject : currentSchedule) {
                String timing = subject.getTiming();
                if (timing != null && !timing.isEmpty()) {
                    String[] parts = timing.split(" ", 2);
                    if (parts.length == 2) {
                        String day = parts[0];
                        String timeRange = parts[1];
                        
                        // Find the day column
                        int dayIndex = -1;
                        for (int i = 0; i < DAYS.length; i++) {
                            if (DAYS[i].equalsIgnoreCase(day)) {
                                dayIndex = i + 1; // +1 because first column is time
                                break;
                            }
                        }
                        
                        if (dayIndex != -1) {
                            // Find the time slot row
                            String[] timeParts = timeRange.split(" - ");
                            if (timeParts.length == 2) {
                                String startTime = timeParts[0].trim();
                                for (int i = 0; i < TIME_SLOTS.length; i++) {
                                    if (TIME_SLOTS[i].startsWith(startTime)) {
                                        String lecturers = String.join(", ", subject.getLecturers());
                                        if (lecturers.length() > 20) {
                                            lecturers = lecturers.substring(0, 17) + "...";
                                        }
                                        String cellContent = String.format("<html><center><b>%s</b><br>Room: %s<br>%s</center></html>", 
                                            subject.getName(), 
                                            subject.getClassroom(),
                                            lecturers);
                                        timetableModel.setValueAt(cellContent, i, dayIndex);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Update schedule counter
            int total = timetableScheduler.getTotalSchedules();
            int current = timetableScheduler.getCurrentScheduleIndex();
            scheduleCounterLabel.setText(String.format("Schedule %d of %d (%d subjects)", 
                current, total, currentSchedule.size()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentPortal("student1").setVisible(true);
        });
    }
} 