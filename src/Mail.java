import java.io.IOException;
import java.io.InputStream;
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
	public Mail()
	{
		
	}
	
	public Mail(String host, String storeType, String user, String password)
	{
		try 
		{
			//create properties field
			Properties properties = new Properties();
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "995");
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			  
			Store store = emailSession.getStore("imaps");
			store.connect(host, user, password);
	
			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			// retrieve the messages from the folder in an array and print it
			 Message messages[] = emailFolder.search(new FlagTerm(new Flags(Flag.RECENT), false));
			System.out.println("messages.length---" + messages.length);
			
			for (int i = messages.length -1; i > 0; i--) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Time: " + message.getSentDate());
				//System.out.println("Text: " + message.getContent().toString());
			}
			
			//close the store and folder objects
			emailFolder.close(false);
			store.close();
		} 
		catch (NoSuchProviderException e) {e.printStackTrace();}
		catch (MessagingException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}
}
