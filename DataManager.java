import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DataManager {
    private static DataManager instance;
    private List<Classroom> classrooms;
    private List<Teacher> teachers;
    private List<Subject> subjects;
    
    private static final String CLASSROOMS_FILE = "classrooms.dat";
    private static final String TEACHERS_FILE = "teachers.dat";
    private static final String SUBJECTS_FILE = "subjects.dat";

    private DataManager() {
        loadData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadData() {
        try {
            // Try to load classrooms
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CLASSROOMS_FILE))) {
                classrooms = (List<Classroom>) ois.readObject();
            } catch (FileNotFoundException e) {
                classrooms = new ArrayList<>();
            }

            // Try to load teachers
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEACHERS_FILE))) {
                teachers = (List<Teacher>) ois.readObject();
            } catch (FileNotFoundException e) {
                teachers = new ArrayList<>();
            }

            // Try to load subjects
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUBJECTS_FILE))) {
                subjects = (List<Subject>) ois.readObject();
            } catch (FileNotFoundException e) {
                subjects = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            classrooms = new ArrayList<>();
            teachers = new ArrayList<>();
            subjects = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            // Save classrooms
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CLASSROOMS_FILE))) {
                oos.writeObject(classrooms);
            }

            // Save teachers
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEACHERS_FILE))) {
                oos.writeObject(teachers);
            }

            // Save subjects
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUBJECTS_FILE))) {
                oos.writeObject(subjects);
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Classroom methods
    public List<Classroom> getClassrooms() {
        return new ArrayList<>(classrooms);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        saveData();
    }

    public void updateClassroom(int index, Classroom classroom) {
        if (index >= 0 && index < classrooms.size()) {
            classrooms.set(index, classroom);
            saveData();
        }
    }

    public void deleteClassroom(int index) {
        if (index >= 0 && index < classrooms.size()) {
            classrooms.remove(index);
            saveData();
        }
    }

    // Teacher methods
    public List<Teacher> getTeachers() {
        return new ArrayList<>(teachers);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        saveData();
    }

    public void updateTeacher(int index, Teacher teacher) {
        if (index >= 0 && index < teachers.size()) {
            teachers.set(index, teacher);
            saveData();
        }
    }

    public void deleteTeacher(int index) {
        if (index >= 0 && index < teachers.size()) {
            teachers.remove(index);
            saveData();
        }
    }

    // Subject methods
    public List<Subject> getSubjects() {
        return new ArrayList<>(subjects);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        saveData();
    }

    public void updateSubject(int index, Subject subject) {
        if (index >= 0 && index < subjects.size()) {
            subjects.set(index, subject);
            saveData();
        }
    }

    public void deleteSubject(int index) {
        if (index >= 0 && index < subjects.size()) {
            subjects.remove(index);
            saveData();
        }
    }

    // Method to clear all data
    public void clearAllData() {
        classrooms.clear();
        teachers.clear();
        subjects.clear();
        saveData();
    }
}