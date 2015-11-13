import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.*;
import org.apache.commons.io.FileCleaningTracker;

import static java.nio.file.StandardCopyOption.*;

public class Printer
{
	private PrintService myPrinter;
	private Settings settings;
	private Timer printTimer;
	//private FileCleaningTracker cleaner;
	
	public Printer(Settings s)
	{
		this.settings = s;
		//this.cleaner = new FileCleaningTracker();
		getPrintService();
	}
	
	public void start(){
		printTimer = new Timer();
		printTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				printQueue();				
			}
		}, 10, 60 * 1000);
	}
	
	public void stop(){
		printTimer.cancel();
		printTimer.purge();
	}
	
	private void getPrintService(){
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		System.out.println("Number of print services: " + printServices.length);

		ArrayList<String> printers = new ArrayList<String>();
		for (PrintService printer : printServices){
			System.out.println("Printer: " + printer.getName());
			printers.add(printer.getName());
		}
		JComboBox<String> jcb = new JComboBox<String>(printers.toArray(new String[printers.size()]));
		jcb.setEditable(false);
		JOptionPane.showMessageDialog( null, jcb, "Select your printer", JOptionPane.QUESTION_MESSAGE);
		//System.out.println(jcb.getSelectedIndex());
		this.myPrinter = printServices[jcb.getSelectedIndex()];
	}
	
	public void printQueue(){
		System.out.println("Printing");
		File folder = new File(this.settings.getWorkDir() + "/downloads/");
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null || listOfFiles.length == 0){
			System.out.println("No files found.");
			return;
		}
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()) {
				try {
					String name = listOfFiles[i].getName();
					String[] data = name.split("\\+");
					System.out.println("Printing " + data[0]);
					continue;
					//print(listOfFiles[i].getName());
				} catch (Exception e) {
					System.out.println(" Could not print " + listOfFiles[i].getName());		
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean print(String filename) throws IOException, PrinterException{
		long startTime = System.currentTimeMillis();
		int copies = Integer.parseInt(filename.split("+")[3]);
		System.out.println("Printing");
		try {
			String  src = this.settings.getWorkDir() + "/downloads/" + filename,
					dest = this.settings.getWorkDir() + "/archive/" + filename,
					temp = this.settings.getWorkDir() + "/temp.pdf";
			PDFMergerUtility ut = new PDFMergerUtility();
			// add the initial source file
			ut.addSource(src);
			ut.setDestinationFileName(temp);
			// merge temp PDF with itself until it creates a document
			// with enough copies
			for(int i = 1;i < copies;i *= 2){
				ut.mergeDocuments(null);
				ut = new PDFMergerUtility();
				ut.setDestinationFileName(temp);
				ut.addSource(temp);
				ut.addSource(temp);
			}
			// load the merged document for printing
			PDDocument doc = PDDocument.load(new File(temp));
			// delete the redundant copies
			while(doc.getNumberOfPages() > copies)
				doc.removePage(doc.getNumberOfPages() - 1);
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			aset.add(new JobName("BratMUN Print", null));
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintService(myPrinter);
			job.setPageable(new PDFPageable(doc));
			job.print(aset);
			doc.close();
			Files.move(Paths.get(src), Paths.get(dest), REPLACE_EXISTING);
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Printing succesful, total time taken to print this shit was: " + (double)totalTime / 1000 + "s");
		return true;
	}
	
	/*public int getNumOfDeletingFiles(){
		return cleaner.getTrackCount();
	}*/
}