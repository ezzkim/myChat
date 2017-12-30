package com.ericsson.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.ericsson.message.MessageQueue;

public class ChatSession implements Runnable {

	private Socket s = null;
	private boolean connected  = false;
	private boolean gracefulDisconnect = true;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private MessageQueue messageQueue = null;
	
	private boolean peerIsAlive = true;
	
	public boolean isPeerAlive() {
		return peerIsAlive;
	}

	public ChatSession(Socket s, MessageQueue messageQueue) {
		this.s = s;
		connected = true;
		this.messageQueue = messageQueue;
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		groupChat();
	}
	
	public void dispathMessage(String message) {
		try {
			dos.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPeerIpAddress() {
		return s.getInetAddress().getHostAddress();
	}
	
	public int getPeerPort() {
		return s.getPort();
	}
	
	////////////private///////////////
	
	private void privateChat()
	{
		//
	}
	
	private void groupChat() {
		while(connected == true) {
			try	{
				String str = dis.readUTF();
				System.out.println("server receive one message : " + str);
				
				String message = s.getInetAddress().getHostAddress()  + ":" + s.getPort() + " say : " + str;
				messageQueue.putMessage(message);
				
				if(str.equalsIgnoreCase("bye") || str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("exit")) {
					connected = false;
					gracefulDisconnect = true;
					peerIsAlive = false;
					System.out.println("Client will exit...");
				}
			} catch (EOFException e) {
				System.out.println("Client closed");
				connected = false;
				gracefulDisconnect = false;
				peerIsAlive = false;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Client exited");
				connected = false;
				gracefulDisconnect = false;
				peerIsAlive = false;
			}
		}
		
		try {
			if(dis != null)  dis.close();
			if(dos != null)  dos.close();
			if(s != null)  s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(gracefulDisconnect) {
			System.out.println("Chater ip[" + s.getInetAddress().getHostAddress() + "],port[" + s.getPort() + "] exited...");
		}
	}
	
}