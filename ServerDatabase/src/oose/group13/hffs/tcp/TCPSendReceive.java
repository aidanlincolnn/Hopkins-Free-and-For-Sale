package oose.group13.hffs.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import oose.group13.hffs.transport.Transportable;


/**
 * This class includes methods to pass data between client and server as well as the data stream objects
 * The class extends the Thread class so we can receive and send messages at the
 * same time
 * @author aidanfowler
 */
public class TCPSendReceive extends Thread 
{

	private DataOutputStream dos;
	private DataInputStream dis;
	
	/**
	 * Get the data output stream
	 * @return dos;
	 */
	public DataOutputStream getDos(){
		return dos;
	}
	/**
	 * Get the data input stream
	 * @return dis;
	 */
	public DataInputStream  getDis(){
		return dis;
	}
	
	public void setDos(Socket s){
		if (s == null){
			dos = null;
		}
		else{	
			try {
				dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDis(Socket s){
		if (s == null){
			dis = null;
		}
		else{
			try {
				dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Method to send the messages from server to client
	 * @param message the message sent by the server
	 */
	public void sendMessage(Transportable trans)
	{
		try
		{	    		
			if (dos != null){
				//Want to store object as an array of bytes
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				//Writing an object to the object output stream will turn int into an array of bytes
				oos.writeObject(trans);
				oos.close();
				//First write the length of the Object we are sending
				dos.writeInt(baos.toByteArray().length);
				//Then write each individual byte of the Object
				dos.write(baos.toByteArray(),0,baos.toByteArray().length);
				//Push to Output Stream
				dos.flush();
			}
		}
		catch (Exception e)
		{
			System.out.println("Send Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * read a message
	 */
	public Transportable readMessage(){
		//find out the length of the object coming through the input stream
		int length = 0;
		Transportable trans = null;
		try {
			if (dis.available()>0){
				length = dis.readInt();
				if (length >0){
					//create a byte array to hold the bytes that represent the object
					//System.out.println("Allocating " + length + " bytes for incoming packet buffer.");
					byte[] objectInBytes = new byte [length];
					//read the rest of the input stream and store in byte array
					dis.readFully(objectInBytes,0,length);
					//make an object input stream that uses the object byte array
					ByteArrayInputStream bais = new ByteArrayInputStream(objectInBytes);
					ObjectInputStream ois = new ObjectInputStream(bais);
					//Read object          
					trans = (Transportable) ois.readObject();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trans;
		
	}

	/**
	 * Run the server
	 * */
	@Override
	public void run()
	{
		super.run();
	}

}
