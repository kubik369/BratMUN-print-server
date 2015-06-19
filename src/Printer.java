import java.awt.Desktop;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.print.Paper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.printing.*;

public class Printer
{
	private PrintService myPrinter;
	
	public Printer()
	{
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
	
	public void print(String filename, int copies) throws IOException, PrinterException
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Printing");
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintService(myPrinter);
        
        if (Desktop.isDesktopSupported()) {
            try {
            	PDFMergerUtility ut = new PDFMergerUtility();
            	/*File f = new File(filename);
            	PDDocument doc = PDDocument.load(f);
            	*/
            	/* 32s*/
            	File f = new File(filename);
            	PDDocument temp = PDDocument.load(f),
            			   doc = new PDDocument();
            	/*System.out.println("Opened the file.");
            	PDPageTree allPages = temp.getPages();
            	for(int i = 0;i < copies;i++){
	            	for(PDPage p : allPages){
	            		doc.addPage(p);
	            	}
            	}
            	System.out.println("Created the output file.");*/
            	//for(int i = 0;i < copies;i++)
            	//	ut.appendDocument(doc, temp);
            	//temp.close();
            	//File myFile = new File(filename);
                //PDDocument doc = PDDocument.load(myFile);
            	
            	// 34.658s
            	/*for(int i = 0;i < copies;i++)
            		ut.addSource(filename);
            	ut.setDestinationFileName("c:\\Users\\Jakub\\Desktop\\test\\temp.pdf");
            	ut.mergeDocuments();
            	File f = new File("c:\\Users\\Jakub\\Desktop\\test\\temp.pdf");*/
            	doc = temp;
            	job.setCopies(copies);
            	System.out.println("Number of copies in printerJob = " + job.getCopies());
            	PDFPrinter printing = new PDFPrinter(doc, Scaling.ACTUAL_SIZE, Orientation.AUTO);
            	//PDFPrinter printing = new PDFPrinter(doc, job, Scaling.ACTUAL_SIZE, Orientation.AUTO, doc.getPage(0).);
            	//for(int i = 0;i < copies;i++)
            		printing.silentPrint();
    	    	doc.close();
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
        System.out.println("Printing succesful");
        long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total time taken to print this shit was: " + (double)totalTime / 1000 + "s");
	}
}