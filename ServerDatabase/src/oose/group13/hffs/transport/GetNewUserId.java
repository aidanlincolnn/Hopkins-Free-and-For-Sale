package oose.group13.hffs.transport;

import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * get new user id transportable 
 * @author aidanfowler
 *
 */
public class GetNewUserId implements Transportable{

	private static final long serialVersionUID = 1L;
	private long id;
	
	public long getId(){
		return id;
	}
	
	/**
	 * This transportable returns a new user id for a user
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET NEW USER ID CALL");
		id = manager.getIds(ID).getUserId();
		server.sendMessage(this);
		
	}

}
