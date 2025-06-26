import java.io.Serializable;

public class Classroom implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private int capacity;
    private boolean hasAudioVideo;
    private String currentSubject;
    private String currentTeacher;

    public Classroom(String roomNumber, int capacity, boolean hasAudioVideo) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hasAudioVideo = hasAudioVideo;
        this.currentSubject = "";
        this.currentTeacher = "";
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasAudioVideo() {
        return hasAudioVideo;
    }

    public String getCurrentSubject() {
        return currentSubject;
    }

    public void setCurrentSubject(String subject) {
        this.currentSubject = subject;
    }

    public String getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(String teacher) {
        this.currentTeacher = teacher;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (Capacity: " + capacity + ", AV: " + (hasAudioVideo ? "Yes" : "No") + ")";
    }
} 