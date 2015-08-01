import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FTP {
	private String SFTPHOST, SFTPUSER, SFTPPASS, SFTPWORKINGDIR;
	private int SFTPPORT;
	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;
	private Settings settings;
	
	public FTP(Settings s) {
		this.settings = s;
	}

	public void startFTP(){
		/*String SFTPHOST = "server02.inetadmin.eu";
		int SFTPPORT = 22;
		String SFTPUSER = "janbolech_kubik369";
		String SFTPPASS = JOptionPane.showInputDialog(new JFrame("InputDialog"), "Input your FTP password?");
		String SFTPWORKINGDIR = "/ironbaron.eu/";*/
		/*SFTPHOST = "eloth.gjh.sk";
		SFTPPORT = 22;
		SFTPUSER = "simo.j";
		SFTPPASS = JOptionPane.showInputDialog(new JFrame("InputDialog"), "Input your FTP password?");
		*/
		SFTPWORKINGDIR = "/home/2012/simo.j/";
		try {
			channelSftp.cd(SFTPWORKINGDIR);
			/*byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get("imagine.txt"));
			File newFile = new File("C:/Users/Jakub/Desktop/test/imagine.txt");
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount; // System.out.println("Getting: " + theLine);
			while ((readCount = bis.read(buffer)) > 0) {
				System.out.println("Writing: ");
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();*/
			// lists all the files in a directory
			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
			String temp;
            for(int i=0; i<filelist.size();i++){
            	temp = filelist.get(i).toString();
                System.out.println(temp.substring(temp.lastIndexOf(" ") + 1));
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean connect(){
		try{
			JSch jsch = new JSch();
			session = jsch.getSession(settings.getUser(), settings.getHost(), settings.getPort());
			session.setPassword(settings.getPassword());
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
		} catch(Exception e){
			System.out.println("Error during connection check: " + e.getMessage());
			return false;
		}
		if(channelSftp.isConnected()){
			settings.setLoginStatus(true);
			return true;
		}
		else
			return false;
	}
	
	public void disconnect(){
		channelSftp.exit();
		session.disconnect();
	}

	public String getHost() {
		return SFTPHOST;
	}

	public void setHost(String sFTPHOST) {
		SFTPHOST = sFTPHOST;
	}

	public String getUser() {
		return SFTPUSER;
	}

	public void setUser(String sFTPUSER) {
		SFTPUSER = sFTPUSER;
	}

	public String getPassword() {
		return SFTPPASS;
	}

	public void setPassword(String sFTPPASS) {
		SFTPPASS = sFTPPASS;
	}

	public String getDir() {
		return SFTPWORKINGDIR;
	}

	public void setDir(String sFTPWORKINGDIR) {
		SFTPWORKINGDIR = sFTPWORKINGDIR;
	}

	public int getPort() {
		return SFTPPORT;
	}

	public void setPort(int sFTPPORT) {
		SFTPPORT = sFTPPORT;
	}
	
}