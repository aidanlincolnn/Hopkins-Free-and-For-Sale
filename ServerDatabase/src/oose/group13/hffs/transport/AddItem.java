package oose.group13.hffs.transport;

import java.util.ArrayList;
import java.util.List;

import oose.group13.hffs.data.Ids;
import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * Add item transportable
 * @author aidanfowler
 */
public class AddItem implements Transportable{

    static final long serialVersionUID = 1L;

    TransType type = TransType.ADDITEM;
    List<Item> items = new ArrayList<Item>();
    User user = null; 
    
    public AddItem(User u, List<Item> i){
    	user = u;
    	items = i;
    }
    
    public User getUser(){
    	return user;
    }

    /**
	 * We received an ADDITEM, we want to add an item to the
	 * databases or update the item if it is already in the database
	 * the transportable should only include an item and user
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("ADD ITEM CALL");
		Item item = manager.getItem(items.get(0).getitemId());
		if (item == null){
			manager.insert_record(items.get(0));
			Ids ids = manager.getIds(ID);
			Ids ids2 = ids;
			long newId =ids.getItemId()+1;
			ids2.setItemId(newId);
			manager.updateEntry(ids, ids2);
		}
		else {
			manager.updateEntry(item,items.get(0));
		}	
	}


}