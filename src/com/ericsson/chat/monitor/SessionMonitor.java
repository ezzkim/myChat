package com.ericsson.chat.monitor;

import java.util.List;

import com.ericsson.thread.ChatSession;

public class SessionMonitor implements Runnable {

	private boolean keepMonitor;
	private List<ChatSession> chaters = null;
	
	public SessionMonitor(List<ChatSession> chaters) {
		keepMonitor =  true;
		this.chaters = chaters;
	}

	public void stopMonitor() {
		keepMonitor = false;
	}
	
	public void run() {
		while(keepMonitor) {
			for(ChatSession peer : chaters) {
				if(peer.isPeerAlive() == false) {
					chaters.remove(peer);
				}
			}
		}
	}

}
