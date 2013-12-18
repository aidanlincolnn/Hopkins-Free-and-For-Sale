package oose.group13.hffs.transport;


import oose.group13.hffs.data.Item;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * Get Items transportable
 * @author aidanfowler
 */
public class GetItemById implements Transportable{


	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETITEMBYID;
    Item item = null;
    long id = 0;
    
    public GetItemById(long i){
    	id = i;
    }
    
    
    public Item getItem(){
    	return item;
    }
    
    public long getId(){
    	return id;
    }
    /**
	 * We receive a GETITEMBYID, we want the item with that id
	 * The transporable should only include an item id
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET ITEM BY ID CALL");
		item = manager.getItem(id);
		server.sendMessage(this);
		
	}

    

}
