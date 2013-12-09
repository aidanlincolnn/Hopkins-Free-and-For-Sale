package oose.group13.hffs.server;


import java.net.ServerSocket;
import java.net.Socket;

import oose.group13.hffs.data.User;
import oose.group13.hffs.tcp.TCPSendReceive;
import oose.group13.hffs.transport.SendMessage;
import oose.group13.hffs.transport.Transportable;

/**
 * The class is our tcp server it extends the Thread class so we can receive and send messages at the
 * same time
 */
public class TCPServer extends TCPSendReceive
{
	private int SERVERPORT = 5659;
	private ServerSocket serverSocket;
	private Socket client = null;
	private boolean running = false;
	private OnMessageReceived messageListener;
	
	/**
	 * Constructor of the class
	 * @param messageListener listens for the messages
	 */
	public TCPServer(OnMessageReceived messageListener)
	{
		this.messageListener = messageListener;
	}

	/**
	 * Run the server
	 * */
	@Override
	public void run()
	{
		super.run();
		running = true;
		
		try
		{
			// create a server socket. A server socket waits for requests to
			// come in over the network.
			serverSocket = new ServerSocket(SERVERPORT);
			while(running){
				try
				{
					System.out.println("Server: Waiting For Connection");
					// create client socket... the method accept() listens for a
					// connection to be made to this socket and accepts it.
					client = serverSocket.accept();
					System.out.println("Server: Connecting Done.");
					setDos(client);
					setDis(client);
					System.out.println("Server:Created Output and Input Streams.");
	            	//Transportable t = new Transportable();
	            	//t.setType(TransType.SENDMESSAGE);
	            	//t.setMessage("Message: Server Connected To Client");
	            	SendMessage t = new SendMessage(new User(),"Message: Server Connected To Clinet");
					sendMessage(t);
	            	
					// in this while we wait to receive messages from client (it's an infinite loop)
					while (getDis() != null)
					{
							Transportable trans = readMessage();
		                	if (trans != null && messageListener != null)
		                	{
		                		if (trans instanceof SendMessage){
		                			SendMessage s = (SendMessage) trans;
			                		if (s.getMessage() == "bye"){
			                			System.out.println("lost connection");
			                			setDis(null);
			                			System.out.println(getDis());
			                		}
		                		}
		                		// call the method messageReceived from ServerDatabaseConnector
		                		messageListener.messageReceived(trans);

		                	}			
					}
					
				}
				catch (Exception e)
				{
					System.out.println("Server: Error: "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					client.close();
					System.out.println("Server: Done.");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Server: Error");
			e.printStackTrace();
		}
		

	}

	/**
	 * Declare the interface. The method messageReceived(String message) will
	 * must be implemented in the ServerBoard
	 * class at on startServer button click
	*/
	public interface OnMessageReceived 
	{
		public void messageReceived(Transportable trans) throws Exception ;
	}

}
