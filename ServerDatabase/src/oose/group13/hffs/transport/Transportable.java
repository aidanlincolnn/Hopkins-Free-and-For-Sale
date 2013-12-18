package oose.group13.hffs.transport;

import java.io.Serializable;

import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/** Transportable interface for all transfers between client and server
 * @author aidanfowler
 */

public interface Transportable extends Serializable
{
	static final long ID = 1;
	
	/** The type of transaction between the client and server*/
    public enum TransType
    {
        SENDOFFER,
        GETOFFERS,
        REMOVEOFFER,
        GETUSER,
        ADDITEM,
        REMOVEITEM,
        GETITEMS,
        ADDUSER,
        SENDMESSAGE,
        GETNEWUSERID,
        GETNEWITEMID,
        GETNEWOFFERID,
        RECOVERPASSWORD,
        GETUSERBYID,
        GETITEMBYID;
        
    }

    public void execute(HibernateManager manager, TCPServer server);
    
}
