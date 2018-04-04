package airtime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameListener;

import net.proteanit.sql.DbUtils;

public class Stocks extends MainMDI implements InternalFrameListener {
	private JTable table_1;
	private JTextField textField;

	public Stocks() {
		JInternalFrame internalFrameStock = new JInternalFrame("Current Stock",true,true,true);
		internalFrameStock.setBounds(10, 0, 414, 229);
		internalFrameStock.setSize(1340, 700);
		internalFrameStock.setVisible(true);
		internalFrameStock.setBackground(Color.magenta);
		desktopPane.add(internalFrameStock);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 398, 199);
		internalFrameStock.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblStock = new JLabel("Stocks");
		lblStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblStock.setBounds(642, 22, 83, 40);
		panel.add(lblStock);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(611,573,100,40);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				internalFrameStock.setVisible(false);
			}
			
		});
		panel.add(btnCancel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(755, 135, 495, 359);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setBounds(755, 492, 495, -359);
		panel.add(table_1);
		scrollPane_1.setViewportView(table_1);
		try
		{
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime?autoReconnect=true&useSSL=false","root","Mbugua21");
			
			PreparedStatement mystmt = myconn.prepareStatement("select *from stocks");
						
			ResultSet myRs = mystmt.executeQuery();
			
			table_1.setModel(DbUtils.resultSetToTableModel(myRs));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		JButton btnPrint = new JButton("Print New");
		btnPrint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrint.setBounds(1024, 573, 100, 40);
		panel.add(btnPrint);
		
		JButton btnPrintCurrent = new JButton("Add");
		btnPrintCurrent.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintCurrent.setBounds(280, 371, 90, 40);
		panel.add(btnPrintCurrent);
		
		JLabel lblAddNewStock = new JLabel("Add New Stock");
		lblAddNewStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblAddNewStock.setBounds(255, 89, 219, 40);
		panel.add(lblAddNewStock);
		
		JLabel lblRequestNewStock = new JLabel("Current Stock");
		lblRequestNewStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblRequestNewStock.setBounds(926, 89, 219, 40);
		panel.add(lblRequestNewStock);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblCompanyName.setBounds(156, 161, 171, 30);
		panel.add(lblCompanyName);
		
		JLabel lblDenominations = new JLabel("Denominations :");
		lblDenominations.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblDenominations.setBounds(156, 224, 171, 30);
		panel.add(lblDenominations);
		
		JLabel lblUnits = new JLabel("Units:");
		lblUnits.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblUnits.setBounds(210, 298, 69, 30);
		panel.add(lblUnits);
		
		JComboBox<Object> cmbCompanyName = new JComboBox<Object>();
		cmbCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbCompanyName.addItem("-Select Company-");
		cmbCompanyName.setBounds(337, 166, 230, 25);
		panel.add(cmbCompanyName);
		try {
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			Statement mystmt= myconn.createStatement();							
			ResultSet myRs = mystmt.executeQuery("select CompanyName from company");
			
			  while(myRs.next())
			  {
				
			  	cmbCompanyName.addItem(myRs.getString("CompanyName"));
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String [] deno = {"-Select Denomination-","10","20","50","100","250","500","1000"};
		JComboBox<Object> cmbDeno = new JComboBox<Object>(deno);
		cmbDeno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbDeno.setBounds(337, 229, 230, 25);
		panel.add(cmbDeno);
		
		textField = new JTextField();
		textField.setBounds(336, 298, 231, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnEdit.setBounds(426, 371, 83, 40);
		panel.add(btnEdit);

	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Stocks();
				}
		});
	}
}

