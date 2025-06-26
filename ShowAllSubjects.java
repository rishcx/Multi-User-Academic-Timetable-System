import javax.swing.SwingUtilities;

public class ShowAllSubjects {
    public static void main(String[] args) {
        // Clear the data first
        DataManager.getInstance().clearAllData();
        
        // Then open the AdminPortal
        SwingUtilities.invokeLater(() -> {
            new AdminPortal().setVisible(true);
        });
    }
} 