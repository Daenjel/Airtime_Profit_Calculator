package airtime;

import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameListener;

public class Sales extends MainMDI implements InternalFrameListener {
	private JTextField txtFldEnterUnits;
	private JTable Salestable;

	public Sales() {
			
		JInternalFrame internalFrameSales = new JInternalFrame("Sales",true,true,true);
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
				internalFrameSales.setVisible(false);
			}
			
		});
		panel.add(btnCancel);
		
		String[] Cname = {"None","Safaricom","Airtel","Telkom","MTN","Tigo","Vodacom"};
		
		JComboBox<String> cbxChseCompany = new JComboBox<String>(Cname);
		cbxChseCompany.setFont(new Font("Times New Roman", Font.ITALIC,18));
		cbxChseCompany.setBounds(316, 126, 180, 27);
		panel.add(cbxChseCompany);
		
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
		
		String[] deno = {"None","10","20","50","100","250","500","1000"};
		
		JComboBox<Object> denomination = new JComboBox<Object>(deno);
		denomination.setBounds(316, 191, 180, 29);
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
			
				if(cbxChseCompany.getSelectedItem() == null){
				JOptionPane.showMessageDialog(null, "Enter Company Name");}
				
				else if(denomination.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "Enter Denomination");}
				
				else if(txtFldEnterUnits.getText() == null){
					JOptionPane.showMessageDialog(null, "Enter Number of Units");}
			}
			
		});
		panel.add(btnAdd);
		
		JLabel lblTodaySales = new JLabel("Today's Sales");
		lblTodaySales.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblTodaySales.setBounds(809, 42, 162, 40);
		panel.add(lblTodaySales);
		
		ScrollPane scrollPaneSales = new ScrollPane();
		scrollPaneSales.setBounds(613, 103, 489, 353);
		panel.add(scrollPaneSales);
		
		Salestable = new JTable();
		Salestable.setBounds(1182, 431, -445, -272);
		panel.add(Salestable);
		
		JButton btnPrintSales = new JButton("Print");
		btnPrintSales.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintSales.setBounds(809, 508, 100, 40);
		panel.add(btnPrintSales);
		
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
}
