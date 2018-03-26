package airtime;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.InternalFrameListener;

public class Sales_Report_Yesterday extends MainMDI implements InternalFrameListener {
	private JTable table;


	public Sales_Report_Yesterday() {
		
		JInternalFrame internalFrameReportY_day = new JInternalFrame("Welcome To Yesterday's Sales",true,true,true);
		internalFrameReportY_day.setBounds(10, 0, 414, 229);
		internalFrameReportY_day.setSize(1340, 700);
		internalFrameReportY_day.setVisible(true);
		desktopPane.add(internalFrameReportY_day);
		internalFrameReportY_day.getContentPane().setLayout(null);
		
		JLabel lblYesterday = new JLabel("Yesterday's Sales");
		lblYesterday.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblYesterday.setBounds(596, 38, 193, 31);
		internalFrameReportY_day.getContentPane().add(lblYesterday);
		
		JButton btnCancelY = new JButton("Cancel");
		btnCancelY.setBounds(752, 503, 100, 40);
		internalFrameReportY_day.getContentPane().add(btnCancelY);
		btnCancelY.setFont(new Font("Segoe UI", Font.ITALIC,12));
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(269, 109, 812, 326);
		internalFrameReportY_day.getContentPane().add(scrollPane);
		
		JButton btnPrintY = new JButton("Print");
		btnPrintY.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintY.setBounds(586, 503, 100, 40);
		internalFrameReportY_day.getContentPane().add(btnPrintY);
		
		table = new JTable();
		table.setBounds(891, 330, -422, -179);
		internalFrameReportY_day.getContentPane().add(table);
		btnCancelY.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				internalFrameReportY_day.setVisible(false);
			}
			
		});

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new Sales_Report_Yesterday();
				
				}
		});
	}
}
