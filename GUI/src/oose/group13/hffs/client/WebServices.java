package oose.group13.hffs.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemType;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.User;
import oose.group13.hffs.tcp.TCPSendReceive;
import oose.group13.hffs.transport.AddItem;
import oose.group13.hffs.transport.AddUser;
import oose.group13.hffs.transport.GetItems;
import oose.group13.hffs.transport.GetNewItemId;
import oose.group13.hffs.transport.GetNewOfferId;
import oose.group13.hffs.transport.GetNewUserId;
import oose.group13.hffs.transport.GetOffers;
import oose.group13.hffs.transport.GetUser;
import oose.group13.hffs.transport.RemoveItem;
import oose.group13.hffs.transport.RemoveOffer;
import oose.group13.hffs.transport.SendOffer;
import oose.group13.hffs.transport.Transportable;
import android.util.Log;
/**
 * sends and gets messages from server
 * @author aidanfowler
 *
 */
public class WebServices extends TCPSendReceive{
    /**
	 * Specify the Server Ip Address here. Whereas our Socket Server is started.
	 * NOTE: we might need to have some blocks after sending and before receiving so that server has time to send back message?
	 * */
	//public static final String SERVERIP = "192.168.1.6"; // your computer IP address
    public static final String SERVERIP = "10.164.15.70";
    public static final int SERVERPORT = 5659;
	private static Socket socket = null;
	
	public void setupSocket(){
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			socket = new Socket(serverAddr, SERVERPORT);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("ERROR","IOEXCEPTION");
		}
        Log.e("TCP Android Client", "Client: Connecting...");
        setDos(socket);
        setDis(socket);
		
	}
	@Override
    public void run() {
    	super.run();
    }
    
    public static void close() throws IOException{
    	socket.close();
    }
 
	
	/**
	 * Use this method to send offers
	 * @param offer
	 * The offer to be sent to users
	 */
	public void sendOffer(User user, Offer offer){
	    Transportable sendOffer = new SendOffer(user,offer);
        sendMessage(sendOffer);
	}
	
	/**
	 * Use this method to get fill a users offer lists
	 * @param user
	 * Target user
	 * @return
	 * A user with their offer lists
	 */
	public User getOffers(User user){
		Log.e("DEBUG","getOffers call");
		Log.e("DEBUG","USER: "+ user.getUserId()+ " "+user.getmEmail()+ " "+user.getmPassword());
		
		GetOffers getOffers = new GetOffers(user);
		sendMessage(getOffers);
		Log.e("DEBUG","sent message");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.e("DEBUG","reading message ");
		getOffers = (GetOffers)readMessage();
		Log.e("DEBUG","got the offers, returning ");
		return getOffers.getUser();
	}
	
	/**
	 * Use this method to delete a specific offer
	 * @param offer
	 * Target offer
	 */
	public void deleteOffer(User user, Offer offer){
	    Transportable removeOffer = new RemoveOffer(user, offer);
	    sendMessage(removeOffer);
	}
	
	/**
	 * Use this method to acquire an user
	 * @param email
	 * email of target user
	 * @param password
	 * password of target user
	 * @return
	 * Return target user
	 */
	public User getUser(String email, String password){
		User thisuser = new User(-1, null, null, email, password, null);
		GetUser getUser = new GetUser(thisuser);
		sendMessage(getUser);
		getUser = null;
		while (getUser == null){
			getUser = (GetUser)readMessage();
		}
		return getUser.getUser();
	}
	
	/**
	 * Use this method to add an item
	 * @param user
	 * Owner of this item
	 * @param item
	 * Item to be added
	 */
	public void addItem(User user, Item item){
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(item);
		AddItem addItem = new AddItem(user, items);
		sendMessage(addItem);
	}
	
	/**
	 * Use this method to get For Sale items of a specific type
	 * @param type
	 * Item type wanted 
	 * @return
	 * List of For Sale items of a type
	 */
	public List<Item> getForSaleItems(User user, ItemType type){
		Item send = new Item(null, null, null, type, -1, null, -1,null);
		ArrayList<Item> sendItems = new ArrayList<Item>();
		sendItems.add(send);
		GetItems listItems = new GetItems(user, sendItems);
		sendMessage(listItems);
		listItems = null;
		while(listItems == null){
			listItems = (GetItems) readMessage();
		}
		return listItems.getItems();
	}
	
	/**
	 * Use this method to remove a specific item
	 * @param item
	 * Target item
	 */
	public void removeItem(User user, Item item){
		ArrayList<Item> it = new ArrayList<Item>();
		it.add(item);
		RemoveItem removeItem = new RemoveItem(user, it);
		sendMessage(removeItem);
	}
	
	/**
	 * Use this method to add an user with his/her info
	 * @param username
	 * username of the user
	 * @param email
	 * email address
	 * @param password
	 * password created
	 * @param name
	 * name of this user
	 * @param phoneNum
	 * phone number of this user
	 */
	public User addUser(long id, String username,String name, String email, String password, String phoneNum){
		User newuser = new User(id, username, name, email, password, phoneNum);
		AddUser addUser = new AddUser(newuser);
		sendMessage(addUser);
		return newuser;
	}
	
	/**
	 * use this to get a new id for a user
	 */
	public long getUserId(){
		GetNewUserId getUserId = new GetNewUserId();
		sendMessage(getUserId);
		getUserId = null;
		while(getUserId == null){
			getUserId = (GetNewUserId) readMessage();
		}
		return getUserId.getId();
	}
	
	/**
	 * use this to get a new id for an item
	 */
	public long getItemId(User user){
		GetNewItemId getItemId = new GetNewItemId(user);
		sendMessage(getItemId);
		getItemId = null;
		while(getItemId == null){
			getItemId = (GetNewItemId) readMessage();
		}
		return getItemId.getId();
	}
	
	/**
	 * use this to get a new id for a user
	 */
	public long getOfferId(User user){
		GetNewOfferId getOfferId = new GetNewOfferId(user);
		sendMessage(getOfferId);
		getOfferId = null;
		while (getOfferId == null){
			getOfferId = (GetNewOfferId) readMessage();
		}
		return getOfferId.getId();
	}
}
