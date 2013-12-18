package oose.group13.hffs.server.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.ServerDatabaseConnector;
import oose.group13.hffs.tcp.TCPSendReceive;
import oose.group13.hffs.transport.*;

/**
 * Tests for server database operations. 
 * */

public class ServerDBTest {
 
    /**
	 * Specify the Server Ip Address here. Whereas our Socket Server is started.
	 * */
	public static final String SERVERIP = "192.168.1.6"; // your computer IP address
    //public static final String SERVERIP = "10.164.11.212";
    public static final int SERVERPORT = 5659;
	private static Socket socket;
	private static HibernateManager manager = HibernateManager.getManager();
	private static TCPSendReceive tcp = new TCPSendReceive();
	/**
	 * set up method, connects to database, drops all values in tables so we can start fresh for test cases
	 */
	@BeforeClass
	public static void setUp(){
		ServerDatabaseConnector sdbc = new ServerDatabaseConnector();
		sdbc.run();
		manager.dropTable("USERS");
		manager.dropTable("ITEMS");
		manager.dropTable("OFFERS");
		manager.dropTable("IDS");
		manager.setUpHibernate();
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			System.out.println("ServerAddr: "+ serverAddr + " SERVERPORT: "+ SERVERPORT);
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
	}
    
	/**
	 * Testing that we can send offers and update offers
	 * @throws InterruptedException 
	 */
	@Test
	public void sendOffer() throws InterruptedException{
		System.out.println("\n sendOffer() \n");
		Offer o = new Offer(100,100,1,"name1",1,2,OfferState.OPEN);
		assertNull(manager.getOffer(100));
		SendOffer sendOffer = new SendOffer(new User(),o);
		tcp.sendMessage(sendOffer);
		Thread.sleep(500);
		assertNotNull(manager.getOffer(100));
		assertEquals("check the offer state",OfferState.OPEN,manager.getOffer(100).getmState());
		o.setmState(OfferState.COUNTERED);
		sendOffer = new SendOffer(new User(),o);
		tcp.sendMessage(sendOffer);
		Thread.sleep(500);
		assertEquals("check the offer state",OfferState.COUNTERED,manager.getOffer(100).getmState());
		o.setmState(OfferState.COUNTERED);
		manager.delete_record(o);
	}
	
	/**
	 * Testing that we can get populate a users offers list
	 */
	@Test 
	public void getOffers(){
		System.out.println("\n getOffers() \n");
		User u = new User(1,"afowler","Aidan Fowler","afowler65@jhu.edu","password","7088226804");
		manager.insert_record(u);
		GetOffers getOffers = new GetOffers(u);
		
		Offer o1 = new Offer(100,101,1,"name1",1,2,OfferState.OPEN);
		Offer o2 = new Offer(101,102,2,"name1",1,3,OfferState.ACCEPTED);
		Offer o3 = new Offer(102,103,3,"name1",1,4,OfferState.OPEN);
		Offer o4 = new Offer(103,104,4,"name1",2,1,OfferState.ACCEPTED);
		Offer o5 = new Offer(104,105,5,"name1",2,1,OfferState.OPEN);
		Offer o6 = new Offer(105,106,6,"name1",2,1,OfferState.OPEN);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(o4);
		manager.insert_record(o5);
		manager.insert_record(o6);
		tcp.sendMessage(getOffers);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getOffers = (GetOffers) tcp.readMessage();
		u = getOffers.getUser();
		assertEquals("Check Sent",2,u.getBuyOffers().size());
		assertEquals("Check received",2,u.getSellOffers().size());
		assertEquals("Check completed",2,u.getCompletedOffers().size());
		manager.delete_record(u);
		manager.delete_record(o1);
		manager.delete_record(o2);
		manager.delete_record(o3);
		manager.delete_record(o4);
		manager.delete_record(o5);
		manager.delete_record(o6);
	
	}
	
	/**
	 * Test if removing an offer works
	 */
	@Test
	public void removeOffer(){
		System.out.println("\n removeOffer() \n");
		Offer o1 = new Offer(100,100,1,"name1",1,2,OfferState.OPEN);
		assertNull(manager.getOffer(100));
		manager.insert_record(o1);
		assertNotNull(manager.getOffer(100));
		RemoveOffer t = new RemoveOffer(new User(), o1);
		tcp.sendMessage(t);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNull(manager.getOffer(100));
		
	}
	
	/**
	 * Test if logging in works
	 */
	@Test
	public void getUser(){
		System.out.println("\n getUser() \n");
		assertNull(manager.getUser(1));
		User u = new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","password","7088226804");
		manager.insert_record(u);
		User u2 = new User(-1,"","","afowler6@jhu.edu","password","");
		GetUser t = new GetUser(u2);
		tcp.sendMessage(t);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t = (GetUser) tcp.readMessage();
		
		User u3 = t.getUser();
		assertEquals("check the returned user",1,u3.getUserId());
		manager.delete_record(u);
		
	}
	
	/**
	 * Test if adding an item works and updating an item works
	 */
	@Test
	public void addItem(){
		System.out.println("\n addItem() \n");
		Item i = new Item("item","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,100,null);
		assertNull(manager.getItem(1));
		List<Item> items = new ArrayList<Item>(1);
		items.add(i);
		AddItem addItemT = new AddItem(new User(),items);
		tcp.sendMessage(addItemT);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNotNull(manager.getItem(100));
		assertEquals("check the state",ItemState.AVAILABLE,manager.getItem(100).getmAvailability());
		Item i2 = new Item("item","short","long",ItemType.BOOKS,1,ItemState.SOLD,100,null);
		items.clear();
		items.add(i2);
		addItemT = new AddItem(new User(),items);
		tcp.sendMessage(addItemT);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("check the state",ItemState.SOLD,manager.getItem(100).getmAvailability());
		manager.delete_record(i2);	
	}
	
	/**
	 * Test if removing an item works
	 */
	@Test
	public void removeItem(){
		System.out.println("\n removeItem() \n");
		Item i = new Item("item","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		assertNull(manager.getItem(1));
		manager.insert_record(i);
		assertNotNull(manager.getItem(1));
		List<Item> items = new ArrayList<Item>(1);
		items.add(i);
		RemoveItem removeItem  = new RemoveItem(new User(),items);
		tcp.sendMessage(removeItem);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNull(manager.getItem(1));
		
	}
	
	/**
	 * Test if Getting items works
	 */
	@Test
	public void getItems(){
		System.out.println("\n getItems() \n");
		Item i1 = new Item("item1","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		Item i2 = new Item("item2","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,2,null);
		Item i3 = new Item("item3","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,3,null);
		Item i4 = new Item("item4","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,4,null);
		Item i5 = new Item("item5","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,5,null);
		List<Item> items = new ArrayList<Item>(1);
		items.add(i1);
		GetItems getItems = new GetItems(new User(),items );
		tcp.sendMessage(getItems);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getItems = (GetItems) tcp.readMessage();
		assertEquals("size is zero",0,getItems.getItems().size());
		manager.insert_record(i1);
		manager.insert_record(i2);
		manager.insert_record(i3);
		manager.insert_record(i4);
		manager.insert_record(i5);
		getItems = new GetItems(new User(),items );
		tcp.sendMessage(getItems);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getItems = (GetItems) tcp.readMessage();
		assertEquals("check size of list",5,getItems.getItems().size());
		manager.delete_record(i1);
		manager.delete_record(i2);
		manager.delete_record(i3);
		manager.delete_record(i4);
		manager.delete_record(i5);
	}
	
	/**
	 * Test if adding and updating a user works
	 */
	@Test 
	public void addUser(){
		System.out.println("\n addUser() \n");
		User u = new User(1,"afowler","Aidan Fowler","afowler63@jhu.edu","password","7088226804");
		assertNull(manager.getUser(1));
		AddUser addUser = new AddUser(u);
		addUser.signUp();
		tcp.sendMessage(addUser);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		addUser = (AddUser) tcp.readMessage();
		assertNotNull(manager.getUser(1));
		u.setmPassword("newPassword");
		addUser = new AddUser(u);
		tcp.sendMessage(addUser);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		addUser = (AddUser) tcp.readMessage();
		assertEquals("new pass","newPassword",manager.getUser(1).getmPassword());
		manager.delete_record(u);
	}
	
	/**
	 * Test if the ids get updated correctly
	 */
	@Test
	public void updateIds(){
		System.out.println("\n updateids() \n");
		long val =1 ;
		assertNotNull(manager.getIds(val));
		long uid = manager.getIds(val).getUserId();
		long iid = manager.getIds(val).getItemId();
		long oid = manager.getIds(val).getOfferId();
		User u = new User(uid,"afowler","Aidan Fowler","afowler61@jhu.edu","password","7088226804");
		Item i1 = new Item("item1","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,iid,null);
		Offer o1 = new Offer(oid,100,1,"name1",1,2,OfferState.OPEN);

		AddUser t = new AddUser(u);
		t.signUp();
		tcp.sendMessage(t);
				try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t = (AddUser) tcp.readMessage();
		
		List<Item> items = new ArrayList<Item>(1);
		items.add(i1);
		AddItem t1 = new AddItem(new User(),items);
		tcp.sendMessage(t1);
		SendOffer t2 = new SendOffer(new User(),o1);
		tcp.sendMessage(t2);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("uid +1",uid+1,manager.getIds(val).getUserId());
		assertEquals("iid +1",iid+1,manager.getIds(val).getItemId());
		assertEquals("oid +1",oid+1,manager.getIds(val).getOfferId());
		manager.delete_record(u);
		manager.delete_record(i1);
		manager.delete_record(o1);
	}
	
	/**
	 * Test getting the newest ids to use
	 */
	@Test
	public void getIds(){
		System.out.println("\n getIds() \n");
		long val = 1;
		assertNotNull(manager.getIds(val));
		long uid = manager.getIds(val).getUserId();
		long iid = manager.getIds(val).getItemId();
		long oid = manager.getIds(val).getOfferId();
		User u = new User(uid,"afowler","Aidan Fowler","afowler62@jhu.edu","password","7088226804");
		Item i1 = new Item("item1","short","long",ItemType.BOOKS,1,ItemState.AVAILABLE,iid,null);
		Offer o1 = new Offer(oid,100,1,"name1",1,2,OfferState.OPEN);
		AddUser t = new AddUser(u);
		t.signUp();
		tcp.sendMessage(t);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t = (AddUser) tcp.readMessage();
		List<Item> items = new ArrayList<Item>(1);
		items.add(i1);
		AddItem t1 = new AddItem(new User(),items);
		tcp.sendMessage(t1);
		SendOffer t2 = new SendOffer(new User(),o1);
		tcp.sendMessage(t2);
		GetNewUserId t3 = new GetNewUserId();
		tcp.sendMessage(t3);
			
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t3 = (GetNewUserId) tcp.readMessage();
		assertEquals("uid",uid+1,t3.getId());
		GetNewItemId t4 = new GetNewItemId(new User());
		
		tcp.sendMessage(t4);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t4 = (GetNewItemId) tcp.readMessage();
		assertEquals("iid",iid+1,t4.getId());
		GetNewOfferId t5 = new GetNewOfferId(new User());
		tcp.sendMessage(t5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t5 = (GetNewOfferId) tcp.readMessage();
		assertEquals("oid",oid+1,t5.getId());
		manager.delete_record(u);
		manager.delete_record(i1);
		manager.delete_record(o1);
	}
}
