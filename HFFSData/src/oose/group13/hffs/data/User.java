package oose.group13.hffs.data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**User Object
 * 
 * @author Lucas, Aidan, Shijian
 * 
 * User class that holds all the currently logged on users offers and items. 
 *
 */
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The id of the user */
	private long userId;
	/** The username of the user*/
	private String mUserName;
    /** The name of the user */
    private String mName;
    /** The email address of the user. Must be .jhu */
    private String mEmail;
    /** The password for the user */
    private String mPassword;
    /** The phone number of the user */
    private String mPhoneNumber;
    /** The offers that the user has to buy an item */
    private List<Offer> mBuyOffers = new ArrayList<Offer>();
    /** The current offers where the user is the seller of the item */
    private List<Offer> mSellOffers = new ArrayList<Offer>();
    /** The current offers that have been completed that the user is a part of */
    private List<Offer> mCompletedOffers = new ArrayList<Offer>();
    /** The items that the user currently has listed to sell */
    private List<Item> mItems;
    
    /**
     * Constructor for a user
     * @param id the id of the user
     * @param userName the username of the user
     * @param name Name of the user
     * @param email Email address of the user must be .jhu and will be used as login.
     * @param password Users password
     * @param phoneNum The phone number of the user
     */
    public User(long id, String userName, String name, String email, String password, String phoneNum)
    {	
    	userId = id;
    	mUserName = userName;
        mName = name;
        mEmail = email;
        mPassword = password;
        mPhoneNumber = phoneNum;
        mBuyOffers = new ArrayList<Offer>();
        mSellOffers = new ArrayList<Offer>();
        mCompletedOffers = new ArrayList<Offer>();
        mItems = new ArrayList<Item>();
    }
    
    /**
     * blank constructor for queries
     */
    public User(){
    	
    }
    
	/**
	 * set the user id
	 * @param id the user id
	 */
	public void setUserId(long id) {
		this.userId = id;
	}
  
	/**
	 * get the user id
	 * @return the user id
	 */
	public long getUserId() {
		return userId;
	}
	
	/**
	 * set the username
	 * @param name the username
	 */
	public void setmUserName(String name){
		this.mUserName = name;
	}
	
	/**
	 * get the username
	 * @return the username
	 */
	public String getmUserName(){
		return this.mUserName;
	}
	/**
	 * set the users name
	 * @param name the user name
	 */
	public void setmName(String name) {
		this.mName = name;
	}
	
	/**
	 * get the users name
	 * @return the users name
	 */
	public String getmName() {
		return mName;
	}
	
	/**
	 * get the users email
	 * @return the users email
	 */
	public String getmEmail() {
		return mEmail;
	}
	
	/**
	 * set the users email
	 * @param email the users email
	 */
	public void setmEmail(String email) {
		this.mEmail = email;
	}
	
	/**
	 * get the users phone number
	 * @return the users phone number
	 */
	public String getmPhoneNumber() {
		return mPhoneNumber;
	}
	
	/**
	 * set the users phone number
	 * @param phoneNumber the phone number
	 */
	public void setmPhoneNumber(String phoneNumber) {
		this.mPhoneNumber = phoneNumber;
	}
	
	/**
	 * get the users password
	 * @return the password
	 */
	public String getmPassword() {
		return mPassword;
	}
	
	/**
	 * set the users password
	 * @param password the password
	 */
	public void setmPassword(String password) {
		this.mPassword = password;
	}
	
	/**
     * Sends the password reset to instruction to the server.
     */
    public void sendPasswordReset()
    {
        
    }
    
    /**
     * Sends an offer to the server which will then send the offer to the other user.
     * @param price Price to pay for the item
     * @param item The item that is going to be bought/sold
     */
    public void makeOffer(int price, Item item)
    {
        
    }
    
    /**
     * Make a counter offer for an offer that was previously made
     * @param price Price for the item
     * @param offer The previous offer
     */
    public void makeCounterOffer(int price, Offer offer)
    {
        
    }
    
    /**
     * Adds an offer to the offers to sell list
     * @param offer for the user to sell
     */
    public void addSellOffer(Offer offer)
    {
        mSellOffers.add(offer);
    }
    
    /**
     * Adds an offer to your offers to buy.
     * @param offer for the user to buy
     */
    public void addBuyOffer(Offer offer)
    {
        mBuyOffers.add(offer);
    }
    
    /**
     * Adds an offer to the users completed offers
     * @param offer that was accepted by both sides
     */
    public void addCompletedOffer(Offer offer)
    {
        mCompletedOffers.add(offer);
    }
    
    /**
     * Adds an item to the list of user items 
     */
    public void addItem(Item item){
    	mItems.add(item);
    }
    
    /**
     * Delete the lists of items when we want to refresh the lists
     */
    public void dumpLists(){
    	if (mBuyOffers != null){
    		mBuyOffers.clear();
    	}
    	if (mSellOffers != null){
    		mSellOffers.clear();
    	}
    	if (mCompletedOffers != null){
    		mCompletedOffers.clear();
    	}
    }
    
    /**
     * get the list of sell offers
     */
    public List<Offer> getSellOffers(){
    	return mSellOffers;
    }
    /**
     * get the list of buy offers
     */
    public List<Offer> getBuyOffers(){
    	return mBuyOffers;
    }
    /**
     * get the list of sell offers
     */
    public List<Offer> getCompletedOffers(){
    	return mCompletedOffers;
    }
}