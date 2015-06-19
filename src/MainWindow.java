import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainWindow
{
	public MainWindow(Mail a)
	{
		JFrame frame = new JFrame();
		frame.setSize(800, 550);
		
		MainPanel myMainPanel = new MainPanel(a); 
		frame.add(myMainPanel);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}