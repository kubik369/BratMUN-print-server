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

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	Settings settings;
	FTP ftp;
	JPanel topPanel;
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
		
		myMainPanel = new MainPanel(); 		
		topPanel = new JPanel();
		btnLogin = new JButton("Login");
		btnConnect = new JButton("Connect");
		btnChangeDir = new JButton("Change dir");
		btnConnect.setEnabled(false);
		
		// position the buttons in the JPanel
		btnLogin.setBounds(348, 11, 100, 23);
		btnConnect.setBounds(215, 11, 123, 23);
		btnChangeDir.setBounds(458, 11, 111, 23);
		
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
		btnChangeDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setDir();
				btnConnect.setEnabled(settings.isDirsCreated());
			}
		});
		topPanel.setLayout(null);
		
		// add the buttons to the JPanel
		topPanel.add(btnConnect);
		topPanel.add(btnLogin);
		topPanel.add(btnChangeDir);
		
		getContentPane().add(topPanel, BorderLayout.CENTER);
		
		btnStartStop = new JButton("Start");
		btnStartStop.setBounds(581, 10, 81, 25);
		btnStartStop.setEnabled(false);
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnStartStop.getText() == "Start"){
					ftp.start();
					btnStartStop.setText("Stop");
				}
				else{
					ftp.stop();
					btnStartStop.setText("Start");
				}
			}
		});
		topPanel.add(btnStartStop);
		getContentPane().add(myMainPanel, BorderLayout.NORTH);
		
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