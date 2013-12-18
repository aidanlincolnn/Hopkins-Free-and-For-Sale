package oose.group13.hffs.server.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Test;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.ServerDatabaseConnector;
import oose.group13.hffs.tcp.TCPSendReceive;
import oose.group13.hffs.transport.AddUser;
import oose.group13.hffs.transport.SendMessage;
/**
 * Demo for final presentation - how the server sends a transportable to database 
 * @author aidanfowler
 */
public class ServerDemo {
	/**
	 * NOTE MUST RUN SERVER BEFORE THIS TEST (oose.group13.hffs.server.RunServer)
	 */
	public static final String SERVERIP = "10.164.0.124"; // your computer IP address
	public static final int SERVERPORT = 5659;
	private static Socket socket;
	private static HibernateManager manager = HibernateManager.getManager();
	private static TCPSendReceive tcp = new TCPSendReceive();
	
	@BeforeClass
	public static void setUp(){
		ServerDatabaseConnector sdbc = new ServerDatabaseConnector();
		sdbc.run();
		manager.setUpHibernate();
		
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			//create a socket to make the connection with the server
			socket = new Socket(serverAddr, SERVERPORT);
			System.out.println("\n\nClient: Connecting Done");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        //create a new buffered output stream to write to and input stream to read from  
		tcp.setDos(socket);
		tcp.setDis(socket);
        System.out.println("Client Created Output and Input Streams.");
        SendMessage t = new SendMessage(new User(),"Message: Client Connected To Server");
        tcp.sendMessage(t);
        //wait to make sure server sends back message
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        t = (SendMessage) tcp.readMessage();
        System.out.println(t.getMessage()+"\n\n");
	}
	
	@Test
	public void testSendUser(){
		System.out.println("About to add user afowler2");
		User u = new User(100,"afowler2","Aidan Fowler2","afowler62@jhu.edu","password","7088226804");
		assertNull(manager.getUser(100));
		System.out.println("Confirmed User Null");
		AddUser addUser = new AddUser(u);
		addUser.signUp();
		tcp.sendMessage(addUser);
		System.out.println("Added New User to Database");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNotNull(manager.getUser(100));
		System.out.println("Confirmed New User Was Added");
		manager.delete_record(u);
		//disconnect from server
		tcp.sendMessage(new SendMessage(u, "bye"));
	}
}
