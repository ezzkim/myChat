package com.ericsson.thread;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageReceiver implements Runnable {

	private boolean keepReceiveMessage = true;
	private TextArea taContent = null;
	private DataInputStream dis = null;
	
	public MessageReceiver(TextArea taContent, DataInputStream dis) {
		this.keepReceiveMessage = true;
		this.taContent = taContent;
		this.dis = dis;
	}
	
	public void stopReceiveMessage() {
		this.keepReceiveMessage = false;
	}
	
	public void run() {
		while(keepReceiveMessage) {
			try {
				String str = dis.readUTF();
				System.out.println("Receive message : " + str);
				taContent.append(str+"\n");
			} catch (IOException e) {
				keepReceiveMessage = false;
				e.printStackTrace();
			}
		}
	}

}
