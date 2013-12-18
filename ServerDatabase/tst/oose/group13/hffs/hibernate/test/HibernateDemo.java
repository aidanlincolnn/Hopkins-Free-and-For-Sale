package oose.group13.hffs.hibernate.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
/**
 * Demo for final presentation - how hibernate works
 * @author aidanfowler
 */
public class HibernateDemo {
	private static HibernateManager manager = HibernateManager.getManager();
	
	
	@BeforeClass
	public static void setUp(){
		manager.setUpHibernate();
	}
	
	@Test
	public void testGetUser(){
		User u1=new User(100,"afowler2","Aidan Fowler2","afowler62@jhu.edu","password1","7088226804");
		System.out.println("About to add user afowler2 to database");
		assertNull(manager.getUser(100));
		System.out.println("Confirmed User Is Not Already In The Database");
		manager.insert_record(u1);
		System.out.println("Added User");
		assertNotNull(manager.getUser("afowler62@jhu.edu", "password1"));
		System.out.println("Confirmed User Was Added");
		manager.delete_record(u1);
		System.out.println("User Deleted");
	}
}
