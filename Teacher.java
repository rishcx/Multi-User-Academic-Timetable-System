import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String department;
    private List<String> subjects;
    private String email;
    private String phone;

    public Teacher(String name, String department, String email, String phone) {
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public void removeSubject(String subject) {
        subjects.remove(subject);
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getSubjects() {
        return new ArrayList<>(subjects);
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
} 