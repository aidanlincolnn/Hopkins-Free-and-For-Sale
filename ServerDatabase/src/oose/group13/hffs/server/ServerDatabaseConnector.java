package oose.group13.hffs.server;

import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.transport.Transportable;

/**
 * class that allows the server to access the database, uses transportables execute method
 * @author aidanfowler
 *
 */
public class ServerDatabaseConnector {
	
	private static HibernateManager manager = HibernateManager.getManager();
	private static TCPServer mServer;
	
	public void run(){
		manager.setUpHibernate();
		mServer = new TCPServer(new TCPServer.OnMessageReceived()
		
		{
			/**
			 * Switch on a received transportable type
			 * execute the transportable query / send
			 */
			@Override
			public void messageReceived(Transportable trans)
			{
				trans.execute(manager, mServer);
			}
		});
		mServer.start();
	}
}
