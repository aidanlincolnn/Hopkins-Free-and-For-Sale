package oose.group13.hffs.transport;

import oose.group13.hffs.data.User;
import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.mail.SendEmail;
import oose.group13.hffs.server.TCPServer;
/**
 * This transportable sends a recovery email to the user with their password
 * @author aidanfowler
 *
 */
public class RecoverPassword implements Transportable{

	private static final long serialVersionUID = 1L;
	TransType type = TransType.RECOVERPASSWORD;
	String email = null;
	
	public RecoverPassword(String e){
		email = e;
	}
	
	/**
	 * Send an email to the user with their password. Transportable needs the user 
	 */
	@Override
	public void execute(HibernateManager manager, TCPServer server) {
		System.out.println("RECOVER PASSWORD CALL");
		User u = manager.getUser(email);
		SendEmail.send(u.getmEmail(),u.getmPassword());
		
		
	}

}
