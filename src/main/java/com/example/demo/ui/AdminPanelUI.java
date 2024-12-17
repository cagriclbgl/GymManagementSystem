package com.example.demo.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminPanelUI extends JFrame {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JPanel buttonPanel, crudButtonPanel;
    private String currentEntity = "users"; // Varsayılan tablo
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminPanelUI() {
        setTitle("Admin Panel");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Üstteki veri yönetim butonları
        buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton userButton = new JButton("Manage Users");
        JButton trainerButton = new JButton("Manage Trainers");
        JButton courseButton = new JButton("Manage Courses");

        buttonPanel.add(userButton);
        buttonPanel.add(trainerButton);
        buttonPanel.add(courseButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Veri tablosu
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Alt kısımda CRUD işlemleri butonları
        crudButtonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        crudButtonPanel.add(addButton);
        crudButtonPanel.add(updateButton);
        crudButtonPanel.add(deleteButton);
        add(crudButtonPanel, BorderLayout.SOUTH);

        // Buton olayları
        userButton.addActionListener(e -> loadData("users/getusers"));
        trainerButton.addActionListener(e -> loadData("trainers/gettrainers"));
        courseButton.addActionListener(e -> loadData("courses"));

        addButton.addActionListener(e -> showAddForm());
        updateButton.addActionListener(e -> showUpdateForm());
        deleteButton.addActionListener(e -> deleteData());
    }

    // Veriyi yükleme
    private void loadData(String entity) {
        currentEntity = entity.split("/")[0]; // "users", "trainers" veya "courses"
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        String apiUrl = "http://localhost:8080/api/" + entity;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Map<String, Object>> dataList = objectMapper.readValue(response.body(),
                        new TypeReference<List<Map<String, Object>>>() {});
                if (!dataList.isEmpty()) {
                    Vector<String> columnNames = new Vector<>(dataList.get(0).keySet());
                    tableModel.setColumnIdentifiers(columnNames);
                    for (Map<String, Object> row : dataList) {
                        tableModel.addRow(new Vector<>(row.values()));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load " + currentEntity + " data.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Ekleme formu
    private void showAddForm() {
        JTextField[] fields = createFormFieldsWithoutId(); // ID'siz form oluştur
        int result = JOptionPane.showConfirmDialog(this, fields, "Add " + currentEntity,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            performPostRequest(fields);
        }
    }

    // Güncelleme formu
    private void showUpdateForm() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        JTextField[] fields = createFormFieldsWithValues(selectedRow);
        int result = JOptionPane.showConfirmDialog(this, fields, "Update " + currentEntity,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            performPutRequest(fields, selectedRow);
        }
    }

    // Silme işlemi
    private void deleteData() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        String id = dataTable.getValueAt(selectedRow, 0).toString(); // ID sütunu
        String apiUrl = getDeleteApiUrl(id);

        if (apiUrl == null) {
            JOptionPane.showMessageDialog(this, "Invalid entity for DELETE operation.");
            return;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                JOptionPane.showMessageDialog(this, "Deleted successfully!");
                loadData(currentEntity + "/get" + currentEntity);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // POST isteği (Ekleme)
    private void performPostRequest(JTextField[] fields) {
        String apiUrl = getPostApiUrl();
        if (apiUrl == null) {
            JOptionPane.showMessageDialog(this, "Invalid entity for POST operation.");
            return;
        }
        sendDataWithoutId(fields, apiUrl, "POST"); // ID'siz veri gönderimi
    }

    // PUT isteği (Güncelleme)
    private void performPutRequest(JTextField[] fields, int selectedRow) {
        String id = dataTable.getValueAt(selectedRow, 0).toString(); // ID al
        String apiUrl = getPutApiUrl(id);
        if (apiUrl == null) {
            JOptionPane.showMessageDialog(this, "Invalid entity for PUT operation.");
            return;
        }
        sendData(fields, apiUrl, "PUT");
    }

    private void sendDataWithoutId(JTextField[] fields, String apiUrl, String method) {
        try {
            Map<String, String> formData = new HashMap<>();
            for (int i = 1; i < tableModel.getColumnCount(); i++) { // 1'den başlıyoruz, çünkü ID hariç tutuldu
                formData.put(tableModel.getColumnName(i), fields[i - 1].getText());
            }

            String jsonBody = objectMapper.writeValueAsString(formData);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json");

            if (method.equals("POST")) {
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
            }

            HttpResponse<String> response = client.send(requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Add successful!");
                loadData(currentEntity + "/get" + currentEntity);
            } else {
                JOptionPane.showMessageDialog(this, "Add failed: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendData(JTextField[] fields, String apiUrl, String method) {
        try {
            Map<String, String> formData = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                formData.put(tableModel.getColumnName(i), fields[i].getText());
            }

            String jsonBody = objectMapper.writeValueAsString(formData);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json");

            if (method.equals("PUT")) {
                requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
            }

            HttpResponse<String> response = client.send(requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Update successful!");
                loadData(currentEntity + "/get" + currentEntity);
            } else {
                JOptionPane.showMessageDialog(this, "Update failed: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Dinamik form alanları oluşturma (ID'siz)
    private JTextField[] createFormFieldsWithoutId() {
        int columnCount = tableModel.getColumnCount() - 1;
        JTextField[] fields = new JTextField[columnCount];
        for (int i = 1; i < tableModel.getColumnCount(); i++) { // 1'den başlıyoruz, çünkü ID hariç tutuldu
            fields[i - 1] = new JTextField();
            fields[i - 1].setBorder(BorderFactory.createTitledBorder(tableModel.getColumnName(i)));
        }
        return fields;
    }

    private JTextField[] createFormFieldsWithValues(int selectedRow) {
        int columnCount = tableModel.getColumnCount();
        JTextField[] fields = new JTextField[columnCount];
        for (int i = 0; i < columnCount; i++) {
            fields[i] = new JTextField(dataTable.getValueAt(selectedRow, i).toString());
            fields[i].setBorder(BorderFactory.createTitledBorder(tableModel.getColumnName(i)));
        }
        return fields;
    }

    private String getPostApiUrl() {
        switch (currentEntity) {
            case "users":
                return "http://localhost:8080/api/users/register";
            case "trainers":
                return "http://localhost:8080/api/trainers/addtrainers";
            case "courses":
                return "http://localhost:8080/api/courses";
            default:
                return null;
        }
    }

    private String getPutApiUrl(String id) {
        return "http://localhost:8080/api/" + currentEntity + "/" + id;
    }

    private String getDeleteApiUrl(String id) {
        return "http://localhost:8080/api/" + currentEntity + "/" + id;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanelUI().setVisible(true));
    }
}
