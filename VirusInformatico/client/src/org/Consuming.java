package org;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Consuming {
	public static String path = System.getProperty("user.dir");
	private static Socket s;
	static int PORT = 9006;
	static String IP = "localhost"; //use your ip

	public static void main(String[] args)  {
	    try {
			s = new Socket(IP ,PORT);	
	        System.err.println("-- Client  --");
			System.out.println("Connecting to Server ->" +  IP  + "/" + PORT);
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			DataInputStream dis = new DataInputStream(ois);
			int number_of_files = dis.readInt();	
			int i = 0;
			File[] files = new File[number_of_files];
			while (i < number_of_files) {
				long fileLength = dis.readLong();		
			    String fileName = dis.readUTF();
				if (fileName != "/Serv.jar"){					
				    files[i] = new File(path + "/recieved" + "/" + fileName);
					
				    FileOutputStream fos = new FileOutputStream(files[i]);
				    BufferedOutputStream bos = new BufferedOutputStream(fos);
	
				    System.out.println("working");		    	
				    for(int j = 0; j < fileLength; j++) bos.write(ois.read());
	
				    bos.close();
					System.out.println("File #"+i+ " recieved from server.");
					fos.close();				
				}
				i++;
    		}
			dis.close();
	    } catch (Exception e) {   
	    	e.printStackTrace();
	    }
	}
}
