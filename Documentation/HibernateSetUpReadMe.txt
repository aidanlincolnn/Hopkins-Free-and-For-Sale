Steps to set up mysql with hibernate on desktop

1. Download XAMPP (program that installs mysql, php, apache, lets you create database)
2. Install XAMPP, run xampp-control.exe on windows or manager-osx mac 
	a. In the manager, start mysql, apache, php
	b. To check if it worked, type in localhost as url (will come up with xampp page)
	c. To make sure mysql is running search localhost/phpmyadmin
		-in phpmyadmin, select databases, then enter your database name (oosedb for my java code)
	d. dont change permissions and passwords for any (root) type of access
3. download hibernate (use version 4.1.2.Final)
   http://sourceforge.net/projects/hibernate/files/hibernate4/ 
   and JDBC connector
   http://www.mysql.com/products/connector/

4. Create new java project in eclipse 
	4.a select project and configure build path
	4.b add external jars (all the jars in 4.1.2.Final/lib/required, and add the mysql connector jar )

5. Download all the code in the src folder from gitlab
6. Run MainClass.java - will create and populate tables for Users, Items, and Offers

I used this guys tutorial - lots of mistakes in his code that I had to fix, took forever to get the right version and build path so lets just stick to the older version of hibernate, in HibernateManager there are some sample methods for adding, updating, and querying from our database.
http://sourabhsoni.com/how-to-use-hibernate-4-with-mysql/