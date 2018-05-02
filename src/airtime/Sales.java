package airtime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;

public class Sales extends MainMDI implements InternalFrameListener {
	private JTextField txtFldEnterUnits;
	private static JTable Salestable;
	//static DefaultTableModel model = new DefaultTableModel();
	static JLabel TotalCost,TotalSales,Profit;
	JComboBox<Object> cbxChseCompany;
	JComboBox<Object> denomination;
	static ArrayList<String> companys;
	static ArrayList<String> deno;
	static String DateToStr;
	static Date curDate = new Date();
	static DecimalFormat df2 = new DecimalFormat(".##");
	static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
	
    static Calendar calSD = Calendar.getInstance();
    static Connection myconn = null;
    static double sum =0;
	public static void main(String[] arg){
		new Sales();
		textFormat();
		getSum();
	}

	public Sales() {
		myconn = JConnection.ConnecrDb();	
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
		txtFldEnterUnits.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char ch = evt.getKeyChar();
				if(!Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE)){
					Toolkit.getDefaultToolkit().beep();
				   	evt.consume();
				JOptionPane.showMessageDialog(null, "Cannot Accept Letters");}
			}
		});
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
					JOptionPane.showMessageDialog(null, "Units Cannot be Equal to Zero");}
				else {
					try{
						String insert = "insert into sales (CompanyName,Denominations,Units,Date) values (?,?,?,?)";
						String select = "select Units from stocks where CompanyName=? and Denominations=?";
						String update = "update stocks Set Units=? where CompanyName=? and Denominations=?";
						
						DateToStr = dateformat.format(curDate);
						System.out.println(DateToStr);
											
						double Balance;
						PreparedStatement seta = myconn.prepareStatement(select);
						seta.setString(1,cbxChseCompany.getSelectedItem().toString() );
						seta.setString(2,denomination.getSelectedItem().toString() );
						ResultSet myRs = seta.executeQuery();
						if(!myRs.next()){						
							JOptionPane.showMessageDialog(internalFrameSales, "No Stock for  "+cbxChseCompany.getSelectedItem()+ "  Denomination "+denomination.getSelectedItem());
						}else{

						int StockUnits = myRs.getInt("Units");
						System.out.println("Current Stock "+StockUnits);
						if(StockUnits == 0){
							PreparedStatement mystmt = myconn.prepareStatement("delete from stocks where CompanyName = ? and Denominations=?");
							mystmt.setString(1,cbxChseCompany.getSelectedItem().toString());
							mystmt.setString(2,denomination.getSelectedItem().toString());
							mystmt.execute();
							System.out.println("Record Deleted");
						}
						String convert = txtFldEnterUnits.getText();
						int SalesUnits = Integer.parseInt(convert);
						System.out.println("Number Units sold "+SalesUnits);
						
						Balance = StockUnits - SalesUnits;
						System.out.println("Stock Balance " +Balance);
						if(Balance < 0){
							JOptionPane.showMessageDialog(null, "Current Stock for "+cbxChseCompany.getSelectedItem()+"  Denomination "+denomination.getSelectedItem()+" is: "+StockUnits);
						}else{
						
						PreparedStatement inserter = myconn.prepareStatement(insert);
						inserter.setString(1,cbxChseCompany.getSelectedItem().toString() );
						inserter.setString(2,denomination.getSelectedItem().toString() );
						inserter.setString(3,txtFldEnterUnits.getText() );
						inserter.setString(4,DateToStr.toString() );
						inserter.execute();
						JOptionPane.showMessageDialog(null, "Sales Saved");
						System.out.println("Sales Saved");
						inserter.close();
						
						PreparedStatement updater = myconn.prepareStatement(update);
						updater.setDouble(1,Balance);
						updater.setString(2,cbxChseCompany.getSelectedItem().toString() );
						updater.setString(3,denomination.getSelectedItem().toString() );
						updater.execute();
						System.out.println("Record Updated");
						}
					} 
						
						TodaysReport();
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
		scrollPaneSales.setBounds(594, 103, 632, 334);
		panel.add(scrollPaneSales);
		
		Salestable = new JTable();
		Salestable.setRowHeight(30);
		Salestable.setBounds(1038, 431, -350, -280);
		scrollPaneSales.setViewportView(Salestable);
		try {
			DateToStr = dateformat.format(curDate);
			PreparedStatement mystmt = myconn.prepareStatement("select distinct companyName from sales where date =? ");
			mystmt.setString(1,DateToStr.toString() );
			ResultSet myRs = mystmt.executeQuery();
			DefaultTableModel model = new DefaultTableModel();
			Salestable.setModel(model);
			Salestable.setShowVerticalLines(true);
			Salestable.setCellSelectionEnabled(true);
			Salestable.setColumnSelectionAllowed(true);
			Salestable.setBackground(Color.WHITE);
			Salestable.setForeground(Color.BLACK);
			Salestable.setFont(new Font("Times New Roman", Font.BOLD, 16));
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
			deno = new ArrayList<>();
			while(myRs1.next()){
				deno.add(myRs1.getString("Denominations"));
				
				model.addRow(new Object[]{myRs1.getString("Denominations")});
		}
			model.addRow(new Object[]{"Total Sales"});
			model.addRow(new Object[]{"Profit"});
			model.addRow(new Object[]{"Cost Price"});
				
			String val ="";
			
			//double sum =0;
			int i,j;
		for(i=0; i<deno.size();i++){
			double totalUnits = 0;
			//double Tsales =0;
			for(j=0; j<companys.size();j++){
				PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where date =? and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
				mystmt2.setString(1,DateToStr.toString() );
				ResultSet myRs2 = mystmt2.executeQuery();
				TableColumn column = Salestable.getColumnModel().getColumn(0);
		        column.setPreferredWidth(110);	
				while(myRs2.next()){
					val=myRs2.getString("Units");
					if(val!= null){
						val=myRs2.getString("Units");	
					}else {val="0";}					
					}
				model.setValueAt(val,i, j+1);
				totalUnits = totalUnits+ Double.parseDouble(val);
				model.setValueAt(totalUnits *Double.parseDouble(deno.get(i)),i, companys.size()+1);
				
				//Tsales = Tsales+Double.parseDouble(model.getValueAt(i, j+1).toString());
				//model.setValueAt(Tsales,deno.size() ,companys.size()+1);
			}			
		}
		for(j=0;j<companys.size();j++){
			for(i=0;i<deno.size();i++){
				
				sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
				//sCurSymbol = dfs.getCurrencySymbol();
				model.setValueAt(sum,deno.size(),j+1);
				
				PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
				pro.setString(1,companys.get(j));
				ResultSet myPro = pro.executeQuery();
				if(!myPro.next()){
					System.out.println("No column sales");
				}else{
				double cost =myPro.getInt("CompanyProfit");
				cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
				model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
				cost = Double.valueOf(df2.format(cost));
				System.out.println("Sum of cost "+j+ "-> " +cost);
				
				double profit = sum-cost;
				model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
				profit = Double.valueOf(df2.format(profit));
				System.out.println("Sum of Profit "+j+ "-> " +profit);}
			}
			System.out.println("Sum of Sales "+j+ "-> " +sum);
			System.out.println(companys.get(j));
			sum=0;
		}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		
		JButton btnPrintSales = new JButton("Print");
		btnPrintSales.setIcon(new ImageIcon(Sales.class.getResource("/images/ic_print_black_24dp_1x.png")));
		btnPrintSales.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintSales.setBounds(809, 541, 100, 40);
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
		lblTotalSales.setBounds(594, 441, 77, 28);
		panel.add(lblTotalSales);
		
		TotalSales = new JLabel("0.0");
		TotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		TotalSales.setFont(new Font("Times New Roman", Font.BOLD, 16));
		TotalSales.setBounds(682, 441, 90, 28);
		panel.add(TotalSales);
		
		Profit = new JLabel("0.0");
		Profit.setHorizontalAlignment(SwingConstants.RIGHT);
		Profit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		Profit.setBounds(866, 441, 90, 28);
		panel.add(Profit);
		
		JLabel lblProfit = new JLabel("Profit:");
		lblProfit.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblProfit.setBounds(806, 441, 50, 28);
		panel.add(lblProfit);
		
		TotalCost = new JLabel("0.0");
		TotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
		TotalCost.setFont(new Font("Times New Roman", Font.BOLD, 16));
		TotalCost.setBounds(1099, 441, 90, 28);
		panel.add(TotalCost);
		
		JLabel lblTotalCost = new JLabel("Total Cost:");
		lblTotalCost.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblTotalCost.setBounds(1012, 441, 77, 28);
		panel.add(lblTotalCost);
		cbxChseSales.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cbxChseSales.getSelectedItem() == "Today") {lblTodaySales.setText("Today's Sales");TodaysReport();}
					
				else if(cbxChseSales.getSelectedItem() == "Yesterday") {lblTodaySales.setText("Yesterday's Sales"); YesterdayReport();}
					
				else if(cbxChseSales.getSelectedItem() == "This Month") {lblTodaySales.setText("This Month Sales"); ThisMonthReport();}
				
				else if(cbxChseSales.getSelectedItem() == "Last Month") {lblTodaySales.setText("Last Month Sales");LastMonthReport();}
				
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
		//String sText = Double.toString(getSum());;
		String sText ="";    
		ndx = sText.indexOf(sCurSymbol);
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
	public static void getSum(){
		double sum = 0;
		double cost =0;
		double profit=0;
			for(int j=0;j<deno.size();j++){
				
				sum = sum+Double.parseDouble(Salestable.getValueAt(j, companys.size()+1).toString());
				TotalSales.setText(""+sum);
				System.out.println("Sum inner is" +sum);
				
				cost =cost+(Double.parseDouble(Salestable.getValueAt(deno.size()+2,j+1).toString()));
				System.out.println("Cost is" +cost);
				TotalCost.setText(""+cost);
				
				profit = sum-cost;
				Profit.setText(""+profit);
				System.out.println("Profit inner is" +profit);
				
			}
			sum=0;
			cost=0;
			profit =0;
		}
	public static void TodaysReport(){
		 
		    try {				
				PreparedStatement mystmt = myconn.prepareStatement("select *from sales where Date = (?)");
				mystmt.setString(1, DateToStr);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded Today !!");
					System.out.println("No Record Today");
					Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
				}else{
				
					try {
						mystmt = myconn.prepareStatement("select distinct companyName from sales where Date = (?)");
						mystmt.setString(1,DateToStr);
						myRs = mystmt.executeQuery();
						
						DefaultTableModel model = new DefaultTableModel();
						Salestable.setModel(model);
						Salestable.setShowVerticalLines(true);
						Salestable.setCellSelectionEnabled(true);
						Salestable.setColumnSelectionAllowed(true);
						companys = new ArrayList<>();
						model.addColumn(" ");
						while(myRs.next()){
							companys.add(myRs.getString("CompanyName"));
						
						model.addColumn(myRs.getString("CompanyName"));
						}
						model.addColumn("Grand Totals");
						
						PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where Date = (?) order by Denominations desc");
						mystmt1.setString(1,DateToStr);
						ResultSet myRs1 = mystmt1.executeQuery();
						ArrayList<String> deno = new ArrayList<>();
						while(myRs1.next()){
							deno.add(myRs1.getString("Denominations"));
							
							model.addRow(new Object[]{myRs1.getString("Denominations")});
					}
						model.addRow(new Object[]{"Total Sales"});
						model.addRow(new Object[]{"Profit"});
						model.addRow(new Object[]{"Cost Price"});
						String val ="";
						
						int i,j;
					for(i=0; i<deno.size();i++){
						double totalUnits = 0;
						for(j=0; j<companys.size();j++){
							PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where Date = (?) and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
							mystmt2.setString(1,DateToStr);
							ResultSet myRs2 = mystmt2.executeQuery();
							TableColumn column = Salestable.getColumnModel().getColumn(0);
					        column.setPreferredWidth(110);
							
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
					for(j=0;j<companys.size();j++){
						for(i=0;i<deno.size();i++){
							sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
							model.setValueAt(sum,deno.size(),j+1);
							
							PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
							pro.setString(1,companys.get(j));
							ResultSet myPro = pro.executeQuery();
							if(!myPro.next()){
								System.out.println("No column sales");
							}else{
							double cost =myPro.getInt("CompanyProfit");
							cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
							model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
							cost = Double.valueOf(df2.format(cost));
							System.out.println("Sum of cost "+j+ "-> " +cost);
							
							double profit = sum-cost;
							model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
							profit = Double.valueOf(df2.format(profit));
							System.out.println("Sum of Profit "+j+ "-> " +profit);}
						}
						System.out.println("Sum of col "+j+ "-> " +sum);
						sum=0;
					}
					System.out.println("Record For Today");
					//textFormat();
					//TotalCost.setText(Double.toString(getSum()));
					
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
						e.printStackTrace();
					}
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null,e, "Error",0);
		        e.printStackTrace();
		    }
	}
	public static void YesterdayReport(){
		
		calSD.setTime(curDate); // convert your date to Calendar object
	    int daysToDecrement = -1;
	    calSD.add(Calendar.DATE, daysToDecrement);
	    Date real_StartDate = calSD.getTime();
	    SimpleDateFormat yesterdayformat = new SimpleDateFormat("yyyy/MM/dd");
	    String sD = yesterdayformat.format(real_StartDate);

		JOptionPane.showMessageDialog(null, "Yesterday's Date:  "+sD);

		    try {				
				PreparedStatement mystmt = myconn.prepareStatement("select *from sales where date =?");
				mystmt.setString(1, sD);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded Yesterday!!");
					System.out.println("No Record Yesterday");
					Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
				}else{
				
					try {
						mystmt = myconn.prepareStatement("select distinct companyName from sales where date=? ");
						mystmt.setString(1,sD );
						myRs = mystmt.executeQuery();
						
						DefaultTableModel model = new DefaultTableModel();
						Salestable.setModel(model);
						Salestable.setShowVerticalLines(true);
						Salestable.setCellSelectionEnabled(true);
						Salestable.setColumnSelectionAllowed(true);
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
						model.addRow(new Object[]{"Total Sales"});
						model.addRow(new Object[]{"Profit"});
						model.addRow(new Object[]{"Cost Price"});	
						String val ="";
						
						int i,j;
					for(i=0; i<deno.size();i++){
						double totalUnits = 0;
						for(j=0; j<companys.size();j++){
							PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where date=? and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
							mystmt2.setString(1,sD);
							ResultSet myRs2 = mystmt2.executeQuery();
							TableColumn column = Salestable.getColumnModel().getColumn(0);
					        column.setPreferredWidth(110);
							
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
					for(j=0;j<companys.size();j++){
						for(i=0;i<deno.size();i++){
							sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
							model.setValueAt(sum,deno.size(),j+1);
							
							PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
							pro.setString(1,companys.get(j));
							ResultSet myPro = pro.executeQuery();
							if(!myPro.next()){
								System.out.println("No column sales");
							}else{
							double cost =myPro.getInt("CompanyProfit");
							cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
							model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
							cost = Double.valueOf(df2.format(cost));
							System.out.println("Sum of cost "+j+ "-> " +cost);
							
							double profit = sum-cost;
							model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
							profit = Double.valueOf(df2.format(profit));
							System.out.println("Sum of Profit "+j+ "-> " +profit);}
						}
						System.out.println("Sum of col "+j+ "-> " +sum);
						sum=0;
					}
					System.out.println("Record Yesterday");
					textFormat();
					getSum();
					//TotalCost.setText();
					
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
						e.printStackTrace();
					}
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e,"Error", 0);
		        e.printStackTrace();
		    }	
	}
public static void ThisMonthReport(){
		
		calSD.setTime(curDate); // convert your date to Calendar object
	    calSD.add(Calendar.MONTH,0);
	    Date real_StartDate = calSD.getTime();
	    SimpleDateFormat yesterdayformat = new SimpleDateFormat("MM");
	    String sD = yesterdayformat.format(real_StartDate);

		JOptionPane.showMessageDialog(null, "This Month:  "+sD);

		    try {				
				PreparedStatement mystmt = myconn.prepareStatement("select *from sales where MONTH(Date) = MONTH(?)");
				mystmt.setString(1, DateToStr);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded This Month!!");
					System.out.println("Record This Month");
					Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
				}else{
				
					try {
						mystmt = myconn.prepareStatement("select distinct companyName from sales where MONTH(Date) = MONTH(?)");
						mystmt.setString(1,DateToStr);
						myRs = mystmt.executeQuery();
						
						DefaultTableModel model = new DefaultTableModel();
						Salestable.setModel(model);
						Salestable.setShowVerticalLines(true);
						Salestable.setCellSelectionEnabled(true);
						Salestable.setColumnSelectionAllowed(true);
						companys = new ArrayList<>();
						model.addColumn(" ");
						while(myRs.next()){
							companys.add(myRs.getString("CompanyName"));
						
						model.addColumn(myRs.getString("CompanyName"));
						}
						model.addColumn("Grand Totals");
						
						PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where MONTH(Date) = MONTH(?) order by Denominations desc");
						mystmt1.setString(1,DateToStr);
						ResultSet myRs1 = mystmt1.executeQuery();
						ArrayList<String> deno = new ArrayList<>();
						while(myRs1.next()){
							deno.add(myRs1.getString("Denominations"));
							
							model.addRow(new Object[]{myRs1.getString("Denominations")});
					}
						model.addRow(new Object[]{"Total Sales"});
						model.addRow(new Object[]{"Profit"});
						model.addRow(new Object[]{"Cost Price"});
						String val ="";
						
						int i,j;
					for(i=0; i<deno.size();i++){
						double totalUnits = 0;
						for(j=0; j<companys.size();j++){
							PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where MONTH(Date) = MONTH(?) and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
							mystmt2.setString(1,DateToStr);
							ResultSet myRs2 = mystmt2.executeQuery();
							TableColumn column = Salestable.getColumnModel().getColumn(0);
					        column.setPreferredWidth(110);
							
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
					for(j=0;j<companys.size();j++){
						for(i=0;i<deno.size();i++){
							sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
							model.setValueAt(sum,deno.size(),j+1);
							
							PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
							pro.setString(1,companys.get(j));
							ResultSet myPro = pro.executeQuery();
							if(!myPro.next()){
								System.out.println("No column sales");
							}else{
							double cost =myPro.getInt("CompanyProfit");
							cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
							model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
							cost = Double.valueOf(df2.format(cost));
							System.out.println("Sum of cost "+j+ "-> " +cost);
							
							double profit = sum-cost;
							model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
							profit = Double.valueOf(df2.format(profit));
							System.out.println("Sum of Profit "+j+ "-> " +profit);}
						}
						System.out.println("Sum of col "+j+ "-> " +sum);
						sum=0;
					}
					System.out.println("Record This Month");
					textFormat();
					//TotalCost.setText(Double.toString(getSum()));
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
						e.printStackTrace();
					}
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null,e, "Error",0);
		        e.printStackTrace();
		    }

		
	}
public static void LastMonthReport(){
	
	calSD.setTime(curDate); // convert your date to Calendar object
	int decrement =-1;
    calSD.add(Calendar.MONTH,decrement);
    Date real_StartDate = calSD.getTime();
    SimpleDateFormat yesterdayformat = new SimpleDateFormat("MM");
    String sD = yesterdayformat.format(real_StartDate);

	JOptionPane.showMessageDialog(null, "Last Month:  "+sD);

	    try {			
			PreparedStatement mystmt = myconn.prepareStatement("select *from sales where MONTH(Date) = MONTH(?)-1");
			mystmt.setString(1, DateToStr);
			ResultSet myRs = mystmt.executeQuery();
			if(!myRs.first()){
				JOptionPane.showMessageDialog(null, "No Sales Recorded Last Month!!");
				System.out.println("No Record Last Month");
				Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
			}else{
			
				try {
					mystmt = myconn.prepareStatement("select distinct companyName from sales where MONTH(Date) = MONTH(?)-1");
					mystmt.setString(1,DateToStr);
					myRs = mystmt.executeQuery();
					
					DefaultTableModel model = new DefaultTableModel();
					Salestable.setModel(model);
					Salestable.setShowVerticalLines(true);
					Salestable.setCellSelectionEnabled(true);
					Salestable.setColumnSelectionAllowed(true);
					companys = new ArrayList<>();
					model.addColumn(" ");
					while(myRs.next()){
						companys.add(myRs.getString("CompanyName"));
					
					model.addColumn(myRs.getString("CompanyName"));
					}
					model.addColumn("Grand Totals");
					
					PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where MONTH(Date) = MONTH(?)-1 order by Denominations desc");
					mystmt1.setString(1,DateToStr);
					ResultSet myRs1 = mystmt1.executeQuery();
					ArrayList<String> deno = new ArrayList<>();
					while(myRs1.next()){
						deno.add(myRs1.getString("Denominations"));
						
						model.addRow(new Object[]{myRs1.getString("Denominations")});
				}
					model.addRow(new Object[]{"Total Sales"});
					model.addRow(new Object[]{"Profit"});
					model.addRow(new Object[]{"Cost Price"});
					String val ="";
					
					int i,j;
				for(i=0; i<deno.size();i++){
					double totalUnits = 0;
					for(j=0; j<companys.size();j++){
						PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where MONTH(Date) = MONTH(?)-1 and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
						mystmt2.setString(1,DateToStr);
						ResultSet myRs2 = mystmt2.executeQuery();
						TableColumn column = Salestable.getColumnModel().getColumn(0);
				        column.setPreferredWidth(110);
						
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
				for(j=0;j<companys.size();j++){
					for(i=0;i<deno.size();i++){
						sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
						model.setValueAt(sum,deno.size(),j+1);
						
						PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
						pro.setString(1,companys.get(j));
						ResultSet myPro = pro.executeQuery();
						if(!myPro.next()){
							System.out.println("No column sales");
						}else{
						double cost =myPro.getInt("CompanyProfit");
						cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
						model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
						cost = Double.valueOf(df2.format(cost));
						System.out.println("Sum of cost "+j+ "-> " +cost);
						
						double profit = sum-cost;
						model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
						profit = Double.valueOf(df2.format(profit));
						System.out.println("Sum of Profit "+j+ "-> " +profit);}
					}
					System.out.println("Sum of col "+j+ "-> " +sum);
					sum=0;
				}
				System.out.println("Record Last Month");
				textFormat();
				//TotalCost.setText(Double.toString(getSum()));
				
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
			}
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null,e, "Error",0);
	        e.printStackTrace();
	    }	
}
	public static void AnnualReport(){
	
		calSD.setTime(curDate); // convert your date to Calendar object
	    int daysToDecrement = 0;
	    calSD.add(Calendar.YEAR, daysToDecrement);
	    Date real_StartDate = calSD.getTime();
	    SimpleDateFormat sdF2 = new SimpleDateFormat("yyyy");
	    String sD = sdF2.format(real_StartDate);

		JOptionPane.showMessageDialog(null, "This Year:  "+sD);
		    try {
		    					
				PreparedStatement mystmt = myconn.prepareStatement("select * from sales where YEAR(Date) = YEAR(?)");
				mystmt.setString(1, DateToStr);
				ResultSet myRs = mystmt.executeQuery();
				if(!myRs.first()){
					JOptionPane.showMessageDialog(null, "No Sales Recorded This Year!!");
					System.out.println("No Record Annual");
					Salestable.setModel(DbUtils.resultSetToTableModel(myRs));
				}else{
					try {
						mystmt = myconn.prepareStatement("select distinct companyName from sales where YEAR(Date) = YEAR(?)");
						mystmt.setString(1,DateToStr);
						myRs = mystmt.executeQuery();
						
						DefaultTableModel model = new DefaultTableModel();
						Salestable.setModel(model);
						Salestable.setShowVerticalLines(true);
						Salestable.setCellSelectionEnabled(true);
						Salestable.setColumnSelectionAllowed(true);
				       
						companys = new ArrayList<>();
						model.addColumn(" ");
						while(myRs.next()){
							companys.add(myRs.getString("CompanyName"));
						
						model.addColumn(myRs.getString("CompanyName"));
						}
						model.addColumn("Grand Totals");
						
						PreparedStatement mystmt1 = myconn.prepareStatement("select distinct Denominations from sales  where YEAR(Date) = YEAR(?) order by Denominations desc");
						mystmt1.setString(1,DateToStr);
						ResultSet myRs1 = mystmt1.executeQuery();
						ArrayList<String> deno = new ArrayList<>();
						while(myRs1.next()){
							deno.add(myRs1.getString("Denominations"));
							
							model.addRow(new Object[]{myRs1.getString("Denominations")});
					}
						model.addRow(new Object[]{"Total Sales"});
						model.addRow(new Object[]{"Profit"});
						model.addRow(new Object[]{"Cost Price"});
							
						String val ="";
						
						int i,j;
					for(i=0; i<deno.size();i++){
						double totalUnits = 0;
						for(j=0; j<companys.size();j++){
							PreparedStatement mystmt2 = myconn.prepareStatement("select sum(Units) AS Units from sales where YEAR(Date) = YEAR(?) and CompanyName ='"+companys.get(j)+"' and Denominations = "+deno.get(i));
							mystmt2.setString(1,DateToStr);
							ResultSet myRs2 = mystmt2.executeQuery();
							TableColumn column = Salestable.getColumnModel().getColumn(0);
					        column.setPreferredWidth(110);
					       
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
					for(j=0;j<companys.size();j++){
						for(i=0;i<deno.size();i++){
							sum = sum+(Double.parseDouble(model.getValueAt(i, j+1).toString())*Double.parseDouble(deno.get(i)));
							model.setValueAt(sum,deno.size(),j+1);
							
							PreparedStatement pro = myconn.prepareStatement("select distinct sales.CompanyName,company.CompanyProfit from sales,company where sales.CompanyName=? and company.CompanyName=sales.CompanyName");
							pro.setString(1,companys.get(j));
							ResultSet myPro = pro.executeQuery();
							if(!myPro.next()){
								System.out.println("No column sales");
							}else{
							double cost =myPro.getInt("CompanyProfit");
							cost = cost*(Double.parseDouble(model.getValueAt(deno.size(),j+1).toString())/100);
							model.setValueAt(Double.valueOf(df2.format(cost)),deno.size()+2,j+1);
							cost = Double.valueOf(df2.format(cost));
							System.out.println("Sum of cost "+j+ "-> " +cost);
							
							double profit = sum-cost;
							model.setValueAt(Double.valueOf(df2.format(profit)),deno.size()+1,j+1);
							profit = Double.valueOf(df2.format(profit));
							System.out.println("Sum of Profit "+j+ "-> " +profit);}
						}
						System.out.println("Sum of col "+j+ "-> " +sum);
						sum=0;
					}
					System.out.println("Record Annual");
					textFormat();
					TotalCost.setText(Double.toString(sum));
										
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
						e.printStackTrace();
					}	
				}
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e,"Error",0);
		        e.printStackTrace();
		    }		
	}
}

