import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import java.awt.ScrollPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	private Settings settings;
	private FTP ftp;
	private MainPanel myMainPanel;
	private JButton btnSettings, btnConnect, btnChangeDir, btnStartStop;
	private JEditorPane infoBox;
	
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
		//create Printer and ask the user for printer, which is to be used
		this.settings.getPrinter().getPrintService();
	}
	
	private void GUI(){
		//general JFrame settings
		setResizable(false);
		setTitle("BratMUN Print Server");
		this.setSize(650, 330);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		//close the connection with FTP if user force-closes the window
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(ftp.isConnected())
					ftp.disconnect();
			}
		});
		//all the buttons
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(173, 247, 123, 23);
		getContentPane().add(btnConnect);
		btnConnect.setEnabled(false);
		
		btnSettings = new JButton("Settings");
		btnSettings.setBounds(483, 247, 100, 23);
		getContentPane().add(btnSettings);
		
		btnChangeDir = new JButton("Change dir");
		btnChangeDir.setBounds(332, 247, 111, 23);
		getContentPane().add(btnChangeDir);
		
		btnStartStop = new JButton("Start");
		btnStartStop.setBounds(44, 247, 81, 25);
		getContentPane().add(btnStartStop);
		btnStartStop.setEnabled(false);
		//info textbox
		infoBox = new JEditorPane();
		//infoBox.setBounds(10, 11, 624, 225);
		//getContentPane().add(infoBox);
		this.settings.setInfoBox(infoBox);
		JScrollPane scroll = new JScrollPane(infoBox);
		scroll.setBounds(10, 11, 624, 225);
		this.add(scroll);
		
		// actionListerens for buttons
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStartAction();
			}
		});
		btnChangeDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setDir();
				btnConnect.setEnabled(settings.isDirsCreated());
			}
		});
		
		btnSettings.addActionListener(new ActionListener() {
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

		//show the window
		this.setVisible(true);
	}
	
	private void btnStartAction(){
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
	
	private void btnConnectAction(){
		if (btnConnect.getText().compareTo("Connect") == 0){
			if(this.ftp.connect()){
				//JOptionPane.showMessageDialog(null, "Succesful connection to the FTP server.");
				//show successfull connection status update in infobox
				this.settings.addMessage("Succesful connection to the FTP server.");
				btnConnect.setText("Disconnect");
				btnStartStop.setEnabled(true);
			}
			else{
				JOptionPane.showMessageDialog(null, "Something went wrong, probably your login data, please try again.");
				this.settings.addMessage("Something went wrong while logging in.");
			}
		}
		else{ 
			btnConnect.setText("Connect");
			this.ftp.disconnect();
			btnStartStop.setEnabled(false);
			//JOptionPane.showMessageDialog(null, "Successful disconnection");
			this.settings.addMessage("Successful disconnection.");
		}
	}
}