package airtime;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameListener;

public class Settings extends MainMDI implements InternalFrameListener {
	private static JTextField txtFieldCompanyName;
	private static JTextField txtFldCompanyProfit;
	private JTextField txtFldEdtProfit;
	private static Connection myconn;
	static PreparedStatement mystmt;
	private ResultSet myRs;
	JComboBox<String> comboBoxEdtCompany;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new Settings();
			}
		});
	}
		

	public Settings() {
		
		JInternalFrame internalFrameCompany = new JInternalFrame("Setting Up Company Details",true,true,true);
		internalFrameCompany.setFrameIcon(new ImageIcon(Settings.class.getResource("/images/ic_settings_input_component_black_18dp_1x.png")));
		internalFrameCompany.setBounds(10, 0, 414, 229);
		internalFrameCompany.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		internalFrameCompany.setSize(1340, 700);
		internalFrameCompany.setVisible(true);
		desktopPane.add(internalFrameCompany);
		internalFrameCompany.getContentPane().setLayout(null);
				
		JLabel lblAddCompanyDetails = new JLabel("Add Company Details");
		lblAddCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblAddCompanyDetails.setBounds(235, 123, 202, 25);
		internalFrameCompany.getContentPane().add(lblAddCompanyDetails);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblCompanyName.setBounds(121, 196, 180, 25);
		internalFrameCompany.getContentPane().add(lblCompanyName);
		
		JLabel lblCompanyProfit = new JLabel("Company Profit:");
		lblCompanyProfit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblCompanyProfit.setBounds(121, 252, 202, 25);
		internalFrameCompany.getContentPane().add(lblCompanyProfit);
		
		txtFieldCompanyName = new JTextField();
		txtFieldCompanyName.setBounds(311, 196, 245, 25);
		internalFrameCompany.getContentPane().add(txtFieldCompanyName);
		txtFieldCompanyName.setColumns(10);
		
		txtFldCompanyProfit = new JTextField();
		txtFldCompanyProfit.setColumns(10);
		txtFldCompanyProfit.setBounds(311, 252, 245, 25);
		internalFrameCompany.getContentPane().add(txtFldCompanyProfit);
				
		comboBoxEdtCompany = new JComboBox<String>();
		comboBoxEdtCompany.setBounds(937, 193, 245, 25);
		comboBoxEdtCompany.addItem("-Select Company-");
		comboBoxEdtCompany.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		internalFrameCompany.getContentPane().add(comboBoxEdtCompany);
				try
				{
									
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
					
					mystmt = myconn.prepareStatement("select CompanyName from company");
					//Statement mystmt= myconn.createStatement();							
					myRs = mystmt.executeQuery();
					
					  while(myRs.next())
					  {
						
					  	comboBoxEdtCompany.addItem(myRs.getString("CompanyName"));
					  }		
					  mystmt.close();
					  System.out.println("Displays Company Name on ComboBoxEdtCompany");
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			
		JLabel lblEdtCompanyName = new JLabel("Company Name:");
		lblEdtCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEdtCompanyName.setBounds(747, 193, 180, 25);
		internalFrameCompany.getContentPane().add(lblEdtCompanyName);
		
		JButton btnEditCompany = new JButton("Update");
		btnEditCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_system_update_alt_black_24dp_1x.png")));
		btnEditCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnEditCompany.setBounds(944, 367, 105, 33);
		internalFrameCompany.getContentPane().add(btnEditCompany);
		btnEditCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxEdtCompany.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
				}else if (txtFldEdtProfit.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Profit is not declared");
				}else{
					try{
						Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
						
						mystmt = myconn.prepareStatement("update company set CompanyProfit =? where CompanyName =?");
						mystmt.setString(1,txtFldEdtProfit.getText().toString());
						mystmt.setString(2,comboBoxEdtCompany.getSelectedItem().toString());
											
						mystmt.execute();
						JOptionPane.showMessageDialog(null, comboBoxEdtCompany.getSelectedItem().toString()+ " Profit has changed to  " + txtFldEdtProfit.getText().toString());
						mystmt.close();
						System.out.println("Edited");
										
						comboBoxEdtCompany.setSelectedItem("-Select Company-");
						txtFldEdtProfit.setText(null);
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, e);
						comboBoxEdtCompany.setSelectedItem("-Select Company-");
						txtFldEdtProfit.setText(null);
						e.printStackTrace();
					}
			}
			}
		});
		
		JButton btnDeleteCompany = new JButton("Delete");
		btnDeleteCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_delete_forever_black_24dp_1x.png")));
		btnDeleteCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnDeleteCompany.setBounds(1092, 367, 105, 33);
		internalFrameCompany.getContentPane().add(btnDeleteCompany);
		btnDeleteCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxEdtCompany.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");}
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete  " + comboBoxEdtCompany.getSelectedItem().toString() + "  from the database"," Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				 
				if (response == JOptionPane.NO_OPTION) {
					 System.out.println("Not Deleted");
				      
				    } else if (response == JOptionPane.YES_OPTION) {
				     
				
				try{
					Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
					
					mystmt = myconn.prepareStatement("delete from company where CompanyName =?");
					mystmt.setString(1,comboBoxEdtCompany.getSelectedItem().toString());
										
					mystmt.execute();
					JOptionPane.showMessageDialog(null, "Company Name  " + comboBoxEdtCompany.getSelectedItem().toString() +"  has been deleted");
					
					mystmt.close();
					System.out.println("Deleted");
					comboBoxEdtCompany.removeAllItems();
					comboBoxEdtCompany.addItem("-Select Company-");
					addToComboEdit();
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
			}
			}
			
		});
		
		JButton btnAddCompany = new JButton("Add");
		btnAddCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_create_new_folder_black_24dp_1x.png")));
		btnAddCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAddCompany.setBounds(235, 367, 90, 33);
		internalFrameCompany.getContentPane().add(btnAddCompany);
		btnAddCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtFieldCompanyName.getText().equals("") && txtFldCompanyProfit.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Company Name and Profit required");
				}else if (txtFieldCompanyName.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
				}else if (txtFldCompanyProfit.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Profit is not declared");
				}else{
			try{
				myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime?autoReconnect=true&useSSL=false","root","Mbugua21");
				
				
				String query = "insert into company (CompanyName,CompanyProfit,Date) values (?,?,?)";
				
				Date curDate = new Date();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

				String DateToStr = format.format(curDate);
				System.out.println(DateToStr);
			
				mystmt = myconn.prepareStatement(query);
				mystmt.setString(1,txtFieldCompanyName.getText() );
				mystmt.setString(2,txtFldCompanyProfit.getText() );
				mystmt.setString(3,DateToStr.toString() );
				
				mystmt.execute();
				 JOptionPane.showMessageDialog(null, "Data Saved");
				mystmt.close();
				System.out.println("Company Name Saved");
				comboBoxEdtCompany.removeAllItems();
				comboBoxEdtCompany.addItem("-Select Company-");
				addToComboEdit();
				txtFieldCompanyName.setText(null);
				txtFldCompanyProfit.setText(null);
			}
			catch(Exception e){
				 JOptionPane.showMessageDialog(null,"Company Name Mentioned Already Exists","Duplicate Entry Alert",1);
				 txtFieldCompanyName.setText(null);
				 txtFldCompanyProfit.setText(null);
				e.printStackTrace();}
				}
			}
	
	
					
		});

				
		JLabel lblEditCompanyDetails = new JLabel("Edit Company Details");
		lblEditCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEditCompanyDetails.setBounds(858, 123, 202, 25);
		internalFrameCompany.getContentPane().add(lblEditCompanyDetails);
		
		JLabel lblSettingCompanyDetails = new JLabel("Setting Up Company Details");
		lblSettingCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblSettingCompanyDetails.setBounds(527, 51, 245, 25);
		internalFrameCompany.getContentPane().add(lblSettingCompanyDetails);
		
		JLabel lblEdtProfit = new JLabel("Edit Profit:");
		lblEdtProfit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEdtProfit.setBounds(747, 252, 202, 25);
		internalFrameCompany.getContentPane().add(lblEdtProfit);
		
		txtFldEdtProfit = new JTextField();
		txtFldEdtProfit.setColumns(10);
		txtFldEdtProfit.setBounds(937, 252, 245, 25);
		internalFrameCompany.getContentPane().add(txtFldEdtProfit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setBounds(376, 367, 105, 33);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				internalFrameCompany.dispose();	
				new Sales();
				close();
				
			}
			
		});
		internalFrameCompany.getContentPane().add(btnCancel);
		
		JButton btnCheckStatus = new JButton("Check Status");
		btnCheckStatus.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_receipt_black_24dp_1x.png")));
		btnCheckStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnCheckStatus.setBounds(629, 367, 129, 33);
		internalFrameCompany.getContentPane().add(btnCheckStatus);
		btnCheckStatus.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CheckStatus.main(null);				
			}
			
		});
		
	}
	protected void close() {
		//WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		//Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
	protected void  addToComboEdit(){
	try
	{				
		Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime","root","Mbugua21");
		
		mystmt = myconn.prepareStatement("select CompanyName from company");							
		myRs = mystmt.executeQuery();
		
		  while(myRs.next())
		  {			
		  	comboBoxEdtCompany.addItem(myRs.getString("CompanyName"));
		  }		
		  mystmt.close();
		  System.out.println("Displays Added Company Name on ComboBoxEdtCompany");
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	}	
		
	}

