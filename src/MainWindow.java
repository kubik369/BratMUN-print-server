import javax.mail.Message;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame
{
	public MainWindow()
	{
		this.setSize(800, 550);
		// pack();
		setVisible(true);
		MainPanel myMainPanel = new MainPanel(); 
		add(myMainPanel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void PrintMessage(Message[] messages)
	{
		
	}
	
	public static void main(String[] args) 
	{
		JFrame frame = new MainWindow();
		frame.setVisible(true);
	}
}