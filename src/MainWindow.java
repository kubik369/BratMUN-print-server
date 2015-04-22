import javax.mail.Message;

public class MainWindow {
	
	public static void main(String[] args)
	{
		 String host = "imap.gmail.com";// change accordingly
	     String mailStoreType = "imap";
	     String username = "kubik0369@gmail.com";// change accordingly
	     String password = "OrAnGeBoX18";// change accordingly
	     Mail gmail = new Mail(host);
	     Message[] messages = gmail.GetUnreadMails(host, mailStoreType, username, password);
	     //gmail.check(host, mailStoreType, username, password);
	}
}