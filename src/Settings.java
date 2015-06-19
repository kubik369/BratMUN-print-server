
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Settings extends JFrame {
	private String name, password, print, archive;
	private Mail mailbox;
	
	public Settings(Mail gmail){
		this.mailbox = gmail;
	}
	
	public boolean getCredentials()
	{
		// creates the input dialog for username and password
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		JLabel lUser = new JLabel("Username:");
		lUser.setHorizontalAlignment(SwingConstants.CENTER);
		myPanel.add(lUser);
		JTextField userField = new JTextField(20);
		myPanel.add(userField);
		JLabel lPass = new JLabel("Password:");
		lPass.setHorizontalAlignment(SwingConstants.CENTER);
		myPanel.add(lPass);
		JPasswordField passwordField = new JPasswordField(20);
		myPanel.add(passwordField);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
		           "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		// if user clicked on cancel, quit
		if(result == -1 || result == JOptionPane.CANCEL_OPTION){
			System.out.println("I never wanted to print anyway.");
			System.exit(0);
		}
		// if OK then get the data from the input fields
		else if (result == JOptionPane.OK_OPTION) {
			this.name = userField.getText();
			this.password = new String(passwordField.getPassword());
			return true;
		}
		return false;
	}
	
	public void setPrintFolder(int choice){
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
	      if(choice == 1) this.print = chooser.getSelectedFile().toString();
	      else this.archive = chooser.getSelectedFile().toString();
	      //System.out.println(chooser.getSelectedFile().toString() + " " + chooser.getSelectedFile().getClass());
	    } else {
	      System.out.println("No Selection ");
	    }
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public void setPassword(String s){
		this.password = s;
	}
	
	public String getPrintDir(){
		return this.print;
	}
	
	public String getArchiveDir(){
		return this.archive;
	}
}
