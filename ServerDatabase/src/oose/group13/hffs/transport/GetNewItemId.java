package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * Get new item id transportable
 * @author aidanfowler
 */
public class GetNewItemId implements Transportable{


	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETNEWITEMID;
    User user = null;
	private long id ;
	
	public GetNewItemId(User u){
		user = u;
	}
	
	public User getUser(){
		return user;
	}
	
	public long getId(){
		return id;
	}
	
	/**
	 * This transportable returns the id which the new item should use
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET NEW ITEM ID CALL");
		id = manager.getIds(ID).getItemId();
		server.sendMessage(this);
		
	}


}
