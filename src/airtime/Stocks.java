package airtime;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameListener;
import java.awt.ScrollPane;
import javax.swing.JTable;

public class Stocks extends MainMDI implements InternalFrameListener {
	private JTable table;
	private JTable table_1;

	public Stocks() {
		JInternalFrame internalFrameStock = new JInternalFrame("Current Stock",true,true,true);
		internalFrameStock.setBounds(10, 0, 414, 229);
		internalFrameStock.setSize(1340, 700);
		internalFrameStock.setVisible(true);
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
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.setBounds(611,573,100,40);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				internalFrameStock.setVisible(false);
			}
			
		});
		panel.add(btnCancel);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(114, 135, 495, 359);
		panel.add(scrollPane);
		
		ScrollPane scrollPane_1 = new ScrollPane();
		scrollPane_1.setBounds(755, 135, 495, 359);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.setBounds(204, 381, 270, -174);
		panel.add(table);
		
		table_1 = new JTable();
		table_1.setBounds(785, 397, 324, -174);
		panel.add(table_1);
		
		JButton btnPrint = new JButton("Print New");
		btnPrint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrint.setBounds(1024, 573, 100, 40);
		panel.add(btnPrint);
		
		JButton btnPrintCurrent = new JButton("Print Current");
		btnPrintCurrent.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintCurrent.setBounds(242, 573, 114, 40);
		panel.add(btnPrintCurrent);
		
		JLabel label = new JLabel("Current Stock");
		label.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		label.setBounds(255, 89, 219, 40);
		panel.add(label);
		
		JLabel lblRequestNewStock = new JLabel("Request New Stock");
		lblRequestNewStock.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		lblRequestNewStock.setBounds(926, 89, 219, 40);
		panel.add(lblRequestNewStock);

	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Stocks();
				}
		});
	}
}
