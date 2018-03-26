package airtime;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameListener;

public class Sales_Reports extends MainMDI implements InternalFrameListener {
	private JTable tableReportT;

     
    public Sales_Reports() {
    
    	JInternalFrame internalFrameReportT_day = new JInternalFrame("Today's Sales",true,true,true);
		internalFrameReportT_day.setBounds(10, 0, 414, 229);
		internalFrameReportT_day.setSize(1340, 700);
		internalFrameReportT_day.setVisible(true);
		desktopPane.add(internalFrameReportT_day);
		internalFrameReportT_day.getContentPane().setLayout(null);
		
		JLabel lblReportT_day = new JLabel("Today's Sales");
		lblReportT_day.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblReportT_day.setBounds(583, 55, 130, 31);
		internalFrameReportT_day.getContentPane().add(lblReportT_day);
				
		JScrollPane scrollPaneReportT = new JScrollPane();
		scrollPaneReportT.setBounds(310, 118, 713, 277);
		internalFrameReportT_day.getContentPane().add(scrollPaneReportT);
		
		JButton btnPrintReportT = new JButton("Print");
		btnPrintReportT.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		btnPrintReportT.setBounds(563, 513, 100, 40);
		internalFrameReportT_day.getContentPane().add(btnPrintReportT);
		
		tableReportT = new JTable();
		tableReportT.setBounds(813, 321, -295, -131);
		internalFrameReportT_day.getContentPane().add(tableReportT);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(732, 513, 100, 40);
		internalFrameReportT_day.getContentPane().add(btnCancel);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				internalFrameReportT_day.setVisible(false);
			}
			
		});
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
           public void run() {
               new Sales_Reports();
           } 
        });
    }


}
