package com.ericsson.chat;


public class CenterServer {

	public static void main(String[] args) {
		int port = 8888;
		if(args.length == 1) {
			port = Integer.parseInt(args[0]);
		}
		
		Backend.getInstance().lunch(port);
	}

}
