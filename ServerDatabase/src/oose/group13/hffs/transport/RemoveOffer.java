package oose.group13.hffs.transport;

import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * remove offer transportable
 * @author aidanfowler
 *
 */
public class RemoveOffer implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.REMOVEOFFER;
    Offer offer = null;
    User user = null; 
    
    public RemoveOffer(User u, Offer o){
    	user = u;
    	offer = o;
    }
    
    public User getUser(){
    	return user;
    }
    
    /**
	* We receive a REMOVEITEM, we want to remove the item from the database	
    */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("REMOVE OFFER CALL");
		manager.delete_record(offer);
		
	}

}
