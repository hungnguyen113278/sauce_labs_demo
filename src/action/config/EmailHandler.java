package config;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import config.ProviderConfiguration;


public class EmailHandler {

	/**
	 * Connect to gmail 
	 * @param username
	 * @param password
	 * @return store
	 * @throws Exception
	 * @author truong.pham
	 */
	public Store connectToGmail(String username, String password) throws Exception
	{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		store = session.getStore("imaps");
		store.connect("imap.gmail.com", username, password);
		return store;
	}
	
	/**
	 * Get all messages from Gmail
	 * @return lstMessage
	 * @throws Exception
	 * @author truong.pham
	 */
	public List<String> getMessages() throws Exception
	{
		List<String> lstMessage = new ArrayList<String>();
		store = connectToGmail(username, password);
		String body ="";
		folder = store.getFolder("_do.not.replay");
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();
		for(int i = message.length - 1; i >= 0; i--){
		    Multipart multipart = (Multipart) message[i].getContent();
            for (int x = 0; x < multipart.getCount(); x++) {
	            BodyPart bodyPart = multipart.getBodyPart(x);
	            MimeMultipart mimeMultipart =(MimeMultipart) bodyPart.getContent();
	            mimeMultipart.getBodyPart(0);
	            body = body + mimeMultipart.getBodyPart(0).getContent().toString();
            }
            lstMessage.add(body);
            body = "";
		}
		return lstMessage;
	}

	/**
	 * Get all messages from Gmail
	 * @return lstMessage
	 * @throws Exception
	 * @author truong.pham
	 */
	public List<String> getMessages(String subjectEmail) throws Exception
	{
		List<String> lstMessage = new ArrayList<String>();
		store = connectToGmail(username, password);
		String body ="";
		folder = store.getFolder("_do.not.replay");
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();
		for(int i = message.length - 1; i >= 0; i--){
			if(message[i].getSubject().equals(subjectEmail)) {
			    Multipart multipart = (Multipart) message[i].getContent();
	            for (int x = 0; x < multipart.getCount(); x++) {
		            BodyPart bodyPart = multipart.getBodyPart(x);
		            MimeMultipart mimeMultipart =(MimeMultipart) bodyPart.getContent();
		            mimeMultipart.getBodyPart(0);
		            body = body + mimeMultipart.getBodyPart(0).getContent().toString();
	            }
	            lstMessage.add(body);
	            body = "";
			}
		}
		return lstMessage;
	}
	
	
	/**
	 * Delete all messages
	 * @throws Exception
	 * @author truong.pham
	 */
	public void deleteAllEmail() throws Exception
	{
		store = connectToGmail(username, password);
		folder = store.getFolder("_do.not.replay");
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();
		for (int i = 0; i < message.length; i++) {
			message[i].setFlag(Flag.DELETED,true);
		}
		folder.close(true);
		
		store.close();
	}
	/**
	 * Delete all messages in Inbox/Trash
	 * @throws Exception
	 * @author truong.pham
	 */
	public void deleteAllEmailInboxTrash() throws Exception
	{
		store = connectToGmail(username, password);		
		
		folderInblox = store.getFolder("_do.not.replay");
		folderInblox.open(Folder.READ_WRITE);
		Message[] messageInbox = folderInblox.getMessages();
		for (int i = 0; i < messageInbox.length; i++) {
			messageInbox[i].setFlag(Flag.DELETED,true);
		}
		
		folderTrash = store.getFolder("[Gmail]/Trash");
		folderTrash.open(Folder.READ_WRITE);
		Message[] messageTrash = folderTrash.getMessages();
		for (int i = 0; i < messageTrash.length; i++) {
			messageTrash[i].setFlag(Flag.DELETED,true);
		}
		
		folderInblox.close(true);
		folderTrash.close(true);
		
		store.close();
	}
	/**
	 * get password of user from email
	 * @return
	 * @author truong.pham
	 */
	public String getPassword(String emailAddress)throws Exception
	{
		String pwd = "";		
		String body ="";
		boolean flag = false;
		try {			
			store = connectToGmail(username, password);			
			folder = store.getFolder("_do.not.replay");
			folder.open(Folder.READ_WRITE);
			Message[] message = folder.getMessages();
			for(int i = message.length -1; i >= 0; i--){
				if(message[i].getSubject().equals("Welcome to BloomBoard")) {
				    Multipart multipart = (Multipart) message[i].getContent();
		            for (int x = 0; x < multipart.getCount(); x++) {
			            BodyPart bodyPart = multipart.getBodyPart(x);
			            MimeMultipart mimeMultipart =(MimeMultipart) bodyPart.getContent();
			            mimeMultipart.getBodyPart(x);
			            body = mimeMultipart.getBodyPart(x).getContent().toString();
			       
			            if (body.indexOf(emailAddress)>0)
			            {
			            	int index = body.indexOf("Your temporary password has been set to");
							pwd =  body.substring(index+73, index+81);
							flag = true;
							break;
			            }//end of if
		            }//end of For
		         if(flag)
		        	 break;
				}//end of if
			}//end of For			
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pwd;
	}
	
	/**
	 * Get subjects of email from gmail
	 * @return
	 * @throws Exception
	 * @author truong.pham
	 */
	public List<String> getSubjects() throws Exception
	{
		List<String> lstSubjects = new ArrayList<String>();
		store = connectToGmail(username, password);
		String subject ="";
		folder = store.getFolder("_do.not.replay");
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();
		for(int i = message.length - 1; i >= 0;i--) {
			subject = message[i].getSubject().toString();
			lstSubjects.add(subject);
        	subject = "";
		}
		return lstSubjects;
	}
	
	/**
	 * check email subject is exist
	 * @param subjectEmail
	 * @return true/false
	 * @throws Exception
	 * @author truong.pham
	 */
	public boolean isSubjectExist(String subjectEmail) {
		boolean isExist = false;
		try {
			store = connectToGmail(username, password);
			folder = store.getFolder("_do.not.replay");
			folder.open(Folder.READ_WRITE);
			Message[] message = folder.getMessages();
			for(int i = message.length - 1; i >= 0;i--) {
//				System.out.print(message[i].getSubject());
//				if(message[i].getSubject().equals(subjectEmail)) {
				if(message[i].getSubject().contains(subjectEmail)) {
					isExist = true;
					break;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isExist;
	}
	
	/**
	 * check email content is exist
	 * @param subjectEmail
	 * @param emailContent
	 * @return true/false
	 * @author truong.pham
	 */
	public boolean isEmailContentExist(String subjectEmail,String emailContent) {
		boolean isExist = false;
		try {
			store = connectToGmail(username, password);
			String body ="";
			folder = store.getFolder("_do.not.replay");
			folder.open(Folder.READ_WRITE);
			Message[] message = folder.getMessages();
			for(int i = message.length - 1; i >= 0; i--){
				if(message[i].getSubject().equals(subjectEmail)) {
				    Multipart multipart = (Multipart) message[i].getContent();
		            for (int x = 0; x < multipart.getCount(); x++) {
			            BodyPart bodyPart = multipart.getBodyPart(x);
			            MimeMultipart mimeMultipart =(MimeMultipart) bodyPart.getContent();
			            mimeMultipart.getBodyPart(0);
			            body = body + mimeMultipart.getBodyPart(0).getContent().toString();
		            }
		            if(body.toLowerCase().contains(emailContent.toLowerCase())) {
		            	isExist = true;
		            	break;
		            }
		            body = "";
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isExist;
	}
	
	/**
	 * check email content is exist
	 * @param emailContent
	 * @return true/false
	 * @author truong.pham
	 */
	public boolean isEmailContentExist(String emailContent) {
		boolean isExist = false;
		try {
			store = connectToGmail(username, password);
			String body ="";
			folder = store.getFolder("_do.not.replay");
			folder.open(Folder.READ_WRITE);
			Message[] message = folder.getMessages();
			for(int i = message.length - 1; i >= 0; i--){
			    Multipart multipart = (Multipart) message[i].getContent();
	            for (int x = 0; x < multipart.getCount(); x++) {
		            BodyPart bodyPart = multipart.getBodyPart(x);
		            MimeMultipart mimeMultipart =(MimeMultipart) bodyPart.getContent();
		            mimeMultipart.getBodyPart(0);
		            body = body + mimeMultipart.getBodyPart(0).getContent().toString();
	            }
	            if(body.toLowerCase().contains(emailContent.toLowerCase())) {
	            	isExist = true;
	            	break;
	            }
	            body = "";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isExist;
	}
	
	/**
	 * Connect to gmail for send
	 * @param username
	 * @param password
	 * @return 
	 * @author minh.pham
	 */
	public Session connectToGmailForSend (final String username, final String password)
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		return session;
	}
	
	/**
	 * send mail to list email
	 * @param list Email for send to
	 * @param Subject
	 * @param Content
	 * @author minh.pham
	 */
	public void sendEmailToList(String[] listEmailTo,String subject, String content)
	{
		connectToGmailForSend(username,password);
		try {
			for (int i = 0; i < listEmailTo.length; i++) {
				Message message = new MimeMessage(session);
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(listEmailTo[i]));
				message.setSubject(subject);
				message.setText(content);
				Transport.send(message);
				System.out.println("Done send email to "+listEmailTo[i]);
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * send mail with attack file to list email
	 * @param list Email for send to
	 * @param Subject
	 * @param Content
	 * @param Attack file
	 * @author minh.pham
	 */
	public void sendEmailAttackFileToList(String[] listEmailTo,String subject, String content, String attachFile)
	{
		connectToGmailForSend(username,password);
		try {
			for (int i = 0; i < listEmailTo.length; i++) {
				Message message = new MimeMessage(session);
				Multipart multipart = new MimeMultipart();
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(listEmailTo[i]));
				message.setSubject(subject);
				// creates body part for the message
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(content, "text/html");
				message.setSentDate(new Date());
				
				// creates body part for the attachment
				MimeBodyPart attachPart = new MimeBodyPart();
				 
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				
				// adds parts to the multipart
				multipart.addBodyPart(messageBodyPart);
				multipart.addBodyPart(attachPart);
				
				// sets the multipart as message's content
				message.setContent(multipart);
				
				Transport.send(message);
				System.out.println("Done send email to "+listEmailTo[i]);
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	private String username = ProviderConfiguration.getProvider().getUsernameEmail();
	private String password = ProviderConfiguration.getProvider().getPasswordEmail();
	private Store store;
	private Session session;
	private Folder folder, folderInblox, folderTrash;
	
}
