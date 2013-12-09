package oose.group13.hffs.transport;

import java.io.Serializable;

import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.server.TCPServer;
/** Transportable Object for all transfers between client and server*/
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
        GETNEWOFFERID;
    }

    public void execute(HibernateManager manager, TCPServer server);
    
}
