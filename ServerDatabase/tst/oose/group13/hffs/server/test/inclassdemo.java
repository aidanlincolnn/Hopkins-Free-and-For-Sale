package oose.group13.hffs.server.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.ServerDatabaseConnector;
import oose.group13.hffs.tcp.TCPSendReceive;
import oose.group13.hffs.transport.GetOffers;
import oose.group13.hffs.transport.GetUser;
import oose.group13.hffs.transport.SendMessage;
import oose.group13.hffs.transport.SendOffer;

/**
 * Demo for the midterm presentation 
 * @author aidanfowler
 */
public class inclassdemo {
    /**
	 * Specify the Server Ip Address here. Whereas our Socket Server is started.
	 * */
	public static final String SERVERIP = "192.168.1.6"; // your computer IP address
    //public static final String SERVERIP = "10.164.25.182";
    public static final int SERVERPORT = 5659;
	private static Socket socket;
	private static HibernateManager manager = HibernateManager.getManager();
	private static TCPSendReceive tcp = new TCPSendReceive();
	/**
	 * set up method, connects to database, drops all values in tables so we can start fresh for test cases
	 */
	
	public static void main(String args[]){
		ServerDatabaseConnector sdbc = new ServerDatabaseConnector();
		sdbc.run();
		manager.dropTable("USERS");
		manager.dropTable("ITEMS");
		manager.dropTable("OFFERS");
		manager.dropTable("IDS");
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
    	
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        t = (SendMessage) tcp.readMessage();
        System.out.println(t.getMessage()+"\n\n");
        demoGo();
	}
	
	/**
	 * This test is for our project demonstration in class
	 */
	public static void demoGo(){
		//add three users to the database
		User u1 = new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","aidanpwd","7088226804");
		User u2 = new User(2,"ltakatori","Lucas Takatori","ltakato1@jhu.edu","lucaspwd","3459023234");
		User u3 = new User(3,"sli","Shijian Li","sli25@jhu.edu","shijianpwd","1040049898");
		manager.insert_record(u1);
		manager.insert_record(u2);
		manager.insert_record(u3);
		//add 4 items to the database all owned by user 1 (Aidan)
		Item i1=new Item("Harry Potter1","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		Item i2=new Item("Harry Potter2","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,2,null);
		Item i3=new Item("Harry Potter3","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,3,null);
		Item i4=new Item("Harry Potter3","ShortD","LongD",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,4,null);
		manager.insert_record(i1);
		manager.insert_record(i2);
		manager.insert_record(i3);
		manager.insert_record(i4);
		//add 4 offers to the database all lucas offering for harry potter 1 to aidan 
		Offer o1 = new Offer(1,100,2,"name2",2,1,OfferState.DECLINED);
		Offer o2 = new Offer(2,300,2,"name2",2,1,OfferState.DECLINED);
		Offer o3 = new Offer(3,400,2,"name2",2,1,OfferState.OPEN);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		
		GetUser logon = new GetUser(new User(-1,"","","afowler6@jhu.edu","aidanpwd",""));
		tcp.sendMessage(logon);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logon = (GetUser) tcp.readMessage();
		System.out.println("\n\nLogged on User afowler6@jhu.edu, username: "+logon.getUser().getmUserName()+"\n\n");
		
		o3.setmState(OfferState.ACCEPTED);
		SendOffer acceptOffer = new SendOffer(new User(),o3);
		tcp.sendMessage(acceptOffer);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\n\n Check that the offer has changed types in db:"+manager.getOffer(o3.getOfferId()).getmState() + "\n\n");

		GetOffers getOffers = new GetOffers(u2);
		tcp.sendMessage(getOffers);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getOffers = (GetOffers) tcp.readMessage();
		User getUserInfo = getOffers.getUser();
		//print offers to sell that item 
		System.out.println("\n");
		for (Offer o: getUserInfo.getBuyOffers()){
			if (o.getmState() != OfferState.ACCEPTED)
				System.out.println("Offer to sell item with id: "+ o.getmItem());
		}
		//print offers that aidan accepted
		for (Offer o: getUserInfo.getCompletedOffers()){
			System.out.println("Seller accepted offer for item with id: "+ o.getmItem());
		}
		
	}
}
