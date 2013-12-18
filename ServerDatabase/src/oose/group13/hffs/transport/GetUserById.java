package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * get user transportable
 * @author aidanfowler
 *
 */
public class GetUserById implements Transportable{
	
	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETUSERBYID;
    User user = null;
    long id = 0;
    
    public GetUserById(long i){
    	id = i;
    }
    public User getUser(){
    	return user;
    }
    
    /**
	* We receive a GETUSER  we want to get a user by their id
	* The transportable should include an id
	*/
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET USER CALL");
		user = manager.getUser(id);
	    server.sendMessage(this);
		
	} 
}
