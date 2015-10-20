import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Settings {
	private String user, password, host, workDir, FTPdir;
	private int port;
	boolean loginStatus, dirsCreated;
	private FTP ftp;
	private LoginWindow login;
	private Printer printer;
	
	public Settings(){}
	
	public void getCredentials(){
		login = new LoginWindow(this);
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
			 downloads = Paths.get(this.workDir + "/downloads");
		if(Files.notExists(archive)){
			int reply = JOptionPane.showConfirmDialog(null, "Do you wish to create the directories?", "Directory wizard", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				try {
					Files.createDirectory(archive);
					Files.createDirectory(downloads);
					System.out.println("Archive directory successfully created.");
					setDirsCreated(true);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error during the creation of archiving directories.", "Directory creation error", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Error while creating an archive directory - " + e.getMessage());
					setDirsCreated(false);
				}
			}
		}
		else
			setDirsCreated(true);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String name) {
		this.user = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getWorkDir() {
		return workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public boolean isDirsCreated() {
		return dirsCreated;
	}

	public void setDirsCreated(boolean dirsCreated) {
		this.dirsCreated = dirsCreated;
	}
	
	public String getFTPdir() {
		return FTPdir;
	}

	public void setFTPdir(String fTPdir) {
		FTPdir = fTPdir;
	}

	public Printer getPrinter() {
		return printer;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}