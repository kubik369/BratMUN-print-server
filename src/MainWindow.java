import javax.mail.Message;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow extends JFrame
{
	public MainWindow()
	{
		JLabel jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);
		this.setSize(300, 300);
		// pack();
		setVisible(true);
	}
	
	public void PrintMessage(Message[] messages)
	{
		
	}
}