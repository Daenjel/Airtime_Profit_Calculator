package airtime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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
		
		String help = ("<html>Airtime Profit Calculator, Airtime Profit Calculator, Airtime Profit Calculator"
				+ "<br>Airtime Profit Calculator, Airtime Profit Calculator, Airtime Profit Calculator"
				+ "<br>Airtime Profit Calculator, Airtime Profit Calculator, Airtime Profit Calculator"
				+"<br>                                                                          "
				+"<br>                                                                          "
				+"<br>                                                                          "
				+"<br>                                                                          "
				+"<br>                                                                          "
				+"<br>                                                                          "
				+"<br>(C) Copyright 2018 by Anthony Wambua, Inc. All Rights Reserved.          *"
				+"<br>* 										                                "
				+"<br>*                                                                         "
				+"<br>* DISCLAIMER: The author  and publisher of this program have used their  *"
				+"<br>* best efforts in preparing the program. These efforts include the       *"
				+"<br>* development, research, and testing of the theories and programs        *"
				+"<br>* to determine their effectiveness. The author and publisher make        *"
				+"<br>* no warranty of any kind, expressed or implied, with regard to          *"
				+"<br>* to the documentation contained in these program. The author            *"
				+"<br>* and publisher shall not be liable in any event for incidental or       *"
				+"<br>* consequential damages in connection with, or arising out of, the       *"
				+"<br>* furnishing, performance, or use of these programs.</html>");
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(237, 94, 797, 426);
		lblNewLabel.setText(help);
		internalFrameAbout.getContentPane().add(lblNewLabel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(About.class.getResource("/images/ic_exit_to_app_black_24dp_1x.png")));
		btnCancel.setBounds(644, 582, 107, 33);
		internalFrameAbout.getContentPane().add(btnCancel);
		btnCancel.setFont(new Font("Segoe UI", Font.ITALIC,12));
		
		JLabel lblNewLabel_1 = new JLabel("Airtime Profit Calculator");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 23));
		lblNewLabel_1.setBounds(486, 36, 248, 33);
		internalFrameAbout.getContentPane().add(lblNewLabel_1);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Sales();
				contentPane.setVisible(false);
			}
			
		});

	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new About();
			}
		});
	}
}
