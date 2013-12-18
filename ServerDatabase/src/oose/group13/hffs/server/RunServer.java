package oose.group13.hffs.server;
/**
 * Main class to run our server
 * @author aidanfowler
 */
public class RunServer {
	private static final ServerDatabaseConnector SDBC = new ServerDatabaseConnector();
	public static void main(String args[]){
		SDBC.run();
	}

}
