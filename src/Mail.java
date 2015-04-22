import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

import java.util.Properties;

public class Mail
{
	private Properties properties;
	private Session emailSession;
	private Store store;
	
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
			// enable SSL secure connection
			properties.put("mail.imap.starttls.enable", "true");
			emailSession = Session.getDefaultInstance(properties);
			// set the protocol to IMAP  
			store = emailSession.getStore("imaps");
		} 
		catch (NoSuchProviderException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public Message[] GetUnreadMails(String host, String storeType, String user, String password)
	{
		Message[] messages = new Message[1];
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
				return new Message[0];
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
		catch (MessagingException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
		return messages;
	}
}
