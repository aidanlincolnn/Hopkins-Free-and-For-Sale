package oose.group13.hffs.hibernate.test;

import static org.junit.Assert.*;


import java.util.List;


import oose.group13.hffs.data.*;
import oose.group13.hffs.hibernate.HibernateManager;

import org.junit.BeforeClass;
import org.junit.Test;
/** Junit tests for everything related to inserting, updating, retrieving from and deleting entrys from databse.*/
public class HibernateManagerTest {
	
	private static HibernateManager manager = HibernateManager.getManager();
	/**
	 * set up method, connects to database, drops all values in tables so we can start fresh for test cases
	 */
	@BeforeClass
	public static void setUp(){
		manager.setUpHibernate();
		manager.dropTable("USERS");
		manager.dropTable("ITEMS");
		manager.dropTable("OFFERS");
		manager.setUpHibernate();	
		
	}
	
	/**
	 * Testing that adding and deleting a user works
	 */
	@Test
	public void addUser() {
		User u1=new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		manager.insert_record(u1);
		assertNotNull(manager.getUser(1));
		assertEquals("name is Aidan Fowler","Aidan Fowler",manager.getUser(1).getmName());
		assertEquals("email is afowler6@jhu.edu","afowler6@jhu.edu",manager.getUser(1).getmEmail());
		assertEquals("password is password1","password1",manager.getUser(1).getmPassword());
		assertEquals("phone is 7088226804","7088226804",manager.getUser(1).getmPhoneNumber());
		assertEquals("username is afowler","afowler",manager.getUser(1).getmUserName());
		assertEquals("size of table",1,manager.getAllUser().size());
		manager.delete_record(u1);
		assertEquals("size of table",0,manager.getAllUser().size());
		manager.insert_record(u1);
		assertNotNull(manager.getUser("afowler6@jhu.edu","password1"));
		assertEquals("name is Aidan Fowler","Aidan Fowler",manager.getUser("afowler6@jhu.edu","password1").getmName());
		assertEquals("email is afowler6@jhu.edu","afowler6@jhu.edu",manager.getUser("afowler6@jhu.edu","password1").getmEmail());
		assertEquals("password is password1","password1",manager.getUser("afowler6@jhu.edu","password1").getmPassword());
		assertEquals("phone is 7088226804","7088226804",manager.getUser("afowler6@jhu.edu","password1").getmPhoneNumber());
		assertEquals("username is afowler","afowler",manager.getUser("afowler6@jhu.edu","password1").getmUserName());
		manager.delete_record(u1);
	}
	
	/**
	 * Testing that adding and deleting an item works
	 */
	@Test 
	public void addItem(){
		Item i1=new Item("Harry Potter","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		manager.insert_record(i1);
		assertNotNull(manager.getItem(1));
		assertEquals("Name ","Harry Potter",manager.getItem(1).getmTitle());
		assertEquals("Short D","ShortD",manager.getItem(1).getmDescriptionS());
		assertEquals("Long D","LongD",manager.getItem(1).getmDescriptionL());
		assertEquals("Type",ItemType.BOOKS,manager.getItem(1).getmType());
		assertEquals("State",ItemState.AVAILABLE,manager.getItem(1).getmAvailability());
		assertEquals("User",1,manager.getItem(1).getmOwnerId());
		assertNotNull(manager.getItem(1).getmDatePosted());
		assertEquals("size of table",1,manager.getAllItems().size());
		manager.delete_record(i1);
		assertEquals("size of table",0,manager.getAllItems().size());
	}
	
	/**
	 * Testing that adding and deleting an offer works
	 */
	@Test
	public void addOffer(){
		Offer o1 = new Offer(1,100,1,"name1",1,2,OfferState.OPEN);
		manager.insert_record(o1);
		assertNotNull(manager.getOffer(1));
		assertEquals("Price",100,manager.getOffer(1).getmPrice());
		assertEquals("Item",1,manager.getOffer(1).getmItem());
		assertEquals("Sender",1,manager.getOffer(1).getmSender());
		assertEquals("Receiver",2,manager.getOffer(1).getmReceiver());
		assertEquals("State",OfferState.OPEN,manager.getOffer(1).getmState());
		assertEquals("size of table",1,manager.getAllOffers().size());
		manager.delete_record(o1);
		assertEquals("size of table",0,manager.getAllOffers().size());
	}
	
	/**
	 * Testing that we can update objects in the db
	 */
	@Test
	public void update(){
		User u1=new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		User u2 = u1;
		u2.setmPassword("password2");
		manager.insert_record(u1);
		assertNotNull(manager.getUser(1));
		manager.updateEntry(u1, u2);
		assertNotNull(manager.getUser(1));
		assertEquals("new password","password2",manager.getUser(1).getmPassword());
		manager.delete_record(u1);
		}
	
	/**
	 * Testing that retrieving items by type works
	 */
	@Test
	public void getForSale(){
		Item i2=new Item("Harry Potter2","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		Item i3=new Item("Harry Potter3","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,2,null);
		Item i4=new Item("Harry Potter3","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,3,null);
		manager.insert_record(i2);
		manager.insert_record(i3);
		manager.insert_record(i4);
		List<Item> books = manager.getForSaleItems(ItemType.BOOKS);
		assertEquals("number of books is 3",3, books.size());
		for (int i =0;i<3;i++){
			assertEquals("book id",i+1,books.get(i).getitemId());
			manager.delete_record(books.get(i));
		}
		manager.delete_record(i4);
	}
	
	/**
	 * Testing that getting offers sent by a user works
	 */
	@Test
	public void getSentOffers(){
		Offer o1 = new Offer(1,100,1,"name1",1,2,OfferState.OPEN);
		Offer o2 = new Offer(2,200,1,"name1",1,3,OfferState.OPEN);
		Offer o3 = new Offer(3,300,1,"name1",1,4,OfferState.OPEN);
		Offer o4 = new Offer(4,400,1,"name1",1,5,OfferState.OPEN);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(o4);
		List<Offer> sent = manager.getSentOffers(1);
		assertEquals("number of sent",4,sent.size());
		for (int i =0;i<4;i++){
			assertEquals("offerid",i+1,sent.get(i).getOfferId());
			manager.delete_record(sent.get(i));
		}
	}
	
	/**
	 * Testing that getting offers received for a user works
	 */
	@Test
	public void getReceivedOffers(){
		Offer o1 = new Offer(1,100,1,"name1",2,1,OfferState.OPEN);
		Offer o2 = new Offer(2,200,1,"name1",3,1,OfferState.OPEN);
		Offer o3 = new Offer(3,300,1,"name1",4,1,OfferState.OPEN);
		Offer o4 = new Offer(4,400,1,"name1",5,1,OfferState.OPEN);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(o4);
		List<Offer> rec = manager.getReceivedOffers(1);
		assertEquals("number of sent",4,rec.size());
		for (int i =0;i<4;i++){
			assertEquals("offerid",i+1,rec.get(i).getOfferId());
			manager.delete_record(rec.get(i));
		}
	}
	
	/**
	 * Testing that we can get completed offers
	 */
	@Test
	public void getConpletedffers(){
		Offer o1 = new Offer(1,100,1,"name1",2,1,OfferState.OPEN);
		Offer o2 = new Offer(2,200,1,"name1",3,1,OfferState.ACCEPTED);
		Offer o3 = new Offer(3,300,1,"name1",4,1,OfferState.OPEN);
		Offer o4 = new Offer(4,400,1,"name1",5,1,OfferState.ACCEPTED);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(o4);
		List<Offer> rec = manager.getCompletedOffers(1);
		assertEquals("number of sent",2,rec.size());
		manager.delete_record(o1);
		manager.delete_record(o2);
		manager.delete_record(o3);
		manager.delete_record(o4);
		
	}
	
	/**
	 * Testing that we can delete offers corresponding to an item (we dont delete accepted offers)
	 */
	@Test
	public void deleteOffers(){
		System.out.println("delete test");
		Offer o1 = new Offer(1,100,1,"name1",2,1,OfferState.ACCEPTED);
		Offer o2 = new Offer(2,200,1,"name1",3,1,OfferState.OPEN);
		Offer o3 = new Offer(3,300,1,"name1",4,1,OfferState.DECLINED);
		Offer o4 = new Offer(4,400,2,"name2",5,1,OfferState.OPEN);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(o4);
		assertEquals("4 offers",4,manager.getAllOffers().size());
		System.out.println("offersize is really"+manager.getAllOffers().size());
		manager.deleteOffers(1);
		assertEquals("now 1 offer",2,manager.getAllOffers().size());
		for (Offer o : manager.getAllOffers()){
			manager.delete_record(o);
		}
		
	}
	
	/**
	 * Testing that we can get a list of items for a user
	 */
	@Test
	public void getItems(){
		Item i1=new Item("Harry Potter1","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		Item i2=new Item("Harry Potter2","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,2,null);
		Item i3=new Item("Harry Potter3","ShortD","LongD",ItemType.BOOKS,1,ItemState.AVAILABLE,3,null);
		Item i4=new Item("Harry Potter3","ShortD","LongD",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,4,null);
		manager.insert_record(i1);
		manager.insert_record(i2);
		manager.insert_record(i3);
		manager.insert_record(i4);
		List<Item> items = manager.getItemsForUser(1);
		assertEquals("number of items is 4",4, items.size());
		for (Item i : items){
			manager.delete_record(i);
		}
	}
	
	/**
	 * Testing if updating the ids works
	 */
	@Test
	public void updateIds(){
		long val4 = 1;
		assertNotNull(manager.getIds(val4));
		Ids old = manager.getIds(val4);
		Ids newids = new Ids();
		long val1 = old.getItemId()+1;
		long val2 = old.getUserId()+1;
		long val3 = old.getOfferId()+1;
		System.out.println(val1+" " +val2+" "+val3);
		newids.setItemId(val1);
		newids.setUserId(val2);
		newids.setOfferId(val3);
		newids.setTableId(val1);
		manager.updateEntry(old, newids);
		Ids rec= manager.getIds(val4);
		assertEquals("user+1",old.getUserId()+1,rec.getUserId());
		assertEquals("item+1",old.getItemId()+1,rec.getItemId());
		assertEquals("offer+1",old.getOfferId()+1,rec.getOfferId());
	}
	


}
