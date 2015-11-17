import java.awt.print.PrinterException;
import java.io.IOException;

public class Server{	
	public static void main(String[] args){
		Server server = new Server();
	}
	
	public Server(){
		Settings settings = new Settings();
		FTP ftp = new FTP(settings);
		MainWindow window = new MainWindow(ftp, settings);
	}
}
