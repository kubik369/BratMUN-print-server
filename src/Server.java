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

import java.awt.print.PrinterException;
import java.io.IOException;
public class Server
{	
	public static void main(String[] args)
	{
		String  host = "imap.gmail.com", // change accordingly
				mailStoreType = "imap";
		Mail gmail = new Mail(host);
		Printer myPrinter = new Printer();
		LoginDialog logAndPass = new LoginDialog();
		String[] credentials = logAndPass.getCredentials();
		System.out.println("Name " + credentials[0]);
		System.out.println("Password " + credentials[1]);
		try{
			myPrinter.print();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
			System.out.println(e.getMessage());
		}
		catch(PrinterException e){
			System.out.println("Bad printer.");
			System.out.println(e.getMessage());
		}
		//System.exit(0);
		//gmail.DownloadUnreadMails(host, mailStoreType, credentials[0], credentials[1]);
		//Message[] messages = gmail.getMessages();
		/*if(messages.length != 0){
			myPrinter.Print();
		}*/
		//gmail.check(host, mailStoreType, username, password);
		MainWindow window = new MainWindow();
	}
}
