package airtime;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
	
public class MainMDI implements InternalFrameListener {

		public JFrame contentPane;
		public JMenuBar menuBar;
		public JMenu mnAdmin;
		public JMenuItem mntmLock;
		public JMenuItem mntmUnlock;
		public JMenuItem mntmExit;
		public JMenu mnSales;
		public JMenuItem mntmSales;
		public JMenu mnStock;
		public JMenuItem mntmCurrent;
		public JMenu mnSettings;
		public JMenuItem mntmAddCompany;
		public JMenu mnHelp;
		public JMenuItem mntmAbout;
		public JDesktopPane desktopPane = new JDesktopPane();
		public JDesktopPane desktopPaneLock = new JDesktopPane();
	
	public MainMDI() {
			
			contentPane = new JFrame();
			contentPane.setLayout(new FlowLayout());
			contentPane.setIconImage(Toolkit.getDefaultToolkit().getImage(MainMDI.class.getResource("/images/images24x24.png")));			
			contentPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			contentPane.setVisible(true);
			contentPane.setExtendedState(JFrame.MAXIMIZED_BOTH);
			contentPane.setResizable(true);
			contentPane.setTitle("Airtime Profit Calculator");
			contentPane.setFont(new Font("Times New Roman", Font.ITALIC, 14));
			contentPane.setContentPane(desktopPane);
		
			desktopPane.setBackground(Color.GREEN);
			desktopPaneLock.setBackground(Color.CYAN);
			
			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setIcon(new ImageIcon(MainMDI.class.getResource("/images/images.png")));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setForeground(Color.BLUE);
			lblNewLabel.setFont(new Font("Harrington", Font.PLAIN, 48));
			lblNewLabel.setVerticalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(100, 10, 797, 385);
			lblNewLabel.setText("Airtime Profit Calculator");
			desktopPaneLock.add(lblNewLabel);
			
			JLabel lblLogo = new JLabel();
			lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogo.setForeground(Color.MAGENTA);
			lblLogo.setBounds(441, 250, 332, 33);
			lblLogo.setFont(new Font("Times New Roman", Font.ITALIC, 23));
			lblLogo.setVerticalAlignment(SwingConstants.CENTER);
			lblLogo.setText("Calculations On Your Finger Tips");
			desktopPaneLock.add(lblLogo);
			
			JLabel lblcCopyright = new JLabel();
			lblcCopyright.setVerticalAlignment(SwingConstants.CENTER);
			lblcCopyright.setText("(C) Copyright 2018 by Anthony Wambua, Daniel Mbugua Inc.");
			lblcCopyright.setHorizontalAlignment(SwingConstants.CENTER);
			lblcCopyright.setForeground(Color.DARK_GRAY);
			lblcCopyright.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			lblcCopyright.setBounds(315, 395, 420, 33);
			desktopPaneLock.add(lblcCopyright);
			
			menuBar = new JMenuBar();
			contentPane.setJMenuBar(menuBar);
			
			Admin();
			Sales();
			Stock();
			Settings();
			Help();
		}
	public void Admin(){	
			mnAdmin = new JMenu("ADMIN");
			mnAdmin.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			mnAdmin.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_verified_user_black_18dp_1x.png")));
			menuBar.add(mnAdmin);
			
			mntmLock = new JMenuItem("Lock");
			mntmLock.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			mntmLock.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_lock_outline_black_18dp_1x.png")));
			mntmLock.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mnSales.setVisible(false);
					mnStock.setVisible(false);
					mnSettings.setVisible(false);
					mnHelp.setVisible(false);
					mntmUnlock.setVisible(true);
					mntmLock.setVisible(false);
					contentPane.setContentPane(desktopPaneLock);
					contentPane.setSize(1000, 500);
					contentPane.setBounds(200, 100, 1000, 500);
				}
			});
			mnAdmin.add(mntmLock);
			
			mntmUnlock = new JMenuItem("Unlock");
			mntmUnlock.setVisible(false);
			mntmUnlock.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_lock_open_black_18dp_1x.png")));
			mntmUnlock.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			mntmUnlock.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mnSales.setVisible(true);
					mnStock.setVisible(true);
					mnSettings.setVisible(true);
					mnHelp.setVisible(true);
					mntmLock.setVisible(true);
					mntmUnlock.setVisible(false);
					contentPane.setContentPane(desktopPane);
					contentPane.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			});
			mnAdmin.add(mntmUnlock);
			
			mntmExit = new JMenuItem("Exit");
			mntmExit.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_remove_from_queue_black_18dp_1x.png")));
			mntmExit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			mntmExit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
				}
			});
			mnAdmin.add(mntmExit);
		}
	public void Sales(){
		
		mnSales = new JMenu("SALES");
		mnSales.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_add_shopping_cart_black_18dp_1x.png")));
		mnSales.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		menuBar.add(mnSales);
		
		mntmSales = new JMenuItem("Open Sales");
		mntmSales.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mntmSales.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_shopping_cart_black_18dp_1x.png")));
		mntmSales.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Sales();
				Sales.getSum();
				contentPane.setVisible(false);
			}
			
		});
		mnSales.add(mntmSales);
	}
	public void Stock(){
		
		mnStock = new JMenu("STOCK");
		mnStock.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_storage_black_18dp_1x.png")));
		mnStock.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		menuBar.add(mnStock);
		
		mntmCurrent = new JMenuItem("Current Stock");
		mntmCurrent.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mntmCurrent.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_shopping_cart_black_18dp_1x.png")));
		mntmCurrent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Stocks();
				Stocks.getStock();
				contentPane.setVisible(false);
			}		
		});
		mnStock.add(mntmCurrent);
	}		
	public void Settings(){
				
			mnSettings = new JMenu("SETTINGS");
			mnSettings.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_settings_input_component_black_18dp_1x.png")));
			mnSettings.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			menuBar.add(mnSettings);
			
			mntmAddCompany = new JMenuItem("Airtime Company");
			mntmAddCompany.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			mntmAddCompany.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_note_add_black_18dp_1x.png")));
			mntmAddCompany.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new Settings();
					contentPane.setVisible(false);
				}
			});
			mnSettings.add(mntmAddCompany);	
		}
	public void Help(){
			
			mnHelp = new JMenu("HELP");
			mnHelp.setFont(new Font("Segoe UI", Font.ITALIC, 12));
			mnHelp.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_help_black_18dp_1x.png")));
			menuBar.add(mnHelp);
			
			mntmAbout = new JMenuItem("About");
			mntmAbout.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			mntmAbout.setIcon(new ImageIcon(MainMDI.class.getResource("/images/ic_info_black_18dp_1x.png")));
			mntmAbout.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new About();
					contentPane.setVisible(false);
				}
			});
			mnHelp.add(mntmAbout);	
		}	
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//new MainMDI();
					   try {
				            //here you can put the selected theme class name in JTattoo
				            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
				 
				        } catch (ClassNotFoundException ex) {
				            java.util.logging.Logger.getLogger(MainMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				            
				        } catch (InstantiationException ex) {
				            java.util.logging.Logger.getLogger(MainMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				            
				        } catch (IllegalAccessException ex) {
				            java.util.logging.Logger.getLogger(MainMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				            
				        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
				            java.util.logging.Logger.getLogger(MainMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				        }
					new Sales();
					Sales.getSum();
				}
			});
		}
		@Override
		public void internalFrameActivated(InternalFrameEvent e) {}
		@Override
		public void internalFrameClosed(InternalFrameEvent e) {}
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {}
		@Override
		public void internalFrameDeactivated(InternalFrameEvent e) {}
		@Override
		public void internalFrameDeiconified(InternalFrameEvent e) {}
		@Override
		public void internalFrameIconified(InternalFrameEvent e) {}
		@Override
		public void internalFrameOpened(InternalFrameEvent e) {}	
	}