package com.ericsson.thread;

public class ThreadFactory {

	public static void lunchThread(Runnable runnable) {
		new Thread(runnable).start();
	}
	
}
