package airtime;

import java.awt.Color;
import java.awt.EventQueue;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameListener;

public class Settings extends MainMDI implements InternalFrameListener {
	private static JTextField txtFieldCompanyName;
	private static JTextField txtFldCompanyProfit;
	private JTextField txtFldEdtProfit;
	private static Connection myconn = null;
	static PreparedStatement mystmt;
	private ResultSet myRs;
	JButton btnAddCompany;
	JComboBox<String> comboBoxEdtCompany,cmbWSaler;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new Settings();
			}
		});
	}
		

	public Settings() {
		myconn = JConnection.ConnecrDb();
		JInternalFrame internalFrameCompany = new JInternalFrame("Setting Up Company Details",true,true,true);
		internalFrameCompany.setFrameIcon(new ImageIcon(Settings.class.getResource("/images/ic_settings_input_component_black_18dp_1x.png")));
		internalFrameCompany.setBounds(10, 0, 414, 229);
		internalFrameCompany.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		internalFrameCompany.setSize(1340, 700);
		internalFrameCompany.setVisible(true);
		desktopPane.add(internalFrameCompany);
		internalFrameCompany.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 414, 229);
		panel.setSize(1340,700);
		internalFrameCompany.getContentPane().add(panel);
		panel.setLayout(null);
				
		JLabel lblAddCompanyDetails = new JLabel("Add Company Details");
		lblAddCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblAddCompanyDetails.setBounds(235, 123, 202, 25);
		panel.add(lblAddCompanyDetails);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblCompanyName.setBounds(121, 196, 180, 25);
		panel.add(lblCompanyName);
		
		JLabel lblCompanyProfit = new JLabel("Company Profit:");
		lblCompanyProfit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblCompanyProfit.setBounds(121, 252, 202, 25);
		panel.add(lblCompanyProfit);
		
		txtFieldCompanyName = new JTextField();
		txtFieldCompanyName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					txtFldCompanyProfit.requestFocus();
				}
			}
		});
		txtFieldCompanyName.setBounds(311, 196, 245, 25);
		panel.add(txtFieldCompanyName);
		txtFieldCompanyName.setColumns(10);
		
		txtFldCompanyProfit = new JTextField();
		txtFldCompanyProfit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char ch = evt.getKeyChar();
				if(!Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE)){
					Toolkit.getDefaultToolkit().beep();
					evt.consume();
				JOptionPane.showMessageDialog(null, "Cannot Accept Letters");}
				else if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					btnAddCompany.requestFocus();
				}
			}
		});
		txtFldCompanyProfit.setColumns(10);
		txtFldCompanyProfit.setBounds(311, 252, 245, 25);
		panel.add(txtFldCompanyProfit);
				
		comboBoxEdtCompany = new JComboBox<String>();
		comboBoxEdtCompany.setBounds(937, 193, 245, 25);
		comboBoxEdtCompany.addItem("-Select Company-");
		comboBoxEdtCompany.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		panel.add(comboBoxEdtCompany);
				try
				{				
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
		panel.add(lblEdtCompanyName);
		
		JButton btnEditCompany = new JButton("Update");
		btnEditCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_system_update_alt_black_24dp_1x.png")));
		btnEditCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnEditCompany.setBounds(944, 367, 105, 33);
		panel.add(btnEditCompany);
		btnEditCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtFldEdtProfit.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Profit is not declared");
				}else{
				int getnumber = Integer.parseInt(txtFldEdtProfit.getText());
				if (comboBoxEdtCompany.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
				}else  if (getnumber >100 ){
					JOptionPane.showMessageDialog(null,"Company Profit cannot be more than 100%");
				}else{
					try{						
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
			}
		});
		
		JButton btnDeleteCompany = new JButton("Delete");
		btnDeleteCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_delete_forever_black_24dp_1x.png")));
		btnDeleteCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnDeleteCompany.setBounds(1092, 367, 105, 33);
		panel.add(btnDeleteCompany);
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
		
		btnAddCompany = new JButton("Add");
		btnAddCompany.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_create_new_folder_black_24dp_1x.png")));
		btnAddCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAddCompany.setBounds(287, 326, 90, 33);
		panel.add(btnAddCompany);
		btnAddCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int a = Integer.parseInt(txtFldCompanyProfit.getText());
				if ( a >100) {
					JOptionPane.showMessageDialog(null,"Company Profit cannot be more than 100%");
				}else{
				if (txtFieldCompanyName.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Name is not declared");
				}else if (txtFldCompanyProfit.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Company Profit is not declared");
				}else{
			try{				
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
				 JOptionPane.showMessageDialog(null, "Company Name "+txtFieldCompanyName.getText()+" and Profit "+txtFldCompanyProfit.getText()+" Saved");
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
		}		
	});

				
		JLabel lblEditCompanyDetails = new JLabel("Edit Company Details");
		lblEditCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEditCompanyDetails.setBounds(858, 123, 202, 25);
		panel.add(lblEditCompanyDetails);
		
		JLabel lblSettingCompanyDetails = new JLabel("Setting Up Company Details");
		lblSettingCompanyDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblSettingCompanyDetails.setBounds(527, 51, 245, 25);
		panel.add(lblSettingCompanyDetails);
		
		JLabel lblEdtProfit = new JLabel("Edit Profit:");
		lblEdtProfit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEdtProfit.setBounds(747, 252, 202, 25);
		panel.add(lblEdtProfit);
		
		txtFldEdtProfit = new JTextField();
		txtFldEdtProfit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char ch = evt.getKeyChar();
				if(!Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE) || (ch == KeyEvent.VK_DELETE)){
					Toolkit.getDefaultToolkit().beep();
					evt.consume();
				JOptionPane.showMessageDialog(null, "Cannot Accept Letters");}
			}
		});
		txtFldEdtProfit.setColumns(10);
		txtFldEdtProfit.setBounds(937, 252, 245, 25);
		panel.add(txtFldEdtProfit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setBounds(428, 326, 105, 33);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				txtFieldCompanyName.setText(null);
				txtFldCompanyProfit.setText(null);
			}
			
		});
		panel.add(btnCancel);
		
		JButton btnCheckStatus = new JButton("Check Status");
		btnCheckStatus.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_receipt_black_24dp_1x.png")));
		btnCheckStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnCheckStatus.setBounds(629, 367, 129, 33);
		panel.add(btnCheckStatus);
		btnCheckStatus.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CheckStatus.main(null);				
			}
			
		});
		
		JLabel lblcCopyright = new JLabel();
		lblcCopyright.setVerticalAlignment(SwingConstants.CENTER);
		lblcCopyright.setText("(C) Copyright 2018 by Anthony Wambua, Daniel Mbugua Inc.");
		lblcCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblcCopyright.setForeground(Color.DARK_GRAY);
		lblcCopyright.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblcCopyright.setBounds(436, 626, 420, 33);
		panel.add(lblcCopyright);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setBounds(38, 425, 459, 183);
		panel.add(panel1);
		panel1.setLayout(null);
		
		JTextField txtWSalerID = new JTextField();
		txtWSalerID.setColumns(10);
		txtWSalerID.setBounds(209, 38, 186, 25);
		panel1.add(txtWSalerID);
		
		JLabel lblWholsalerId = new JLabel("WholeSaler ID:");
		lblWholsalerId.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblWholsalerId.setBounds(52, 38, 129, 24);
		panel1.add(lblWholsalerId);
		
		JLabel lblDelWSaler = new JLabel("WholeSaler ID:");
		lblDelWSaler.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblDelWSaler.setBounds(52, 73, 129, 24);
		lblDelWSaler.setVisible(false);
		panel1.add(lblDelWSaler);
		
		cmbWSaler = new JComboBox<String>();
		cmbWSaler.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		cmbWSaler.setBounds(209, 74, 186, 25);
		cmbWSaler.addItem("-Select WholeSalerID-");
		cmbWSaler.setVisible(false);
		panel1.add(cmbWSaler);
		try
		{				
			mystmt = myconn.prepareStatement("select WholeSalerID from wholesale");
			//Statement mystmt= myconn.createStatement();							
			myRs = mystmt.executeQuery();
			
			  while(myRs.next())
			  {
				
				  cmbWSaler.addItem(myRs.getString("WholeSalerID"));
			  }		
			  mystmt.close();
			  System.out.println("Displays WholeSalerID Name on cmbWholSaler");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		JButton btnAddWSaler = new JButton("Add");
		btnAddWSaler.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_create_new_folder_black_24dp_1x.png")));
		btnAddWSaler.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAddWSaler.setBounds(73, 125, 90, 33);
		panel1.add(btnAddWSaler);
		btnAddWSaler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String insert = "insert into wholesale(WholeSalerID,Date) values (?,?)";
				
				Date curdate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				
				String DateToday = format.format(curdate);
				if(txtWSalerID.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Enter WholeSalerID on the TextArea");
				}else{
				try {
					mystmt = myconn.prepareStatement(insert);
					mystmt.setString(1, txtWSalerID.getText().toString());
					mystmt.setString(2, DateToday.toString());
					mystmt.execute();
					mystmt.close();
					txtWSalerID.setText(null);
					JOptionPane.showMessageDialog(null, "WholeSaler ID Saved");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			}
		});
		
		JButton btnStatusWSaler = new JButton("Check Status");
		btnStatusWSaler.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_receipt_black_24dp_1x.png")));
		btnStatusWSaler.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnStatusWSaler.setBounds(184, 125, 129, 33);
		panel1.add(btnStatusWSaler);
		btnStatusWSaler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new WholeSalerStatus();
			}
			
		});
		JButton btnDelWSaler = new JButton("Delete");
		btnDelWSaler.setIcon(new ImageIcon(Settings.class.getResource("/images/ic_delete_forever_black_24dp_1x.png")));
		btnDelWSaler.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnDelWSaler.setBounds(328, 125, 105, 33);
		panel1.add(btnDelWSaler);
		btnDelWSaler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblWholsalerId.setVisible(false);
				txtWSalerID.setVisible(false);
				lblDelWSaler.setVisible(true);
				cmbWSaler.setVisible(true);
				if (cmbWSaler.getSelectedItem().equals("-Select Company-")){
					JOptionPane.showMessageDialog(null,"WholeSalerID is not declared");}
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete  " + cmbWSaler.getSelectedItem().toString() + "  from the database"," Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				 
				if (response == JOptionPane.NO_OPTION) {
					 System.out.println("Not Deleted");
				      
				    } else if (response == JOptionPane.YES_OPTION) {
				     
				
				try{					
					mystmt = myconn.prepareStatement("delete from wholesale where WholeSalerID =?");
					mystmt.setString(1,cmbWSaler.getSelectedItem().toString());
										
					mystmt.execute();
					JOptionPane.showMessageDialog(null, "WholeSalerID  " + cmbWSaler.getSelectedItem().toString() +"  has been deleted");

					lblWholsalerId.setVisible(true);
					txtWSalerID.setVisible(true);
					lblDelWSaler.setVisible(false);
					cmbWSaler.setVisible(false);
					mystmt.close();
					System.out.println("Deleted");
					cmbWSaler.removeAllItems();
					cmbWSaler.addItem("-Select WholeSalerID-");
					addToComboWlSale();
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
			}
			}
			
		});
				
		
	}
	protected void  addToComboEdit(){
	try
	{		
		mystmt = myconn.prepareStatement("select CompanyName from company");							
		myRs = mystmt.executeQuery();
		
		  while(myRs.next())
		  {			
		  	comboBoxEdtCompany.addItem(myRs.getString("CompanyName"));
		  }		
		  mystmt.close();	
	}
	catch(Exception e) {
		e.printStackTrace();
	}
  }		
	protected void  addToComboWlSale(){
		try
		{		
			mystmt = myconn.prepareStatement("select WholeSalerID from wholesale");							
			myRs = mystmt.executeQuery();
			
			  while(myRs.next())
			  {			
				  cmbWSaler.addItem(myRs.getString("WholeSalerID"));
			  }		
			  mystmt.close();	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	  }		
}

