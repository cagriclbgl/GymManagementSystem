package com.example.demo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;  // JSON işleme için

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel resultLabel;

    public LoginUI() {
        setTitle("Login Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        add(panel, BorderLayout.CENTER);

        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(loginButton);

        // Register button
        registerButton = new JButton("Register");
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(registerButton);

        // Result label
        resultLabel = new JLabel("", JLabel.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        // Button click event handling
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // API ile login işlemi yapacak
                String result = loginUser(email, password);
                resultLabel.setText(result);
            }
        });

        // Register button click event
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Register UI'yi göster
                new RegisterUI().setVisible(true);
                dispose(); // Login UI'yi kapat
            }
        });
    }

    private String loginUser(String email, String password) {
        try {
            String apiUrl = "http://localhost:8080/api/auth/login"; // API URL
            Map<String, String> data = new HashMap<>();
            data.put("email", email);
            data.put("password", password);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(data);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, String> responseBody = objectMapper.readValue(response.body(), Map.class);
                String role = responseBody.get("role");

                // Rol kontrolü
                if ("admin".equalsIgnoreCase(role)) {
                    JOptionPane.showMessageDialog(this, "Welcome Admin!");
                    dispose();
                    new AdminPanelUI().setVisible(true); // Admin panelini aç
                } else {
                    JOptionPane.showMessageDialog(this, "Welcome User!");
                    dispose();
                    new GymHomePageUI().setVisible(true); // Normal kullanıcı sayfasını aç
                }
                return "Login successful";
            } else {
                return "Login failed: " + response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while connecting to API";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }

}
