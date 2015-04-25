import javax.mail.Message;

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
