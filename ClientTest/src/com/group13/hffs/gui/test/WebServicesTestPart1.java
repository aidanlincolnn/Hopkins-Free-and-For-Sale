package com.group13.hffs.gui.test;

import java.util.List;

import oose.group13.hffs.client.WebServices;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import oose.group13.hffs.data.User;
import oose.group13.hffs.transport.SendMessage;
import android.test.AndroidTestCase;

/**
 * Tests for webservices, note - run hibernatemanagertest or serverdbtest first to clear database (cant clear from android)
 * need server to be running to test 
 * @author aidanfowler
 */
public class WebServicesTestPart1 extends AndroidTestCase{

	private static WebServices ws = new WebServices();
	private static boolean setUpDone = false;
	
	public WebServicesTestPart1(){
		super();
	}
	
	public void setUp(){
		if (!setUpDone){
			ws.setupSocket();
			System.out.println("Client Created Output and Input Streams.");
		    SendMessage t = new SendMessage(new User(),"Message: Client Connected To Server");
		    ws.sendMessage(t);
		    t = null;
		    while (t == null){
		    	t = (SendMessage) ws.readMessage();
		    }
		    System.out.println("t null: "+t);
		    System.out.println(t.getMessage()+"\n\n");
		    setUpDone = true;
		}
	}
	
	/**
	 * Send Offer Test
	 */
	public void testSendOffer(){
		System.out.println("testSendOffers()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Offer o1 = new Offer(ws.getNewOfferId(u1),100,1,"name1",ws.getNewUserId(),2,OfferState.ACCEPTED);
		User user = ws.getOffers(u1);
		int number = user.getCompletedOffers().size();
		ws.sendOffer(u1,o1);
		User user2 = ws.getOffers(u1);
		assertEquals(number+1,user2.getCompletedOffers().size());
	}
	
	/**
	 * Get Offers Test
	 */
	public void testGetOffers(){
		System.out.println("testGetOffers()");
	    User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Offer o1 = new Offer(ws.getNewOfferId(u1),100,1,"name1",ws.getNewUserId(),2,OfferState.OPEN);
		User user = ws.getOffers(u1);
		int number = user.getBuyOffers().size();
		ws.sendOffer(u1,o1);
		user = ws.getOffers(u1);
		assertEquals(number+1, user.getBuyOffers().size());
	}
	
	/**
	 * Delete Offers Test
	 */
	public void testDeleteOffer(){
		System.out.println("testDeleteoffers()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Offer o1 = new Offer(ws.getNewOfferId(u1),100,1,"name1",ws.getNewUserId(),2,OfferState.OPEN);
		ws.sendOffer(u1,o1);
		User user = ws.getOffers(u1);
		int number = user.getBuyOffers().size();
		ws.deleteOffer(u1, o1);
		user = ws.getOffers(u1);
		assertEquals(number-1,user.getBuyOffers().size());
	}
	
	/**
	 * Sign On Test
	 */
	public void testGetUser(){
		System.out.println("testGetuser()");
		ws.addUser(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804",true);
		User user = ws.getUser("afowler6@jhu.edu","password1");
		assertNotNull(user);
		assertEquals("afowler", user.getmUserName());
	}
	
	/**
	 * Add Item Test
	 */
	public void testAddItem(){
		System.out.println("testAddItem()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Item i1=new Item("Harry Potter","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,ws.getNewItemId(u1),null);
		List<Item> items = ws.getForSaleItems(u1, ItemType.BOOKS);
		int number = items.size();
		ws.addItem(u1, i1);
		items = ws.getForSaleItems(u1, ItemType.BOOKS);
		assertEquals(number+1,items.size());
	}
	
	/**
	 * Get Items Test
	 */
	 public void testGetForSaleItems(){
		System.out.println("testGetForSaleItems");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		List<Item> items = ws.getForSaleItems(u1, ItemType.BOOKS);
		int number = items.size();
		Item i1=new Item("Harry Potter1","ShortD","LongD",ItemType.BOOKS,ws.getNewUserId(),ItemState.AVAILABLE,ws.getNewItemId(u1),null);
		ws.addItem(u1, i1);
		Item i2=new Item("Harry Potter2","ShortD","LongD",ItemType.BOOKS,ws.getNewUserId(),ItemState.AVAILABLE,ws.getNewItemId(u1),null);
		ws.addItem(u1, i2);
		Item i3=new Item("Harry Potter3","ShortD","LongD",ItemType.BOOKS,ws.getNewUserId(),ItemState.AVAILABLE,ws.getNewItemId(u1),null);
		ws.addItem(u1, i3);
		items = ws.getForSaleItems(u1, ItemType.BOOKS);
		assertEquals(number+3,items.size());
	}
	
	
}
