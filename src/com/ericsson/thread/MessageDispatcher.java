package com.ericsson.thread;

import java.util.List;

import com.ericsson.message.MessageQueue;

public class MessageDispatcher implements Runnable {

	private boolean keepDispatch = true;
	private MessageQueue messageQueue = null;
	private List<ChatSession> chaters = null;
	
	public MessageDispatcher(MessageQueue messageQueue, List<ChatSession> chaters) {
		this.messageQueue = messageQueue;
		this.chaters = chaters;
		this.keepDispatch = true;
	}
	
	public void stopDispatcher() {
		this.keepDispatch = false;
	}
	
	public void run() {
		while(keepDispatch) {
			String message = messageQueue.getMessage();
			if(message == null) {
				continue;
			}
			
			//System.out.println("Dispatcher will dispatch message : " + message);
			
			for(ChatSession session : chaters) {
				//String m = session.getPeerIpAddress()  + ":" + session.getPeerPort() + " say : " + message;
				session.dispathMessage(message);
			}
		}
		
	}

}
