import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;


public class LoginDialog {
	
	public LoginDialog(){
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public String[] getCredentials()
	{
		JPanel myPanel = new JPanel();
		
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Username:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		myPanel.add(label);
		JTextField usernameField = new JTextField(20);
		myPanel.add(usernameField);
		JLabel label_1 = new JLabel("Password:");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		myPanel.add(label_1);
		JPasswordField passwordField = new JPasswordField(20);
		myPanel.add(passwordField);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
		           "Please Enter your username and password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		String[] cred = new String[2];
		if (result == JOptionPane.OK_OPTION) {
			cred[0] = usernameField.getText();
			cred[1] = new String(passwordField.getPassword()); 
			/*System.out.println("Username value: " + cred[0]);
			System.out.println("Password value: " + cred[1]);
			*/
		}
		return cred;
	}
}
