package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * get user transportable
 * @author aidanfowler
 *
 */
public class GetUser implements Transportable{
	
	private static final long serialVersionUID = 1L;
	TransType type = TransType.GETUSER;
    User user = null;
    
    public GetUser(User u){
    	user = u;
    }
    
    public User getUser(){
    	return user;
    }
    
    /**
	* We receive a GETUSER  we want to log in a user
	* The transportable should include a user whos only attributes are 
	* email and password 
	*/
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("GET USER CALL");
		user = manager.getUser(user.getmEmail(),user.getmPassword());
	    server.sendMessage(this);
		
	} 
}
