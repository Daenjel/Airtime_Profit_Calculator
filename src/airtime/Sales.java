package airtime;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.util.Calendar;
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
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Sales extends MainMDI implements InternalFrameListener {
	private JTextField txtFldEnterUnits;
	private static JTable Salestable;
	static JLabel TotalCost;
	JComboBox<Object> cbxChseCompany;
	JComboBox<Object> denomination;
	static ArrayList<String> companys;
	static String DateToStr;
	static Date curDate = new Date();
	
	static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
	
    static Calendar calSD = Calendar.getInstance();
    
	public static void main(String[] arg){
		new Sales();
		textFormat();
	}

	public Sales() {
			
		JInternalFrame internalFrameSales = new JInternalFrame("Sales");
		internalFrameSales.setFrameIcon(new ImageIcon(Sales.class.getResource("/images/ic_add_shopping_cart_black_18dp_1x.png")));
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
		btnCancel.setIcon(new ImageIcon(Sales.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(365,374,100,40);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cbxChseCompany.setSelectedItem("-Select Company-");
				denomination.setSelectedItem("-Select Denomination-");
				txtFldEnterUnits.setText(null);
			}
			
		});
		panel.add(btnCancel);
		
		
		cbxChseCompany = new JComboBox<Object>();
		cbxChseCompany.setFont(new Font("Times New Roman", Font.ITALIC,18));
		cbxChseCompany.setBounds(316, 126, 180, 27);
		cbxChseCompany.setSize(230, 30);
		cbxChseCompany.addItem("-Select Company-");
		panel.add(cbxChseCompany);
				try
				{
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			
					Statement mystmt= myconn.createStatement();							
					ResultSet myRs = mystmt.executeQuery("select distinct CompanyName from stocks");
					
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
		
		String[] Deno = {"-Select Denomination-","10","20","50","100","250","500","1000"};
		
		denomination = new JComboBox<Object>(Deno);
		denomination.setBounds(316, 191, 180, 29);
		denomination.setSize(230, 30);
		denomination.setFont(new Font("Times New Roman", Font.ITALIC,18));
		panel.add(denomination);
		
		JLabel lblUnitsSold = new JLabel("Units Sold:");
		lblUnitsSold.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblUnitsSold.setBounds(109, 262, 180, 40);
		panel.add(lblUnitsSold);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(Sales.class.getResource("/images/ic_library_add_black_24dp_1x.png")));
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
				else if(txtFldEnterUnits.getText().equals("0")){
					JOptionPane.showMessageDialog(null, "Units cannot be == to Zero");}
				else {
					try{
						Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime?autoReconnect=true&useSSL=false","root","Mbugua21");
						
						
						String query = "insert into sales (CompanyName,Denominations,Units,Date) values (?,?,?,?)";
						String select = "select Units from stocks where CompanyName=? and Denominations=?";
						String update = "update stocks Set Units=? where CompanyName=? and Denominations=?";
						
						DateToStr = dateformat.format(curDate);
						System.out.println(DateToStr);
					
						PreparedStatement mystmt = myconn.prepareStatement(query);
						mystmt.setString(1,cbxChseCompany.getSelectedItem().toString() );
						mystmt.setString(2,denomination.getSelectedItem().toString() );
						mystmt.setString(3,txtFldEnterUnits.getText() );
						mystmt.setString(4,DateToStr.toString() );
						mystmt.execute();
						
						double Balance;
						PreparedStatement seta = myconn.prepareStatement(select);
						seta.setString(1,cbxChseCompany.getSelectedItem().toString() );
						seta.setString(2,denomination.getSelectedItem().toString() );
						ResultSet myRs = seta.executeQuery();
						if(myRs.next()){
						int StockUnits = myRs.getInt("Units");
						System.out.println("Current Stock"+StockUnits);
						/*if (myRs.getString(StockUnits) != null){
							JOptionPane.showMessageDialog(null, "No Stock for "+cbxChseCompany.getSelectedItem()+ " "+denomination.getSelectedItem());
						}else{*/
						String convert = txtFldEnterUnits.getText();
						int SalesUnits = Integer.parseInt(convert);
						System.out.println("Number Units sold"+SalesUnits);
						
						Balance = StockUnits - SalesUnits;
						System.out.println("Stock Balance" +Balance);
						
						PreparedStatement updater = myconn.prepareStatement(update);
						updater.setDouble(1,Balance);
						updater.setString(2,cbxChseCompany.getSelectedItem().toString() );
						updater.setString(3,denomination.getSelectedItem().toString() );
						updater.execute();
					}
						
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
		scrollPaneSales.setBounds(594, 103, 508, 272);
		panel.add(scrollPaneSales);
		
		Salestable = new JTable();
		Salestable.setBounds(1038, 431, -350, -280);
		//panel.add(Salestable);
		scrollPaneSales.setViewportView(Salestable);

		Connection myconn;
		try {
			myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			DateToStr = dateformat.format(curDate);
			PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from sales where date =? ");
			mystmt.setString(1,DateToStr.toString() );
			ResultSet myRs = mystmt.executeQuery();
			
			DefaultTableModel model;
			model = new DefaultTableModel();
			Salestable.setModel(model);
			Salestable.setShowVerticalLines(true);
			Salestable.setCellSelectionEnabled(true);
			Salestable.setColumnSelectionAllowed(true);
			//model.setColumnIdentifiers(myRs.getString("CompanyName"));
			companys = new ArrayList<>();
			model.addColumn(" ");
			while(myRs.next()){
				companys.add(myRs.getString("CompanyName"));
			
			model.addColumn(myRs.getString("CompanyName"));
			}
			model.addColumn("Grand Totals");
			
			PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where date = ? order by Denominations desc");
			mystmt1.setString(1,DateToStr.toString() );
			ResultSet myRs1 = mystmt1.executeQuery();
			ArrayList<String> deno = new ArrayList<>();
			while(myRs1.next()){
				deno.add(myRs1.getString("Denominations"));
				
				model.addRow(new Object[]{myRs1.getString("Denominations")});
		}
			//model.addRow(new Object[]{"CompanySales"});
				
			String val ="";
			
			int i,j;
		for(i=0; i<deno.size();i++){
			double companyTotal =0;
			double totalUnits = 0;
			for(j=0; j<companys.size();j++){
				PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where date =? and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
				mystmt2.setString(1,DateToStr.toString() );
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
			
			/*double sum = 0;
				
				sum = sum+Double.parseDouble(Salestable.getValueAt(i, 2).toString());
				model.setValueAt(sum,deno.size()+1,2);*/
			//model.setValueAt(model.getValueAt(i,j),deno.size(),companys.size()+1);
			//System.out.println(i);
		}

		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		
		JButton btnPrintSales = new JButton("Print");
		btnPrintSales.setIcon(new ImageIcon(Sales.class.getResource("/images/ic_print_black_24dp_1x.png")));
		btnPrintSales.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintSales.setBounds(809, 508, 100, 40);
		panel.add(btnPrintSales);
		btnPrintSales.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					MessageFormat header = new MessageFormat("Page Header");
					MessageFormat footer = new MessageFormat("Page(1,number,integer");
					
					Salestable.print(JTable.PrintMode.FIT_WIDTH,header,footer);
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}	
			}
		});
		
		String[] sal = {"Today","Yesterday","This Month","Last Month","Annual"};
		
		JComboBox<Object> cbxChseSales = new JComboBox<Object>(sal);
		cbxChseSales.setFont(new Font("Times New Roman", Font.ITALIC,18));
		cbxChseSales.setBounds(1104, 55, 180, 27);
		panel.add(cbxChseSales);		
		
		JLabel lblTotalSales = new JLabel("Total Sales:");
		lblTotalSales.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblTotalSales.setBounds(594, 386, 77, 28);
		panel.add(lblTotalSales);
		
		JLabel TotalSales = new JLabel("0.0");
		TotalSales.setFont(new Font("Times New Roman", Font.BOLD, 16));
		TotalSales.setBounds(682, 386, 90, 28);
		panel.add(TotalSales);
		
		JLabel Profit = new JLabel("0.0");
		Profit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		Profit.setBounds(830, 386, 90, 28);
		panel.add(Profit);
		
		JLabel lblProfit = new JLabel("Profit:");
		lblProfit.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblProfit.setBounds(782, 386, 50, 28);
		panel.add(lblProfit);
		
		TotalCost = new JLabel("0.0");
		TotalCost.setFont(new Font("Times New Roman", Font.BOLD, 16));
		TotalCost.setText(Double.toString(getSum()));
		TotalCost.setBounds(1012, 386, 90, 28);
		panel.add(TotalCost);
		
		JLabel lblTotalCost = new JLabel("Total Cost:");
		lblTotalCost.setForeground(Color.green);
		lblTotalCost.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblTotalCost.setBounds(925, 386, 77, 28);
		panel.add(lblTotalCost);
		cbxChseSales.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cbxChseSales.getSelectedItem() == "Today") {lblTodaySales.setText("Today's Sales");}
					
				else if(cbxChseSales.getSelectedItem() == "Yesterday") {lblTodaySales.setText("Yesterday's Sales"); YesterdayReport();}
					
				else if(cbxChseSales.getSelectedItem() == "This Month") {lblTodaySales.setText("This Month Sales");}
				
				else if(cbxChseSales.getSelectedItem() == "Last Month") {lblTodaySales.setText("Last Month Sales");}
				
				else if(cbxChseSales.getSelectedItem() == "Annual") {lblTodaySales.setText("Annual Sales"); AnnualReport();}
				
			}
		});
		
		
		
	}
	public static void textFormat(){
		boolean bool = true;
		int ndx = 0;
    
		NumberFormat cfLocal = NumberFormat.getCurrencyInstance();

		String sCurSymbol = "";

		DecimalFormatSymbols dfs = null;

		if( cfLocal instanceof DecimalFormat ){ // determine if symbol is prefix or suffix
		      dfs = ((DecimalFormat)cfLocal).getDecimalFormatSymbols();
		      sCurSymbol = dfs.getCurrencySymbol();      
		    }
		Number n = null;
		String sText = Double.toString(getSum());;
		    
		ndx = sText.indexOf( sCurSymbol );
		if( ndx == -1 ){ 
		      if( bool ){
		    	  sText = sCurSymbol + sText; }
		      else{
		    	  sText = sText + " " + sCurSymbol; }
		    }
		    try{
		      n = cfLocal.parse( sText );
		      TotalCost.setText( cfLocal.format( n ) );
		    }
		    catch( ParseException pe ) {
		    	TotalCost.setText( "" ); }
		}
	public static double getSum(){
		int rowcount = Salestable.getRowCount();
		double sum = 0;
		for(int i=0;i<rowcount;i++){
			
			sum = sum+Double.parseDouble(Salestable.getValueAt(i, companys.size()+1).toString());
		}
		return sum;
		
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
	public static void YesterdayReport(){
		
		calSD.setTime(curDate); // convert your date to Calendar object
	    int daysToDecrement = -2;
	    calSD.add(Calendar.DATE, daysToDecrement);
	    Date real_StartDate = calSD.getTime();
	    String sD = dateformat.format(real_StartDate);

		JOptionPane.showMessageDialog(null, "Yesterday's Date:  "+sD);

		    try {
		    	Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
				
				PreparedStatement mystmt = myconn.prepareStatement("select *from sales where date =?");
				mystmt.setString(1, sD);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded Yesterday");
				}else{
				
					try {
						myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
						mystmt = myconn.prepareStatement("select distinct companyName from sales where date=? ");
						mystmt.setString(1,sD );
						myRs = mystmt.executeQuery();
						
						DefaultTableModel model;
						model = new DefaultTableModel();
						Salestable.setModel(model);
						Salestable.setShowVerticalLines(true);
						Salestable.setCellSelectionEnabled(true);
						Salestable.setColumnSelectionAllowed(true);
						//model.setColumnIdentifiers(myRs.getString("CompanyName"));
						companys = new ArrayList<>();
						model.addColumn(" ");
						while(myRs.next()){
							companys.add(myRs.getString("CompanyName"));
						
						model.addColumn(myRs.getString("CompanyName"));
						}
						model.addColumn("Grand Totals");
						
						PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where date=? order by Denominations desc");
						mystmt1.setString(1,sD);
						ResultSet myRs1 = mystmt1.executeQuery();
						ArrayList<String> deno = new ArrayList<>();
						while(myRs1.next()){
							deno.add(myRs1.getString("Denominations"));
							
							model.addRow(new Object[]{myRs1.getString("Denominations")});
					}
						//model.addRow(new Object[]{"CompanySales"});
							
						String val ="";
						
						int i,j;
					for(i=0; i<deno.size();i++){
						double companyTotal =0;
						double totalUnits = 0;
						for(j=0; j<companys.size();j++){
							PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where date=? and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
							mystmt2.setString(1,sD);
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
					
					}
					textFormat();
					TotalCost.setText(Double.toString(getSum()));
					
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
						e.printStackTrace();
					}
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, "Error");
		        e.printStackTrace();
		    }

		
	}
	public static void AnnualReport(){
	
		calSD.setTime(curDate); // convert your date to Calendar object
	    int daysToDecrement = -1;
	    calSD.add(Calendar.DATE, daysToDecrement);
	    Date real_StartDate = calSD.getTime();
	    String sD = dateformat.format(real_StartDate);

		JOptionPane.showMessageDialog(null, sD);

		    Date endDate =  new Date();
		    Calendar calED = Calendar.getInstance();
		    calED.setTime(endDate); // convert your date to Calendar object
		    int daysToIncrement = 0;
		    calED.add(Calendar.DATE, daysToIncrement);
		    Date real_endDate = calED.getTime();
		    SimpleDateFormat sdF2 = new SimpleDateFormat("yyyy-MM-dd");
		    String eD = sdF2.format(real_endDate);
		    JOptionPane.showMessageDialog(null, eD);
		    try {
		    	Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
				
				PreparedStatement mystmt = myconn.prepareStatement("select *from sales where date =?");
				mystmt.setString(1, sD);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded Yesterday");
				}else{
					Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, "Error");
		        e.printStackTrace();
		    }

		
		/*Connection myconn;
		try {
			myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
			DateToStr = format.format(curDate);
			PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from sales where year(date)=year(?) ");
			mystmt.setString(1,DateToStr.toString() );
			ResultSet myRs = mystmt.executeQuery();
			
			DefaultTableModel model;
			model = new DefaultTableModel();
			Salestable.setModel(model);
			Salestable.setShowVerticalLines(true);
			Salestable.setCellSelectionEnabled(true);
			Salestable.setColumnSelectionAllowed(true);
			//model.setColumnIdentifiers(myRs.getString("CompanyName"));
			companys = new ArrayList<>();
			model.addColumn(" ");
			while(myRs.next()){
				companys.add(myRs.getString("CompanyName"));
			
			model.addColumn(myRs.getString("CompanyName"));
			}
			model.addColumn("Grand Totals");
			
			PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where year(date) =year(?) order by Denominations desc");
			mystmt1.setString(1,DateToStr.toString() );
			ResultSet myRs1 = mystmt1.executeQuery();
			ArrayList<String> deno = new ArrayList<>();
			while(myRs1.next()){
				deno.add(myRs1.getString("Denominations"));
				
				model.addRow(new Object[]{myRs1.getString("Denominations")});
		}
			//model.addRow(new Object[]{"CompanySales"});
				
			String val ="";
			
			int i,j;
		for(i=0; i<deno.size();i++){
			double companyTotal =0;
			double totalUnits = 0;
			for(j=0; j<companys.size();j++){
				PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where year(date) =year(?) and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
				mystmt2.setString(1,DateToStr.toString() );
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
		
		}

		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}*/
	}
}

