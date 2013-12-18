package oose.group13.hffs.mail;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * This class sends an email from hopkinsfreeandforsale@gmail.com to a users email address with their password information
 * @author aidanfowler
 */
public class SendEmail
{
public static void send(String email, String password)
{    
   // Sender's email ID needs to be mentioned
   String from = "hopkinsfreeandforsale@gmail.com";
   String pass ="fowler12";
   // Recipient's email ID needs to be mentioned.
   String to = email;

   String host = "smtp.gmail.com";

   // Get system properties
   Properties properties = System.getProperties();
   // Setup mail server
   properties.put("mail.smtp.starttls.enable", "true");
   properties.put("mail.smtp.host", host);
   properties.put("mail.smtp.user", from);
   properties.put("mail.smtp.password", pass);
   properties.put("mail.smtp.port", "587");
   properties.put("mail.smtp.auth", "true");

   // Get the default Session object.
   Session session = Session.getDefaultInstance(properties);

   try{
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO,
                               new InternetAddress(to));

      // Set Subject: header field
      message.setSubject("Hopkins Free And For Sale Password");

      // Now set the actual message
      message.setText("This is your password: "+ password);

      // Send message
      Transport transport = session.getTransport("smtp");
      transport.connect(host, from, pass);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();
      System.out.println("Sent message successfully....");
   }catch (MessagingException mex) {
      mex.printStackTrace();
   }
}
}