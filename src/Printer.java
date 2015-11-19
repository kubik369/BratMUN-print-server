import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.*;

import com.activetree.common.print.SilentPrint;
import com.activetree.pdfprint.SilentPrintPdf;

import org.apache.commons.io.FileCleaningTracker;

import static java.nio.file.StandardCopyOption.*;

public class Printer
{
	private PrintService myPrinter;
	private Settings settings;
	private Timer printTimer;
	private boolean printing;
	//private FileCleaningTracker cleaner;
	
	public Printer(Settings s)
	{
		this.settings = s;
		//this.cleaner = new FileCleaningTracker();
	}
	
	public void start(){
		printTimer = new Timer(60 * 1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				printQueue();
			}
		});
		printTimer.start();
	}
	
	public void stop(){
		printTimer.stop();
	}
	
	public void getPrintService(){
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		//System.out.println("Number of print services: " + printServices.length);

		ArrayList<String> printers = new ArrayList<String>();
		for (PrintService printer : printServices){
			//System.out.println("Printer: " + printer.getName());
			printers.add(printer.getName());
		}
		JComboBox<String> jcb = new JComboBox<String>(printers.toArray(new String[printers.size()]));
		jcb.setEditable(false);
		JOptionPane.showMessageDialog( null, jcb, "Select your printer", JOptionPane.QUESTION_MESSAGE);
		//System.out.println(jcb.getSelectedIndex());
		this.myPrinter = printServices[jcb.getSelectedIndex()];
		this.settings.addMessage("Printer chosen: " + this.myPrinter.getName());
	}
	
	public void printQueue(){
		if(this.printing == true) return;
		this.printing = true;
		File folder = new File(this.settings.getWorkDir() + "/downloads/");
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null || listOfFiles.length == 0){
			System.out.println("No files found.");
			this.printing = false;
			return;
		}
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()) {
				try {
					String name = listOfFiles[i].getName();
					String[] data = name.split("\\+");
					//System.out.println("Printing " + data[0]);
					//this.settings.addMessage("Printing " + data[0]);
					print(listOfFiles[i].getName());
				} catch (Exception e) {
					//System.out.println("Could not print " + listOfFiles[i].getName());		
					this.settings.addMessage(" Could not print " + listOfFiles[i].getName());
					e.printStackTrace();
				}
			}
		}
		this.printing = false;
	}
	
	public boolean print(String filename) throws IOException, PrinterException{
		// used for measuring the print time
		long startTime = System.currentTimeMillis();
		// parse the number of copies from the PDF file
		// the file is suppossed to to be in format
		// filename+firstName+secondName+numOfCopies+commitee.pdf
		String[] data = filename.split("\\+");
		String  src =  this.settings.getWorkDir() + "\\downloads\\" + filename,
				dest = this.settings.getWorkDir() + "/archive/" + filename;
		int copies = Integer.parseInt(data[3]);
		//System.out.println("Printing");
		this.settings.addMessage(String.format(
				"Printing %d copies of the file %s sent by %s %s from %s",
				copies, data[0], data[1], data[2], data[4].substring(0, data[4].length() - 4)
		));
		Files.move(Paths.get(src), Paths.get(dest), REPLACE_EXISTING);
		String acrobat = "C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe";
		for(int i = 0;i < copies;i++){
			Process p = Runtime.getRuntime().exec(String.format("%s /t \"%s\"", acrobat, dest));
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Printer
		//watermark version
	     /*SilentPrint silentPrint = new SilentPrintPdf();
	     //default media size
	     String paperSize = "(0, 0, 0, 0, 612, 792)";
	     silentPrint.setAttribute(SilentPrint.PAPER, paperSize);
	     //printer to print
	     String printerName = myPrinter.getName();
	     silentPrint.setAttribute(SilentPrint.PRINTER_NAME, printerName);
	     //auto rotate and center
	     silentPrint.setAttribute(SilentPrint.AUTO_ROTATE_AND_CENTER, Boolean.TRUE);
	     //page scaling
	     silentPrint.setAttribute(SilentPrint.PAGE_SCALING, SilentPrint.FIT_TO_PRINTABLE_AREA);
	     //auto-match paper based on PDF page size.
	     silentPrint.setAttribute(SilentPrint.AUTO_MATCH_PAPER, Boolean.FALSE);
	     //collate
	     silentPrint.setAttribute(SilentPrint.COLLATE_COPIES, Boolean.TRUE);
	     //copies defaut 1; can make it to N copies
	     silentPrint.setAttribute(SilentPrint.COPIES, new Integer(copies));
	     //print all docs as one print job
	     //silentPrint.setAttribute(SilentPrint.SINGLE_PRINT_JOB, Boolean.TRUE);
	     //debug it
	     //silentPrint.setAttribute(SilentPrint.DEBUG, Boolean.TRUE);
	     silentPrint.setAttribute(SilentPrint.PRINT_QUALITY, SilentPrint.HIGH);
	     //job name
	     silentPrint.setAttribute(SilentPrint.JOB_NAME, data[0] + " Bratmun Print");
	     //document
	     silentPrint.setAttribute(SilentPrint.DOC_LIST, "file:/" + dest);
	     //Add a docListener
	     //must have a default constructor for this class.
	     
	     //silentPrint.setAttribute(SilentPrint.DOC_LISTENER, "demo.activetree.pdfprint.PdfDocListener");
	     
	     silentPrint.setAttribute(SilentPrint., arg1);
	     //doc password protected if any
	     //silentPrint.setAttribute(SilentPrint.PASSWORD, docPassword);
	     //url protection if any
	     //silentPrint.setAttribute(SilentPrint.URL_AUTH_ID, urlAuthId);
	     //silentPrint.setAttribute(SilentPrint.URL_AUTH_PASSWORD, urlAuthPassword);
	     try {
	       silentPrint.start();
	     }catch(Throwable t) {
	       t.printStackTrace();
	     }*/
		// merging version, super slow
		/*try {
			String  src = this.settings.getWorkDir() + "/downloads/" + filename,
					dest = this.settings.getWorkDir() + "/archive/" + filename,
					temp = this.settings.getWorkDir() + "/temp.pdf";
			PDFMergerUtility ut = new PDFMergerUtility();
			// add the initial source file
			ut.addSource(src);
			ut.setDestinationFileName(temp);
			// merge temp PDF with itself until it creates a document
			// with enough copies
			PDDocument tempDoc = PDDocument.load(new File(src));
			int numOfPages = tempDoc.getNumberOfPages();
			tempDoc.close();
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
			while(doc.getNumberOfPages() > copies * numOfPages)
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
		*/
		//get the print time and display it
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		this.settings.addMessage("Printing succesful, total time taken to print this shit was: " + (double)totalTime / 1000 + "s");
		return true;
	}
	
	/*public int getNumOfDeletingFiles(){
		return cleaner.getTrackCount();
	}*/
}