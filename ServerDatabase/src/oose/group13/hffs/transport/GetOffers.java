package oose.group13.hffs.transport;

import java.util.List;

import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * get offers transportable
 * @author aidanfowler
 *
 */
public class GetOffers implements Transportable{


	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETOFFERS;
    User user = null; 
	
    public GetOffers(User u){
    	user = u;
    }
    
    public User getUser(){
    	return user;
    }
	
    /**
	 * We receive a GETOFFERS Transportable from the client, want to populate the offers lists for user 
	 * send the user back to the client
	 * The Transportable should only include a user
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET OFFERS CALL");
		System.out.println("lists null? "+ user.getSellOffers());
		user.dumpLists();
		List<Offer> sent = manager.getSentOffers(user.getUserId());
		List<Offer> received = manager.getReceivedOffers(user.getUserId());
		List<Offer> completed = manager.getCompletedOffers(user.getUserId());
		for (Offer o: sent){
			user.addBuyOffer(o);
		}
		for (Offer o: received){
			user.addSellOffer(o);
		}
		for (Offer o: completed){
			user.addCompletedOffer(o);
		}
	
		server.sendMessage(this);
		
	}

}
