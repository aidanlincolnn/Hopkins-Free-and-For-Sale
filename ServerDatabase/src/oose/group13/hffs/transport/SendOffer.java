package oose.group13.hffs.transport;

import oose.group13.hffs.data.Ids;
import oose.group13.hffs.data.Offer;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * send offer transportable 
 * @author aidanfowler
 *
 */
public class SendOffer implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.SENDOFFER;
    User user = null; 
    Offer offer = null;
    
    public SendOffer(User u, Offer o){
    	user = u;
    	offer = o;
    }
    
    public User getUser(){
    	return user;
    }
    
    /**
	 * We receive a SENDOFFER from the client, want to either add an offer
	 * or update an offer that is already in the database
	 * The Transportable should only include an offer and user 
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("SEND OFFER CALL");
		Offer checkOffer = manager.getOffer(offer.getOfferId());
		if (checkOffer == null){
			manager.insert_record(offer);
			Ids ids = manager.getIds(ID);
			Ids ids2 = ids;
			long newId = ids.getOfferId()+1;
			ids2.setOfferId(newId);
			manager.updateEntry(ids, ids2);
		}
		else {
			manager.updateEntry(checkOffer, offer);
		}
		
	}

}
