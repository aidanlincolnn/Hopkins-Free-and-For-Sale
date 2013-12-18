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
 * Second half of test class for web services (java lang out of memory error) 
 * run hibernatemanagertest or serverdbtest first to clear database (cant clear from android)
 * need server to be running to test 
 * @author aidanfowler
*/
public class WebServicesTestPart2 extends AndroidTestCase{

	
	private static WebServices ws = new WebServices();
	private static boolean setUpDone = false;
	
	public WebServicesTestPart2(){
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
	 * Remove Items Test
	 */
	public void testRemoveItem(){
		System.out.println("testRemoveItem()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Item i1=new Item("Harry Potter123","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,ws.getNewItemId(u1),null);
		ws.addItem(u1, i1);
		List<Item> items = ws.getForSaleItems(u1, ItemType.BOOKS);
		int size = items.size();
		ws.removeItem(u1, i1);
		items = ws.getForSaleItems(u1, ItemType.BOOKS);
		assertEquals(size-1,items.size());
	}
	
	/**
	 * Add User Test
	 */
	public void testAddUser(){
		System.out.println("TestAddUser()");
		assertNull(ws.getUser("afowler62@jhu.edu", "password1"));
		ws.addUser(ws.getNewUserId(),"afowler2","Aidan Fowler2","afowler62@jhu.edu","password1","7088226804",true);
		User user = ws.getUser("afowler62@jhu.edu","password1");
		assertNotNull(user);
		assertEquals("afowler2", user.getmUserName());
	}
	
	/**
	 * Get New User Id Test
	 */
	public void testGetUserId(){
		System.out.println("testGetUserId");
		long id = ws.getNewUserId();
		ws.addUser(id,"afowler3","Aidan Fowler2","afowler63@jhu.edu","password2","7088226804",true);
		long id2 = ws.getNewUserId();
		assertEquals(id+1, id2);
	}
	
	/**
	 * Get New Item Id Test
	 */
	public void testGetItemId(){
		System.out.println("testGetItemId()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		long id = ws.getNewItemId(u1);
		Item i1=new Item("Harry Potter8","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,id,null);
		ws.addItem(u1, i1);
		long id2 = ws.getNewItemId(u1);
		assertEquals(id+1, id2);
	}
	
	/**
	 * Get New Offer Id Test
	 */
	public void testGetOfferId(){
		System.out.println("testGetOfferId()");
		User u1=new User(ws.getNewUserId(),"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		long id = ws.getNewOfferId(u1);
		Offer o1 = new Offer(ws.getNewOfferId(u1),100,1,"name1",1,2,OfferState.OPEN);
		ws.sendOffer(u1, o1);
		long id2 = ws.getNewOfferId(u1);
		assertEquals(id+1, id2);
	} 
}
