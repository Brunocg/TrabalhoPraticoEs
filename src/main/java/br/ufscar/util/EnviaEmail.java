package br.ufscar.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviaEmail 
{
	private static final String hostName = "smtp.gmail.com";
	private static final String SMTPport = "465";

	public boolean envia(StringBuilder mensagem, String nomeMaquina, String qtdMsg, String[] recipients)
	{
		boolean ok = true;
		try 
		{
			boolean debug = true;
			String ssl = "javax.net.ssl.SSLSocketFactory";
			String subject = "Trabalho ES - " +qtdMsg+ " - " +nomeMaquina;
			String message = mensagem.toString();
			String from = "@gmail.com";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", hostName);
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", SMTPport);
			props.put("mail.smtp.socketFactory.port", SMTPport);
			props.put("mail.smtp.socketFactory.class", ssl);
			props.put("mail.smtp.socketFactory.fallback", "false");
			
			

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator(){

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("usuario", "senha");
				}
			});

			session.setDebug(debug);

			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html");
			Transport.send(msg);

		}
		catch(MessagingException e) 
		{
			ok = false;
			System.out.println("Não foi possível estabelecer conexão!");
		}
		return ok;
	}
	
	public boolean sendWithAttachment(StringBuilder textMessage, String typeMessage, String specificationMessage, String[] recipients, String[] recipientsCC, String[] recipientsCCO, String[] pathFile)
	{
		boolean ok = true;
		try 
		{
			boolean debug = true;
			String ssl = "javax.net.ssl.SSLSocketFactory";
			String subject = "Trabalho ES - " +typeMessage+ " - " +specificationMessage;
			String message = textMessage.toString();
			String from = "@gmail.com";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", hostName);
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", SMTPport);
			props.put("mail.smtp.socketFactory.port", SMTPport);
			props.put("mail.smtp.socketFactory.class", ssl);
			props.put("mail.smtp.socketFactory.fallback", "false");



			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator(){

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("usuario", "senha");
				}
			});

			session.setDebug(debug);

			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			InternetAddress[] addressCC = new InternetAddress[recipientsCC.length];
			for (int i = 0; i < recipientsCC.length; i++) {
				addressCC[i] = new InternetAddress(recipientsCC[i]);
			}
			msg.setRecipients(Message.RecipientType.CC, addressCC);
			InternetAddress[] addressCCO = new InternetAddress[recipientsCCO.length];
			for (int i = 0; i < recipientsCCO.length; i++) {
				addressCCO[i] = new InternetAddress(recipientsCCO[i]);
			}
			msg.setRecipients(Message.RecipientType.BCC, addressCCO);

			// Setting the Subject and Content Type
			msg.setSubject(subject);

			//First part of the email
			MimeBodyPart partOne = new MimeBodyPart();
			partOne.setContent(message, "text/html");

			//Creates a message composed
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(partOne);
		
			//More parts of the email (Attachment)
			for (String pathFileAttachment : pathFile) {
				MimeBodyPart partAttachment = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(pathFileAttachment);
				partAttachment.setDataHandler(new DataHandler(fds));
				partAttachment.setFileName(fds.getName());
				
				multiPart.addBodyPart(partAttachment);
			}

			msg.setContent(multiPart);

			Transport.send(msg);

		}
		catch(MessagingException e) 
		{
			ok = false;
			System.out.println("Unable to establish connection!");
		}
		return ok;
	}
}

