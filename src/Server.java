import java.awt.print.PrinterException;
import java.io.IOException;

public class Server
{	
	public static void main(String[] args)
	{
		String  host = "imap.gmail.com", // change accordingly
				mailStoreType = "imap";
		
		Mail gmail = new Mail(host);
		Settings settings = new Settings(gmail);
		gmail.setSettings(settings);
		// get the name and password and try the connection to the server.
		do{
			settings.getCredentials();
		}
		while(gmail.checkConnection(host, settings.getName(), settings.getPassword()) != true);
		/*
		gmail.DownloadUnreadMails(host, mailStoreType, settings.getName(), settings.getPassword());
		System.out.println("Getting senders.");
		gmail.getSenders();
		System.out.println("Getting attachments.");
		gmail.getAttachments();
	*/
		MainWindow window = new MainWindow(gmail);

		settings.setPrintFolder(0);
		Printer myPrinter = new Printer();
		try{
			myPrinter.print("c:\\Users\\Jakub\\Desktop\\test\\test.pdf", 3);
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
        }
	}
}
