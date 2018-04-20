package airtime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameListener;

public class About extends MainMDI implements InternalFrameListener {


	public About() {
		
		JInternalFrame internalFrameAbout = new JInternalFrame("All About Airtime Profit Calculator",true,true,true);
		internalFrameAbout.setBounds(10, 0, 414, 229);
		internalFrameAbout.setSize(1340, 700);
		internalFrameAbout.setVisible(true);
		internalFrameAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		desktopPane.add(internalFrameAbout);
		internalFrameAbout.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(237, 50, 797, 426);
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textArea.setSize(900,550);
		textArea.setEditable(false);
		internalFrameAbout.getContentPane().add(textArea);
		
				try{
				String one = null;
				String two = null;
				InputStream fileIs = About.class.getResourceAsStream("about.txt");
				BufferedReader rd = null;
				rd = new BufferedReader(new InputStreamReader(fileIs));
				
				while ((one = rd.readLine()) != null) {
				two = two + one +"\n";
				//System.out.println(two);
				}
				textArea.setText(two);;
				rd.close();
				textArea.requestFocus();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
					e.printStackTrace();
				}
				
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(About.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setBounds(650, 614, 107, 33);
		internalFrameAbout.getContentPane().add(btnCancel);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		
		JLabel lblNewLabel_1 = new JLabel("Airtime Profit Calculator");
		lblNewLabel_1.setIcon(new ImageIcon(About.class.getResource("/images/images24x24gray.png")));
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Harrington", Font.PLAIN, 23));
		lblNewLabel_1.setBounds(553, 11, 285, 33);
		internalFrameAbout.getContentPane().add(lblNewLabel_1);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Sales();
				contentPane.setVisible(false);
			}
		});
	}	
	//private static final String filename = "about.txt";
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new About();
			}
		});
	}
}
