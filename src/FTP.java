import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FTP {
	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;
	private Settings settings;
	private Timer downloadTimer;
	private boolean downloading;

	public FTP(Settings s) {
		this.settings = s;
	}
	
	public void start(){
		downloadTimer = new Timer();
		downloadTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				downloadFiles();				
			}
		}, 10 * 1000, 10 * 1000);
	}
	
	public void stop(){
		downloadTimer.cancel();
		downloadTimer.purge();
	}
	
	public void downloadFiles(){
		this.downloading = true;
		//System.out.println("Downloading");
		//folder from which the files will be downloaded
		//String SFTPWORKINGDIR = "/home/other/bratmun/www/printing/print-ready/";
		String SFTPWORKINGDIR = settings.getFTPdir();
		try {
			channelSftp.cd(SFTPWORKINGDIR);
			// lists all the files in a directory
			Vector fileList = channelSftp.ls(SFTPWORKINGDIR);
            for(int i = 0;i < fileList.size();i++){
            	String name = fileList.get(i).toString().substring(56);
            	//System.out.println("Substringed name = " + name);
            	if(name.equals(".") || name.equals("..")) continue;
            	byte[] buffer = new byte[10000];
    			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(name));
    			File newFile = new File(this.settings.getWorkDir() + "/downloads/" + name);
    			OutputStream os = new FileOutputStream(newFile);
    			BufferedOutputStream bos = new BufferedOutputStream(os);
    			int readCount; // System.out.println("Getting: " + theLine);
    			while ((readCount = bis.read(buffer)) > 0) {
    				//System.out.println("Writing: ");
    				bos.write(buffer, 0, readCount);
    			}
    			channelSftp.rm(SFTPWORKINGDIR + "/" + name);
    			this.settings.addMessage("Downloaded " + name);
    			bis.close();
    			bos.close();
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.downloading = false;
	}
	
	public boolean connect(){
		// check if we aren't already connected to the FTP
		if(channelSftp != null && isConnected())
			return true;
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
			this.settings.addMessage("Error during the connection.");
			//System.out.println("Error during connection check: " + e.getMessage());
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
		if(isConnected()){
			channelSftp.exit();
			session.disconnect();
		}
	}
	
	public boolean isConnected() {
		return channelSftp != null && channelSftp.isConnected();
	}

	public boolean isDownloading() {
		return downloading;
	}
}