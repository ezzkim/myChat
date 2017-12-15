package com.ericsson.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	private static boolean started = false;
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8888);
			started = true;
			System.out.println("Server listing on 8888 port");
			while(started) {
				boolean connected = false;
				Socket s = ss.accept();
				connected = true;
				System.out.println("a client connected");
				System.out.println("Client ip = " + s.getInetAddress().getHostAddress());
				DataInputStream dis = new DataInputStream(s.getInputStream());
				while(connected) {
					String str = dis.readUTF();
					System.out.println("server receive message : " + str);
				}
				dis.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
