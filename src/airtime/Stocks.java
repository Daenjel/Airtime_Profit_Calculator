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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
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
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Stocks extends MainMDI implements InternalFrameListener {
	private static JTable table_1;
	private JTextField textField;
	private JComboBox<Object> cmbCompanyName;
	private JComboBox<Object> cmbDeno;
	ArrayList<String> companys;

	public Stocks() {
		JInternalFrame internalFrameStock = new JInternalFrame("Current Stock",true,true,true);
		internalFrameStock.setFrameIcon(new ImageIcon(Stocks.class.getResource("/images/ic_storage_black_18dp_1x.png")));
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
		btnCancel.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(611,573,100,40);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Sales();
				contentPane.setVisible(false);
			}
			
		});
		panel.add(btnCancel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(666, 135, 584, 301);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cmbCompanyName.setSelectedItem(table_1.getValueAt(table_1.getSelectedRow(),0).toString());
				cmbDeno.setSelectedItem(table_1.getValueAt(table_1.getSelectedRow(),1).toString());
				textField.setText(table_1.getValueAt(table_1.getSelectedRow(),2).toString());
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		try {
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from stocks");
			
			ResultSet myRs = mystmt.executeQuery();
			
			DefaultTableModel model;
			model = new DefaultTableModel();
			table_1.setModel(model);
			table_1.setShowVerticalLines(true);
			table_1.setCellSelectionEnabled(true);
			table_1.setColumnSelectionAllowed(true);
			companys = new ArrayList<>();
			model.addColumn(" ");
			while(myRs.next()){
				companys.add(myRs.getString("CompanyName"));
			
			model.addColumn(myRs.getString("CompanyName"));
			}
			model.addColumn("Total Stocks");
			
			PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from stocks order by Denominations desc");
			
			ResultSet myRs1 = mystmt1.executeQuery();
			ArrayList<String> deno = new ArrayList<>();
			while(myRs1.next()){
				deno.add(myRs1.getString("Denominations"));
				
				model.addRow(new Object[]{myRs1.getString("Denominations")});
		}				
			String val ="";
			
			int i,j;
		for(i=0; i<deno.size();i++){
			double totalUnits = 0;
			for(j=0; j<companys.size();j++){
				PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from stocks where CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
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
			System.out.println(i);
		}
		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
				
		JButton btnPrint = new JButton("Print Stock");
		btnPrint.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_print_black_24dp_1x.png")));
		btnPrint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrint.setBounds(897, 508, 121, 40);
		panel.add(btnPrint);
		btnPrint.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					MessageFormat header = new MessageFormat("Page Header");
					MessageFormat footer = new MessageFormat("Page(1,number,integer");
					
					table_1.print(JTable.PrintMode.FIT_WIDTH,header,footer);
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}	
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_library_add_black_24dp_1x.png")));
		btnAdd.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAdd.setBounds(240, 371, 90, 40);
		panel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cmbCompanyName.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null, "Enter Company Name");}
					
					else if(cmbDeno.getSelectedItem().equals("-Select Denomination-")){
						JOptionPane.showMessageDialog(null, "Enter Denomination");}
					
					else if(textField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Enter Number of Units");}
					else if(textField.getText().equals("0")){
						JOptionPane.showMessageDialog(null, "Units cannot be equals to Zero");}
					else {
				try{
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
					
					Date curDate = new Date();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

					String DateToStr = format.format(curDate);
					System.out.println(DateToStr);
					//String StockUnits;
					PreparedStatement check = myconn.prepareStatement("SELECT Units FROM stocks WHERE CompanyName =? and Denominations =?");
					check.setString(1,cmbCompanyName.getSelectedItem().toString());
					check.setString(2,cmbDeno.getSelectedItem().toString());
					ResultSet myRs = check.executeQuery();
					   boolean recordAdded = false;
					    if(!myRs.first()){            
					       /// Do your insertion of new records
					    	PreparedStatement mystmt = myconn.prepareStatement("insert into stocks (CompanyName,Denominations,Units,Date) values (?,?,?,?)");
							mystmt.setString(1,cmbCompanyName.getSelectedItem().toString());
							mystmt.setString(2,cmbDeno.getSelectedItem().toString());
							mystmt.setString(3,textField.getText().toString());
							mystmt.setString(4,DateToStr.toString());
							
							mystmt.execute();
							JOptionPane.showMessageDialog(null,"Stock Saved");
							displayStock();
							System.out.println("Stocks Saved");
							mystmt.close();
							
					    	//JOptionPane.showMessageDialog(null, "Recorder");
					         recordAdded = true;
					    }
					    else if( recordAdded ){
					      JOptionPane.showMessageDialog(null, "Record added");
					    }else{
					       JOptionPane.showMessageDialog(null, "Record already exists Press EDIT to update");
					   	   cmbCompanyName.getSelectedItem().toString();
						   cmbDeno.getSelectedItem().toString();
						   textField.getText().toString();
					    }
					check.close();
					/*myRs.first();
					while(!myRs.next()){
					record = true;
					StockUnits = myRs.getString("Units");
					System.out.println(StockUnits);
				
					if(StockUnits != " "){
						JOptionPane.showMessageDialog(null,"Record already Exists Press EDIT to update");
						
					}else{
					PreparedStatement mystmt = myconn.prepareStatement("insert into stocks (CompanyName,Denominations,Units,Date) values (?,?,?,?)");
					mystmt.setString(1,cmbCompanyName.getSelectedItem().toString());
					mystmt.setString(2,cmbDeno.getSelectedItem().toString());
					mystmt.setString(3,textField.getText().toString());
					mystmt.setString(4,DateToStr.toString());
					
					mystmt.execute();
					JOptionPane.showMessageDialog(null,"Stock Saved");
					displayStock();
					System.out.println("Stocks Saved");
					mystmt.close();
					}
				
					check.close();}*/
				
					cmbCompanyName.setSelectedItem("-Select Company-");
					cmbDeno.setSelectedItem("-Select Denomination-");
					textField.setText(null);
				}catch (Exception e){
					JOptionPane.showMessageDialog(null,e);
					e.printStackTrace();
					cmbCompanyName.setSelectedItem("-Select Company-");
					cmbDeno.setSelectedItem("-Select Denomination-");
					textField.setText(null);
				}
				
			}
			}
		});
		
		
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
		
		cmbCompanyName = new JComboBox<Object>();
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
			  System.out.println("Displays Company Name on cmbCompanyName");
			  myconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String [] deno = {"-Select Denomination-","10","20","50","100","250","500","1000"};
		cmbDeno = new JComboBox<Object>(deno);
		cmbDeno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbDeno.setBounds(337, 229, 230, 25);
		panel.add(cmbDeno);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char ch = evt.getKeyChar();
				if(!Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE)){
					//getToolkit().beep();
					evt.consume();
				JOptionPane.showMessageDialog(null, "Cannot Accept Letters");}
			}
		});
		textField.setBounds(336, 298, 231, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_edit_black_24dp_1x.png")));
		btnEdit.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnEdit.setBounds(370, 371, 83, 40);
		panel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cmbCompanyName.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
				}else if (cmbDeno.getSelectedItem().equals("-Select Denomination-")){
					JOptionPane.showMessageDialog(null,"Company Denomination is not declared");
				}else if (textField.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Number of Units are not declared");
				}else{
					try{
						Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
						
						PreparedStatement mystmt = myconn.prepareStatement("update stocks set Denominations =?, Units =? where CompanyName =?");
						mystmt.setString(1,cmbDeno.getSelectedItem().toString());
						mystmt.setString(2,textField.getText().toString());
						mystmt.setString(3,cmbCompanyName.getSelectedItem().toString());
											
						mystmt.execute();
						JOptionPane.showMessageDialog(null, cmbCompanyName.getSelectedItem().toString()+ " denomination "+cmbDeno.getSelectedItem().toString()+ " has changed to  " + textField.getText().toString());
						mystmt.close();
						System.out.println("Edited");
						displayStock();
						cmbCompanyName.setSelectedItem("-Select Company-");
						cmbDeno.setSelectedItem("-Select Denomination-");
						textField.setText(null);
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, e);
						cmbCompanyName.setSelectedItem("-Select Company-");
						cmbDeno.setSelectedItem("-Select Denomination-");
						textField.setText(null);
						e.printStackTrace();
					}
			}
			}				
			
			
		});


		JButton btnRemove = new JButton("Remove");
		btnRemove.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_remove_shopping_cart_black_18dp_1x.png")));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cmbCompanyName.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
			}else if (cmbDeno.getSelectedItem().equals("-Select Denomination-")){
				JOptionPane.showMessageDialog(null,"Company Denomination is not declared");
			}else if(textField.getText().equals(" ")){
				JOptionPane.showMessageDialog(null,"Number of Units are not declared");}
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete  " + cmbCompanyName.getSelectedItem().toString() + " with Demonimation "+cmbDeno.getSelectedItem().toString()+ "  from Stock"," Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				 
				if (response == JOptionPane.NO_OPTION) {
					 System.out.println("Not Deleted");
				      
				    } else if (response == JOptionPane.YES_OPTION) {
				     
				
				try{
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
					
					PreparedStatement mystmt = myconn.prepareStatement("delete from airtime.stocks where CompanyName = ? and Denominations=?");
					mystmt.setString(1,cmbCompanyName.getSelectedItem().toString());
					mystmt.setString(2,cmbDeno.getSelectedItem().toString());
					
										
					mystmt.execute();
					JOptionPane.showMessageDialog(null, "Company Name  " + cmbCompanyName.getSelectedItem().toString() +"  has been deleted");
					
					mystmt.close();
					System.out.println("Deleted");
					displayStock();
					cmbCompanyName.setSelectedItem("-Select Company-");
					cmbDeno.setSelectedItem("-Select Denomination-");
					textField.setText(null);
				
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
			}
			}
			
		});
		
		btnRemove.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnRemove.setBounds(484, 371, 100, 40);
		panel.add(btnRemove);
		
		JLabel TotalCost = new JLabel("0.0");
		TotalCost.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		TotalCost.setBounds(1173, 453, 77, 28);
		TotalCost.setText(Double.toString(getSum()));
		panel.add(TotalCost);
	}
	public double getSum(){
		int rowcount = table_1.getRowCount();
		double sum = 0;
		for(int i=0;i<rowcount;i++){
			sum = sum+Double.parseDouble(table_1.getValueAt(i, companys.size()+1).toString());
		}
		return sum;
		
	}
	public static void displayStock(){
		try{
			Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
		
			PreparedStatement mystmt = myconn.prepareStatement("select * from stocks");
			
			ResultSet myRs = mystmt.executeQuery();
			
			table_1.setModel(DbUtils.resultSetToTableModel(myRs));
		
			System.out.println("Displays Stocks");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Stocks();
				}
		});
	}
}

