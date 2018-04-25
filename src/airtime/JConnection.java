package airtime;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class JConnection {

	public static Connection ConnecrDb(){
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection myconn = DriverManager.getConnection("JDBC:mysql://localhost:3306/airtime?autoReconnect=true&useSSL=false","root","Mbugua21");
		System.out.println("Connected");
		return myconn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
	}
	}
}
