import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	Settings settings;
	FTP ftp;
	MainPanel myMainPanel;
	JButton btnLogin, btnConnect, btnChangeDir, btnStartStop;
	
	public MainWindow(FTP f, Settings s)
	{
		this.ftp = f;
		this.settings = s;
		// nice look & feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("Exception occured in MainWindow constructor: " + e.getMessage());
			e.printStackTrace();
		}
		GUI();
	}
	
	private void GUI(){
		setResizable(false);
		setTitle("BratMUN Print Server");
		this.setSize(800, 550);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 292, 123, 23);
		getContentPane().add(btnConnect);
		btnConnect.setEnabled(false);
		btnLogin = new JButton("Login");
		btnLogin.setBounds(25, 338, 100, 23);
		getContentPane().add(btnLogin);
		btnChangeDir = new JButton("Change dir");
		btnChangeDir.setBounds(179, 290, 111, 23);
		getContentPane().add(btnChangeDir);
		
		btnStartStop = new JButton("Start");
		btnStartStop.setBounds(191, 336, 81, 25);
		getContentPane().add(btnStartStop);
		btnStartStop.setEnabled(false);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 11, 353, 225);
		getContentPane().add(editorPane);
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnStartStop.getText() == "Start"){
					ftp.start();
					settings.getPrinter().start();
					btnStartStop.setText("Stop");
				}
				else{
					ftp.stop();
					settings.getPrinter().stop();
					btnStartStop.setText("Start");
				}
			}
		});
		btnChangeDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setDir();
				btnConnect.setEnabled(settings.isDirsCreated());
			}
		});
		
		// actionListerens for buttons
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				settings.getCredentials();			
			}
		});
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnConnectAction();
			}
	    });
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(ftp.isConnected())
					ftp.disconnect();
			}
		});
		this.setVisible(true);
	}
	
	private void btnConnectAction(){
		if (btnConnect.getText().compareTo("Connect") == 0){
			if(this.ftp.connect()){
				JOptionPane.showMessageDialog(null, "Succesful connection to the FTP server.");
				btnConnect.setText("Disconnect");
				btnStartStop.setEnabled(true);
			}
			else
				JOptionPane.showMessageDialog(null, "Something went wrong, probably your login data.");
		}
		else{ 
			btnConnect.setText("Connect");
			this.ftp.disconnect();
			btnStartStop.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Successful disconnection");
		}
	}
}