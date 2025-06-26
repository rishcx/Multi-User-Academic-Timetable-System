import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class TeacherPortal extends JFrame {
    private String teacherUsername;
    private List<Subject> subjects;
    private Map<String, List<Subject>> weeklySchedule;
    private DataManager dataManager;
    
    private JTable timetableTable;
    private DefaultTableModel timetableModel;
    private JLabel welcomeLabel;
    
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

    private Teacher teacher;
    private DefaultTableModel scheduleModel;
    private JTable scheduleTable;

    public TeacherPortal(Teacher teacher) {
        this.teacher = teacher;
        this.dataManager = DataManager.getInstance();
        this.weeklySchedule = new HashMap<>();
        this.subjects = new ArrayList<>();
        
        setTitle("Teacher Portal - " + teacher.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Initialize schedule table
        String[] columnNames = new String[DAYS.length + 1];
        columnNames[0] = "Time";
        System.arraycopy(DAYS, 0, columnNames, 1, DAYS.length);
        
        scheduleModel = new DefaultTableModel(columnNames, TIME_SLOTS.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        scheduleTable = new JTable(scheduleModel);
        scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        for (int i = 1; i < columnNames.length; i++) {
            scheduleTable.getColumnModel().getColumn(i).setPreferredWidth(200);
        }
        
        initializeData();
        setupGUI();
    }

    private void initializeData() {
        // Get all subjects from DataManager
        subjects = dataManager.getSubjects();
        List<Subject> teacherSubjects = new ArrayList<>();
        
        // Get teacher's full name and create variations for matching
        String teacherFullName = teacher.getName().toLowerCase().trim();
        String teacherNameNoTitle = teacherFullName.replaceAll("^(dr|prof)\\.\\s*", "");
        String teacherNameNoSpaces = teacherFullName.replaceAll("\\s+", "");
        
        System.out.println("Looking for subjects for teacher: " + teacher.getName());
        System.out.println("Teacher name variations: " + teacherFullName + ", " + teacherNameNoTitle + ", " + teacherNameNoSpaces);
        
        // First try exact matching
        for (Subject subject : subjects) {
            boolean isTeacher = false;
            for (String lecturer : subject.getLecturers()) {
                String lecturerName = lecturer.toLowerCase().trim();
                String lecturerNameNoTitle = lecturerName.replaceAll("^(dr|prof)\\.\\s*", "");
                String lecturerNameNoSpaces = lecturerName.replaceAll("\\s+", "");
                
                // Try different name variations
                if (lecturerName.equals(teacherFullName) ||
                    lecturerNameNoTitle.equals(teacherNameNoTitle) ||
                    lecturerNameNoSpaces.equals(teacherNameNoSpaces)) {
                    isTeacher = true;
                    System.out.println("Exact match found! Adding subject: " + subject.getName());
                    break;
                }
            }
            if (isTeacher) {
                teacherSubjects.add(subject);
            }
        }
        
        // If no exact matches found, try partial matching
        if (teacherSubjects.isEmpty()) {
            System.out.println("No exact matches found, trying partial matching...");
            for (Subject subject : subjects) {
                boolean isTeacher = false;
                for (String lecturer : subject.getLecturers()) {
                    String lecturerName = lecturer.toLowerCase().trim();
                    String lecturerNameNoTitle = lecturerName.replaceAll("^(dr|prof)\\.\\s*", "");
                    String lecturerNameNoSpaces = lecturerName.replaceAll("\\s+", "");
                    
                    // Check if any name variation is contained in the other
                    if (lecturerName.contains(teacherNameNoTitle) || 
                        teacherNameNoTitle.contains(lecturerNameNoTitle) ||
                        lecturerNameNoSpaces.contains(teacherNameNoSpaces) ||
                        teacherNameNoSpaces.contains(lecturerNameNoSpaces)) {
                        isTeacher = true;
                        System.out.println("Partial match found! Adding subject: " + subject.getName());
                        break;
                    }
                }
                if (isTeacher) {
                    teacherSubjects.add(subject);
                }
            }
        }
        
        System.out.println("Filtered subjects for teacher: " + teacherSubjects.size());
        subjects = teacherSubjects;
        
        // Initialize weekly schedule for each day
        for (String day : DAYS) {
            weeklySchedule.put(day, new ArrayList<>());
        }
        
        // Add subjects to weekly schedule
        for (Subject subject : subjects) {
            String timing = subject.getTiming();
            if (timing != null && !timing.isEmpty()) {
                String[] parts = timing.split(" ");
                if (parts.length > 1) {
                    String day = parts[0];
                    if (weeklySchedule.containsKey(day)) {
                        weeklySchedule.get(day).add(subject);
                    }
                }
            }
        }
    }

    private void setupGUI() {
        setTitle("Teacher Portal - Welcome " + teacher.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 50, 50));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Add welcome label
        welcomeLabel = new JLabel("Welcome, " + teacher.getName());
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

        // Create timetable panel
        JPanel timetablePanel = createTimetablePanel();
        mainPanel.add(timetablePanel, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel(" Weekly Timetable View");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 0));
        statusBar.add(statusLabel, BorderLayout.WEST);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createTimetablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table model
        String[] columns = new String[DAYS.length + 1];
        columns[0] = "Time";
        System.arraycopy(DAYS, 0, columns, 1, DAYS.length);
        
        timetableModel = new DefaultTableModel(columns, TIME_SLOTS.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }
        };
        
        // Set time slots in the first column
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            timetableModel.setValueAt(TIME_SLOTS[i], i, 0);
        }
        
        // Initialize all cells as "Free"
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            for (int j = 1; j <= DAYS.length; j++) {
                timetableModel.setValueAt("Free", i, j);
            }
        }
        
        // Fill in the timetable data
        for (Subject subject : subjects) {
            String timing = subject.getTiming();
            if (timing != null && !timing.isEmpty()) {
                String[] parts = timing.split(" ");
                if (parts.length > 1) {
                    String day = parts[0];
                    String timeSlot = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                    
                    int dayIndex = getDayColumnIndex(day);
                    if (dayIndex >= 0) {
                        for (int i = 0; i < TIME_SLOTS.length; i++) {
                            if (TIME_SLOTS[i].equals(timeSlot)) {
                                String cellContent = String.format("<html><center><b>%s</b><br>Room: %s</center></html>", 
                                    subject.getName(), 
                                    subject.getClassroom());
                                timetableModel.setValueAt(cellContent, i, dayIndex);
                                break;
                            }
                        }
                    }
                }
            }
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
        
        // Center align all cells and set colors
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column > 0 && value != null) {  // Skip the time column
                    if (value.toString().equals("Free")) {
                        c.setBackground(new Color(240, 240, 240));  // Light gray for free slots
                    } else {
                        c.setBackground(new Color(200, 230, 200));  // Light green for scheduled classes
                    }
                } else {
                    c.setBackground(table.getBackground());
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        
        for (int i = 0; i < timetableTable.getColumnCount(); i++) {
            timetableTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(timetableTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private int getDayColumnIndex(String day) {
        for (int i = 0; i < DAYS.length; i++) {
            if (DAYS[i].equals(day)) {
                return i + 1; // +1 because first column is time
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Teacher teacher = new Teacher("Dr. Sarah Johnson", "Computer Science", "sarah.johnson@university.edu", "1234567890");
            new TeacherPortal(teacher).setVisible(true);
        });
    }
} 