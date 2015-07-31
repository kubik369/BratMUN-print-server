
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	private FTP ftp;
	
	public Settings(Mail gmail, FTP f){
		this.mailbox = gmail;
		this.ftp = f;
	}
	
	public void getCredentials()
	{
		/*this.loginStatus = false;
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
		}*/
		this.loginStatus = false;
		// creates the input dialog for username and password
		JPanel myPanel = new JPanel(),
				pUser = new JPanel(new GridBagLayout()),
				pPassword = new JPanel(new GridBagLayout()),
				pServer = new JPanel(new GridBagLayout()),
				pPort = new JPanel(new GridBagLayout());
		
		JLabel lUser = new JLabel("Username"),
			   lPassword = new JLabel("Password"),
			   lServer = new JLabel("Server"),
			   lPort = new JLabel("Port");
		JTextField tfUser = new JTextField(20),
				   tfServer = new JTextField(20),
				   tfPort = new JTextField(4);
		JPasswordField pfPassword = new JPasswordField(20);
		
		pServer.add(lServer);
		pServer.add(tfServer);
		pPort.add(lPort);
		pPort.add(tfPort);
		pUser.add(lUser);
		pUser.add(tfUser);
		pPassword.add(lPassword);
		pPassword.add(pfPassword);
		
		pPort.setBackground(Color.RED);
		
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(pServer);
		myPanel.add(pPort);
		myPanel.add(pUser);
		myPanel.add(pPassword);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
		           "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			if(mailbox.checkConnection("imap.gmail.com", tfUser.getText(), new String(pfPassword.getPassword()))){
				this.name = tfUser.getText();
				this.password = new String(pfPassword.getPassword());
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
		if (option == JFileChooser.APPROVE_OPTION && Files.isDirectory(Paths.get(chooser.getSelectedFile().toString()))) {
			this.workDir = chooser.getSelectedFile().toString();
			System.out.println("Directory successfully changed.");
			//JOptionPane.showMessageDialog(null, "Directory successfuly changed.", "Directory status", JOptionPane.INFORMATION_MESSAGE);
		} 
		else{
			JOptionPane.showMessageDialog(null, "You have not chosen a directory, using last open directory.", "Directory change error", JOptionPane.INFORMATION_MESSAGE);
			if(this.workDir == null)
				this.workDir = chooser.getCurrentDirectory().toString();
		}
		Path archive = Paths.get(this.workDir + "/archive"),
			 serialized = Paths.get(this.workDir + "/archive/serialized"),
			 original = Paths.get(this.workDir + "/archive/originals"),
			 downloads = Paths.get(this.workDir + "/downloads");
		if(Files.notExists(archive)){
			int reply = JOptionPane.showConfirmDialog(null, "Do you wish to create the directories?", "Directory wizard", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				try {
					Files.createDirectory(archive);
					Files.createDirectory(serialized);
					Files.createDirectory(original);
					Files.createDirectory(downloads);
					System.out.println("Archive directory successfully created.");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error during the creation of archiving directories.", "Directory creation error", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Error while creating an archive directory - " + e.getMessage());
				}
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