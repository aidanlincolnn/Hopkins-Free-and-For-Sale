package oose.group13.hffs.server;

import oose.group13.hffs.hibernate.HibernateManager;
import oose.group13.hffs.transport.Transportable;

/**
 * class that allows the server to access the database, uses transportable's execute method
 * @author aidanfowler
 */

public class ServerDatabaseConnector {
	
	private static HibernateManager manager = HibernateManager.getManager();
	private static TCPServer mServer;
	
	public void run(){
		manager.setUpHibernate();
		mServer = new TCPServer(new TCPServer.OnMessageReceived()
		
		{
			//execute the transportable's query
			@Override
			public void messageReceived(Transportable trans)
			{
				trans.execute(manager, mServer);
			}
		});
		mServer.start();
	}
}
