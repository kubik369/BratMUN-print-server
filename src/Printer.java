import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.*;
import org.apache.commons.io.FileCleaningTracker;
import static java.nio.file.StandardCopyOption.*;

public class Printer
{
	private PrintService myPrinter;
	private String workDir;
	private FileCleaningTracker cleaner;
	
	public Printer(String dir)
	{
		this.workDir = dir;
		this.cleaner = new FileCleaningTracker();
		getPrintService();
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
	
	public boolean print(String filename, int copies) throws IOException, PrinterException
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Printing");
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintService(myPrinter);
		try {
			Path src = Paths.get(this.workDir + "/downloads/" + filename),
				 dest = Paths.get(this.workDir + "/archive/originals/test.pdf"),
				 temp = Paths.get(this.workDir + "/temp.pdf");
			
			PDFMergerUtility ut = new PDFMergerUtility();
			File f = src.toFile();
			for(int i = 0;i < copies;i++)
				ut.addSource(f);
			ut.setDestinationFileName(temp.toString());
			ut.mergeDocuments();
			File out = temp.toFile();
			PDDocument doc = PDDocument.load(out);
			PDFPrinter printing = new PDFPrinter(doc, Scaling.ACTUAL_SIZE, Orientation.AUTO);
			printing.silentPrint(job);
			doc.close();
			Files.delete(temp);
			Files.copy(src, dest, REPLACE_EXISTING);
			cleaner.track(f, job);
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
	
	public int getNumOfDeletingFiles(){
		return cleaner.getTrackCount();
	}
}