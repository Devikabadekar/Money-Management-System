package loginPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import mainPackage.Homepage;





public class LoginDesign {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
    private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDesign window = new LoginDesign();
					window.frame.setVisible(true);
				

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginDesign() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 102, 153));
		frame.setBounds(100, 100, 902, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Money Management");
		lblNewLabel_1_1.setBounds(61, 30, 211, 41);
		lblNewLabel_1_1.setFont(new Font("Lava Telugu", Font.BOLD, 19));
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(484, 142, 325, 393);
		panel_1_1.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Email ");
		lblNewLabel_1_2_1.setBounds(18, 83, 123, 47);
		lblNewLabel_1_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel_1_1.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Password ");
		lblNewLabel_1_1_2_1.setBounds(18, 163, 123, 47);
		lblNewLabel_1_1_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel_1_1.add(lblNewLabel_1_1_2_1);
		
		    passwordField = new JPasswordField();
	        passwordField.setBounds(18, 203, 250, 26);
	        passwordField.setFont(new Font("Lava Telugu", Font.PLAIN, 13));
	        passwordField.setEchoChar('•');
	        passwordField.setBorder(new LineBorder(new Color(0, 0, 0)));
	        panel_1_1.add(passwordField);

	        JToggleButton tglbtnNewToggleButton = new JToggleButton("👁");
	        tglbtnNewToggleButton.setBounds(270, 203, 35, 26);
	        panel_1_1.add(tglbtnNewToggleButton);

	        tglbtnNewToggleButton.addActionListener(new ActionListener() {
	            private boolean isPasswordVisible = false;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (isPasswordVisible) {
	                    passwordField.setEchoChar('•');
	                    tglbtnNewToggleButton.setText("👁");
	                } else {
	                    passwordField.setEchoChar((char) 0);
	                    tglbtnNewToggleButton.setText("🙈");
	                }
	                isPasswordVisible = !isPasswordVisible;
	            }
	        });

		
		
		textField = new JTextField();
		textField.setBounds(18, 125, 289, 26);
		textField.setColumns(10);
		textField.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.add(textField);
		
		JButton btnNewButton_2 = new JButton("Sign in");
		btnNewButton_2.setBounds(36, 266, 251, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String email = textField.getText();
		        String password = String.valueOf(passwordField.getPassword());  // ✅ Fixed line

		        try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mmsdatabase", "root", "newpass");
		            String query = "SELECT * FROM LoginTable WHERE email = ? AND password = ?";
		            PreparedStatement pst = conn.prepareStatement(query);
		            pst.setString(1, email);
		            pst.setString(2, password);

		            ResultSet rs = pst.executeQuery();

		            if (rs.next()) {
		                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
		                frame.dispose(); // Close login window
		            
						Homepage homepage = new Homepage(email);
						homepage.setVisible(true);
		            }
 else {
		                JOptionPane.showMessageDialog(frame, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
		            }

		            rs.close();
		            pst.close();
		            conn.close();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		btnNewButton_2.setOpaque(true);
		btnNewButton_2.setBorderPainted(false); 
		btnNewButton_2.setBackground(new Color(92, 0, 153)); 
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		panel_1_1.add(btnNewButton_2);
		
		
		JLabel lblNewLabel_1 = new JLabel("Login To Your Account");
		lblNewLabel_1.setBounds(18, 22, 332, 65);
		lblNewLabel_1.setFont(new Font("Lava Telugu", Font.BOLD, 25));
		panel_1_1.add(lblNewLabel_1);
		
		
		JButton btnNewButton_1_1 = new JButton("Sign up");
		btnNewButton_1_1.setBounds(192, 325, 77, 33);
		btnNewButton_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose(); 
		        Signup signupWindow = new Signup();
		        signupWindow.SignupForm();
		    }
		});
		btnNewButton_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));

		
		panel_1_1.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("Forgot Password?");
		lblNewLabel_4.setBounds(184, 227, 123, 26);
		lblNewLabel_4.setForeground(Color.BLUE);
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1_1.add(lblNewLabel_4);
		
       
		textField_1 = new JTextField();
		textField_1.setBounds(18, 203, 289, 26);
		panel_1_1.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        String email = JOptionPane.showInputDialog(null, "Enter your registered email:", "Forgot Password", JOptionPane.QUESTION_MESSAGE);

		        if (email != null && !email.trim().isEmpty()) {
		            try {
		                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mmsdatabase", "root", "newpass");

		                // Check if the email exists in the database
		                String checkQuery = "SELECT email FROM LoginTable WHERE email = ?";
		                PreparedStatement checkPst = conn.prepareStatement(checkQuery);
		                checkPst.setString(1, email);
		                ResultSet rs = checkPst.executeQuery();

		                if (rs.next()) { // If email exists
		                    String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:", "Reset Password", JOptionPane.QUESTION_MESSAGE);

		                    if (newPassword != null && !newPassword.trim().isEmpty()) {
		                        // Update password for the given email
		                        String updateQuery = "UPDATE LoginTable SET password = ? WHERE email = ?";
		                        PreparedStatement updatePst = conn.prepareStatement(updateQuery);
		                        updatePst.setString(1, newPassword);
		                        updatePst.setString(2, email);

		                        int updated = updatePst.executeUpdate();
		                        if (updated > 0) {
		                            JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		                        } else {
		                            JOptionPane.showMessageDialog(null, "Password update failed!", "Error", JOptionPane.ERROR_MESSAGE);
		                        }

		                        updatePst.close();
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(null, "Email not found!", "Error", JOptionPane.ERROR_MESSAGE);
		                }

		                rs.close();
		                checkPst.close();
		                conn.close();
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});


		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(41, 38, 20, 20);
		frame.getContentPane().add(lblNewLabel);
		
		ImageIcon icon1 = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/logo.png");
		Image img1 = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(img1));
		
		JLabel lblNewLabel_2 = new JLabel("Welcome");
		lblNewLabel_2.setBounds(78, 181, 155, 41);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Lava Telugu", Font.PLAIN, 35));
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("Back To Your");
		lblNewLabel_2_2.setBounds(78, 222, 281, 41);
		lblNewLabel_2_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2_2.setFont(new Font("Lava Telugu", Font.PLAIN, 30));
		frame.getContentPane().add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Money Manager!");
		lblNewLabel_2_3.setBounds(78, 262, 296, 41);
		lblNewLabel_2_3.setForeground(Color.WHITE);
		lblNewLabel_2_3.setFont(new Font("Lava Telugu", Font.PLAIN, 35));
		frame.getContentPane().add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(-17, 281, 289, 254);
		frame.getContentPane().add(lblNewLabel_3);
		
		ImageIcon icon = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/coin.png");
		Image img = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
		lblNewLabel_3.setIcon(new ImageIcon(img));
		
	

        frame.setVisible(true);
    }


	public void LoginForm() {
	    frame.setVisible(true);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}