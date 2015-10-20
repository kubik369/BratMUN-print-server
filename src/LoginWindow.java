import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
	private JTextField tfServer,tfPort, tfUsername, tfRootDir;
	private JPasswordField passwordField;
	private JLabel lblServer,lblPort, lblUsername, lblPassword;
	private JButton btnConnect;
	private Settings settings;
	
	public LoginWindow(Settings s){
		setupGUI();
		this.settings = s;
	}
	private void setupGUI() {
		setBounds(new Rectangle(0, 0, 340, 210));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		tfServer = new JTextField();
		tfServer.setBounds(20, 30, 134, 20);
		getContentPane().add(tfServer);
		tfServer.setColumns(10);
		
		tfPort = new JTextField();
		tfPort.setBounds(174, 30, 45, 20);
		getContentPane().add(tfPort);
		tfPort.setColumns(10);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(20, 78, 134, 20);
		getContentPane().add(tfUsername);
		tfUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(174, 78, 134, 20);
		getContentPane().add(passwordField);
		
		lblServer = new JLabel("Server");
		lblServer.setHorizontalAlignment(SwingConstants.LEFT);
		lblServer.setBounds(20, 14, 134, 14);
		getContentPane().add(lblServer);
		
		lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setBounds(20, 61, 134, 14);
		getContentPane().add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(174, 61, 134, 14);
		getContentPane().add(lblPassword);
		
		lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.LEFT);
		lblPort.setBounds(174, 14, 45, 14);
		getContentPane().add(lblPort);
		
		btnConnect = new JButton("Connect");
		btnConnect.setActionCommand("");
		btnConnect.setBounds(110, 150, 105, 23);
		btnConnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getData();
			}
		});
		getContentPane().add(btnConnect);
		
		JLabel lblRootFolder = new JLabel("Root Folder");
		lblRootFolder.setBounds(20, 102, 288, 15);
		getContentPane().add(lblRootFolder);
		
		tfRootDir = new JTextField();
		tfRootDir.setBounds(20, 119, 288, 19);
		getContentPane().add(tfRootDir);
		tfRootDir.setColumns(10);
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	
	private void getData(){
		String  server = tfServer.getText(),
				username = tfUsername.getText(),
				password = new String(passwordField.getPassword()),
				port = tfPort.getText(),
				rootDir = tfRootDir.getText();
		if(server.isEmpty()){
			tfServer.setBackground(Color.RED);
			return;
		}
		tfServer.setBackground(Color.WHITE);
		if(port.isEmpty() || !port.matches("\\d+")){
			tfPort.setBackground(Color.RED);
			return;
		}
		tfPort.setBackground(Color.WHITE);		
		if(username.isEmpty()){
			tfUsername.setBackground(Color.RED);
			return;
		}
		tfUsername.setBackground(Color.WHITE);
		if(password.isEmpty()){
			passwordField.setBackground(Color.RED);
			return;
		}
		passwordField.setBackground(Color.WHITE);
		if(rootDir.isEmpty()){
			tfRootDir.setBackground(Color.RED);
			return;
		}
		settings.setUser(username);
		settings.setPassword(password);
		settings.setPort(Integer.parseInt(port));
		settings.setHost(server);
		settings.setFTPdir(rootDir);
		this.dispose();
	}
}
