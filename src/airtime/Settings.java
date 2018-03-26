package airtime;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Settings extends MainMDI implements InternalFrameListener {
	private JTextField txtFieldCompanyName;
	private JTextField txtFldCompanyProfit;
	private JTextField txtFldEdtProfit;

	public Settings() {
		
		JInternalFrame internalFrameCompany = new JInternalFrame("Setting Up Company Details",true,true,true);
		internalFrameCompany.setBounds(10, 0, 414, 229);
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
				
		JComboBox<Object> comboBoxEdtCompany = new JComboBox<Object>();
		comboBoxEdtCompany.setBounds(937, 193, 245, 25);
		internalFrameCompany.getContentPane().add(comboBoxEdtCompany);
		
		JLabel lblEdtCompanyName = new JLabel("Company Name:");
		lblEdtCompanyName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblEdtCompanyName.setBounds(747, 193, 180, 25);
		internalFrameCompany.getContentPane().add(lblEdtCompanyName);
		
		JButton btnEditCompany = new JButton("Edit");
		btnEditCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnEditCompany.setBounds(889, 367, 90, 33);
		internalFrameCompany.getContentPane().add(btnEditCompany);
		btnEditCompany.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*comboBoxEdtCompany.setVisible(true);
				lblEdtCompanyName.setVisible(true);
				lblCompanyName.setVisible(false);
				txtFieldCompanyName.setVisible(false);*/
			}
			
		});
		
		JButton btnDeleteCompany = new JButton("Delete");
		btnDeleteCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnDeleteCompany.setBounds(1036, 367, 90, 33);
		internalFrameCompany.getContentPane().add(btnDeleteCompany);
		
		JButton btnAddCompany = new JButton("Add");
		btnAddCompany.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnAddCompany.setBounds(235, 367, 90, 33);
		internalFrameCompany.getContentPane().add(btnAddCompany);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(404, 367, 90, 33);
		internalFrameCompany.getContentPane().add(btnCancel);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		
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
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				internalFrameCompany.setVisible(false);			
			}
			
		});

	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new Settings();
								
			}
		});
	}
}
