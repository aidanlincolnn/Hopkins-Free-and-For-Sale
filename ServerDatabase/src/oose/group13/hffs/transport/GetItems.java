package oose.group13.hffs.transport;

import java.util.ArrayList;
import java.util.List;

import oose.group13.hffs.data.Item;
import oose.group13.hffs.data.ItemState;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * Get Items transportable
 * @author aidanfowler
 */
public class GetItems implements Transportable{


	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETITEMS;
    List<Item> items = new ArrayList<Item>();
    User user = null;
    
    public GetItems(User u, List<Item> i){
    	user = u;
    	items = i;
    }
    
    public User getUser(){
    	return user;
    }
    
    public List<Item> getItems(){
    	return items;
    }
    /**
	 * We receive a GETITEMS, we want the list of all items with a specific type
	 * The transporable should only include an item with only a type attribute and a user
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET ITEMS CALL");
		List <Item> getItems = manager.getForSaleItems(items.get(0).getmType());
		List <Item> sendItems = new ArrayList<Item>();
		for (Item i: getItems){
			if (i.getmAvailability() != ItemState.SOLD){
				sendItems.add(i);
			}
		}
		items = sendItems;
		server.sendMessage(this);	
	}

    

}
