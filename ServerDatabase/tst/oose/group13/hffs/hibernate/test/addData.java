package oose.group13.hffs.hibernate.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.OfferState;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class addData {
	private static HibernateManager manager = HibernateManager.getManager();
	private static byte[] imageInByte;
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
		
		try {
			BufferedImage originalImage = ImageIO.read(new File("/Users/aidanfowler/Desktop/penguin.jpg"));
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
			System.out.println(imageInByte.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ADD FAKE DATA FOR USER 
	 */
	@Test
	public void addDataForTest(){
		User u1=new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","password1","7088226804");
		Offer o1 = new Offer(1,100,1,"offer1",1,2,OfferState.OPEN);
		Offer o2 = new Offer(2,200,1,"offer2",1,2,OfferState.OPEN);
		Offer o3 = new Offer(3,300,1,"offer3",1,2,OfferState.OPEN);
		Item i1 = new Item("book 1","s","l",ItemType.BOOKS,1,ItemState.AVAILABLE,1,null);
		Item i2 = new Item("book 2","s","l",ItemType.BOOKS,1,ItemState.AVAILABLE,2,null);
		Item i3 = new Item("book 3","s","l",ItemType.BOOKS,1,ItemState.AVAILABLE,3,null);
		manager.insert_record(u1);
		manager.insert_record(o1);
		manager.insert_record(o2);
		manager.insert_record(o3);
		manager.insert_record(i3);
		manager.insert_record(i2);
		manager.insert_record(i1);
		
	}
}
