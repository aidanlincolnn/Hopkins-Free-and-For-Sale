package oose.group13.hffs.data;
/** Ids Object to keep track of highest ids*/
public class Ids {

	private long tableId;
	private long userId;
	private long itemId;
	private long offerId;
	
	public Ids(){
	}
	
	/**
	 * Constructor for IDS table
	 * @param u the lowest user id
	 * @param i the lowest item id
	 * @param o the lowest offer id
	 */
	public Ids(long u,long i, long o){
		long table = 1;
		tableId = table;
		userId = u;
		itemId = i;
		offerId = o;
	}
    
	public long getTableId(){
		return tableId;
	}
	public void setTableId(long i){
		tableId = 1;
	}
	/**
	 * get the largest user id
	 * @return the largest user id
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * set the largest user id
	 * @param userId the new user id
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * get the largest item id
	 * @return the largest item id
	 */
	public long getItemId() {
		return itemId;
	}

	/**
	 * set the largest item id 
	 * @param itemId the new item id
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	/**
	 * get the largest offer id
	 * @return the largest offer id
	 */
	public long getOfferId() {
		return offerId;
	}

	/**
	 * set the largest offer id
	 * @param offerId the new id
	 */
	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}
	
}
