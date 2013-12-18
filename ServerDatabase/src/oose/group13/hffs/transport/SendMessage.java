package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/**
 * send message transportable
 * @author aidanfowler
 *
 */
public class SendMessage implements Transportable{

	private static final long serialVersionUID = 1L;
	private String message = null;
	private User user;
	
	
	public SendMessage(User u, String m){
		user = u;
		message = m;
	}

	public String getMessage(){
		return message;
	}
	public User getUser(){
		return user;
	}
	/**
	 * This case is for debugging purposes so we can send strings we also use it to connect the client and server
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("SEND MESSAGE CALL");
		System.out.println(message);
		
	}

}
