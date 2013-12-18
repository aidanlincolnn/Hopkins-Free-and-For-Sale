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
		user.dumpLists();
		List<Offer> sent = manager.getSentOffers(user.getUserId());
		List<Offer> received = manager.getReceivedOffers(user.getUserId());
		List<Offer> completed = manager.getCompletedOffers(user.getUserId());
		Boolean add = true;
		//We make sure not to add offers that include items which are in accepted offers to sent and received offers
		for (Offer s: sent){
			for (Offer a: completed){
				if (s.getmItem() == a.getmItem()){
					add = false;
				}
			}
			if (add == true){	
				user.addBuyOffer(s);
			}
			add = true;
		}
		for (Offer r: received){
			for (Offer a: completed){
				if (r.getmItem() == a.getmItem()){
					add = false;
				}
			}
			if (add == true){
				user.addSellOffer(r);
			}	
			add = true;
		}
		
		for (Offer o: completed){
			user.addCompletedOffer(o);
		}
		server.sendMessage(this);
		
	}

}
