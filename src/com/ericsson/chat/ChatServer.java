package com.ericsson.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	private static boolean flag = true;
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8888);
			System.out.println("Server listing on 8888 port");
			while(flag) {
				Socket s = ss.accept();
				System.out.println("a client connected");
				System.out.println("Client ip = " + s.getInetAddress().getHostAddress());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
