import javax.swing.BoxLayout;
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
			setName(userField.getText());
			setPassword(new String(passwordField.getPassword()));
			return true;
		}
		return false;
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
}
