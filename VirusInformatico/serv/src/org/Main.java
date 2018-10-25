package org;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
	public static final int PORT = 9006;
    public static ServerSocket ss = null; 
    public static Socket s = null;

	public static void main(String[] args) {
		try {
			ss = new ServerSocket(PORT);
			System.err.println("- -  Server - -");
			while(true){
				s = ss.accept();
				System.out.println("NEW CONNECTION WORKING ON ADDRESS -> " + s.getInetAddress().getHostName());
				Thread thread = new SocketServer(s);
				thread.start();
			}
		} catch (IOException e) {	
			System.err.println("Port already in use.");
			e.printStackTrace();}
	}
}