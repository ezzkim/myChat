package com.ericsson.chat;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Frame {

	private TextField tfTxt = new TextField();
	private TextArea taContent = new TextArea();
	private Socket s = null;
	private DataOutputStream dos = null;

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	private void launchFrame() {
		this.setLocation(400, 300);
		this.setSize(300, 300);
		this.add(tfTxt, BorderLayout.SOUTH);
		this.add(taContent, BorderLayout.NORTH);

		this.pack();

		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				logout();
				System.exit(0);
			}
			
		});
		
		tfTxt.addActionListener(new TFListener());
		
		this.setVisible(true);
		
		login();
	}
	
	private void login() {
		try {
			s = new Socket("127.0.0.1", 8888);
			System.out.println("Client have connected to server");
			dos = new DataOutputStream(s.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage() {
		
	}
	
	private void logout() {
		try {
			dos.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			//taContent.setText(str);
			tfTxt.setText("");
			//taContent.append(str+"\n");
			
			try {
				//dos.writeBytes(str);
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
