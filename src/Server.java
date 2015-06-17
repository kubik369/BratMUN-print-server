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

import java.awt.print.PrinterException;
import java.io.IOException;

import javax.mail.Message;

public class Server
{	
	public static void main(String[] args)
	{
		String  host = "imap.gmail.com", // change accordingly
				mailStoreType = "imap";
		LoginDialog logAndPass = new LoginDialog();
		String[] credentials;
		Mail gmail = new Mail(host);
		do{
			credentials = logAndPass.getCredentials();
			System.out.println("Name " + credentials[0]);
			System.out.println("Password " + credentials[1]);
		} while(gmail.checkConnection(host, credentials[0], credentials[1]) != true);

		
		Printer myPrinter = new Printer();
		/*try{
			myPrinter.print("c:\\Users\\Jakub\\Desktop\\test\\test.pdf", 50);
		}
		catch (PrinterException e){
        	System.out.println("Something went wrong with the printer.");
        	System.out.println(e.getMessage());
        	java.awt.Toolkit.getDefaultToolkit().beep();
        }
        catch (IOException e){
        	System.out.println("File could not be opened.");
        	System.out.println(e.getMessage());
        	java.awt.Toolkit.getDefaultToolkit().beep();
        }*/

		//System.exit(0);
		gmail.DownloadUnreadMails(host, mailStoreType, credentials[0], credentials[1]);
		gmail.getSender();
		//Message[] messages = gmail.getMessages();
		/*if(messages.length != 0){
			myPrinter.Print();
		}*/
		//gmail.check(host, mailStoreType, username, password);
		//MainWindow window = new MainWindow();
	}
}
