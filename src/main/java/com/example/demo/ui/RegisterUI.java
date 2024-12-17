/*package com.example.demo.ui;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class RegisterUI extends JFrame {
    private JTextField nameField, surnameField, emailField, phoneField, birthDateField;
    private JPasswordField passwordField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JLabel resultLabel;

    public RegisterUI() {
        setTitle("Register Form");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));
        add(panel, BorderLayout.CENTER);


        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);


        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField();
        panel.add(surnameLabel);
        panel.add(surnameField);


        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);


        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);


        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField();
        panel.add(phoneLabel);
        panel.add(phoneField);


        JLabel birthDateLabel = new JLabel("Birth Date:");
        birthDateField = new JTextField();
        panel.add(birthDateLabel);
        panel.add(birthDateField);


        JLabel genderLabel = new JLabel("Gender:");
        JPanel genderPanel = new JPanel();
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        panel.add(genderLabel);
        panel.add(genderPanel);


        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[] {"User", "Trainer"});
        panel.add(roleLabel);
        panel.add(roleComboBox);


        registerButton = new JButton("Register");
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(registerButton);

        resultLabel = new JLabel("", JLabel.CENTER);
        add(resultLabel, BorderLayout.SOUTH);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcı verilerini al
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phoneNumber = phoneField.getText();
                String birthDate = birthDateField.getText();
                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String role = (String) roleComboBox.getSelectedItem();

                // API ile register işlemi yapacak
                String result = registerUser(name, surname, email, password, phoneNumber, birthDate, gender, role);
                resultLabel.setText(result);
            }
        });
    }

    private String registerUser(String name, String surname, String email, String password, String phoneNumber, String birthDate, String gender, String role) {
        try {
            String apiUrl = "http://localhost:8080/api/users/register"; // Kayıt URL
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            data.put("surname", surname);
            data.put("email", email);
            data.put("password", password);
            data.put("phoneNumber", phoneNumber);
            data.put("birthDate", birthDate);
            data.put("gender", gender);
            data.put("role", role);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(data);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // API yanıtını kontrol et
            if (response.statusCode() == 201) {
                return "Registration successful"; // Başarıyla kaydedildi
            } else if (response.statusCode() == 409) {
                return "Email or phone number already taken"; // Çakışma (email veya telefon numarası alınmış)
            } else {
                return "Registration failed: " + response.body(); // Diğer hatalar
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while connecting to API"; // API'ye bağlanırken hata
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegisterUI frame = new RegisterUI();
                frame.setVisible(true);
            }
        });
    }
}*/


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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class RegisterUI extends JFrame {
    private JTextField nameField, surnameField, emailField, phoneField, birthDateField;
    private JPasswordField passwordField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JLabel resultLabel;

    public RegisterUI() {
        setTitle("Register Form");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Ana panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Başlık
        JLabel titleLabel = new JLabel("User Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Bileşenler
        gbc.gridwidth = 1; // Reset gridwidth

        nameField = addLabeledField("Name:", 1, mainPanel, gbc);
        surnameField = addLabeledField("Surname:", 2, mainPanel, gbc);
        emailField = addLabeledField("Email:", 3, mainPanel, gbc);
        passwordField = addLabeledPasswordField("Password:", 4, mainPanel, gbc);
        phoneField = addLabeledField("Phone Number:", 5, mainPanel, gbc);
        birthDateField = addLabeledField("Birth Date (YYYY-MM-DD):", 6, mainPanel, gbc);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(240, 248, 255));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        maleRadio.setBackground(new Color(240, 248, 255));
        femaleRadio.setBackground(new Color(240, 248, 255));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        gbc.gridx = 1;
        mainPanel.add(genderPanel, gbc);

        // Role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"User", "Trainer"});
        gbc.gridx = 1;
        mainPanel.add(roleComboBox, gbc);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 102, 204));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setIcon(new ImageIcon("icons/register.png")); // Opsiyonel ikon

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        mainPanel.add(registerButton, gbc);

        // Result label
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        resultLabel.setForeground(Color.RED);
        gbc.gridy = 10;
        mainPanel.add(resultLabel, gbc);

        add(mainPanel);

        // Register buton tıklama olayı
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phoneNumber = phoneField.getText();
                String birthDate = birthDateField.getText();
                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String role = (String) roleComboBox.getSelectedItem();

                String result = registerUser(name, surname, email, password, phoneNumber, birthDate, gender, role);
                resultLabel.setText(result);
            }
        });
    }

    private JTextField addLabeledField(String labelText, int yPos, JPanel panel, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(label, gbc);

        JTextField textField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(textField, gbc);
        return textField;
    }

    private JPasswordField addLabeledPasswordField(String labelText, int yPos, JPanel panel, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(label, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        return passwordField;
    }

    private String registerUser(String name, String surname, String email, String password, String phoneNumber, String birthDate, String gender, String role) {
        try {
            String apiUrl = "http://localhost:8080/api/users/register";
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            data.put("surname", surname);
            data.put("email", email);
            data.put("password", password);
            data.put("phoneNumber", phoneNumber);
            data.put("birthDate", birthDate);
            data.put("gender", gender);
            data.put("role", role);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(data);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return "Registration successful!";
            } else {
                return "Error: " + response.body();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return "Connection Error";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterUI().setVisible(true));
    }
}

