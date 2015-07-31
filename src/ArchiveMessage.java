public class ArchiveMessage implements java.io.Serializable {
	private String raw, filename, sentBy;
	private int numOfPages;
	private FileStatus status;
	
	public ArchiveMessage(String r, FileStatus f){
		this.raw = r;
		this.status = f;
		extractData();
	}
	
	private void extractData(){
		String[] data = this.raw.split("_");
		System.out.println(data.length);
		this.filename = data[0];
		this.sentBy = data[1];
		this.numOfPages = Integer.parseInt(data[2].substring(0, data[2].length() - 4));
	}

	public String getRaw() {
		return raw;
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
