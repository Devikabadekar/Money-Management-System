package mainPackage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import loginPackage.LoginDesign;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Profile {

    private JFrame frame;
    private JLabel lblGreeting, lblEmail, lblProfilePic;
    private JButton btnChangePassword, btnEditProfile, btnViewTransactions, btnLogout;
    private JPanel profilePanel;
    private String name = "";
    private String email = ""; 
    private JTable dummyTable;



  


    public Profile(String email) {
        this.email = email;
        fetchUserDetails();
        initialize();
        loadProfilePic();
        loadMonthlyTable();
        frame.setVisible(true);
        frame.setVisible(true);

    }
 // Function to display monthly analysis


    
    private void fetchUserDetails() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass");
            PreparedStatement ps = con.prepareStatement("SELECT NAME, EMAIL FROM SignupTable WHERE EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("NAME");
                System.out.println("User Found: " + name);
            } else {
                System.out.println("User not found in DB for email: " + email);
                JOptionPane.showMessageDialog(null, "User not found!");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void loadProfilePic() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass")) {
            PreparedStatement ps = con.prepareStatement("SELECT IMG FROM ProfileTable WHERE EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("IMG");
                if (imgBytes != null) {
                    ImageIcon icon = new ImageIcon(imgBytes);
                    Image img = icon.getImage().getScaledInstance(lblProfilePic.getWidth(), lblProfilePic.getHeight(), Image.SCALE_SMOOTH);
                    lblProfilePic.setIcon(new ImageIcon(img));
                    lblProfilePic.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame("My Profile - Money Management System");
        frame.setTitle("");
        frame.setBounds(100, 100, 920, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(99, 35, 65));
        frame.getContentPane().setLayout(null);

        // Main Profile Panel
        profilePanel = new JPanel();
        profilePanel.setBounds(50, 50, 827, 588);
        profilePanel.setBackground(new Color(255, 204, 255));
        profilePanel.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));
        profilePanel.setLayout(null);
        frame.getContentPane().add(profilePanel);

        // Profile Picture Placeholder
        lblProfilePic = new JLabel("Profile Pic");
        lblProfilePic.setBounds(52, 42, 138, 134);
        lblProfilePic.setOpaque(true);
        lblProfilePic.setBackground(Color.LIGHT_GRAY);
        lblProfilePic.setHorizontalAlignment(SwingConstants.CENTER);
        lblProfilePic.setFont(new Font("Arial", Font.BOLD, 14));
        lblProfilePic.setBorder(new LineBorder(Color.DARK_GRAY));
        profilePanel.add(lblProfilePic);
        

        lblProfilePic.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (FileInputStream fis = new FileInputStream(selectedFile)) {
                        byte[] imageBytes = fis.readAllBytes();
                        saveProfilePicToDB(imageBytes);
                        ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(lblProfilePic.getWidth(), lblProfilePic.getHeight(), Image.SCALE_SMOOTH);
                        lblProfilePic.setIcon(new ImageIcon(img));
                        lblProfilePic.setText("");
                        JOptionPane.showMessageDialog(null, "Profile picture updated successfully!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        // Greeting
        lblGreeting = new JLabel("Hey, " + name + "!");
        lblGreeting.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblGreeting.setForeground(new Color(60, 60, 60));
        lblGreeting.setBounds(220, 66, 400, 40);
        profilePanel.add(lblGreeting);

        // Email
        lblEmail = new JLabel("Email: " + email);
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblEmail.setBounds(220, 118, 400, 30);
        profilePanel.add(lblEmail);


        // Action Buttons
        btnEditProfile = createButton("Edit Profile", new Color(52, 152, 219), 30, 200);
        profilePanel.add(btnEditProfile);

        btnChangePassword = createButton("Change Password", new Color(155, 89, 182), 220, 200);
        profilePanel.add(btnChangePassword);

        btnViewTransactions = createButton("View Transactions", new Color(46, 204, 113), 420, 200);
        profilePanel.add(btnViewTransactions);

        btnLogout = createButton("Logout", new Color(231, 76, 60), 630, 200);
        profilePanel.add(btnLogout);

        // Footer / Table placeholder
     // Initialize dummyTable as a class-level variable
        dummyTable = new JTable(new DefaultTableModel(new Object[]{"MONTH", "INCOME", "EXPENSES"}, 0));
        JScrollPane tableScroll = new JScrollPane(dummyTable);
        tableScroll.setBackground(new Color(153, 51, 102));
        tableScroll.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Montly Expenses", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        tableScroll.setBounds(41, 303, 747, 249);
        profilePanel.add(tableScroll);
        
        JButton btn = new JButton("Change Password");
        btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                changePassword();
            
        	}
        });
        btn.setForeground(new Color(0, 0, 0));
        btn.setFont(new Font("Dialog", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 152, 219));
        btn.setBounds(231, 230, 160, 35);
        profilePanel.add(btn);

        JButton btn_1 = new JButton("Dashboard");
        btn_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide current panel (Profile)
                profilePanel.setVisible(false);
                  switchToHomePage();
            }
        });

        btn_1.setForeground(new Color(0, 0, 0));
        btn_1.setFont(new Font("Dialog", Font.BOLD, 14));
        btn_1.setFocusPainted(false);
        btn_1.setBackground(new Color(52, 152, 219));
        btn_1.setBounds(430, 230, 160, 35);
        profilePanel.add(btn_1);
        
        JButton btn_1_1 = new JButton("Logout");
        btn_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              email = "";
                frame.dispose();
                LoginDesign loginPage = new LoginDesign();
                loginPage.setVisible(true);  
            }
        });
        btn_1_1.setForeground(new Color(0, 0, 0));
        btn_1_1.setFont(new Font("Dialog", Font.BOLD, 14));
        btn_1_1.setFocusPainted(false);
        btn_1_1.setBackground(new Color(52, 152, 219));
        btn_1_1.setBounds(636, 234, 160, 35);
        profilePanel.add(btn_1_1);
    }


private void switchToHomePage() {
 
    frame.dispose();

    Homepage homePage = new Homepage(); 

    homePage.setVisible(true);


    frame.revalidate();
    frame.repaint();
}

    private void loadMonthlyTable() {
        PreparedStatement prestm = null;
        ResultSet rst = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass");
            if (con == null || con.isClosed()) {
                JOptionPane.showMessageDialog(frame, "Database connection not available!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT MONTH, INCOME, EXPENSES FROM MonthlyTable";
            prestm = con.prepareStatement(query);
            rst = prestm.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"MONTH", "INCOME", "EXPENSES"});

            while (rst.next()) {
                String month = rst.getString("MONTH");
                double income = rst.getDouble("INCOME");
                double expenses = rst.getDouble("EXPENSES");
                model.addRow(new Object[]{month, income, expenses});
            }

            if (dummyTable != null) {
                dummyTable.setModel(model);
            } else {
                JOptionPane.showMessageDialog(frame, "Error: Table not initialized!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading monthly data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rst != null) rst.close();
                if (prestm != null) prestm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    private void changePassword() {
        // Prompt user for current password
        String currentPassword = JOptionPane.showInputDialog(frame, "Enter current password:");

        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Password change cancelled.");
            return;
        }

        // Verify current password from database
        if (!verifyCurrentPassword(currentPassword)) {
            JOptionPane.showMessageDialog(frame, "Incorrect current password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prompt user for new password
        String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
        if (newPassword == null || newPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm new password
        String confirmPassword = JOptionPane.showInputDialog(frame, "Confirm new password:");
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update password in database
        if (updatePasswordInDatabase(newPassword)) {
            JOptionPane.showMessageDialog(frame, "Password changed successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to change password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verifyCurrentPassword(String currentPassword) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass");
            PreparedStatement ps = con.prepareStatement("SELECT PASSWORD FROM LoginTable WHERE EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("PASSWORD");
                System.out.println("Stored Password: " + storedPassword);
                System.out.println("Entered Password: " + currentPassword);
                con.close();
                return storedPassword.equals(currentPassword);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Function to update the password in the database
    private boolean updatePasswordInDatabase(String newPassword) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass");

            // Update password in SignupTable
            PreparedStatement ps1 = con.prepareStatement("UPDATE LoginTable SET PASSWORD = ? WHERE EMAIL = ?");
            ps1.setString(1, newPassword);
            ps1.setString(2, email);
            int rowsUpdated1 = ps1.executeUpdate();

            // Update password in LoginTable
            PreparedStatement ps2 = con.prepareStatement("UPDATE LoginTable SET PASSWORD = ? WHERE EMAIL = ?");
            ps2.setString(1, newPassword);
            ps2.setString(2, email);
            int rowsUpdated2 = ps2.executeUpdate();

            con.close();
            return rowsUpdated1 > 0 && rowsUpdated2 > 0; // Returns true if both tables updated successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private JButton createButton(String text, Color bgColor, int x, int y) {
        JButton btn = new JButton(text);
        btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btn.setBounds(27, 230, 160, 35);
        btn.setBackground(bgColor);
        btn.setForeground(new Color(0, 0, 0));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        
        if (text.equalsIgnoreCase("Edit Profile")) {
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editProfile();
                }
            });
        }
        return btn;
    }

 // Function to handle profile editing
    private void editProfile() {
        // Prompt user for a new name
        String newName = JOptionPane.showInputDialog(frame, "Enter new name:", name);

        if (newName != null && !newName.trim().isEmpty()) {
            // Update name in database
            if (updateNameInDatabase(newName)) {
                name = newName; // Update local variable
                lblGreeting.setText("Hey, " + name + " 👋"); // Update UI
                JOptionPane.showMessageDialog(frame, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update profile!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Function to update the name in the database
    private boolean updateNameInDatabase(String newName) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass");
            PreparedStatement ps = con.prepareStatement("UPDATE SignupTable SET NAME = ? WHERE EMAIL = ?");
            ps.setString(1, newName);
            ps.setString(2, email);  // Ensure 'email' is declared and holds the logged-in user's email
            int rowsUpdated = ps.executeUpdate();
            con.close();

            return rowsUpdated > 0; // Returns true if update was successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void saveProfilePicToDB(byte[] imageBytes) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MMSDatabase", "root", "newpass")) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ProfileTable (EMAIL, IMG) VALUES (?, ?) ON DUPLICATE KEY UPDATE IMG = ?");
            ps.setString(1, email);
            ps.setBytes(2, imageBytes);
            ps.setBytes(3, imageBytes);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Profile picture saved successfully!");
            } else {
                System.out.println("Profile picture update failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
    }

