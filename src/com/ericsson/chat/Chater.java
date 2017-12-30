package com.ericsson.chat;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ericsson.thread.MessageReceiver;
import com.ericsson.thread.ThreadFactory;

public class Chater extends Frame {

	private static String ip = "127.0.0.1";
	private static int port = 8888;
	
	private TextField tfTxt = new TextField();
	private TextArea taContent = new TextArea();
	private Socket s = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;

	public static void main(String[] args) {
		if(args.length == 2) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		new Chater().launchFrame();
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
		
		MessageReceiver receiver = new MessageReceiver(taContent, dis);
		ThreadFactory.lunchThread(receiver);
	}
	
	private void login() {
		try {
			s = new Socket(ip, port);
			System.out.println("Client have connected to server");
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void logout() {
		try {
			dos.writeUTF("bye");
			dos.flush();
			
			dos.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			tfTxt.setText("");
			
			try {
				//String message = s.getInetAddress().getHostAddress()  + ":" + s.getPort() + " say : " + str;
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
