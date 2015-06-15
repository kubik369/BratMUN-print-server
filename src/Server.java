/*import javax.mail.Message;

public class Server
{
	public static void main(String[] args)
	{
		 String host = "imap.gmail.com";// change accordingly
	     String mailStoreType = "imap";
	     String username = "kubik0369@gmail.com";// change accordingly
	     String password = "********";// change accordingly
	     
	     Printer myPrinter = new Printer();
	     Mail gmail = new Mail(host);
	     
	     Message[] messages = gmail.GetUnreadMails(host, mailStoreType, username, password);
	     if(messages.length != 0)
	     {
	    	 myPrinter.Print();
	     }
	     //gmail.check(host, mailStoreType, username, password);
	     MainWindow window = new MainWindow();
	}
}
*/

import javax.mail.Message;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.BoxLayout;

public class Server
{
	public static String host = "imap.gmail.com", // change accordingly
						  mailStoreType = "imap",
						  username,
						  password;
	public static Printer myPrinter; 
	public static Mail gmail;
	
	public static void main(String[] args)
	{    
	     myPrinter = new Printer();
	     gmail = new Mail(host);
	     getCredentials();
	     
	     System.exit(0);
	     gmail.DownloadUnreadMails(host, mailStoreType, username, password);
	     Message[] messages = gmail.getMessages();
	     if(messages.length != 0){
	    	 myPrinter.Print();
	     }
	     //gmail.check(host, mailStoreType, username, password);
	     MainWindow window = new MainWindow();
	}
	
	public static void getCredentials(){
		JPanel myPanel = new JPanel(),
			   prompt = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));
		JLabel label = new JLabel("Username:");
		myPanel.add(label);
		JTextField usernameField = new JTextField(20);
		myPanel.add(usernameField);
		JLabel label_1 = new JLabel("Password:");
		myPanel.add(label_1);
		JPasswordField passwordField = new JPasswordField(20);
		myPanel.add(passwordField);
		
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
		           "Please Enter your username and password", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.out.println("Username value: " + usernameField.getText());
			System.out.println("Password value: " + new String(passwordField.getPassword()));
		}
	}
}
