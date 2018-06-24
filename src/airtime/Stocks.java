package airtime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;
import javax.swing.SwingConstants;

public class Stocks extends MainMDI implements InternalFrameListener {
	private static JTable table_1;
	private JTextField textField;
	static JLabel lblTotalSales;
	static double sum = 0;
	static DecimalFormat df2 = new DecimalFormat(".##");
	private JComboBox<Object> cmbCompanyName;
	private JComboBox<Object> cmbDeno;
	static ArrayList<String> companys;
	static ArrayList<String> deno;
	private JButton btnCurrentStock;
	static Connection myconn = null;
	private JTextField txtWSalerID;
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Stocks();
				getStock();
				}
		});
	}
	public Stocks() {
		myconn = JConnection.ConnecrDb();
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
		lblStock.setBounds(642, 22, 83, 40);
		lblStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		panel.add(lblStock);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(611, 573, 100, 40);
		btnCancel.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cmbCompanyName.setSelectedItem("-Select Company-");
				cmbDeno.setSelectedItem("-Select Denomination-");
				textField.setText(null);
			}		
		});
		panel.add(btnCancel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(666, 135, 584, 301);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setRowHeight(30);
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
		CurrentStock();
		
		JButton btnPrint = new JButton("Print Stock");
		btnPrint.setBounds(897, 508, 121, 40);
		btnPrint.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_print_black_24dp_1x.png")));
		btnPrint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
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
		btnAdd.setBounds(240, 430, 90, 40);
		btnAdd.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_library_add_black_24dp_1x.png")));
		btnAdd.setFont(new Font("Segoe UI", Font.ITALIC, 12));
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
					Date curDate = new Date();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

					String DateToStr = format.format(curDate);
					System.out.println(DateToStr);
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
							CurrentStock();
							System.out.println("Stocks Saved");
							mystmt.close();
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
		lblAddNewStock.setBounds(255, 89, 219, 40);
		lblAddNewStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		panel.add(lblAddNewStock);
		
		JLabel lblRequestNewStock = new JLabel("Current Stock");
		lblRequestNewStock.setBounds(856, 89, 144, 40);
		lblRequestNewStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		panel.add(lblRequestNewStock);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setBounds(156, 220, 171, 30);
		lblCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		panel.add(lblCompanyName);
		
		JLabel lblDenominations = new JLabel("Denominations :");
		lblDenominations.setBounds(156, 283, 171, 30);
		lblDenominations.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		panel.add(lblDenominations);
		
		JLabel lblUnits = new JLabel("Units:");
		lblUnits.setBounds(210, 357, 69, 30);
		lblUnits.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		panel.add(lblUnits);
		
		cmbCompanyName = new JComboBox<Object>();
		cmbCompanyName.setBounds(337, 225, 230, 25);
		cmbCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbCompanyName.addItem("-Select Company-");
		panel.add(cmbCompanyName);
		try {
			Statement mystmt= myconn.createStatement();							
			ResultSet myRs = mystmt.executeQuery("select CompanyName from company");
			
			  while(myRs.next())
			  {
				cmbCompanyName.addItem(myRs.getString("CompanyName"));
			  }
			  System.out.println("Displays Company Name on cmbCompanyName");
			 // myconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String [] deno = {"-Select Denomination-","10","20","50","100","250","500","1000"};
		cmbDeno = new JComboBox<Object>(deno);
		cmbDeno.setBounds(337, 288, 230, 25);
		cmbDeno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		panel.add(cmbDeno);
		
		textField = new JTextField();
		textField.setBounds(336, 357, 231, 30);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char ch = evt.getKeyChar();
				if(!Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE)){
					Toolkit.getDefaultToolkit().beep();
					evt.consume();
				JOptionPane.showMessageDialog(null, "Cannot Accept Letters");}
			}
		});
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(370, 430, 83, 40);
		btnEdit.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_edit_black_24dp_1x.png")));
		btnEdit.setFont(new Font("Segoe UI", Font.ITALIC, 12));
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
						PreparedStatement mystmt = myconn.prepareStatement("update stocks set  Units =? where Denominations =? and CompanyName =?");
						mystmt.setString(1,textField.getText().toString());
						mystmt.setString(2,cmbDeno.getSelectedItem().toString());
						mystmt.setString(3,cmbCompanyName.getSelectedItem().toString());
											
						mystmt.execute();
						JOptionPane.showMessageDialog(null, cmbCompanyName.getSelectedItem().toString()+ " denomination "+cmbDeno.getSelectedItem().toString()+ " has changed to  " + textField.getText().toString());
						mystmt.close();
						System.out.println("Edited");
						CurrentStock();
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
		btnRemove.setBounds(484, 430, 100, 40);
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
					PreparedStatement mystmt = myconn.prepareStatement("delete from airtime.stocks where CompanyName = ? and Denominations=?");
					mystmt.setString(1,cmbCompanyName.getSelectedItem().toString());
					mystmt.setString(2,cmbDeno.getSelectedItem().toString());
					
										
					mystmt.execute();
					JOptionPane.showMessageDialog(null, "Company Name  " + cmbCompanyName.getSelectedItem().toString() +"  has been deleted");
					
					mystmt.close();
					System.out.println("Deleted");
					CurrentStock();
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
		panel.add(btnRemove);
		
		lblTotalSales = new JLabel("0.0");
		lblTotalSales.setBounds(1160, 444, 90, 30);
		lblTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalSales.setForeground(Color.BLACK);
		lblTotalSales.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
		panel.add(lblTotalSales);
		
		JButton btnRefillStock = new JButton("Refill Stock");
		btnRefillStock.setBounds(1121, 99, 129, 30);
		btnRefillStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblRequestNewStock.setText("Refill Stock");
				try{				
					PreparedStatement mystmt = myconn.prepareStatement("select Date,CompanyName,Denominations,Units from stocks order by date desc");
					ResultSet myRs = mystmt.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(myRs));
					System.out.println("Displays Refill Stocks");
				}catch (Exception e){
					e.printStackTrace();
				}
				btnRefillStock.setVisible(false);
				btnCurrentStock.setVisible(true);
			}
		});
		btnRefillStock.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_storage_black_18dp_1x.png")));
		btnRefillStock.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		panel.add(btnRefillStock);
		
		btnCurrentStock = new JButton("Current Stock");
		btnCurrentStock.setBounds(1121, 99, 129, 30);
		btnCurrentStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblRequestNewStock.setText("Current Stock");
				CurrentStock();
				System.out.println("Displays Current Stocks");
				btnCurrentStock.setVisible(false);
				btnRefillStock.setVisible(true);
			}
		});
		btnCurrentStock.setIcon(new ImageIcon(Stocks.class.getResource("/images/ic_storage_black_18dp_1x.png")));
		btnCurrentStock.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		panel.add(btnCurrentStock);
		
		JLabel Label_10 = new JLabel("Total Sales:");
		Label_10.setBounds(1060, 440, 90, 30);
		Label_10.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		panel.add(Label_10);
		
		JLabel lblcCopyright = new JLabel();
		lblcCopyright.setBounds(436, 626, 420, 33);
		lblcCopyright.setVerticalAlignment(SwingConstants.CENTER);
		lblcCopyright.setText("(C) Copyright 2018 by Anthony Wambua, Daniel Mbugua Inc.");
		lblcCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblcCopyright.setForeground(Color.DARK_GRAY);
		lblcCopyright.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		panel.add(lblcCopyright);
		
		JLabel label = new JLabel("Total Cost:");
		label.setBounds(666, 440, 90, 30);
		label.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		panel.add(label);
		
		JLabel lblTotalCost = new JLabel("0.0");
		lblTotalCost.setBounds(755, 440, 68, 34);
		lblTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalCost.setForeground(Color.BLACK);
		lblTotalCost.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel.add(lblTotalCost);
		
		JLabel lblProft = new JLabel("Profit:");
		lblProft.setBounds(874, 440, 69, 30);
		lblProft.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		panel.add(lblProft);
		
		JLabel lblProfit = new JLabel("0.0");
		lblProfit.setBounds(953, 442, 77, 28);
		lblProfit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProfit.setForeground(Color.BLACK);
		lblProfit.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel.add(lblProfit);
		
		txtWSalerID = new JTextField();
		txtWSalerID.setColumns(10);
		txtWSalerID.setBounds(336, 169, 231, 30);
		panel.add(txtWSalerID);
		
		JLabel lblWholsalerId = new JLabel("WholeSaler ID:");
		lblWholsalerId.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblWholsalerId.setBounds(156, 169, 171, 30);
		panel.add(lblWholsalerId);
		
		JComboBox<Object> cmbWholSaler = new JComboBox<Object>();
		cmbWholSaler.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbWholSaler.setBounds(1077, 37, 171, 25);
		cmbWholSaler.addItem("-WholeSaler ID-");
		panel.add(cmbWholSaler);
	}

	public static void CurrentStock(){
		try {
			PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from stocks");
			
			ResultSet myRs = mystmt.executeQuery();
			
			DefaultTableModel model = new DefaultTableModel();
			table_1.setModel(model);
			table_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
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
			deno = new ArrayList<>();
			while(myRs1.next()){
				deno.add(myRs1.getString("Denominations"));
				
				model.addRow(new Object[]{myRs1.getString("Denominations")});
		}	model.addRow(new Object[]{("Total Stk:")});			
			String val ="";
			
			int i,j;
		for(i=0; i<deno.size();i++){
			double totalUnits = 0;
			for(j=0; j<companys.size();j++){
				PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from stocks where CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
				ResultSet myRs2 = mystmt2.executeQuery();
				TableColumn column = table_1.getColumnModel().getColumn(0);
		        column.setPreferredWidth(100);
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
		for(j=0;j<companys.size();j++){
			for(i=0;i<deno.size();i++){
				sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
				model.setValueAt(sum,deno.size(),j+1);
				}
			System.out.println("Sum of col "+j+ "-> " +sum);
			sum=0;
		}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		System.out.println("Displays Stocks");
	}
	public static double getStock(){
		
		for(int j=0;j<deno.size();j++){
			
			sum = sum+Double.parseDouble(table_1.getValueAt(j, companys.size()+1).toString());
			lblTotalSales.setText(""+sum);
			System.out.println("Sum inner is" +sum);
			
			boolean bool = true;
			int ndx = 0;

			NumberFormat cfLocal = NumberFormat.getCurrencyInstance();

			String sCurSymbol = " ";

			DecimalFormatSymbols dfs = null;

			if( cfLocal instanceof DecimalFormat ){ // determine if symbol is prefix or suffix
			      dfs = ((DecimalFormat)cfLocal).getDecimalFormatSymbols();
			      sCurSymbol = dfs.getCurrencySymbol();      
			    }
			Number n = null;
			String sText = lblTotalSales.getText();    
			ndx = sText.indexOf(sCurSymbol);
			if( ndx == -1 ){ 
			      if( bool ){
			    	  sText = sCurSymbol + sText; }
			      else{
			    	  sText = sText + " " + sCurSymbol; }
			    }
			    try{
			      n = cfLocal.parse( sText );
			      lblTotalSales.setText( cfLocal.format( n ) );
			    }
			    catch( ParseException pe ) {
			    	lblTotalSales.setText( "" ); }
			    
			}
		
		return sum=0;
	}
}