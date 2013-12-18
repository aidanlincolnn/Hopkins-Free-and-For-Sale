package oose.group13.hffs.transport;

import oose.group13.hffs.data.Ids;
import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * Add user transportable
 * @author aidanfowler
 */
public class AddUser implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.ADDUSER;
    User user = null; 
    Boolean signup = false;
    
    public AddUser(User u){
    	user =u;
    }

    public User getUser(){
    	return user;
    }
    
    public void signUp(){
    	signup = true;
    }
    
    /**
	* We receive an ADDUSER , we want to add the user to the database
	* if they are already in it we want to update their info 
	*/
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("ADD USER CALL");
		User user1 = manager.getUser(user.getmEmail());
		//user is not in the database and user is signing up for first time
		if (user1 == null && signup){ 
			manager.insert_record(user);
			Ids ids = manager.getIds(ID);
			Ids ids2 = ids;
			long newId = ids.getUserId()+1;
			System.out.println("New id:"+newId);
			ids2.setUserId(newId);
			manager.updateEntry(ids, ids2);
		}
		else {
			// user is in the database and is not trying to sign up, update their information
			if (!signup){ 
				manager.updateEntry(user1,user);
				System.out.println("UPDATING USER");
			}
			//user is in the database and trying to sign up, do not add them
			else {
				System.out.println("USER ALREADY EXISTS DONT WANT TO ADD");
				user = null;
			}
		}
		server.sendMessage(this);
	}
}
