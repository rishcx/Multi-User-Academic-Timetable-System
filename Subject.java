import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String code;
    private List<String> lecturers;
    private String classroom;
    private String timing;
    private int credits;

    public Subject(String name, String code, int credits) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.lecturers = new ArrayList<>();
        this.classroom = "";
        this.timing = "";
    }

    public void addLecturer(String lecturer) {
        if (!lecturers.contains(lecturer)) {
            lecturers.add(lecturer);
        }
    }

    public void removeLecturer(String lecturer) {
        lecturers.remove(lecturer);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<String> getLecturers() {
        return new ArrayList<>(lecturers);
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
} 