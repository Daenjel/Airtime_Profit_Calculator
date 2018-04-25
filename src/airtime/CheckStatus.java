package airtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class CheckStatus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	Connection myconn = null;
	public static void main(String[] args) {
		new CheckStatus();
	}

	public CheckStatus() {
		myconn = JConnection.ConnecrDb();
		JFrame frame = new JFrame();
				
		JOptionPane optionPane = new JOptionPane();
		JScrollPane scrollPane = getscrollPane(optionPane);
	    optionPane.setMessage(scrollPane);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionPane.setOptionType(JOptionPane.CLOSED_OPTION);
		
	    JDialog dialog = optionPane.createDialog(frame, "Company Status");
	    dialog.setVisible(true);
	    dialog.setSize(50,50);
		setUndecorated(true);
			
	}
	private JScrollPane getscrollPane(JOptionPane optionPane) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 430, 235);
		optionPane.add(scrollPane);
		
		table = new JTable();
		table.setBounds(430, 229, -427, -225);
		optionPane.add(table);
		scrollPane.setViewportView(table);
		try{
			
			PreparedStatement mystmt = myconn.prepareStatement("select *from company");
					
			ResultSet myRs = mystmt.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(myRs));
			
			myRs.close();
			mystmt.close();
			myconn.close();
			System.out.println("Displays Company Status");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return scrollPane;
	}

	/*private void close() {
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
		
	}*/
	
}
