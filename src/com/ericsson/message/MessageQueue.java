package com.ericsson.message;

import java.util.LinkedList;

public class MessageQueue {

	private int size;
	private LinkedList<String> queue = null;
	
	public MessageQueue() {
		size = 0;
		queue = new LinkedList<String>();
		
	}
	
	public synchronized int getMessageSize() {
		//return size;
		return queue.size();
	}
	
	public synchronized String getMessage() {
		if(queue.size() == 0) {
			return null;
		}
		String message = queue.removeFirst();
		size--;
		return message;
	}
	
	public synchronized void putMessage(String message) {
		queue.add(message);
		size++;
	}
	
}
