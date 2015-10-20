public class Server{	
	public static void main(String[] args){
		Server server = new Server();
	}
	
	public Server(){
		Settings settings = new Settings();
		Printer myPrinter = new Printer(settings.getWorkDir());
		settings.setPrinter(myPrinter);
		FTP ftp = new FTP(settings);
		MainWindow window = new MainWindow(ftp, settings);
		/*
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
