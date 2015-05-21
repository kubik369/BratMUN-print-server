import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.mail.Message;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class MainPanel extends JPanel 
{
	private JTable myTable;
		
	public MainPanel()
	{				
		DefaultTableModel myModel = new DefaultTableModel() 
		{
	        //set the JTable read-only
	        public boolean isCellEditable(int row, int column) 
	        {
	            return false;
	        }               
	    };
	    
	    // Set column names
        String[] columnNames = {"FileName", "Sent-by", "Number of Pages", "Status"};
        myModel.setColumnIdentifiers(columnNames);
	    
		//get the message data values
    	Vector<String[]> TableData = new Vector<String[]>();  
    	int numOfMessages = 2; //will get this from message array
        for(int i=0; i<numOfMessages; i++)
        {	
        	//String[] messageData = {Mail.getMessages()[i].getFileName(), Mail.getMessages()[i].getFrom(), Mail.getMessages()[i].getNumOfPages(), "Queued"};  
        	String[] messageData = {"message.getFileName()", "message.getFrom()", "message.getNumOfPages()", "Queued"};   // placeholder
        	TableData.add(messageData);
        }

        // add DataModel to table
        for(int i=0; i<TableData.size(); i++)
        	myModel.addRow(TableData.get(i));
        
		myTable = new JTable(myModel);
		myTable.getTableHeader().setReorderingAllowed(false);
		myTable.getTableHeader().setResizingAllowed(false);
		JScrollPane myScrollPane = new JScrollPane(myTable);
        myScrollPane.setPreferredSize(new Dimension(750, 500));
		add(myScrollPane);
	}
	
	public void updateData()
	{		

	}
}
