package com.example.demo.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class GymHomePageUI extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GymHomePageUI();
            frame.setVisible(true);
        });
    }

    JPanel contentPanel;
    CardLayout cardLayout;
    private String selectedTrainer = "None";
    private List<String> selectedGroupClasses = new ArrayList<>();

    public GymHomePageUI() {

        setTitle("Gym Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);


        HomePage homePage = new HomePage(this);
        PersonalTrainersPage trainersPage = new PersonalTrainersPage(this);
        AccountPage accountPage = new AccountPage(this);
        GroupClassesPage groupClassesPage = new GroupClassesPage(this);

        contentPanel.add(homePage, "Home");
        contentPanel.add(trainersPage, "PersonalTrainers");
        contentPanel.add(new PlanPage(), "Plan");
        contentPanel.add(accountPage, "Account");
        contentPanel.add(groupClassesPage, "GroupClasses");

        // Alt menü oluşturma
        JPanel bottomMenu = new JPanel();
        bottomMenu.setLayout(new GridLayout(1, 3));

        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
        bottomMenu.add(homeButton);

        JButton planButton = new JButton("Plan");
        planButton.addActionListener(e -> cardLayout.show(contentPanel, "Plan"));
        bottomMenu.add(planButton);

        JButton accountButton = new JButton("Account");
        accountButton.addActionListener(e -> {
            accountPage.updateAccountInfo();
            cardLayout.show(contentPanel, "Account");
        });
        bottomMenu.add(accountButton);

        // Ana paneli ve alt menüyü ekle
        add(contentPanel, BorderLayout.CENTER);
        add(bottomMenu, BorderLayout.SOUTH);
    }

    public void setSelectedTrainer(String trainer) {
        this.selectedTrainer = trainer;
    }

    public String getSelectedTrainer() {
        return selectedTrainer;
    }

    public void addGroupClass(String groupClass) {
        if (!selectedGroupClasses.contains(groupClass)) {
            selectedGroupClasses.add(groupClass);
        }
    }

    public List<String> getSelectedGroupClasses() {
        return selectedGroupClasses;
    }
}

//HomePage sayfası yönetimi
class HomePage extends JPanel {
    public HomePage(GymHomePageUI parent) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Gym Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));

        JButton selectTrainerButton = new JButton("Select Personal Trainer");
        selectTrainerButton.addActionListener(e -> parent.cardLayout.show(parent.contentPanel, "PersonalTrainers"));

        JButton groupClassesButton = new JButton("Join Group Classes");
        groupClassesButton.addActionListener(e -> parent.cardLayout.show(parent.contentPanel, "GroupClasses"));

        JButton dietButton = new JButton("View Diet Plan");
        dietButton.addActionListener(e -> showDietPlan());

        infoPanel.add(selectTrainerButton);
        infoPanel.add(groupClassesButton);
        infoPanel.add(dietButton);


        add(infoPanel, BorderLayout.CENTER);
    }

    private void showDietPlan() {
        JOptionPane.showMessageDialog(this, "Here is your Diet Plan:\n1. Breakfast: Oats with fruits and 2 egg\n2. Lunch: Grilled chicken with veggies\n3. Dinner: Salmon with quinoa");
    }
}

// GroupClassesPage sınıfı
class GroupClassesPage extends JPanel {
    public GroupClassesPage(GymHomePageUI parent) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Join Group Classes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel classesPanel = new JPanel(new GridLayout(4, 1));

        JButton yogaButton = new JButton("Yoga Class");
        yogaButton.addActionListener(e -> {
            parent.addGroupClass("Yoga Class");
            JOptionPane.showMessageDialog(this, "You joined Yoga Class");
        });

        JButton pilatesButton = new JButton("Pilates Class");
        pilatesButton.addActionListener(e -> {
            parent.addGroupClass("Pilates Class");
            JOptionPane.showMessageDialog(this, "You joined Pilates Class");
        });

        JButton zumbaButton = new JButton("Zumba Class");
        zumbaButton.addActionListener(e -> {
            parent.addGroupClass("Zumba Class");
            JOptionPane.showMessageDialog(this, "You joined Zumba Class");
        });

        JButton spinningButton = new JButton("Spinning Class");
        spinningButton.addActionListener(e -> {
            parent.addGroupClass("Spinning Class");
            JOptionPane.showMessageDialog(this, "You joined Spinning Class");
        });

        classesPanel.add(yogaButton);
        classesPanel.add(pilatesButton);
        classesPanel.add(zumbaButton);
        classesPanel.add(spinningButton);

        add(classesPanel, BorderLayout.CENTER);
    }
}

//Account Page ile kullanıcının kendine ait bilgileri gördüğü kısım
class AccountPage extends JPanel {
    private JLabel trainerLabel;
    private JLabel groupClassesLabel;
    private JLabel remainingDaysLabel;

    public AccountPage(GymHomePageUI parent) {
        setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Account Information", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        trainerLabel = new JLabel("Selected Trainer: None", JLabel.CENTER);
        add(trainerLabel);

        groupClassesLabel = new JLabel("Group Classes: None", JLabel.CENTER);
        add(groupClassesLabel);

        remainingDaysLabel = new JLabel("Remaining Days: 0", JLabel.CENTER); // Başlangıçta 0 gün göster
        add(remainingDaysLabel);
    }

    public void updateAccountInfo() {
        GymHomePageUI parent = (GymHomePageUI) SwingUtilities.getWindowAncestor(this);
        trainerLabel.setText("Selected Trainer: " + parent.getSelectedTrainer());

        List<String> classes = parent.getSelectedGroupClasses();
        if (classes.isEmpty()) {
            groupClassesLabel.setText("Group Classes: None");
        } else {
            groupClassesLabel.setText("Group Classes: " + String.join(", ", classes));
        }
    }

    public void updateRemainingDays(int days) {
        remainingDaysLabel.setText("Remaining Days: " + days); // Gün sayısını buradan güncelle
    }
}



// PersonalTrainersPage sınıfı: Personal Trainer seçimi için sayfa
class PersonalTrainersPage extends JPanel {
    public PersonalTrainersPage(GymHomePageUI parent) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select Your Personal Trainer", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel trainerPanel = new JPanel(new GridLayout(4, 1));

        JButton trainerAButton = new JButton("Trainer A");
        trainerAButton.addActionListener(e -> {
            parent.setSelectedTrainer("Trainer A");
            JOptionPane.showMessageDialog(this, "You selected Trainer A");
            parent.cardLayout.show(parent.contentPanel, "Home");
        });

        JButton trainerBButton = new JButton("Trainer B");
        trainerBButton.addActionListener(e -> {
            parent.setSelectedTrainer("Trainer B");
            JOptionPane.showMessageDialog(this, "You selected Trainer B");
            parent.cardLayout.show(parent.contentPanel, "Home");
        });

        JButton trainerCButton = new JButton("Trainer C");
        trainerCButton.addActionListener(e -> {
            parent.setSelectedTrainer("Trainer C");
            JOptionPane.showMessageDialog(this, "You selected Trainer C");
            parent.cardLayout.show(parent.contentPanel, "Home");
        });

        trainerPanel.add(trainerAButton);
        trainerPanel.add(trainerBButton);
        trainerPanel.add(trainerCButton);

        add(trainerPanel, BorderLayout.CENTER);
    }
}

// PlanPage sınıfı - Plan Seçimi Yapıldığında Remaining Days Güncelleme !!Tam çalışmıyor kontrol edilmeli
class PlanPage extends JPanel {
    private GymHomePageUI parent;

    public PlanPage() {
        this.parent = parent;
        setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Choose Your Package", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        // Paket seçenekleri
        JButton oneMonthButton = new JButton("1 Month Plan: 30 Days Access");
        JButton threeMonthButton = new JButton("3 Months Plan: 120 Days + Personal Trainer");
        JButton sixMonthButton = new JButton("6 Months Plan: 240 Days + Personal Trainer + Diet Plan + Unlimited Classes");

        // Butonlar için aksiyonlar
        oneMonthButton.addActionListener(e -> selectPlan(30));
        threeMonthButton.addActionListener(e -> selectPlan(120));
        sixMonthButton.addActionListener(e -> selectPlan(240));

        add(oneMonthButton);
        add(threeMonthButton);
        add(sixMonthButton);
    }

    private void selectPlan(int days) {
        JOptionPane.showMessageDialog(this, "You selected a plan with " + days + " days.");
        updateRemainingDays(days);
        parent.cardLayout.show(parent.contentPanel, "Home");
    }

    private void updateRemainingDays(int days) {

        AccountPage accountPage = (AccountPage) parent.contentPanel.getComponent(3);
        accountPage.updateRemainingDays(days);
    }
}

