import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class MainPanel extends JPanel 
{
	//private JTable myTable;
	private Mail m;
	private ArrayList<Message> messageList;
	private ArrayList<String[]> tableData = new ArrayList<String[]>();
	private JTable myTable;
	private JTable table;
		
	public MainPanel(Mail a)
	{				
		this.m = a;
		
		DefaultTableModel myModel = new DefaultTableModel(){
	        //set the JTable read-only
	        public boolean isCellEditable(int row, int column) 
	        {
	            return false;
	        }               
	    };
	    // Set column names
        String[] columnNames = {"FileName", "Sent-by", "Number of Pages", "Status"};
        myModel.setColumnIdentifiers(columnNames);
        table = new JTable(myModel);
		table.setRowSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(750, 460));
		this.add(scrollPane);
		//ArrayList<Message> messageList = m.getMessages();
        
		//get the message data values
    	Vector<String[]> TableData = new Vector<String[]>();
        /*for(int i=0; i<messageList.size(); i++){	
        	try{
        		//String[] messageData = {"message.getFileName()", messageList.get(0).getFrom()[0].toString(), "message.getNumOfPages()", "Queued"};   // placeholder
        		tableData.add(new String[] {"message.getFileName()", messageList.get(0).getFrom()[0].toString(), "message.getNumOfPages()", "Queued"} );
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	catch (MessagingException e) {
     			e.printStackTrace();
     		}
        	
        	for(int j=0; i<tableData.size(); i++){
        		TableData.add(tableData.get(i));
        	}
        }*/
    	/*TableData.add(new String[] {"message.getFileName()", "some random shit", "message.getNumOfPages()", "Queued"} );
    	
        // add DataModel to table
        for(int i=0; i<TableData.size(); i++)
        	myModel.addRow(TableData.get(i));
        
        myTable = new JTable(myModel);
		myTable.getTableHeader().setReorderingAllowed(false);
		myTable.getTableHeader().setResizingAllowed(false);
		JScrollPane myScrollPane = new JScrollPane(myTable);
        myScrollPane.setPreferredSize(new Dimension(750, 500));
		this.add(myScrollPane);*/
	}
	
	public void updateData()
	{		

	}
}
