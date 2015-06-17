//import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;


//import javax.mail.Address;
//import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
//import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
//import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

//import java.util.Properties;

// TO DO http://www.codejava.net/java-ee/javamail/download-attachments-in-e-mail-messages-using-javamail

public class Mail
{
	private Properties properties;
	private Session emailSession;
	private Store store;
	private Message[] messages = new Message[1];

	
	public Mail()
	{
		
	}
	
	public Mail(String host)
	{
		try 
		{
			// create properties field
			properties = new Properties();
			// set the host, default will be Gmail
			properties.put("mail.imap.host", host);
			// set the port of the connection
			properties.put("mail.imap.port", "995");
			//properties.put("mail.imap.socketFactory.port", "995");
			//properties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// enable SSL secure connection
			properties.put("mail.imap.starttls.enable", "true");
			
			emailSession = Session.getDefaultInstance(properties);
			// set the protocol to IMAP  
			store = emailSession.getStore("imaps");
		} 
		catch (NoSuchProviderException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public void DownloadUnreadMails(String host, String storeType, String user, String password)
	{
		//Message[] messages = new Message[1];
		try{
			// connect to the server
			store.connect(host, user, password);
			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			// retrieve the messages from the folder in an array
			Arrays.copyOf(messages, emailFolder.getUnreadMessageCount() + 10);
			int numOfMessages = emailFolder.getUnreadMessageCount();
			if(numOfMessages != 0)
			{
				System.out.println("Unread messages count: " + emailFolder.getUnreadMessageCount());
				messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
				System.out.println("Number of downloaded messages: " + messages.length);
			}
			else
			{
				System.out.println("No unread messages.");
				//return new Message[0];
			}
			
			for (int i = messages.length -1; i >= 0; i--) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Time: " + message.getSentDate());
				System.out.println("Text: " + message.getContent().toString());
			}
			// mark the mails as read in the folder
			emailFolder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
			
			//close the store and folder objects
			emailFolder.close(false);
			store.close();
		}
		catch (Exception e) {
			System.out.println("Something went wrong while downloading mails.");
			System.out.println(e.getMessage());
		}
		//return messages;
	}
	
	public boolean checkConnection(String host, String user, String password){
		try{
			store.connect(host, user, password);
			store.close();
			return true;
		}
		catch (MessagingException e){
			System.out.println("Wrong mail or password.");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public ArrayList<String> getSender(){
		ArrayList<String> names = new ArrayList<String>();
		for(Message message : this.messages){
			try{
				names.add(message.getFrom()[0].toString());
			}
			catch (MessagingException e){
				System.out.println("Error while getting recipient.");
				System.out.println(e.getMessage());
			}
		}
		for(String name : names) System.out.println("Name is " + name);
		return names;
	}
	
	public Message[] getMessages()
	{
		return messages;
	}
}
