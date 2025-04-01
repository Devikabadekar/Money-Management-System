package loginPackage;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup {

    private JFrame frame;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPass;

    public void SignupForm() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signup window = new Signup();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Signup() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 902, 703);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(47, 119, 175));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(389, 0, 513, 675);
        panel_2.setFont(new Font("Lava Telugu", Font.PLAIN, 13));
        panel_2.setBackground(new Color(47, 119, 175));
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(-44, 176, 551, 269);
        panel_2.add(lblNewLabel);

        ImageIcon icon = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/money.png");
        Image img = icon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        lblNewLabel.setIcon(new ImageIcon(img));

        JLabel lblWelcomeToMoney = new JLabel("Welcome To Money Management!");
        lblWelcomeToMoney.setBounds(34, 104, 473, 60);
        lblWelcomeToMoney.setForeground(new Color(255, 255, 255));
        lblWelcomeToMoney.setFont(new Font("Lava Telugu", Font.PLAIN, 30));
        panel_2.add(lblWelcomeToMoney);

        JLabel lblNewLabel_3 = new JLabel("Join us and take charge of your finances today!");
        lblNewLabel_3.setBounds(72, 442, 393, 34);
        panel_2.add(lblNewLabel_3);
        lblNewLabel_3.setForeground(new Color(13, 26, 57));
        lblNewLabel_3.setFont(new Font("Lava Telugu", Font.BOLD | Font.ITALIC, 17));

        JLabel lblNewLabel_1 = new JLabel("Money Management");
        lblNewLabel_1.setBounds(59, 30, 211, 41);
        lblNewLabel_1.setForeground(new Color(0, 0, 0));
        lblNewLabel_1.setFont(new Font("Lava Telugu", Font.BOLD, 19));
        panel.add(lblNewLabel_1);

        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBounds(33, 150, 311, 396);
        panel.add(panel_1_1);
        panel_1_1.setBackground(new Color(255, 255, 255));
        panel_1_1.setLayout(null);

        JLabel lblNewLabel_1_2_2 = new JLabel("Name ");
        lblNewLabel_1_2_2.setBounds(18, 66, 123, 47);
        lblNewLabel_1_2_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        panel_1_1.add(lblNewLabel_1_2_2);

        JLabel lblNewLabel_1_1_2 = new JLabel("Email");
        lblNewLabel_1_1_2.setBounds(18, 141, 123, 47);
        lblNewLabel_1_1_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        panel_1_1.add(lblNewLabel_1_1_2);

        txtName = new JTextField();
        txtName.setBounds(18, 103, 271, 26);
        txtName.setColumns(10);
        txtName.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1_1.add(txtName);

        txtEmail = new JTextField();
        txtEmail.setBounds(18, 181, 271, 26);
        txtEmail.setColumns(10);
        txtEmail.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1_1.add(txtEmail);

        JLabel lblSignUp = new JLabel("Sign Up ");
        lblSignUp.setForeground(new Color(13, 26, 57));
        lblSignUp.setBounds(103, 16, 101, 60);
        panel_1_1.add(lblSignUp);
        lblSignUp.setFont(new Font("Lava Telugu", Font.BOLD, 23));

        JLabel lblNewLabel_1_1_2_1 = new JLabel("Password ");
        lblNewLabel_1_1_2_1.setBounds(18, 221, 123, 47);
        lblNewLabel_1_1_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        panel_1_1.add(lblNewLabel_1_1_2_1);

        txtPass = new JTextField();
        txtPass.setBounds(18, 261, 271, 26);
        txtPass.setColumns(10);
        txtPass.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1_1.add(txtPass);

        JButton btnNewButton = new JButton("Sign up");
        btnNewButton.setBounds(47, 314, 215, 33);
        btnNewButton.setOpaque(true);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setBackground(new Color(51, 153, 204)); // Blue color
        btnNewButton.setForeground(Color.WHITE);
        panel_1_1.add(btnNewButton);

            	btnNewButton.addActionListener(new ActionListener() {
            	    public void actionPerformed(ActionEvent e) {
            	        String name = txtName.getText().trim();
            	        String email = txtEmail.getText().trim();
            	        String password = txtPass.getText().trim();

            	        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            	            JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            	            return;
            	        }

            	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MMSdatabase", "root", "newpass")) {
            	            
            	            // Insert into SIGNUPTABLE
            	            String signupQuery = "INSERT INTO SIGNUPTABLE (NAME, EMAIL, PASSWORD) VALUES (?, ?, ?)";
            	            PreparedStatement stmtSignup = conn.prepareStatement(signupQuery);
            	            stmtSignup.setString(1, name);
            	            stmtSignup.setString(2, email);
            	            stmtSignup.setString(3, password);

            	            int signupInserted = stmtSignup.executeUpdate();
            	            stmtSignup.close();

            	            // Insert into LoginTable as well
            	            String loginQuery = "INSERT INTO LoginTable (EMAIL, PASSWORD) VALUES (?, ?)";
            	            PreparedStatement stmtLogin = conn.prepareStatement(loginQuery);
            	            stmtLogin.setString(1, email);
            	            stmtLogin.setString(2, password);

            	            int loginInserted = stmtLogin.executeUpdate();
            	            stmtLogin.close();

            	            if (signupInserted > 0 && loginInserted > 0) {
            	                JOptionPane.showMessageDialog(null, "Sign Up Successful! You can now log in.");

            	                // Redirect to Login.java
            	                LoginDesign loginWindow = new LoginDesign();
            	                loginWindow.LoginForm();

            	                frame.dispose();  
            	            } else {
            	                JOptionPane.showMessageDialog(null, "Sign Up Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            	            }

            	        } catch (SQLException e1) {
            	            e1.printStackTrace();
            	        }
            	    }
            	});


        btnNewButton.setFont(new Font("Lava Devanagari", Font.BOLD, 13));
        btnNewButton.setBorder(UIManager.getBorder("ToggleButton.border"));
        btnNewButton.setBackground(new Color(0, 51, 102));

        JCheckBox chckbxNewCheckBox = new JCheckBox("Remember me");
        chckbxNewCheckBox.setBounds(18, 359, 128, 23);
        panel_1_1.add(chckbxNewCheckBox);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(40, 38, 32, 20);
        panel.add(lblNewLabel_2);
        ImageIcon icon1 = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/logo.png");
		Image img1 = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		lblNewLabel_2.setIcon(new ImageIcon(img1));
        
    }
}
