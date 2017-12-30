package com.ericsson.chat;

import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.chat.monitor.SessionMonitor;
import com.ericsson.message.MessageQueue;
import com.ericsson.thread.MessageDispatcher;
import com.ericsson.thread.ChatSession;
import com.ericsson.thread.ThreadFactory;

public class Backend {

	private boolean started = false;
	private List<ChatSession> chaters = null;
	private MessageQueue messageQueue = null;
	
	private static Backend instance = null;
	
	private Backend() {
		started = false;
		chaters = new ArrayList<ChatSession>();
		messageQueue = new MessageQueue();
	}
	
	public static Backend getInstance() {
		if(instance == null) {
			instance = new Backend();
		}
		
		return instance;
	}
	
	public void lunch(int port) {
		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(port);
		} catch (BindException e) {
			System.out.println("Port " + port + " is in used...");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Backend server lunch fail...");
			System.exit(0);
		}
		
		started = true;
		System.out.println("Backend server listing on 8888 port");
		
		while(started) {
			ThreadFactory.lunchThread(new SessionMonitor(chaters));
			ThreadFactory.lunchThread(new MessageDispatcher(messageQueue, chaters));
			
			Socket s = null;
			try {
				s = ss.accept();  // backend thread just accept client connect request
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println("a client connected");
			System.out.println("Chater ip[" + s.getInetAddress().getHostAddress() + "],port["+ s.getPort() + "]");
			
			ChatSession chatThread = new ChatSession(s, messageQueue);
			ThreadFactory.lunchThread(chatThread);
			chaters.add(chatThread);	
		}
	}
	
}
