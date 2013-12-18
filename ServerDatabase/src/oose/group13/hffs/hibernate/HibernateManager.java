package oose.group13.hffs.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import oose.group13.hffs.data.*;

/**
 * This Class is a manager for our hibernate database, it creates the database and preforms queries on it
 * @author aidanfowler
 */

public class HibernateManager {
	
	// One hibernate manager for database  
	private static final HibernateManager MANAGER = new HibernateManager();
  
	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory;
	
	/**
	 * Set up method, reads the configuration connects and creates tables
	 */
	public void setUpHibernate(){
		Configuration cfg=new Configuration().configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(
	    cfg.getProperties()).buildServiceRegistry();
	    sessionFactory = cfg.buildSessionFactory(serviceRegistry);
	    
	    Session session=null;
		Transaction tx=null;
		Ids ids = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			long val = 1;
			ids=(Ids)session.get(Ids.class, val);
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		if (ids == null){
			long val = 0;
			ids = new Ids(val,val,val);
			MANAGER.insert_record(ids);
		}
		
	}
	
	/**
	 * get the hibernate manager
	 * @return the hibernate manager
	 */
	public static HibernateManager getManager(){
		return MANAGER;
	}
	
	/**
	 * drop a table from the db ( for unit tests ) 
	 * @param name the name of the table to drop
	 */
	public void dropTable(String name){
		Session session=null;
		Transaction tx=null;
		try
		{
			session =sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			session.createSQLQuery("DROP TABLE "+name).executeUpdate();
			tx.commit();
			session.flush();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	/**
	 * insert a record into the db
	 * @param emp the record
	 */
	public void insert_record(Object emp)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session =sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			session.save(emp);
			tx.commit();
			session.flush();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	/**
	 * delete a record from the db
	 * @param emp the record
	 */
	public void delete_record(Object emp)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session =sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			session.delete(emp);
			tx.commit();
			session.flush();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	/** Update an entry in the db
	 * @param oldE the entry to update
	 * @param newE the the new entry
	 */
	public void updateEntry(Object oldE, Object newE){
		delete_record(oldE);
		insert_record(newE);
	}
	
	/**
	 * get a user based on their id
	 * @param id the id of the user
	 * @return the user object
	 */
	public User getUser(long id)
	{
		Session session=null;
		Transaction tx=null;
		User user = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			user=(User)session.get(User.class, id);
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return user;
	}
	
	/**
	 * get a user from their email and password 
	 * @param email the email address of the user
	 * @param password the password of the user
	 * @return the User object
	 */
	public User getUser(String email, String password)
	{
		Session session=null;
		Transaction tx=null;
		User user = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			user = (User) session.createCriteria(User.class)
					.add(Restrictions.and(Restrictions.eq("mEmail",email),Restrictions.eq("mPassword",password)))
					.uniqueResult();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return user;
	}
	
	/**
	 * get a user from their email
	 * @param userName the email 
	 * @return the User object
	 */
	public User getUser(String email)
	{
		Session session=null;
		Transaction tx=null;
		User user = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			user = (User) session.createCriteria(User.class)
					.add(Restrictions.eq("mEmail",email))
					.uniqueResult();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return user;
	}
	
	/**
	 * Get All Users
	 * @return the list of users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUser()
	{
		Session session=null;
		Transaction tx=null;
		List<User> users = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			users=(List<User>) session.createCriteria(User.class).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return users;
	}
	

	/**
	 * get an item object from the db
	 * @param id the item id
	 * @return the item object
	 */
	public Item getItem(long id)
	{
		Session session=null;
		Transaction tx=null;
		Item item = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			item=(Item)session.get(Item.class, id);
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return item;
	}
	
	/**
	 * get all items of a specific type from the db
	 * @param type the item type
	 * @return the list of items
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getForSaleItems(ItemType type)
	{
		Session session=null;
		Transaction tx=null;
		List<Item> items = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			items = (List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("mType",type)).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return items;
	}
	
	
	/**
	 * get all items of items for a user
	 * @param id the id of the seller
	 * @return the list of items
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getItemsForUser(long id)
	{
		Session session=null;
		Transaction tx=null;
		List<Item> items = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			items = (List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("mOwnerId",id)).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return items;
	}
	
	/**
	 * Get All Items
	 * @return the list of items
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getAllItems()
	{
		Session session=null;
		Transaction tx=null;
		List<Item> items = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			items=(List<Item>) session.createCriteria(Item.class).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return items;
	}
	
	/**
	 * get an offer from the db
	 * @param id the offer id
	 * @return the offer object
	 */
	public Offer getOffer(long id)
	{
		Session session=null;
		Transaction tx=null;
		Offer offer = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			offer=(Offer)session.get(Offer.class, id);
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return offer;
	}
	
	/**
	 * get all sent offers for a user
	 * @param id the id of the user
	 * @return the list of offers
	 */
	@SuppressWarnings("unchecked")
	public List<Offer> getSentOffers(long id)
	{
		Session session=null;
		Transaction tx=null;
		List<Offer> offers = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			offers = (List<Offer>) session.createCriteria(Offer.class).add(Restrictions.eq("mSender",id)).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return offers;
	}
	
	/**
	 * get all received offers for a user
	 * @param id the id of the user
	 * @return the list of offers
	 */
	@SuppressWarnings("unchecked")
	public List<Offer> getReceivedOffers(long id)
	{
		Session session=null;
		Transaction tx=null;
		List<Offer> offers = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			offers = (List<Offer>) session.createCriteria(Offer.class).add(Restrictions.eq("mReceiver",id)).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return offers;
	}
	
	/**
	 * get all completed offers for a user
	 * @param id the id of the user
	 * @return the list of offers
	 */
	public List<Offer> getCompletedOffers(long id)
	{
		List<Offer> list = getSentOffers(id);
		List<Offer> list2 = getReceivedOffers(id);
		List<Offer> completed = new ArrayList<Offer>();
		for (Offer o: list){
			if (o.getmState() == OfferState.ACCEPTED){
				completed.add(o);
			}
		}
		for (Offer o: list2){
			if (o.getmState() == OfferState.ACCEPTED){
				completed.add(o);
			}
		}
		return completed;
		
	}
	
	/**
	 * Get All Offers
	 * @return the list of offers
	 */
	@SuppressWarnings("unchecked")
	public List<Offer> getAllOffers()
	{
		Session session=null;
		Transaction tx=null;
		List<Offer> offers = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			offers=(List<Offer>) session.createCriteria(Offer.class).list();
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return offers;
	}
	
	/**
	 * Delete all offers for an item as long as the state is not accepted
	 * @param id the id of the item the offers include
	 */
	public void deleteOffers(long id){
		Session session=null;
		Transaction tx=null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Offer> offers = (List<Offer>) session.createCriteria(Offer.class)
				.add(Restrictions.eq("mItem",id))
				.add(Restrictions.ne("mState", OfferState.ACCEPTED)).list();
			for (Offer o : offers){
				session.delete(o);
			}
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}

	}
	
	/**
	 * get the id table
	 * @param id the item id
	 * @return the item object
	 */
	public Ids getIds(long val)
	{
		Session session=null;
		Transaction tx=null;
		Ids ids = null;
		try
		{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			ids=(Ids)session.get(Ids.class, val);
			tx.commit();
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			he.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		return ids;
	}
}