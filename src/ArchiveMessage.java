import javax.mail.Message;

public class ArchiveMessage implements java.io.Serializable {
	private Message original;
	private String filename, sentBy;
	private int numOfPages;
	private FileStatus status;
	
	public ArchiveMessage(String f, String a, int n, FileStatus s, Message m){
		this.filename = f;
		this.sentBy = a;
		this.numOfPages = n;
		this.status = s;
		this.original = m;
	}

	public Message getOriginal() {
		return original;
	}

	public String getFilename() {
		return filename;
	}

	public String getSentBy() {
		return sentBy;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public FileStatus getStatus() {
		return status;
	}
}
