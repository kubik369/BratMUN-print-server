
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	private String name, password, workDir;
	boolean loginStatus = false;
	private Mail mailbox;
	
	public Settings(Mail gmail){
		this.mailbox = gmail;
	}
	
	public void getCredentials()
	{
		this.loginStatus = false;
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
		if (result == JOptionPane.OK_OPTION) {
			if(mailbox.checkConnection("imap.gmail.com", userField.getText(), new String(passwordField.getPassword()))){
				this.name = userField.getText();
				this.password = new String(passwordField.getPassword());
				this.loginStatus = true;
			}
		}
	}
	
	public void setDir() {
		JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Please choose your operating directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			// System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			//System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

			// checks whether user has not picked a file
			if (Files.isDirectory(Paths.get(chooser.getSelectedFile().toString()))) {
				this.workDir = chooser.getSelectedFile().toString();
				JOptionPane.showMessageDialog(null, "Directory successfuly changed.", "Directory status", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "You have not chosen a directory!", "Directory change error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} 
		else{
			System.out.println("No selection or cancel.");
			return;
		}
		Path archive = FileSystems.getDefault().getPath(this.workDir + "\\archive");
		if (Files.notExists(archive)) {
			try {
				Files.createDirectory(archive);
				System.out.println("Archive directory successfully created.");
			} catch (IOException e) {
				System.out.println("Error while creating an archive directory - " + e.getMessage());
			}
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public boolean getLoginStatus(){
		return this.loginStatus;
	}
	
	public String getDir(){
		return this.workDir;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public void setPassword(String s){
		this.password = s;
	}
	
	public void setLoginStatus(boolean b){
		this.loginStatus = b;
	}
}