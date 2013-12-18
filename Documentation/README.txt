Documentation:

GUI: This is our android application. The GUI is in the hffs.gui package. The web services client is in the client package. We use the webservices to connect to the server and send information back and forth.

GUITest: This is an android test project for testing web services. We have full tests coverage for all sends and receives from the clinet to server and back.

HFFSData: This is a project containing all of the different objects that we use on both the client and server end as well as to create our hibernate database.

ServerDatabase: This project contains all operations related to the database. As well as our transportable objects which contain execute methods to update the database and send data through the server to the client. There is full test coverage for the standalone database as well as all the operations accessing the database using calls by the server.

In order to run our application:

1. Set up a database on your machine.
2. Edit the hibernate config file to log onto your database
3. Run RunServer in the ServerDatabase Project to set up the hibernate database and start the server
4. Set the ip address in GUI -> WebServices to the ip address of your computer
5. Run AddData in ServerDatabase to populate the database with users and items
	- There will be three users (email,pass) afowler6@jhu.edu,12345  , ltakto1@jhu.edu,12345, sili64@jhu.edu,12345
	- You can also make a new account when you run the app
6. To run the app run GUI as an android application if the device is connected, otherwise upload and install the app on the android devices and run it the normal way (must start server first). When you quit the android app, it disconnects from the server and you can let it keep running. If the app crashes you must restart the server.
