package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Homepage {
    private JFrame frame;
    
 // In Homepage.java
    private String userEmail; // store it in class variable

    public Homepage(String email) {
        this.userEmail = email;  // now you have userEmail stored
        initialize();
    }


    public Homepage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Money Management System");
        frame.setTitle("");
        frame.setSize(902, 703);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245));
        frame.getContentPane().setLayout(null);

        // Profile Section
        JPanel profilePanel = new JPanel();
        profilePanel.setBounds(20, 28, 850, 80);
        profilePanel.setBackground(new Color(70, 130, 180));
        frame.getContentPane().add(profilePanel);
        profilePanel.setLayout(null);
        
        JLabel profileIcon = new JLabel(new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/profile_icon.png"));
        profileIcon.setBounds(20, 15, 50, 50);
        profilePanel.add(profileIcon);
        
        JLabel lblMoneyManagementSydashboard = new JLabel("Money Management Dashboard");
        lblMoneyManagementSydashboard.setForeground(new Color(255, 255, 255));
        lblMoneyManagementSydashboard.setBounds(237, 15, 391, 52);
        profilePanel.add(lblMoneyManagementSydashboard);
        lblMoneyManagementSydashboard.setFont(new Font("Lava Telugu", Font.PLAIN, 27));
        
        // Navigation Panel
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(0, 0, 51));
        navPanel.setBounds(20, 151, 850, 484);
        navPanel.setLayout(null);
        frame.getContentPane().add(navPanel);

        // Buttons with Icons
        JButton transactionsBtn = createNavButton("Transactions", "/Users/devikabadekar/eclipse-workspace/MMS/src/Images/t1.jpeg", 22, 16);
        JButton balanceBtn = createNavButton("Balance", "/Users/devikabadekar/eclipse-workspace/MMS/src/Images/b.jpg", 439, 16);
        JButton analyticsBtn = createNavButton("Analytics", "/Users/devikabadekar/eclipse-workspace/MMS/src/Images/ai.jpg", 22, 250);
        JButton profileBtn = createNavButton("My Profile", "/Users/devikabadekar/eclipse-workspace/MMS/src/Images/p1.jpg", 439, 250);

        // Action Listeners
        transactionsBtn .addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); 
                Transactions transactionspage = new Transactions(userEmail);
                transactionspage.setVisible(true); // open transactions page
            }
        });

        balanceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Balance();
            }
        });

        analyticsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Analysis analysis = new Analysis();
                analysis.setVisible(true);
            }
        });

        profileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Profile profilePage = new Profile(userEmail);
                profilePage.setVisible(true);
 
            }
        });

        // Add buttons to navigation panel
        navPanel.add(transactionsBtn);
        navPanel.add(balanceBtn);
        navPanel.add(analyticsBtn);
        navPanel.add(profileBtn);
        
        frame.setVisible(true);
    }

    private JButton createNavButton(String text, String imagePath, int x, int y) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);

        JButton button = new JButton(text.toUpperCase(), new ImageIcon(img));
        button.setForeground(Color.WHITE);
        button.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), text.toUpperCase(), TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
        button.setBounds(x, y, 392, 205);

        return button;
    }

    public static void main(String[] args) {
        new Homepage();
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}


