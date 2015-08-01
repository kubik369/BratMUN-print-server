import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

// downloading attachments http://www.codejava.net/java-ee/javamail/download-attachments-in-e-mail-messages-using-javamail

public class Mail
{
	private Properties properties;
	private Session emailSession;
	private Store store;
	private Folder emailFolder;
	private ArrayList<Message> messages;
	private Settings settings;
	
	public Mail(String host){
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
			properties.put("mail.mime.base64.ignoreerrors", "true");
			properties.put("mail.imaps.partialfetch", "false");
			
			this.emailSession = Session.getDefaultInstance(properties);
			// set the protocol to IMAP  
			this.store = emailSession.getStore("imaps");
			this.messages = new ArrayList<Message>();
		} 
		catch (NoSuchProviderException e) {
			System.out.println("Error while creating Mail object.");
			e.printStackTrace();
		}
	}
	
	public void DownloadUnreadMails(String host, String storeType, String user, String password){
		try{
			// connect to the server
			store.connect(host, user, password);
			// create the folder object and open it
			emailFolder.open(Folder.READ_WRITE);
			// retrieve the messages from the folder in an array
			int numOfMessages = emailFolder.getUnreadMessageCount();
			if(numOfMessages != 0){
				System.out.println("Unread messages count: " + numOfMessages);
				messages.addAll(Arrays.asList(emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false))));
				System.out.println("Number of downloaded messages: " + numOfMessages);
			}
			else{
				System.out.println("No unread messages.");
				return;
			}
			// print all the information from the downloaded mails
			for (int i = messages.size() -1; i >= 0; i--) {
				Message message = messages.get(i);
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Time: " + message.getSentDate());
				System.out.println("Text: " + message.getContent().toString());
			}
			// mark the mails as read in the folder
			Message[] arrMessages = new Message[messages.size()];
			emailFolder.setFlags(messages.toArray(arrMessages), new Flags(Flags.Flag.SEEN), true);
			//close the store and folder objects
			emailFolder.close(false);
			store.close();
		}
		catch (MessagingException|IOException e) {
			System.out.println("Something went wrong while downloading mails.");
			System.out.println(e.getMessage());
		}
		//return messages;
	}
	
	public boolean checkConnection(String host, String user, String password){
		try{
			store.connect(host, user, password);
			this.emailFolder = store.getFolder("INBOX");
			store.close();
			return true;
		}
		catch (MessagingException e){
			System.out.println("Wrong mail or password.");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public ArrayList<String> getSenders(){
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
	
	public void connect() throws MessagingException{
		if(!store.isConnected())
			store.connect();
		if(!emailFolder.isOpen())
			emailFolder.open(Folder.READ_WRITE);
		if(!(store.isConnected() && emailFolder.isOpen()))
		{
			System.out.println("Could not connect to the server.");
			throw new MessagingException();
		}
	}
	
	public void disconnect() throws MessagingException{
		if(store.isConnected())
			store.close();
		if(emailFolder.isOpen())
			emailFolder.close(false);
		if(emailFolder.isOpen() || store.isConnected());
			throw new MessagingException();
	}
	
	public ArrayList<String> getAttachments(){
		ArrayList<String> files = new ArrayList<String>();
		try{
			this.connect();
		}
		catch(MessagingException e){
			System.out.println("Could not connect to the server.");
			e.printStackTrace();
			return files;
		}
		for(Message message : messages){
			try{
				MimeMessage tempmsg = new MimeMessage((MimeMessage)message);
				Multipart multiPart = (Multipart) tempmsg.getContent();
				for (int i = 0; i < multiPart.getCount(); i++) {
				    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
				    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				        System.out.println("Trying to save the file " + part.getFileName());
				    	part.saveFile(settings.getWorkDir() + part.getFileName());
				        System.out.println("Succesfully saved file " + part.getFileName() + " from " + message.getFrom()[0].toString());
				    }
				}	
			}
			catch(MessagingException|IOException e){
				System.out.println("Something wrong while getting attachments.");
				e.printStackTrace();
			}
		}
		try{
			this.disconnect();
		}
		catch(MessagingException e){
			System.out.println("Could not disconnect from the server.");
			e.printStackTrace();
			return new ArrayList<String>();
		}
		System.out.println("Succesfully downloaded all the attachments.");
		return files;
	}
	
	public ArrayList<Message> getMessages(){
		return this.messages;
	}
	
	public void setSettings(Settings set){
		this.settings = set;
	}
}
