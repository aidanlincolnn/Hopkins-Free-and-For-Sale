package oose.group13.hffs.transport;

import java.util.ArrayList;
import java.util.List;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * remove item transportable
 * @author aidanfowler
 *
 */
public class RemoveItem implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.REMOVEITEM;
    List<Item> items = new ArrayList<Item>();
    User user = null; 

    public RemoveItem(User u, List<Item> i){
    	user = u;
    	items = i;
    }
    
    public User getUser(){
    	return user;
    }
    	
	/**
	 * We receive a REMOVEITEM, we want to remove the item from the database	
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("REMOVE ITEM CALL");
		manager.delete_record(items.get(0));
	}

}
