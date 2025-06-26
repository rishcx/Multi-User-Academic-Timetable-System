import java.util.ArrayList;
import java.util.List;

public class Student {
    private String username;
    private String name;
    private String email;
    private List<Subject> enrolledSubjects;
    private int totalCredits;

    public Student(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.enrolledSubjects = new ArrayList<>();
        this.totalCredits = 0;
    }

    public void enrollSubject(Subject subject) {
        if (!enrolledSubjects.contains(subject) && enrolledSubjects.size() < 7) {
            if (totalCredits + subject.getCredits() <= 25) {
                enrolledSubjects.add(subject);
                totalCredits += subject.getCredits();
            }
        }
    }

    public void dropSubject(Subject subject) {
        if (enrolledSubjects.remove(subject)) {
            totalCredits -= subject.getCredits();
        }
    }

    public boolean hasSubject(Subject subject) {
        return enrolledSubjects.contains(subject);
    }

    public boolean hasSchedulingConflict(Subject newSubject) {
        for (Subject enrolledSubject : enrolledSubjects) {
            if (enrolledSubject.getTiming().equals(newSubject.getTiming())) {
                return true;
            }
        }
        return false;
    }

    public boolean canEnrollMoreSubjects() {
        return enrolledSubjects.size() < 7;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Subject> getEnrolledSubjects() {
        return new ArrayList<>(enrolledSubjects);
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public int getEnrolledSubjectCount() {
        return enrolledSubjects.size();
    }

    @Override
    public String toString() {
        return name + " (" + username + ")";
    }
} 