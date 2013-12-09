package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * get new offer id transportable
 * @author aidanfowler
 *
 */
public class GetNewOfferId implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETNEWOFFERID;
    User user = null;
    private long id;
    
	public GetNewOfferId(User u){
		user = u;
	}
	
	public User getUser(){
		return user;
	}
	
	public long getId(){
		return id;
	}
	/**
	 * This case is for getting a new offerId
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET NEW OFFER ID CALL ");
		id = manager.getIds(ID).getOfferId();
		server.sendMessage(this);
		
	}

}
