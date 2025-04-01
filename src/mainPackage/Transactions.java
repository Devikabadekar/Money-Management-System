package mainPackage;

import java.sql.*;
import java.awt.EventQueue;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;


import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Transactions {

	private JFrame frame;
	private JTextField txtDate;
	private JTextField txtItemname;
	private JTextField txtCategory;
	private JTextField txtSearch;
	private JTextField txtAmount;
	private static String loggedInEmail;
	private Stack<String[]> undoStack = new Stack<>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transactions window = new Transactions(loggedInEmail);
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
	public Transactions(String userEmail) {
	    Transactions.loggedInEmail = userEmail;
	    initialize();
	    buildConnection();
	    loadTable();
	}

	
	Connection con;
	PreparedStatement prestm;
	ResultSet rst;
	private JTable table;
	
	public void buildConnection(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MMSDatabase", "root", "newpass");
			System.out.println("Done with the stable connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
	public void loadTable() {
	    try {
	    	 prestm = con.prepareStatement("SELECT DATE, `ITEM NAME`, CATEGORY, AMOUNT FROM TransactionsTable WHERE EMAIL = ? ORDER BY DATE DESC");
	        prestm.setString(1, loggedInEmail);
	        rst = prestm.executeQuery();
	        

	        table.setModel(DbUtils.resultSetToTableModel(rst));
	        table.revalidate();
	        table.repaint();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	private void clearFields() {
	    txtDate.setText("");
	    txtItemname.setText("");
	    txtCategory.setText("");
	    txtAmount.setText("");
	    txtDate.requestFocus();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 255));
		frame.setBounds(100, 100, 902, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(69, 94, 275, 255);
		panel_1.setBackground(new Color(204, 204, 255));
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(51, 51, 51), new Color(51, 51, 51)), "Add Transactions ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Date :");
		lblNewLabel_1_2.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(18, 24, 123, 47);
		panel_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Item Name :");
		lblNewLabel_1_1_2.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
		lblNewLabel_1_1_2.setBounds(18, 63, 123, 47);
		panel_1.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Category :");
		lblNewLabel_1_1_1_2.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
		lblNewLabel_1_1_1_2.setBounds(18, 105, 130, 47);
		panel_1.add(lblNewLabel_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Amount :");
		lblNewLabel_1_1_1_2_1.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
		lblNewLabel_1_1_1_2_1.setBounds(18, 146, 130, 47);
		panel_1.add(lblNewLabel_1_1_1_2_1);
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtDate.setBounds(126, 35, 130, 26);
		panel_1.add(txtDate);
		
		txtItemname = new JTextField();
		txtItemname.setColumns(10);
		txtItemname.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtItemname.setBounds(126, 74, 130, 26);
		panel_1.add(txtItemname);
		
		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		txtCategory.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCategory.setBounds(126, 116, 130, 26);
		panel_1.add(txtCategory);
		
		
		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtAmount.setBounds(126, 154, 130, 26);
		panel_1.add(txtAmount);
		

		JButton btnAddT = new JButton("ADD");
		btnAddT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
						
						String date, itemname, category, amount;
						
						date = txtDate.getText();
						itemname = txtItemname.getText();
				         category = txtCategory.getText();
						amount = txtAmount.getText();
						
						try {
							prestm = con.prepareStatement("INSERT INTO TransactionsTable (`DATE`, `ITEM NAME`, CATEGORY, AMOUNT) VALUES (?, ?, ?, ?)");
							  
							    prestm.setString(1, date);
					            prestm.setString(2, itemname);
					            prestm.setString(3, category);
					            prestm.setString(4, amount);
					            
					            prestm.executeUpdate();
					            
					            undoStack.push(new String[]{"DELETE", date, itemname, category, amount});
					   
					            
					            JOptionPane.showMessageDialog(null, "Transaction Added Successfully!"); 
					            
					            loadTable();
					            
					            txtDate.setText("");
					            txtItemname.setText("");
					            txtCategory.setText("");
					            txtAmount.setText("");
					            
					            txtDate.requestFocus();
					            
						} catch (SQLException e1) {
							e1.printStackTrace();
				}
						
					   }
			
		});
	
		btnAddT.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnAddT.setBorder(UIManager.getBorder("Button.border"));
		btnAddT.setBounds(18, 205, 95, 33);
		panel_1.add(btnAddT);

		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		           txtDate.setText("");
		            txtItemname.setText("");
		            txtCategory.setText("");
		            txtAmount.setText("");
		            
		            txtDate.requestFocus();
			}
		});
		btnClear.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnClear.setBounds(148, 205, 95, 33);
		panel_1.add(btnClear);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(424, 90, 429, 454);
		panel_2.setBackground(new Color(102, 102, 153));
		frame.getContentPane().add(panel_2);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(102, 102, 153), new Color(102, 102, 153)), "All Transactions", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_2.setLayout(null);

	
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 22, 417, 414);
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(scrollPane_1);  

		// Table to display data
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setGridColor(new Color(8, 8, 8));
		scrollPane_1.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            txtDate.setText(table.getValueAt(selectedRow, 0).toString());
		            txtItemname.setText(table.getValueAt(selectedRow, 1).toString());
		            txtCategory.setText(table.getValueAt(selectedRow, 2).toString());
		            txtAmount.setText(table.getValueAt(selectedRow, 3).toString());
		        }
		    }
		});


		JLabel lblNewLabel = new JLabel("Money Management System");
		lblNewLabel.setBounds(235, 18, 421, 52);
		lblNewLabel.setFont(new Font("Lava Telugu", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(69, 373, 275, 137);
		panel_1_1.setBackground(new Color(204, 204, 255));
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(51, 51, 51), new Color(51, 51, 51)), "Search Transaction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Date :");
		lblNewLabel_1_2_1.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
		lblNewLabel_1_2_1.setBounds(18, 24, 123, 47);
		panel_1_1.add(lblNewLabel_1_2_1);
		
		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtSearch.setBounds(114, 35, 130, 26);
		panel_1_1.add(txtSearch);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String searchDate = txtSearch.getText();
		        try {
		            prestm = con.prepareStatement("SELECT DATE, `ITEM NAME`, CATEGORY, AMOUNT FROM TransactionsTable WHERE DATE = ?");
		            prestm.setString(1, searchDate);
		            rst = prestm.executeQuery();

		            // Check if the ResultSet has any data
		            if (!rst.isBeforeFirst()) { // isBeforeFirst() checks if the result set is empty
		            	 JOptionPane.showMessageDialog(null, "No Transaction done on this Date.");
		                return;
		            }

		            // Directly set the table model without calling next()
		            table.setModel(DbUtils.resultSetToTableModel(rst));

		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		});


		btnSearch.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnSearch.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnSearch.setBounds(18, 83, 95, 33);
		panel_1_1.add(btnSearch);
		
		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				        loadTable();
			}
		});
		btnRefresh.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnRefresh.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnRefresh.setBounds(149, 83, 95, 33);
		panel_1_1.add(btnRefresh);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(69, 534, 275, 100);
		panel_1_1_1.setBackground(new Color(204, 204, 255));
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(51, 51, 51), new Color(51, 51, 51)), "Modify Transaction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1_1_1);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Please select a transaction to update.");
		            return;
		        }

		        // Get old values (to identify the row in the database)
		        String oldDate = table.getValueAt(selectedRow, 0).toString();
		        String oldItemName = table.getValueAt(selectedRow, 1).toString();
		        String oldCategory = table.getValueAt(selectedRow, 2).toString();
		        String oldAmount = table.getValueAt(selectedRow, 3).toString();

		        // Get new values from the text fields
		        String newDate = txtDate.getText();
		        String newItemName = txtItemname.getText();
		        String newCategory = txtCategory.getText();
		        String newAmount = txtAmount.getText();

		        if (newDate.isEmpty() || newItemName.isEmpty() || newCategory.isEmpty() || newAmount.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "All fields must be filled out to update.");
		            return;
		        }

		        try {
		            String updateQuery = "UPDATE TransactionsTable SET `DATE` = ?, `ITEM NAME` = ?, CATEGORY = ?, AMOUNT = ? " +
		                                 "WHERE `DATE` = ? AND `ITEM NAME` = ? AND CATEGORY = ? AND AMOUNT = ?";
		            prestm = con.prepareStatement(updateQuery);

		            // Set new values
		            prestm.setString(1, newDate);
		            prestm.setString(2, newItemName);
		            prestm.setString(3, newCategory);
		            prestm.setString(4, newAmount);

		            // Set old values for identifying the correct row
		            prestm.setString(5, oldDate);
		            prestm.setString(6, oldItemName);
		            prestm.setString(7, oldCategory);
		            prestm.setString(8, oldAmount);

		            int rowsUpdated = prestm.executeUpdate();
		          

		            if (rowsUpdated > 0) {
		            	  undoStack.push(new String[]{"INSERT", oldDate, oldItemName, oldCategory, oldAmount});
		                JOptionPane.showMessageDialog(null, "Transaction Updated Successfully!");
		                loadTable();  // Refresh the table
		                clearFields();  // Clear input fields
		            } else {
		                JOptionPane.showMessageDialog(null, "Update failed. Please try again.");
		            }

		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		});


		btnUpdate.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnUpdate.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnUpdate.setBounds(17, 40, 95, 33);
		panel_1_1_1.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Please select a transaction to delete.");
		            return;
		        }

		        // Retrieve values from the selected row
		        String date = table.getValueAt(selectedRow, 0).toString();
		        String itemName = table.getValueAt(selectedRow, 1).toString();
		        String category = table.getValueAt(selectedRow, 2).toString();
		        double amount = Double.parseDouble(table.getValueAt(selectedRow, 3).toString()); // Convert to double

		        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this transaction?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
		        if (confirm == JOptionPane.YES_OPTION) {
		            try {
		                // Fix SQL query (removed extra placeholder)
		                prestm = con.prepareStatement("DELETE FROM TransactionsTable WHERE Date = ? AND `ITEM NAME` = ? AND CATEGORY = ? AND AMOUNT = ?");
		                prestm.setString(1, date);
		                prestm.setString(2, itemName);
		                prestm.setString(3, category);
		                prestm.setDouble(4, amount); // Use double for numeric field

		                int rowsDeleted = prestm.executeUpdate();

		                if (rowsDeleted > 0) {
		                    // Store deleted data for undo functionality
		                    undoStack.push(new String[]{"INSERT", date, itemName, category, String.valueOf(amount)});

		                    JOptionPane.showMessageDialog(null, "Transaction Deleted Successfully!");
		                    loadTable(); // Reload the table after deletion
		                } else {
		                    JOptionPane.showMessageDialog(null, "Delete failed. No matching record found.");
		                }

		                clearFields(); // Clear input fields

		            } catch (SQLException e1) {
		                e1.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Database error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});


		btnDelete.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		btnDelete.setBorder(UIManager.getBorder("ToggleButton.border"));
		btnDelete.setBounds(143, 40, 95, 33);
		panel_1_1_1.add(btnDelete);
		
		JButton btnNewButton = new JButton("VIEW BALANCE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   Balance balanceFrame = new Balance(); // Create an instance of Balance
			        balanceFrame.setVisible(true); 
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton.setBounds(571, 569, 171, 52);
		btnNewButton.setFont(new Font("Kohinoor Devanagari", Font.BOLD, 13));
		
		
	    ImageIcon icon = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/e.jpg");
        Image img = icon.getImage().getScaledInstance(39,40, Image.SCALE_SMOOTH);
        btnNewButton.setIcon(new ImageIcon(img));

		frame.getContentPane().add(btnNewButton);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setBorder(UIManager.getBorder("Button.border"));
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				        if (!undoStack.isEmpty()) {
				            String[] action = undoStack.pop();
				     
				            
				            if (action[0].equals("DELETE")) {
				                // Undo INSERT (Delete it)
				                executeQuery("DELETE FROM TransactionsTable WHERE `DATE` = ? AND `ITEM NAME` = ? AND CATEGORY = ? AND AMOUNT = ?", action, 1);
				            } else if (action[0].equals("INSERT")) {
				                // Undo DELETE (Re-insert it)
				                executeQuery("INSERT INTO TransactionsTable (`DATE`, `ITEM NAME`, CATEGORY, AMOUNT) VALUES (?, ?, ?, ?)", action, 1);
				            } else if (action[0].equals("UPDATE")) {
				                // Undo UPDATE (Revert old values)
				                executeQuery("UPDATE TransactionsTable SET `DATE` = ?, `ITEM NAME` = ?, CATEGORY = ?, AMOUNT = ? WHERE `DATE` = ? AND `ITEM NAME` = ? AND CATEGORY = ? AND AMOUNT = ?", action, 5);
				            }

				            loadTable();
				        }
				    }

			private void executeQuery(String query, String[] action, int offset) {
			    try {
			        PreparedStatement prestm = con.prepareStatement(query);
			        for (int i = 0; i < action.length - offset; i++) {
			            prestm.setString(i + 1, action[i + offset]); // Skip the first index (action type)
			        }
			        prestm.executeUpdate();
			    } catch (SQLException e) {
			        e.printStackTrace();
			        JOptionPane.showMessageDialog(null, "Error executing query: " + e.getMessage());
			    }
			}

				});
			
		btnUndo.setBounds(779, 6, 117, 29);
		frame.getContentPane().add(btnUndo);
		
		JButton btnUndo_1 = new JButton("Exit");
		btnUndo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			       frame.dispose();  

		
			        Homepage homePage = new Homepage(); 
			        homePage.setVisible(true);
			}
		});
		btnUndo_1.setBounds(779, 29, 117, 29);
		frame.getContentPane().add(btnUndo_1);
		
	}


	public void show() {
	    frame.setVisible(true);
	}

	public void setVisible(boolean b) {

        frame.setVisible(b);
	}
}
