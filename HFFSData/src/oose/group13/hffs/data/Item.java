package oose.group13.hffs.data;

import java.io.Serializable;
import java.util.Calendar;
/** Item Object*/
public class Item implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The title of the item posting */
    private String mTitle;
    /** A short description of the item */
    private String mDescriptionS;
    /** A long description of the item */
    private String mDescriptionL;
    /** The type of item this is */
    private ItemType mType;
    /** The seller of the item */
    private long mOwnerId;
    /** The state that the item is in, either sold or available*/
    private ItemState mAvailability;
    /** The date that this posting was made */
    private String mDatePosted;
    /** The item id*/
    private long itemId;
    /** The byte array that makes up the item image*/
    private byte[] mPic;
    
    /** 
     * Blank constructor for queries
     */
	public Item(){	
	}
    
    /**
     * Constructor for free or for sale item.
     * @param ttl Title of the post
     * @param descS short description of the item that is for sale.
     * @param descL long description of the item that is for sale.
     * @param type Type of the item that is for sale. What category it should be in.
     * @param owner The user that posted the item.
     * @param availability State of the item. Sold or Available.
     * @param picUrl the url of the folder where pictures of the item are stored
     * @param id the unique item id
     */
    public Item(String ttl, String descS, String descL,ItemType type, long ownerId, ItemState availability,long id,byte[] pic)
    {
        mTitle = ttl;
        mDescriptionS = descS;
        mDescriptionL = descL;
        mType = type;
        mOwnerId = ownerId;
        mAvailability = availability;
        mDatePosted = Calendar.getInstance().getTime().toString();
        itemId = id;
        mPic = pic;
        
    }
    
    /**
     * set the item title
     * @param name the title of the item
     */
	public void setmTitle(String name) {
		this.mTitle = name;
	}
	
	/**
	 * get the item title
	 * @return the title of the item
	 */
	public String getmTitle() {
		return mTitle;
	}
	
	/**
	 * set the short description of the item
	 * @param shortDes the short description
	 */
	public void setmDescriptionS(String shortDes) {
		this.mDescriptionS = shortDes;
	}
	
	/**
	 * get the short description of the item
	 * @return the short description
	 */
	public String getmDescriptionS() {
		return mDescriptionS;
	}
	
	/**
	 * set the long description of the item
	 * @param longDes the long description
	 */
	public void setmDescriptionL(String longDes) {
		this.mDescriptionL = longDes;
	}
	
	/**
	 * get the long description of the item
	 * @return the long description of the item
	 */
	public String getmDescriptionL() {
		return mDescriptionL;
	}
	
	/**
	 * set the item type
	 * @param type the type of item
	 */
	public void setmType(ItemType type) {
		this.mType = type;
	}
	
	/**
	 * get the type of the item
	 * @return the type of the item
	 */
	public ItemType getmType() {
		return mType;
	}
	
	/**
	 * get the id of the owner
	 * @return the id of the item owner
	 */
	public long getmOwnerId() {
		return mOwnerId;
	}
	
	/**
	 * set the id of the owner
	 * @param id the id of the owner
	 */
	public void setmOwnerId(long id) {
		this.mOwnerId = id;
	}
	
	/**
	 * set the availability of an item
	 * @param state the state of availability
	 */
	public void setmAvailability(ItemState state){
		this.mAvailability=state;
	}
	
	/**
	 * get the state of the item
	 * @return the availability state of the item
	 */
	public ItemState getmAvailability(){
		return this.mAvailability;
	}
	
	/**
	 * blank setter for hibernate
	 */
	public void setmDatePosted(String a){
		mDatePosted = a;
	}
	
	/**
	 * get the date the item was posted
	 * @return the date time it was posted
	 */
	public String getmDatePosted(){
		return mDatePosted;//.getTime().toString();
	}
	
	
	/**
	 * set the id of the item
	 * @param id the id
	 */
	public void setitemId(long id){
		this.itemId = id;
	}
	
	/**
	 * get the id of the item
	 * @return the id of the item
	 */
	public long getitemId(){
		return itemId;
	}
	
	/**
	 * set the pic byte array
	 */
	public void setmPic(byte[] pic){
		this.mPic = pic;
	}
	/**
	 * get the pic byte array
	 * @return the byte array
	 */
	public byte[] getmPic(){
		return mPic;
	}
}
