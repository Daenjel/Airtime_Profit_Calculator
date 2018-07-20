package airtime;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminAuth extends JFrame {

	private static final long serialVersionUID = 1L;
	Connection myconn = null;
	private JTextField txtFldEnterUnits;
	JLabel lblNewLabel,lblNewLabel1;
	JComboBox<Object> cbxChseCompany,denomination;
	public static void main(String[] args) {
		new AdminAuth();
	}

	public AdminAuth() {
		myconn = JConnection.ConnecrDb();
		JFrame frame = new JFrame();
				
		JOptionPane optionPane = new JOptionPane();
		JPanel scrollPane = getscrollPane(optionPane);
	    optionPane.setMessage(scrollPane);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionPane.setOptionType(JOptionPane.CLOSED_OPTION);
		
	    JDialog dialog = optionPane.createDialog(frame, "Requires Administrative Previleges!");
	    dialog.setVisible(true);
	    dialog.setSize(750,750);
		setUndecorated(true);
		dialog.getContentPane().setLayout(null);
		
	}
	private JPanel getscrollPane(JOptionPane optionPane) {
		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 430, 235);
		optionPane.add(panel);
		//panel.setLayout(null);
		
		JLabel lblWelcomeCompany = new JLabel("Sales");
		lblWelcomeCompany.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblWelcomeCompany.setBounds(292, 42, 77, 40);
		panel.add(lblWelcomeCompany);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(Sales.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(428,374,100,40);
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
		
		return panel;
	}
}
