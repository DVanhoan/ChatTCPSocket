package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;

public class ManagerChatter extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSeverPort;
	
	ServerSocket srvSocket = null;
	BufferedReader bf = null;
	Thread t;
	private JTabbedPane tabbedPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerChatter frame = new ManagerChatter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerChatter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lb = new JLabel("Manager Port:");
		panel.add(lb);
		
		txtSeverPort = new JTextField();
		txtSeverPort.setText("12340");
		panel.add(txtSeverPort);
		txtSeverPort.setColumns(10);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		this.setSize(600, 300);
		
		int serverPort = Integer.parseInt(txtSeverPort.getText());
		
		try {
		srvSocket = new ServerSocket(serverPort);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		while(true) {
			try {
				Socket aStaffSocket = srvSocket.accept();
				if(aStaffSocket != null) {
					bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
					String S = bf.readLine();
					int pos = S.indexOf(":");
					String staffName = S.substring(pos+1);
					ChatPanel p = new ChatPanel(aStaffSocket, "Manager", staffName);
					tabbedPane.add(staffName, p);
					p.updateUI();
				}
				Thread.sleep(100);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
