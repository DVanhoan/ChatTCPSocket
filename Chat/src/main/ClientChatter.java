package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientChatter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSeverStaff;
	private JTextField txtSeverIP;
	private JTextField txtSeverPort;


	Socket mngSocket = null;
	String mngIp = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread t = null;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChatter frame = new ClientChatter();
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
	public ClientChatter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Staff and sever info.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 7, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Staff:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel);
		
		txtSeverStaff = new JTextField();
		panel.add(txtSeverStaff);
		txtSeverStaff.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Mng ip: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);
		
		txtSeverIP = new JTextField();
		panel.add(txtSeverIP);
		txtSeverIP.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("port:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2);
		
		txtSeverPort = new JTextField();
		panel.add(txtSeverPort);
		txtSeverPort.setColumns(10);
		
		
		JFrame thisFrame = this;
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mngIp = txtSeverIP.getText();
				mngPort = Integer.parseInt(txtSeverPort.getText());
				staffName = txtSeverStaff.getText();
				try{
					mngSocket = new Socket(mngIp, mngPort);
					if(mngSocket != null) {
						ChatPanel chatPanel = new ChatPanel(mngSocket, staffName, "manager" );
						thisFrame.getContentPane().add(chatPanel);
						chatPanel.gettxtMessages().append("Manager is running");
						chatPanel.updateUI();
						
						bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
						os = new DataOutputStream(mngSocket.getOutputStream());
						
						os.writeBytes("Staff: " + staffName);
						os.write(13);
						os.write(10);
						os.flush();
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnConnect.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnConnect);
	}

}
