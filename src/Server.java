import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server
{	
	public static void main(String[] args)
	{
		String  host = "imap.gmail.com", // change accordingly
				mailStoreType = "imap";
		
		//Mail gmail = new Mail(host);
		Settings settings = new Settings();
		FTP ftp = new FTP(settings);
		MainWindow window = new MainWindow(ftp, settings);
		//gmail.setSettings(settings);
		
		/*Archive archive = new Archive("C:/Users/Jakub/Desktop/test");
		ArchiveMessage am = new ArchiveMessage("random_Jakub-Simo_10.pdf", FileStatus.Downloaded);
		archive.backupMessage(am);
		archive.loadArchive();
		System.out.println(archive.getMessages().get(0).getFilename());*/
		//System.exit(0);
		
		//ftp.startFTP();
		/*
		gmail.DownloadUnreadMails(host, mailStoreType, settings.getName(), settings.getPassword());
		System.out.println("Getting senders.");
		gmail.getSenders();
		System.out.println("Getting attachments.");
		gmail.getAttachments();
	*/
		/*settings.setDirs();
		Printer myPrinter = new Printer(settings.getDir());
		try{
			System.out.println(settings.getDir());
			//System.exit(0);
			myPrinter.print(settings.getDir() + "/test.pdf", 1);
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
	}
}
