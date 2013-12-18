package oose.group13.hffs.hibernate.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import oose.group13.hffs.data.Ids;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;

import org.junit.BeforeClass;
import org.junit.Test;
/**
 * This class populates the database with three users and a bunch of items the users are selling
 * @author aidanfowler
 */
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
		manager.dropTable("IDS");
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
		User u1=new User(1,"afowler","Aidan Fowler","afowler6@jhu.edu","12345","7088226804");
		User u2=new User(2,"lucas","Lucas Takatori","ltakato1@jhu.edu","12345","1111111111");
		User u3=new User(3,"shijan","Shijan Li","sli64@jhu.edu","12345","2222222222");
		
		Item i1 = new Item("Harry Potter 1","book 1","Harry Potter And The Sorcers Stone",ItemType.BOOKS,2,ItemState.AVAILABLE,1,extractBytes("hp1.jpg"));
		Item i2 = new Item("Harry Potter 2","book 2","Harry Potter And The Chamber Of Secrets",ItemType.BOOKS,2,ItemState.AVAILABLE,2,extractBytes("hp2.jpg"));
		Item i3 = new Item("Harry Potter 3","book 3","Harry Potter And The Prisoner Of Azkaban",ItemType.BOOKS,2,ItemState.AVAILABLE,3,extractBytes("hp3.jpg"));
		Item i4 = new Item("Harry Potter 4","book 4","Harry Potter And The Goblet Of Fire",ItemType.BOOKS,2,ItemState.AVAILABLE,4,extractBytes("hp4.jpg"));
		//Item i5 = new Item("Harry Potter 5","book 5","Harry Potter And The Order Of The Pheonix",ItemType.BOOKS,2,ItemState.AVAILABLE,5,extractBytes("hp5.jpg"));
		//Item i6 = new Item("Harry Potter 6","book 6","Harry Potter And The Half Blood Prince",ItemType.BOOKS,2,ItemState.AVAILABLE,6,extractBytes("hp6.jpg"));
		//Item i7 = new Item("Harry Potter 7","book 7","Harry Potter And The Deathly Hallows",ItemType.BOOKS,2,ItemState.AVAILABLE,7,extractBytes("hp7.jpg"));
		
		//Item i8 = new Item("Xbox 360","New Xbox 360","Most recent generation xbox 360",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,8,extractBytes("xbox.jpg"));
		Item i9 = new Item("Xbox One","New Xbox One","Most recent generation xbox one",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,9,extractBytes("xbox1.jpg"));
		Item i10 = new Item("Playstation 4","New PS4","Most recent generation ps4",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,10,extractBytes("ps4.jpg"));
		//Item i11 = new Item("Playstation 3","New PS3","Most recent generation ps3",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,11,extractBytes("ps3.jpg"));
		Item i12 = new Item("Wii u","New wii u","Most recent generation wii u",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,12,extractBytes("wiiu.jpg"));
		//Item i13 = new Item("Wii","New wii ","Most recent generation wii ",ItemType.ELECTRONICS,1,ItemState.AVAILABLE,13,extractBytes("wii.jpg"));
		
		Item i14 = new Item("Table","New table ","ikea table ",ItemType.FURNITURE,3,ItemState.AVAILABLE,14,extractBytes("table.jpg"));
		Item i15 = new Item("Chair","New chair ","ikea chair ",ItemType.FURNITURE,3,ItemState.AVAILABLE,15,extractBytes("chair.jpg"));
		//Item i16 = new Item("Couch","New couch ","ikea couch ",ItemType.FURNITURE,3,ItemState.AVAILABLE,16,extractBytes("couch.jpg"));
		
		//Item i17 = new Item("Coat","New coat ","nice red coat ",ItemType.CLOTHES,3,ItemState.AVAILABLE,17,extractBytes("jacket.jpg"));
		Item i18 = new Item("Jeans","New jeans ","new blue jeans ",ItemType.CLOTHES,3,ItemState.AVAILABLE,18,extractBytes("jeans.jpg"));
		//Item i19 = new Item("Shirt","New shirt ","new red shirt ",ItemType.CLOTHES,3,ItemState.AVAILABLE,19,extractBytes("shirt.jpg"));
		
		Item i20 = new Item("Monopoly","New Monopoly Game ","classic version of monopoly",ItemType.GAMES,3,ItemState.AVAILABLE,20,extractBytes("monopoly.jpg"));
		//Item i21 = new Item("Chutes And Ladders","New Game ","classic version of chutes and ladders",ItemType.GAMES,3,ItemState.AVAILABLE,21,extractBytes("monopoly.jpg"));
		
		Item i22 = new Item("Dog Picture","Framed Picture ","Framed Picture of Dogs",ItemType.MISC,3,ItemState.AVAILABLE,22,extractBytes("dogs.jpg"));
		//Item i23 = new Item("Cat Picture","Framed Picuture ","Framed Picture of Cats",ItemType.MISC,3,ItemState.AVAILABLE,23,extractBytes("cats.jpg"));
		
		
		manager.insert_record(u1);
		manager.insert_record(u2);
		manager.insert_record(u3);
		manager.insert_record(i1);
		manager.insert_record(i2);
		
		manager.insert_record(i3);
		manager.insert_record(i4);
		//manager.insert_record(i5);
		//manager.insert_record(i6);
		//manager.insert_record(i7);
		//manager.insert_record(i8);
		manager.insert_record(i9);
		
		manager.insert_record(i10);
		
		//manager.insert_record(i11);
		manager.insert_record(i12);
		//manager.insert_record(i13);
		manager.insert_record(i14);
		manager.insert_record(i15);
		
		//manager.insert_record(i16);
		//manager.insert_record(i17);
		manager.insert_record(i18);
		//manager.insert_record(i19);
		manager.insert_record(i20);
		//manager.insert_record(i21);
		manager.insert_record(i22);
		//manager.insert_record(i23);

		Ids id = new Ids(3,23,0);
		manager.updateEntry(manager.getIds(1), id);
		
		
	}
	
	public byte[] extractBytes(String name){
	    File file = new File("/Users/aidanfowler/Desktop/oose/"+name);
	
	    FileInputStream fis =null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //create FileInputStream which obtains input bytes from a file in a file system
	    //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
	
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    try {
	        for (int readNum; (readNum = fis.read(buf)) != -1;) {
	            //Writes to this byte array output stream
	            bos.write(buf, 0, readNum); 
	            System.out.println("read " + readNum + " bytes,");
	        }
	    } catch (IOException ex) {
	      
	    }
	    try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] bytes = bos.toByteArray();
	    return bytes;
	}
}
