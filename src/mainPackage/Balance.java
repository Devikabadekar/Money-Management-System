package mainPackage;

import java.sql.*;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import net.proteanit.sql.DbUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Balance {
    private JFrame frame;
    private JTable table;
    private Connection con;
    private PreparedStatement prestm;
    private ResultSet rst;
    private JTextField txtIncome, txtExpense, txtBalance, txtSavings, inputIncome, inputMonth;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Balance window = new Balance();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Balance() {
        initialize();
        buildConnection();
        frame.setVisible(true);
    }

    private void buildConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MMSDatabase", "root", "newpass");
            System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTable(String month) {
        try {
            prestm = con.prepareStatement("SELECT * FROM TransactionsTable WHERE DATE_FORMAT(Date, '%Y-%m') = ?");
            prestm.setString(1, month);
            rst = prestm.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFullTable() {
        try {
            prestm = con.prepareStatement("SELECT * FROM TransactionsTable");
            rst = prestm.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame("Financial Dashboard");
        frame.getContentPane().setBackground(new Color(204, 153, 153));
        frame.setTitle("");
        frame.setBounds(100, 100, 902, 703);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(102, 51, 51), new Color(102, 51, 51)), "Enter Total Income & Month (YYYY-MM)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        inputPanel.setBounds(20, 164, 400, 80);
        inputPanel.setBackground(new Color(204, 153, 153));
        frame.getContentPane().add(inputPanel);
        inputPanel.setLayout(new GridLayout(1, 3));

        inputIncome = new JTextField();
        inputIncome.setBorder(new LineBorder(new Color(0, 0, 0)));
        inputMonth = new JTextField();
        inputMonth.setBorder(new LineBorder(new Color(0, 0, 0)));
        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        inputPanel.add(inputIncome);
        inputPanel.add(inputMonth);
        inputPanel.add(btnCalculate);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 51, 51), new Color(102, 51, 51)), "Financial Overview", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(455, 93, 413, 253);
        panel.setBackground(new Color(204, 153, 153));
        frame.getContentPane().add(panel);

        JLabel lblIncome = new JLabel("Total Income:");
        lblIncome.setBounds(6, 19, 194, 56);
        lblIncome.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
        txtIncome = new JTextField();
        txtIncome.setBounds(200, 21, 194, 44);
        txtIncome.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtIncome.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        txtIncome.setEditable(false);
        panel.setLayout(null);
        panel.add(lblIncome);
        panel.add(txtIncome);

        JLabel lblExpense = new JLabel("Total Expenses:");
        lblExpense.setBounds(6, 75, 194, 56);
        lblExpense.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
        txtExpense = new JTextField();
        txtExpense.setBounds(200, 77, 194, 44);
        txtExpense.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtExpense.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        txtExpense.setEditable(false);
        panel.add(lblExpense);
        panel.add(txtExpense);

        JLabel lblBalance = new JLabel("Balance:");
        lblBalance.setBounds(6, 131, 194, 56);
        lblBalance.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
        txtBalance = new JTextField();
        txtBalance.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtBalance.setBounds(200, 131, 194, 44);
        txtBalance.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        txtBalance.setEditable(false);
        panel.add(lblBalance);
        panel.add(txtBalance);

        JLabel lblSavings = new JLabel("Savings (%):");
        lblSavings.setBounds(6, 187, 194, 56);
        lblSavings.setFont(new Font("Lava Devanagari", Font.BOLD, 16));
        txtSavings = new JTextField();
        txtSavings.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtSavings.setBounds(200, 187, 194, 44);
        txtSavings.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        txtSavings.setEditable(false);
        panel.add(lblSavings);
        panel.add(txtSavings);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(102, 51, 51), new Color(102, 51, 51)), "Monthly Transactions", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        scrollPane.setBounds(20, 358, 860, 242);
        scrollPane.setBackground(new Color(102, 51, 51));
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel lblFinancialDashboard = new JLabel("Financial Dashboard");
        lblFinancialDashboard.setBounds(298, 17, 344, 52);
        lblFinancialDashboard.setFont(new Font("Lava Telugu", Font.BOLD, 30));
        frame.getContentPane().add(lblFinancialDashboard);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(172, 612, 158, 44);
        btnRefresh.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        btnRefresh.addActionListener(e -> {
            inputIncome.setText("");
            inputMonth.setText("");
            txtIncome.setText("");
            txtExpense.setText("");
            txtBalance.setText("");
            txtSavings.setText("");
            loadFullTable();
        });
        frame.getContentPane().add(btnRefresh);

        JButton btnExportPDF = new JButton("Download PDF");
        btnExportPDF.setBounds(367, 612, 174, 44);
        btnExportPDF.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        btnExportPDF.addActionListener(e -> exportToPDF());
        frame.getContentPane().add(btnExportPDF);
        
        JButton btnRefresh_1 = new JButton("View Analysis");
        btnRefresh_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    Analysis analysis = new Analysis(); 
        	    analysis.displayCharts();
        		
        	}
        });
        frame.getContentPane().add( btnRefresh_1);
        
        btnRefresh_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        btnRefresh_1.setBounds(585, 607, 174, 52);
        
		
	    ImageIcon icon = new ImageIcon("/Users/devikabadekar/eclipse-workspace/MMS/src/Images/e.jpg");
        Image img = icon.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
        btnRefresh_1.setIcon(new ImageIcon(img));
        
        frame.getContentPane().add( btnRefresh_1);
        
        JButton btnUndo_1 = new JButton("Exit");
        btnUndo_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	      frame.dispose();  


        	        Homepage homePage = new Homepage();  
        	        homePage.setVisible(true);
        	}
        });
        btnUndo_1.setBounds(785, 6, 117, 29);
        frame.getContentPane().add(btnUndo_1);
		
		   btnCalculate.addActionListener(e -> calculateFinancials());
	}



    
    private void calculateFinancials() {
        try {
            double totalIncome = Double.parseDouble(inputIncome.getText());
            String month = inputMonth.getText();
            txtIncome.setText("₹" + totalIncome);

            System.out.println("Month: " + month + ", Total Income: " + totalIncome);
            
            // Get total expenses for the given month
            prestm = con.prepareStatement("SELECT SUM(AMOUNT) FROM TransactionsTable WHERE CATEGORY <> 'Income' AND DATE_FORMAT(Date, '%Y-%m') = ?");
            prestm.setString(1, month);
            rst = prestm.executeQuery();
            double totalExpenses = 0;
            if (rst.next()) totalExpenses = rst.getDouble(1);
            txtExpense.setText("₹" + totalExpenses);

            double balance = totalIncome - totalExpenses;
            txtBalance.setText("₹" + balance);

            double savingsPercentage = (balance / totalIncome) * 100;
            txtSavings.setText(String.format("%.2f%%", savingsPercentage));

            // Save income, expenses, and month to MonthlyTable
            prestm = con.prepareStatement("INSERT INTO MonthlyTable (MONTH, INCOME, EXPENSES) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE INCOME = ?, EXPENSES = ?");
            prestm.setString(1, month);
            prestm.setDouble(2, totalIncome);
            prestm.setDouble(3, totalExpenses);
            prestm.setDouble(4, totalIncome);
            prestm.setDouble(5, totalExpenses);
            prestm.executeUpdate();
            
            loadTable(month);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Invalid input or database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToPDF() {
        try {
            String filePath = "Monthly_Transactions.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Monthly Financial Transactions", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph("\n"));

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new Phrase(table.getColumnName(i)));
            }

            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    pdfTable.addCell(table.getValueAt(rows, cols).toString());
                }
            }

            document.add(pdfTable);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Financial Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Total Income: " + txtIncome.getText()));
            document.add(new Paragraph("Total Expenses: " + txtExpense.getText()));
            document.add(new Paragraph("Balance: " + txtBalance.getText()));
            document.add(new Paragraph("Savings: " + txtSavings.getText()));

            document.close();

            JOptionPane.showMessageDialog(frame, "PDF Exported Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Open PDF after exporting
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(frame, "PDF file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error exporting PDF!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	public void setVisible(boolean b) {
	frame.setVisible(b);
		
	}
}