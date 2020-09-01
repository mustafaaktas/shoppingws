package com.shoppingws.util;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.Serializable;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class SendMail implements Serializable{
	
	//private String hostName			= "10.77.240.100"; //DIS IP : "91.230.73.131";
	private String hostName			="mail.nwsesys.com";
	private String fromAddress		= "dcs_info@nwsesys.com";
	private String messageSubject	= "No Subject";
	private String messageBody		= "No body"; 
	private Vector toList;
	private Vector bccList;	
	private String fileName;
	public boolean result 			= false;
	
	private String airlineCode		= Constants.USER_AIRLINE_CODE_VAL;
	
	public MimeMessage message;

	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = fromAddress;
			String password = "Nwsys2020@";
		   return new PasswordAuthentication(username, password);
		}
	}
	private class SMTPAuthenticatorZagrosjet extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
		   String username = "reservation@zagrosjet.com";
		   String password = "ZJ93210";
		   return new PasswordAuthentication(username, password);
		}
	}
	public SendMail(){
		this(Constants.USER_AIRLINE_CODE_VAL);
	}
		 
	public SendMail(String airlineCode){
		try {
			toList = new Vector();
			this.airlineCode = airlineCode;
			if(airlineCode.equals("Z4")){
				zargrosjetMailSettings();
			}else{
				fromAddress = "dcs_info@nwsesys.com";
				Properties props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", hostName);
				props.put("mail.smtp.auth","true");
				props.put("mail.smtp.port", "587");
				props.put("mail.start.tls","false");
				props.put("mail.smtp.starttls.enable", "false");
				props.put("mail.debug","false");
				
				Authenticator auth = new SMTPAuthenticator();
				Session s = Session.getInstance(props,auth);
				message = new MimeMessage(s);	
				fileName=null;
			}
			
		} catch (Exception e) {
			Helper.errorLogger(this.getClass(), e);
		}
	}
	
	public void addAddress(String address){
		if(address!=null)
			toList.add(address);
	}
	private void zargrosjetMailSettings(){
		try {
			
			hostName		="mail.zagrosjet.com";
			fromAddress		="info@zagrosjet.com";
			messageSubject	="No Subject";
			messageBody		="No body"; 
			
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.host", hostName);
			props.put("mail.smtp.auth","true");

			Authenticator auth = new SMTPAuthenticatorZagrosjet();
			Session s = Session.getInstance(props,auth);
			message = new MimeMessage(s);	
			fileName=null;
			
		} catch (Exception e) {
			Helper.errorLogger(this.getClass(), e);
		}
	}
	public void sendIt(){
		String toAddress = "";
		try {

			InternetAddress from = new InternetAddress(fromAddress);
			message.setFrom(from);

			

			if(fileName!=null){
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(messageBody, "text/html; charset=UTF-8");
				
				MimeBodyPart attachFilePart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileName);
				attachFilePart.setDataHandler(new DataHandler(fds));
				attachFilePart.setFileName(fds.getName());
				
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(textPart);
				mp.addBodyPart(attachFilePart);
				message.setContent(mp);
			}
			else{				 
				message.setContent(messageBody, "text/html; charset=UTF-8");
			}
						
			message.setSubject(messageSubject,"UTF-8");

			Iterator i = toList.iterator();
			while(i.hasNext()){
				InternetAddress to = new InternetAddress((String) i.next());
				toAddress = to.getAddress();
				message.addRecipient(Message.RecipientType.TO, to);
				
			}

			Transport.send(message);
			result = true;
		} catch (Exception e) {
			Helper.errorLogger(this.getClass(), e,"[fromAddress]..:" + fromAddress + " [toAddress]..:" + toAddress);			
			result = false;
		}
	}
	public void sendItHtml(URL url,String fileName){
		try {

			InternetAddress from = new InternetAddress(fromAddress);
			message.setFrom(from);
	

			if(fileName!=null){
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(messageBody, "text/html; charset=UTF-8");
				
				MimeBodyPart attachFilePart = new MimeBodyPart();
				attachFilePart.setDataHandler(new DataHandler(url));
				attachFilePart.setFileName(fileName);
				
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(textPart);
				mp.addBodyPart(attachFilePart);
				message.setContent(mp);

			}
			else{
				message.setContent(messageBody, "text/html; charset=UTF-8");
			}
						
			message.setSubject(messageSubject,"UTF-8");

			Iterator i = toList.iterator();
			while(i.hasNext()){
				InternetAddress to = new InternetAddress((String) i.next());
				message.addRecipient(Message.RecipientType.TO, to);	
			}

			Transport.send(message);
			result = true;
		} catch (Exception e) {
			//Helper.printConsoleDebug("SendMail Error: " + e.getMessage());			
			result = false;
		}
	}
	public void sendItHtml(){
		try {

			InternetAddress from = new InternetAddress(fromAddress);
			message.setFrom(from);
	

			if(fileName!=null){
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(messageBody, "text/html; charset=UTF-8");
				
				MimeBodyPart attachFilePart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileName);
				attachFilePart.setDataHandler(new DataHandler(fds));
				attachFilePart.setFileName(fds.getName());
				
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(textPart);
				mp.addBodyPart(attachFilePart);
				message.setContent(mp);

			}
			else{
				message.setContent(messageBody, "text/html; charset=UTF-8");
			}
						
			message.setSubject(messageSubject,"UTF-8");

			Iterator i = toList.iterator();
			while(i.hasNext()){
				InternetAddress to = new InternetAddress((String) i.next());
				message.addRecipient(Message.RecipientType.TO, to);	
			}

			Transport.send(message);
			result = true;
		} catch (Exception e) {
			//Helper.printConsoleDebug("SendMail Error: " + e.getMessage());			
			result = false;
		}
	}
	public static void insMailData(Connection conn,String owner,String subject,String recipients,String attachment,String encoding,String sender,String mailbody){
		String insSQL = "";
		try {

			insSQL = "begin ETS_VNL.ATLASJET_MAIL_SEND('"+owner+"','"+sender+"','"+subject+"','"+mailbody+"','"+attachment+"','"+encoding+"','"+recipients+"'); end;";
			
			CallableStatement pResultSet = (CallableStatement) conn.prepareCall(insSQL);
			pResultSet.execute();
			pResultSet.close();							
			
		}catch(Exception e){
			Helper.errorLogger(SendMail.class, e,insSQL);
				
		}
	}
	
	public static void insHistoryData(Connection conn,String bkgno,String email,String username){
		
		try {
			
			String message_tr = "";
			String message_en = ""; 

			message_tr = email+" MAIL ADRESINE BILET METNI GONDERILMISTIR.";
			message_en = "TICKET SEND TO "+email+" MAIL ADDRESS";   
			
			//Tr icin B
			String sqlStr = "INSERT INTO BOOKING_CHANGE_HISTORY(BKG_NUMBER,BKG_SUB_NUMBER,CHANGE_TYPE,CHANGE_DATE,CHANGE_TIME,CHANGE_USER_ID,CHANGE_COMMENT)"; 
			sqlStr +="VALUES("+bkgno+",0 ,'SENDML' ,to_char(sysdate,'YYYYMMDD'),to_char(sysdate,'HH24MISS'),'"+username+"','"+message_tr+"')"; 
			//Helper.insSql(conn,sqlStr);
			//Tr icin E
			
			//EN icin B
			sqlStr = "INSERT INTO BOOKING_CHANGE_HISTORY_EN(BKG_NUMBER,BKG_SUB_NUMBER,CHANGE_TYPE,CHANGE_DATE,CHANGE_TIME,CHANGE_USER_ID,CHANGE_COMMENT)"; 
			sqlStr +="VALUES("+bkgno+",0 ,'SENDML' ,to_char(sysdate,'YYYYMMDD'),to_char(sysdate,'HH24MISS'),'"+username+"','"+message_en+"')"; 
			//Helper.insSql(conn,sqlStr);
			//EN icin E

		}catch(Exception e){
			Helper.errorLogger(SendMail.class, e);		
		}
	}
	
		
	public String getFromAddress() {
		return fromAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setFromAddress(String string) {
		fromAddress = string;
	}

	public void setHostName(String string) {
		hostName = string;
	}

	public void setMessageBody(String string) {
		messageBody = string;
	}

	public void setMessageSubject(String string) {
		messageSubject = string;
	}

	public String getFileName() {
		return fileName;
	}

	public MimeMessage getMessage() {
		return message;
	}

	public Vector getToList() {
		return toList;
	}

	public void setFileName(String string) {
		fileName = string;
	}

	public void setMessage(MimeMessage message) {
		this.message = message;
	}

	public void setToList(Vector vector) {
		toList = vector;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

}
