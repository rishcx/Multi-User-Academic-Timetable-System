import java.util.*;

public class TimetableScheduler {
    private List<Subject> allSubjects;
    private List<List<Subject>> possibleSchedules;
    private int currentScheduleIndex;
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    
    public TimetableScheduler(List<Subject> allSubjects) {
        this.allSubjects = allSubjects;
        this.possibleSchedules = new ArrayList<>();
        this.currentScheduleIndex = -1;
    }
    
    public void generateSchedules(List<String> courseNames) {
        possibleSchedules.clear();
        currentScheduleIndex = -1;
        
        // Filter subjects based on course names
        List<Subject> selectedSubjects = new ArrayList<>();
        for (String courseName : courseNames) {
            String trimmedCourseName = courseName.trim().toLowerCase();
            
            // Find all matching subjects for this course name
            for (Subject subject : allSubjects) {
                String subjectName = subject.getName().toLowerCase();
                if (subjectName.contains(trimmedCourseName) || 
                    trimmedCourseName.contains(subjectName)) {
                    selectedSubjects.add(subject);
                }
            }
        }
        
        if (selectedSubjects.isEmpty()) {
            return;
        }
        
        // Remove duplicates while preserving order
        selectedSubjects = new ArrayList<>(new LinkedHashSet<>(selectedSubjects));
        
        // Generate all possible non-clashing schedules
        generateNonClashingSchedules(selectedSubjects, new ArrayList<>(), 0);
        
        // Sort schedules by number of subjects (prefer schedules with more subjects)
        Collections.sort(possibleSchedules, (a, b) -> b.size() - a.size());
    }
    
    private void generateNonClashingSchedules(List<Subject> subjects, List<Subject> currentSchedule, int index) {
        // Only save the schedule if it contains all requested subjects
        if (currentSchedule.size() == subjects.size()) {
            possibleSchedules.add(new ArrayList<>(currentSchedule));
            return;
        }
        
        // Try adding each remaining subject
        for (int i = index; i < subjects.size(); i++) {
            Subject currentSubject = subjects.get(i);
            boolean canAdd = true;
            
            // Check for time conflicts with already scheduled subjects
            for (Subject scheduledSubject : currentSchedule) {
                if (hasTimeConflict(currentSubject, scheduledSubject)) {
                    canAdd = false;
                    break;
                }
            }
            
            if (canAdd) {
                currentSchedule.add(currentSubject);
                generateNonClashingSchedules(subjects, currentSchedule, i + 1);
                currentSchedule.remove(currentSchedule.size() - 1);
            }
        }
    }
    
    private boolean hasTimeConflict(Subject subject1, Subject subject2) {
        String timing1 = subject1.getTiming();
        String timing2 = subject2.getTiming();
        
        if (timing1 == null || timing2 == null || timing1.isEmpty() || timing2.isEmpty()) {
            return false;
        }
        
        // Extract day and time from timing strings
        String[] parts1 = timing1.split(" ", 2);
        String[] parts2 = timing2.split(" ", 2);
        
        if (parts1.length < 2 || parts2.length < 2) {
            return false;
        }
        
        String day1 = parts1[0];
        String day2 = parts2[0];
        String time1 = parts1[1];
        String time2 = parts2[1];
        
        // Check if subjects are on the same day
        if (!day1.equalsIgnoreCase(day2)) {
            return false;
        }
        
        // Parse time ranges
        String[] timeRange1 = time1.split(" - ");
        String[] timeRange2 = time2.split(" - ");
        
        if (timeRange1.length != 2 || timeRange2.length != 2) {
            return false;
        }
        
        // Convert times to comparable format (24-hour)
        int start1 = convertTo24Hour(timeRange1[0]);
        int end1 = convertTo24Hour(timeRange1[1]);
        int start2 = convertTo24Hour(timeRange2[0]);
        int end2 = convertTo24Hour(timeRange2[1]);
        
        // Check for overlap
        return (start1 < end2 && start2 < end1);
    }
    
    private int convertTo24Hour(String time) {
        // Remove any extra spaces
        time = time.trim();
        
        // Split into time and AM/PM
        String[] parts = time.split(" ");
        if (parts.length != 2) {
            return -1;
        }
        
        String timeStr = parts[0];
        String period = parts[1].toUpperCase();
        
        // Split hours and minutes
        String[] timeParts = timeStr.split(":");
        if (timeParts.length != 2) {
            return -1;
        }
        
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        
        // Convert to 24-hour format
        if (period.equals("PM") && hours != 12) {
            hours += 12;
        } else if (period.equals("AM") && hours == 12) {
            hours = 0;
        }
        
        // Return total minutes since midnight for easier comparison
        return hours * 60 + minutes;
    }
    
    public List<Subject> getNextSchedule() {
        if (possibleSchedules.isEmpty()) {
            return null;
        }
        
        currentScheduleIndex = (currentScheduleIndex + 1) % possibleSchedules.size();
        return possibleSchedules.get(currentScheduleIndex);
    }
    
    public List<Subject> getPreviousSchedule() {
        if (possibleSchedules.isEmpty()) {
            return null;
        }
        
        currentScheduleIndex = (currentScheduleIndex - 1 + possibleSchedules.size()) % possibleSchedules.size();
        return possibleSchedules.get(currentScheduleIndex);
    }
    
    public List<Subject> getCurrentSchedule() {
        if (currentScheduleIndex == -1 || possibleSchedules.isEmpty()) {
            return null;
        }
        return possibleSchedules.get(currentScheduleIndex);
    }
    
    public int getTotalSchedules() {
        return possibleSchedules.size();
    }
    
    public int getCurrentScheduleIndex() {
        return currentScheduleIndex + 1; // 1-based index for display
    }
    
    public void saveCurrentSchedule(String name) {
        // This method will be implemented to save the current schedule
        // For now, we'll just print a message
        System.out.println("Saving schedule: " + name);
    }
} 