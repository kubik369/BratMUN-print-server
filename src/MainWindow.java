import javax.mail.Message;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame
{
	public MainWindow()
	{
		JLabel jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);
		this.setSize(300, 300);
		// pack();
		setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void PrintMessage(Message[] messages)
	{
		
	}
}