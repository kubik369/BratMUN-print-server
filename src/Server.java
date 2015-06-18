
import java.awt.print.PrinterException;
import java.io.IOException;

import javax.mail.Message;

public class Server
{	
	public static void main(String[] args)
	{
		String  host = "imap.gmail.com", // change accordingly
				mailStoreType = "imap";
		
		Mail gmail = new Mail(host);
		Settings settings = new Settings(gmail);
		MainWindow window = new MainWindow();
		do{settings.getCredentials();}
		while(gmail.checkConnection(host, settings.getName(), settings.getPassword()) != true);
		//Printer myPrinter = new Printer();
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
		gmail.DownloadUnreadMails(host, mailStoreType, settings.getName(), settings.getPassword());
		System.out.println("Getting senders.");
		gmail.getSenders();
		System.out.println("Getting attachments.");
		gmail.getAttachments();
		//Message[] messages = gmail.getMessages();
		/*if(messages.length != 0){
			myPrinter.Print();
		}*/
		//gmail.check(host, mailStoreType, username, password);
	}
}
