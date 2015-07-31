import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FTP {
	public FTP() {}

	public void startFTP(){
		/*String SFTPHOST = "server02.inetadmin.eu";
		int SFTPPORT = 22;
		String SFTPUSER = "janbolech_kubik369";
		String SFTPPASS = "orangebox";
		String SFTPWORKINGDIR = "/ironbaron.eu/";*/
		String SFTPHOST = "eloth.gjh.sk";
		int SFTPPORT = 22;
		String SFTPUSER = "simo.j";
		String SFTPPASS = "OrAnGeBoX18";
		String SFTPWORKINGDIR = "/home/2012/simo.j/imagine/";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
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
			channelSftp.rm("imagine.txt");
			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
			String temp;
            for(int i=0; i<filelist.size();i++){
            	temp = filelist.get(i).toString();
                System.out.println(temp.substring(temp.lastIndexOf(" ") + 1));
            }
            channelSftp.exit();
            session.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}