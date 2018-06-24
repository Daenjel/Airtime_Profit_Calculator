package airtime;

import java.sql.Connection;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminAuth extends JFrame {

	private static final long serialVersionUID = 1L;
	Connection myconn = null;
	private JTextField textField;
	JLabel lblNewLabel,lblNewLabel1;
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
	    dialog.setSize(50,50);
		setUndecorated(true);
		dialog.getContentPane().setLayout(null);
		
			
	}
	private JPanel getscrollPane(JOptionPane optionPane) {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 398, 199);
		optionPane.add(panel);

		lblNewLabel = new JLabel("<html>Password: </html>");
		lblNewLabel.setBounds(95, 143, 46, 14);
		panel.add(lblNewLabel);
		
		
		textField = new JTextField();
		textField.setBounds(224, 140, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		return panel;
	}
}
