package com.ccd.common.utility;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.ccd.common.model.MailBean;

@Component
public class MailSender { 
	
	public Integer sendHtmlMail(final MailBean mailBean) throws MessagingException{
		Integer statusCode = null;

		Session session = Session.getDefaultInstance(mailBean.getProperties(),
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailBean.getSenderMail(),mailBean.getSenderPass());
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailBean.getSenderMail()));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailBean.getReceiverMail()));
			
			if (null != mailBean.getCcMail() && !mailBean.getCcMail().equals("")) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailBean.getCcMail()));
			}
			
			message.setSubject(mailBean.getSubject());
			message.setContent(mailBean.getBodyHtml(), "text/html");
			Transport.send(message);
			
			statusCode =  200;

		} catch (MessagingException e) {
			throw e;
		}
		return statusCode;
	}
	
	

}
