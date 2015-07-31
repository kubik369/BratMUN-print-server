import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Archive {
	
	private String dir;
	ArrayList<ArchiveMessage> messages;
	
	public Archive(String s){
		this.messages = new ArrayList<ArchiveMessage>();
		this.dir = s;
	}
	
	public void loadArchive(){
		try{
			Files.walk(Paths.get(this.dir)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)){
					String s = filePath.toString();
					if(s.substring(s.length() - 4).equals(".ser")){
				    	try {
							FileInputStream fileIn = new FileInputStream(filePath.toString());
							ObjectInputStream in = new ObjectInputStream(fileIn);
							this.messages.add((ArchiveMessage) in.readObject());
							in.close();
							fileIn.close();
						} catch (Exception e) {
							System.out.println("Error during the deserialization of a message: " + e.getMessage());
							e.printStackTrace();
						}
					}
			    }
			});
		}
		catch(IOException e){
			System.out.println("Error during the opening of a directory: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void backupMessage(ArchiveMessage am){
		try {
			FileOutputStream fileOut = new FileOutputStream(this.dir + "/" + am.getRaw() + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(am);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + this.dir + "/" + am.getRaw() + ".ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArchiveMessage> getMessages() {
		return this.messages;
	}
}
