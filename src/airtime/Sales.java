package airtime;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Sales extends MainMDI implements InternalFrameListener {
	private JTextField txtFldEnterUnits;
	private static JTable Salestable;
		
	public static void main(String[] arg){
		new Sales();
				
	}

	public Sales() {
			
		JInternalFrame internalFrameSales = new JInternalFrame("Sales");
		internalFrameSales.setBounds(10, 0, 414, 229);
		internalFrameSales.setSize(1340, 700);
		internalFrameSales.setVisible(true);
		desktopPane.add(internalFrameSales);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 398, 199);
		internalFrameSales.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblWelcomeCompany = new JLabel("Sales");
		lblWelcomeCompany.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblWelcomeCompany.setBounds(292, 42, 77, 40);
		panel.add(lblWelcomeCompany);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(365,374,100,40);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//internalFrameSales.setVisible(false);
			}
			
		});
		panel.add(btnCancel);
		
		
		JComboBox<String> cbxChseCompany = new JComboBox<String>();
		cbxChseCompany.setFont(new Font("Times New Roman", Font.ITALIC,18));
		cbxChseCompany.setBounds(316, 126, 180, 27);
		cbxChseCompany.setSize(230, 30);
		cbxChseCompany.addItem("-Select Company-");
		panel.add(cbxChseCompany);
				try
				{
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			
					Statement mystmt= myconn.createStatement();							
					ResultSet myRs = mystmt.executeQuery("select CompanyName from company");
					
					  while(myRs.next())
					  {						
					  	cbxChseCompany.addItem(myRs.getString("CompanyName"));}
						System.out.println("Displays Company Name on ComboBox Choose");		
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
		
		txtFldEnterUnits = new JTextField();
		txtFldEnterUnits.setBounds(316, 271, 180, 28);
		panel.add(txtFldEnterUnits);
		txtFldEnterUnits.setColumns(10);
		
		JLabel lblAirtimeCompany = new JLabel("Airtime Company:");
		lblAirtimeCompany.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblAirtimeCompany.setBounds(109, 116, 180, 40);
		panel.add(lblAirtimeCompany);
		
		JLabel lblAirtimeDenonimation = new JLabel("Airtime Denonimation:");
		lblAirtimeDenonimation.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblAirtimeDenonimation.setBounds(109, 184, 194, 40);
		panel.add(lblAirtimeDenonimation);
		
		String[] deno = {"-Select Denomination-","10","20","50","100","250","500","1000"};
		
		JComboBox<String> denomination = new JComboBox<String>(deno);
		denomination.setBounds(316, 191, 180, 29);
		denomination.setSize(230, 30);
		denomination.setFont(new Font("Times New Roman", Font.ITALIC,18));
		panel.add(denomination);
		
		JLabel lblUnitsSold = new JLabel("Units Sold:");
		lblUnitsSold.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblUnitsSold.setBounds(109, 262, 180, 40);
		panel.add(lblUnitsSold);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAdd.setBounds(181, 374, 100, 40);
		btnAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				if(cbxChseCompany.getSelectedItem().equals("-Select Company-")){
				JOptionPane.showMessageDialog(null, "Enter Company Name");}
				
				else if(denomination.getSelectedItem().equals("-Select Denomination-")){
					JOptionPane.showMessageDialog(null, "Enter Denomination");}
				
				else if(txtFldEnterUnits.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Enter Number of Units");}
				else {
					try{
						Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime?autoReconnect=true&useSSL=false","root","Mbugua21");
						
						
						String query = "insert into sales (CompanyName,Denominations,Units,Date) values (?,?,?,?)";
						
						Date curDate = new Date();
						
						SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

						String DateToStr = format.format(curDate);
						System.out.println(DateToStr);
					
						PreparedStatement mystmt = myconn.prepareStatement(query);
						mystmt.setString(1,cbxChseCompany.getSelectedItem().toString() );
						mystmt.setString(2,denomination.getSelectedItem().toString() );
						mystmt.setString(3,txtFldEnterUnits.getText() );
						mystmt.setString(4,DateToStr.toString() );
						
						mystmt.execute();
						
						 JOptionPane.showMessageDialog(null, "Data Saved");
						mystmt.close();
						System.out.println("Sales Saved");
						//displayResults();
						cbxChseCompany.setSelectedItem("-Select Company-");
						denomination.setSelectedItem("-Select Denomination-");
						txtFldEnterUnits.setText(null);
					}
					catch(Exception e){
						 JOptionPane.showMessageDialog(null,e,"Duplicate Entry Alert",1);
						 
						cbxChseCompany.setSelectedItem("-Select Company-");
						denomination.setSelectedItem("-Select Denomination-");
						txtFldEnterUnits.setText(null);
						
						e.printStackTrace();}
						}
					}		
				});
				
		panel.add(btnAdd);
		
		JLabel lblTodaySales = new JLabel("Today's Sales");
		lblTodaySales.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblTodaySales.setBounds(809, 42, 162, 40);
		panel.add(lblTodaySales);
		
		JScrollPane scrollPaneSales = new JScrollPane();
		scrollPaneSales.setBounds(613, 103, 489, 353);
		panel.add(scrollPaneSales);
		
		Salestable = new JTable();
		Salestable.setBounds(1038, 431, -350, -280);
		panel.add(Salestable);
		scrollPaneSales.setViewportView(Salestable);
		try{
			
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			
			PreparedStatement mystmt = myconn.prepareStatement("select CompanyName,Denominations,sum(Units) AS Units,sum(Units)*Denominations AS GrandTotals FROM airtime.sales group by CompanyName,Denominations");
			
			/*Date curDate = new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

			String DateToStr = format.format(curDate);
			System.out.println(DateToStr);
			mystmt.setString(1,DateToStr.toString());
			
						
			ResultSet myRs = mystmt.executeQuery();
			
			Salestable.setModel(DbUtils.resultSetToTableModel(myRs));*/
			DefaultTableModel model;
			model = new DefaultTableModel();
			Salestable.setModel(model);
			model.addRow(new Object[]{"Mark","Alex","loe"});


			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		JButton btnPrintSales = new JButton("Print");
		btnPrintSales.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintSales.setBounds(809, 508, 100, 40);
		panel.add(btnPrintSales);
		btnPrintSales.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Connection myconn;
				try {
					myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
					PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from sales");
					
					ResultSet myRs = mystmt.executeQuery();
					
					DefaultTableModel model;
					model = new DefaultTableModel();
					Salestable.setModel(model);
					Salestable.setShowVerticalLines(true);
					Salestable.setCellSelectionEnabled(true);
					Salestable.setColumnSelectionAllowed(true);
					//model.setColumnIdentifiers(myRs.getString("CompanyName"));
					ArrayList<String> companys = new ArrayList<>();
					model.addColumn(" ");
					while(myRs.next()){
						companys.add(myRs.getString("CompanyName"));
					
					model.addColumn(myRs.getString("CompanyName"));
				
	               
					//model.addRow(new Object[]{myRs.getString("CompanyName"),"Mark","Alex","loe","Resource"});
					}
					model.addColumn("Grand Totals");
					
					PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales order by Denominations asc");
					
					ResultSet myRs1 = mystmt1.executeQuery();
					ArrayList<String> deno = new ArrayList<>();
					while(myRs1.next()){
						deno.add(myRs1.getString("Denominations"));
						
						model.addRow(new Object[]{myRs1.getString("Denominations")});
				}
					model.addRow(new Object[]{"CompanySales"});
						
					String val ="";
					
					int i,j;
				for(i=0; i<deno.size();i++){
					double companyTotal =0;
					double totalUnits = 0;
					for(j=0; j<companys.size();j++){
						PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
						ResultSet myRs2 = mystmt2.executeQuery();
						
						
						while(myRs2.next()){
							val=myRs2.getString("Units");
							if(val!= null){
								val=myRs2.getString("Units");	
							}else {val="0";}
							
							}
						model.setValueAt(val,i, j+1);
						totalUnits = totalUnits+ Double.parseDouble(val);
						model.setValueAt(totalUnits *Double.parseDouble(deno.get(i)),i, companys.size()+1);
					
					}
					
					model.setValueAt(model.getValueAt(i,j),j+2,i+1);
					System.out.println(i);
				}
					
				
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
				
			
				
			}
			
		});
		
		String[] sal = {"Today","Yesterday","This Month","Last Month","Annual"};
		
		JComboBox<Object> cbxChseSales = new JComboBox<Object>(sal);
		cbxChseSales.setFont(new Font("Times New Roman", Font.ITALIC,18));
		cbxChseSales.setBounds(1104, 55, 180, 27);
		panel.add(cbxChseSales);		
		cbxChseSales.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cbxChseSales.getSelectedItem() == "Today") {lblTodaySales.setText("Today's Sales");}
					
				else if(cbxChseSales.getSelectedItem() == "Yesterday") {lblTodaySales.setText("Yesterday's Sales");}
					
				else if(cbxChseSales.getSelectedItem() == "This Month") {lblTodaySales.setText("This Month Sales");}
				
				else if(cbxChseSales.getSelectedItem() == "Last Month") {lblTodaySales.setText("Last Month Sales");}
				
				else if(cbxChseSales.getSelectedItem() == "Annual") {lblTodaySales.setText("Annual Sales");}
				
			}
		});
		
		
	}
	public static void displayResults(){
		try{
		
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			
			PreparedStatement mystmt = myconn.prepareStatement("select *from sales");
					
			ResultSet myRs = mystmt.executeQuery();
			
			Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
