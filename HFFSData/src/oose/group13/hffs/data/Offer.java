package oose.group13.hffs.data;

import java.io.Serializable;

/**Offer Object
 * @author aidanfowler
 */

public class Offer implements Serializable{

    private static final long serialVersionUID = 1L;
    /** the id of the offer*/ 
	private long offerId;
    /** The price that the buyer will pay the seller if accepted */
    private int mPrice;
    /** The item that is involved with this offer */
    private long mItem;
    /** The item namethat is involved with this offer */
    private String mItemName;
    /** The seller of the item or the owner */
    private long mSender;
    /** The user interested in buying the item */
    private long mReceiver;
    /** The current state of the offer */
    private OfferState mState;
    
    /**
     * Constructor for an offer. 
     * @param id the offerid
     * @param price The offered price for the item that the buyer is willing to pay
     * @param item The id of the item that the buyer is looking to purchase
     * @param sender the id of the person sending the offer
     * @param receiver the id of the person receiving the offer
     * @param state The state that the offer is currently in. Accepted, declined or open.
     */
    public Offer(long id,int price, long item, String name,long sender, long receiver, OfferState state)
    {
    	offerId = id;
        mPrice = price;
        mItem = item;
        mItemName = name;
        mSender = sender;
        mReceiver = receiver;
        mState = state;
    }
	
    /** 
     * Blank constructor for queries
     */
    public Offer(){
    	
    }
    /**
     * set offer id
     * @param offerId the offer id
     */
	public void setOfferId(long offerId){
		this.offerId = offerId;
	}
	
	/**
	 * get offerId
	 * @return the offerid
	 */
	public long getOfferId(){
		return this.offerId;
	}
	
	/**
	 * set the sender id
	 * @param senderId the sender id
	 */
	public void setmSender(long senderId){
		this.mSender = senderId;
	}
	
	/**
	 * get the sender id
	 * @return the sender id
	 */
	public long getmSender(){
		return this.mSender;
	}
	
	/**
	 * set the receiver id
	 * @param receiverId the receiver id
	 */
	public void setmReceiver(long receiverId){
		this.mReceiver = receiverId;
	}
	
	/**
	 * get the receiver id
	 * @return the receiver id
	 */
	public long getmReceiver(){
		return this.mReceiver;
	}
	
	/** set the item id
	 * @param itemId the item id
	 */
	public void setmItem(long itemId){
		this.mItem = itemId;
	}
	
	/**
	 * get the item id
	 * @return the itemid
	 */
	public long getmItem(){
		return this.mItem;
	}
	
	/** set the item name
	 * @param itemName the item name
	 */
	public void setmItemName(String name){
		this.mItemName = name;
	}
	
	/**
	 * get the item name
	 * @return the itemName
	 */
	public String getmItemName(){
		return this.mItemName;
	}
	
	/**
	 * set the price
	 * @param price the price
	 */
	public void setmPrice(int price){
		this.mPrice = price;
	}
	
	/**get the price
	 * @return the price
	 */
	public int getmPrice(){
		return this.mPrice;
	}
	
	/**
	 * set the state
	 * @param offerState the offer state
	 */
	public void setmState(OfferState offerState){
		this.mState = offerState;
	}
	
	/**get the offerstate
	 * @return the offerstate
	 */
	public OfferState getmState(){
		return this.mState;
	}
}