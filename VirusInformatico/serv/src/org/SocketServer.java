package org;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

	public class SocketServer extends Thread{
		private static Socket s = null;
	    public static String path = System.getProperty("user.dir");
	    static Scanner input = new Scanner (System.in);
	    
	    SocketServer(Socket s) {
	    	SocketServer.s = s;
	    }
	    
	    public void run(){
				menu();	
			}
	    
	    public static void menu() {
			try {
				System.out.println(path + "/virus/test.txt");
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				DataOutputStream dos = new DataOutputStream(oos);

				File folder = new File(path + "/virus");
				File[] listOfFiles = folder.listFiles();
				dos.writeInt(listOfFiles.length);
			    	for (File file: listOfFiles) {
				      if (file.isFile()) {
				    	  
				    	dos.writeLong(file.length());
				    	dos.writeUTF(file.getName());
				    	
				        FileInputStream fis = new FileInputStream(file);
				        BufferedInputStream bis = new BufferedInputStream(fis);
				        
				        int counter = 0;
				        while ((counter = bis.read()) != -1) oos.writeByte(counter);
				        System.out.println("File #"+file+" sent to client.");
				        bis.close();
				      
				      } else if (file.isDirectory()) {
				        System.out.println("Directory " + file.getName());
				      }
				    }
				    oos.close();
			} catch (Exception e) {
				 e.printStackTrace();	
			}
		}
	}

