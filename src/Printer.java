import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

public class Printer
{
	public Printer()
	{
	
	}
	public void print() throws IOException, PrinterException
	{}
}
	/*	
	public void print() throws IOException, PrinterException
	{
		System.out.println("Printing initiated.");

		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	    PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
	    patts.add(Sides.DUPLEX);
	    PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
	    if (ps.length == 0) {
	        throw new IllegalStateException("No Printer found");
	    }
	    System.out.println(ps.length);
	    System.out.println("Available printers: " + Arrays.asList(ps));

	    PrintService myService = null;
	    for (PrintService printService : ps) {
	        if (printService.getName().equals("Microsoft XPS Document Writer")) {
	            myService = printService;
	            break;
	        }
	    }

	    if (myService == null) {
	        throw new IllegalStateException("Printer not found");
	    }

	    FileInputStream fis = new FileInputStream("C:/test.pdf");
	    Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
	    DocPrintJob printJob = myService.createPrintJob();
	    try{
	    printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
	    }
	    catch (Exception e){
	    	System.out.println("problem " + e.getMessage());
	    }
	    fis.close();       
	}
}
*/
/*
import com.johannasoft.sjprint.api.SJPrintApiImpl;
import com.johannasoft.sjprint.api.SJPrintException;
import java.util.logging.Level;
import java.util.logging.Logger;
*/
/*
public class SJPrintApiExamplePrint {

    public static void main(String[] args) {
        try {
            // init SJPrintApi 
            SJPrintApiImpl sjprint_api = new SJPrintApiImpl();

            // set log dir path, it must exist! 
            sjprint_api.setDirPathForLogging("e:\\temp");
            // several options

            // REMEMBER , files are deleted after print completion !!!!!!!!!

            // option 1, print with default settings on default printer. Execute it on its own thread
            sjprint_api.setFilePathToPrint("e:\\temp\\test.pdf");
            sjprint_api.QuickPrint();

            // option 2, print with default settings, given a printer name. Execute it on its own thread 
            sjprint_api.setFilePathToPrint("e:\\temp\\test2.pdf");
            sjprint_api.setPrinterName("HP LaserJet 4100 PCL 5");
            sjprint_api.QuickPrint();

            // option 3, print setting ALL parameters. 
            // PrintAndWait -> will execute the print job and wait until it finish 
            // QeuePrint-> will qeue print job on its own thread and continue 
            // refer to SJPrintApiExampleGetInfo to see how to get parameters info 
            // or refer to full documentation
            sjprint_api.setDocumentDetectionType("PDF Document");
            sjprint_api.setMediaSizeName("A4");
            sjprint_api.setNumberOfCopies(1);
            sjprint_api.setOrientationRequested("Landscape");
            sjprint_api.setPDFDocumentPassword("");
            sjprint_api.setPrintQuality("Normal");
            sjprint_api.setShrinkToPrintableArea(true);
            sjprint_api.setSidesToPrint("One sided");
            sjprint_api.setFilePathToPrint("e:\\temp\\test3.pdf");
            sjprint_api.QeuePrint();
        } catch (SJPrintException ex) {
            Logger.getLogger(SJPrintApiExamplePrint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}*/

/*
package com.johannasoft.sjprint.api.examples;

import com.johannasoft.sjprint.api.SJPrintApiImpl;
import com.johannasoft.sjprint.api.SJPrintException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SJPrintApiExampleGetInfo {

    public static void main(String[] args) {
        // avalilable printers 
        String printer_1 = null;
        List<String> printers = SJPrintApiImpl.getAllPrinters();
        System.out.println("Available printers");
        for (String s : printers) {
            System.out.println(s);
            // we keep printer name from 1st on the list 
            // just for another test 
            if (printer_1 == null) {
                printer_1 = s;
            }
        }
        System.out.println("========");

        // available size media names (all) 
        Enumeration<String> mediaNames = SJPrintApiImpl.getAllMediaSizeNames();
        System.out.println("All media size names");
        while (mediaNames.hasMoreElements()) {
            System.out.println(mediaNames.nextElement());
        }
        System.out.println("========");

        //available media size name given a printer name 
        try {
            List<String> mediaNamesPerPrinter = SJPrintApiImpl.getAllMediaSizeNameByPrinter(printer_1);
            System.out.println("Available media for printer " + printer_1);
            for (String s : mediaNamesPerPrinter) {
                System.out.println(s);
            }
            System.out.println("========");
        } catch (SJPrintException ex) {
            Logger.getLogger(SJPrintApiExampleGetInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Printing qualities 
        Enumeration<String> quality = SJPrintApiImpl.getAllPrintQualities();
        System.out.println("Print quality");
        while (quality.hasMoreElements()) {
            System.out.println(quality.nextElement());
        }
        System.out.println("========");

        // Printing sides 
        Enumeration<String> print_sides = SJPrintApiImpl.getAllPrintSides();
        System.out.println("Print sides");
        while (print_sides.hasMoreElements()) {
            System.out.println(print_sides.nextElement());
        }
        System.out.println("========");
        
        // Orientations
        Enumeration<String> orientation = SJPrintApiImpl.getAllOrientations();
        System.out.println("Orientation");
        while (orientation.hasMoreElements()) {
            System.out.println(orientation.nextElement());
        }
        System.out.println("========");
        
        // Document types 
        Enumeration<String> doc_type = SJPrintApiImpl.getAllDocumentDetectionTypes();
        System.out.println("Document type");
        while (doc_type.hasMoreElements()) {
            System.out.println(doc_type.nextElement());
        }
        System.out.println("========");
    }
}
*/