import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private DatabaseManager dbManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LoginGUI() {
        dbManager = new DatabaseManager();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Login System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create card layout for switching between login and signup
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create login panel
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "LOGIN");

        // Create signup panel
        JPanel signupPanel = createSignupPanel();
        mainPanel.add(signupPanel, "SIGNUP");

        add(mainPanel);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Welcome to TimeTable Generator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        panel.add(usernamePanel);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        panel.add(passwordPanel);

        // Role selection
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Admin", "Teacher", "Student"};
        roleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleLabel);
        rolePanel.add(roleComboBox);
        panel.add(rolePanel);

        panel.add(Box.createVerticalStrut(20));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.addActionListener(e -> handleLogin());
        panel.add(loginButton);

        // Signup link
        JButton signupLink = new JButton("New user? Sign up here");
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLink.setBorderPainted(false);
        signupLink.setContentAreaFilled(false);
        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.addActionListener(e -> cardLayout.show(mainPanel, "SIGNUP"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(signupLink);  

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField signupUsernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(signupUsernameField);
        panel.add(usernamePanel);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField signupPasswordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(signupPasswordField);
        panel.add(passwordPanel);

        // Role selection
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Admin", "Teacher", "Student"};
        JComboBox<String> signupRoleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleLabel);
        rolePanel.add(signupRoleComboBox);
        panel.add(rolePanel);

        panel.add(Box.createVerticalStrut(20));

        // Signup button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setMaximumSize(new Dimension(200, 40));
        signupButton.addActionListener(e -> {
            String result = dbManager.signupUser(
                signupUsernameField.getText(),
                new String(signupPasswordField.getPassword()),
                (String) signupRoleComboBox.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, result, "Signup Result", 
                result.contains("successful") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (result.contains("successful")) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });
        panel.add(signupButton);

        // Login link
        JButton loginLink = new JButton("Already have an account? Login here");
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setForeground(Color.BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginLink);

        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DatabaseManager dbManager = new DatabaseManager();
        String[] authResult = dbManager.authenticateUser(username, password, role);
        if (authResult[0].equals("true")) {
            if (role.equals("Admin")) {
                this.setVisible(false);
                AdminPortal adminPortal = new AdminPortal();
                adminPortal.setVisible(true);
                adminPortal.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                    }
                });
            } else if (role.equals("Teacher")) {
                this.setVisible(false);
                Teacher teacher = dbManager.getTeacher(username);
                if (teacher != null) {
                    new TeacherPortal(teacher).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Teacher not found!");
                }
            } else if (role.equals("Student")) {
                this.setVisible(false);
                StudentPortal studentPortal = new StudentPortal(username);
                studentPortal.setVisible(true);
                studentPortal.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, authResult[1], "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}