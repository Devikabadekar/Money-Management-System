package mainPackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Analysis {
    private Connection con;
    
    public Analysis() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mmsdatabase", "root", "newpass");
            System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DefaultCategoryDataset getMonthlyData() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            String query = "SELECT DATE_FORMAT(Date, '%Y-%m') AS Month, Category, SUM(Amount) FROM TransactionsTable " +
                           "WHERE Category IN ('Food', 'Utilities', 'Entertainment') " +
                           "GROUP BY Month, Category ORDER BY Month";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String month = rs.getString(1);
                String category = rs.getString(2);
                int amount = rs.getInt(3);
                dataset.addValue(amount, category, month);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public DefaultCategoryDataset getYearlyData() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            String query = "SELECT Category, SUM(Amount) FROM TransactionsTable " +
                           "WHERE Category IN ('Food', 'Utilities', 'Entertainment') " +
                           "GROUP BY Category";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String category = rs.getString(1);
                int amount = rs.getInt(2);
                dataset.addValue(amount, category, "Total Yearly Expense");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public void displayCharts() {
        JFrame frame = new JFrame("Expense Analysis Charts");
        frame.setTitle("");
        frame.setSize(900, 700);
        frame.getContentPane().setLayout(new GridLayout(2, 1));

        // Monthly Expense Chart
        JFreeChart monthlyChart = ChartFactory.createBarChart(
                "Monthly Expense Distribution",
                "Month",
                "Amount (₹)",
                getMonthlyData(),
                PlotOrientation.VERTICAL,
                true, true, false);
        
        CategoryPlot plot1 = (CategoryPlot) monthlyChart.getPlot();
        plot1.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(monthlyChart);
        frame.getContentPane().add(chartPanel);
        chartPanel.setLayout(null);
        
        JButton btnUndo_1 = new JButton("Exit");
        btnUndo_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	      frame.dispose();  

        
        	        Homepage homePage = new Homepage();  
        	        homePage.setVisible(true);
        	}
        });
        btnUndo_1.setBounds(777, 6, 117, 29);
        chartPanel.add(btnUndo_1);

        // Yearly Expense Chart
        JFreeChart yearlyChart = ChartFactory.createBarChart(
                "Yearly Expense Breakdown",
                "Category",
                "Total Amount (₹)",
                getYearlyData(),
                PlotOrientation.VERTICAL,
                true, true, false);
        
        CategoryPlot plot2 = (CategoryPlot) yearlyChart.getPlot();
        plot2.setBackgroundPaint(Color.white);
        ChartPanel chartPanel_1 = new ChartPanel(yearlyChart);
        frame.getContentPane().add(chartPanel_1);
        chartPanel_1.setLayout(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Analysis graph = new Analysis();
        graph.displayCharts();
    }

    public void setVisible(boolean b) {
        
    	   if (b) {
    	
    	        displayCharts();
    	    }
    	}
    }
    
