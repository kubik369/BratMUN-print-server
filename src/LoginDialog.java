import javax.swing.BoxLayout;
import javax.swing.JFrame;
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
		String[] cred = new String[2];
		if(result == -1){
			System.out.println("I never wanted to print anyway.");
			System.exit(0);
		}
		if (result == JOptionPane.OK_OPTION) {
			cred[0] = userField.getText();
			cred[1] = new String(passwordField.getPassword()); 
			/*System.out.println("Username value: " + cred[0]);
			System.out.println("Password value: " + cred[1]);
			*/
		}
		return cred;
	}
}
