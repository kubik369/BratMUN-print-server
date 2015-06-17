// SJPrint API
import com.johannasoft.sjprint.api.SJPrintApiImpl;
import com.johannasoft.sjprint.api.SJPrintException;
import java.util.Enumeration;
import java.util.List;
public class Printer
{
	public Printer()
	{
	
	}
	public void print()
	{
		try {
			System.out.println("Printing");
            // init SJPrintApi 
            SJPrintApiImpl sjprint_api = new SJPrintApiImpl();

            // set log dir path, it must exist! 
            sjprint_api.setDirPathForLogging("//home//jakub");
            // several options

            // REMEMBER , files are deleted after print completion !!!!!!!!!

            // option 1, print with default settings on default printer. Execute it on its own thread
            //sjprint_api.setFilePathToPrint("//home//jakub//test.pdf");
            //sjprint_api.QuickPrint();
            /*
            // option 2, print with default settings, given a printer name. Execute it on its own thread 
            sjprint_api.setFilePathToPrint("e:\\temp\\test2.pdf");
            sjprint_api.setPrinterName("HP LaserJet 4100 PCL 5");
            sjprint_api.QuickPrint();
			*/
            // option 3, print setting ALL parameters. 
            // PrintAndWait -> will execute the print job and wait until it finish 
            // QeuePrint-> will qeue print job on its own thread and continue 
            // refer to SJPrintApiExampleGetInfo to see how to get parameters info 
            // or refer to full documentation
            sjprint_api.setDocumentDetectionType("PDF Document");
            sjprint_api.setPrinterName("pdfprint");
            sjprint_api.setMediaSizeName("A4");
            sjprint_api.setOrientationRequested("Portrait");
            sjprint_api.setPrintQuality("Normal");
            sjprint_api.setSidesToPrint("One sided");
            sjprint_api.setFilePathToPrint("//home//jakub//test.pdf");
            sjprint_api.setNumberOfCopies(5);
            sjprint_api.QeuePrint();
            System.out.println("Print quasi successful.");
        } catch (SJPrintException ex) {
        	System.out.println("Some error with printing");
            System.out.println(ex.getMessage());
        	//Logger.getLogger(SJPrintApiExamplePrint.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public void getInfo(){
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
            //Logger.getLogger(SJPrintApiExampleGetInfo.class.getName()).log(Level.SEVERE, null, ex);
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