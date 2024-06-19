// FaceAuthSystem.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FaceAuthSystem {
    private UserDAO userDAO = new UserDAO();
    private FaceDataDAO faceDataDAO = new FaceDataDAO();
    private FaceRecognition faceRecognition = new FaceRecognition();

    public static void main(String[] args) {
        new FaceAuthSystem().showMenu();
    }

    private void showMenu() {
        JFrame frame = new JFrame("Face Authentication System");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 50, 120, 30);
        frame.add(registerButton);

        JButton authenticateButton = new JButton("Authenticate");
        authenticateButton.setBounds(200, 50, 120, 30);
        frame.add(authenticateButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        authenticateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        frame.setVisible(true);
    }

    private void registerUser() {
        // Simple dialog to enter user details
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        // Hash password for security (e.g., using BCrypt)
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Hash password before saving

        userDAO.insertUser(user);

        // Capture face image (this is a placeholder, implement actual image capture)
        String imagePath = "path/to/captured/image.jpg";
        Mat face = faceRecognition.preprocessImage(imagePath);

        // Store face data
        List<Mat> images = new ArrayList<>();
        images.add(face);

        List<Integer> labels = new ArrayList<>();
        labels.add(userDAO.getUserByUsername(username).getId());

        faceRecognition.train(images, labels);

        FaceData faceData = new FaceData();
        faceData.setUserId(userDAO.getUserByUsername(username).getId());
        faceData.setFaceTemplate(faceRecognition.preprocessImage(imagePath).dataAddr());

        faceDataDAO.insertFaceData(faceData);

        JOptionPane.showMessageDialog(null, "User registered successfully!");
    }

    private void authenticateUser() {
        // Capture face image (this is a placeholder, implement actual image capture)
        String imagePath = "path/to/captured/image.jpg";
        Mat face = faceRecognition.preprocessImage(imagePath);

        int userId = faceRecognition.predict(face);
        User user = userDAO.getUserByUsername(userId);

        if (user != null) {
            JOptionPane.showMessageDialog(null, "User authenticated: " + user.getUsername());
        } else {
            JOptionPane.showMessageDialog(null, "Authentication failed!");
        }
    }
}

