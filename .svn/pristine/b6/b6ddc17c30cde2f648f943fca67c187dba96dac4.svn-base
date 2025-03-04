package com.ccd.common.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ccd.common.dto.UserDto;
import com.ccd.common.model.MailBean;
import com.ccd.common.service.MailService;
import com.ccd.common.utility.MailSender;
import com.ccd.mailReader.MailConstantReader;

@Service
public class MailServiceImpl implements MailService {

	static Logger logger = LogManager.getLogger(MailServiceImpl.class);

	Properties props;

	public MailServiceImpl() {
		props = MailConstantReader.getMailProperties();
	}

	@Autowired
	private MailSender mailSender;
	
	@Value("${emailConfig.emailId}")
	private String SENDER_MAIL_ID;
	@Value("${emailConfig.password}")
	private String SENDER_PASSWORD;

	@Override
	public Integer sendDefaultPasswordMail(UserDto userDto, String otp) {
		Integer statusCode = null;
		try {

			StringBuffer bodyHtml = new StringBuffer();
			MailBean mailBean = new MailBean();

			mailBean.setProperties(props);
			mailBean.setSenderMail(SENDER_MAIL_ID);
			mailBean.setSenderPass(SENDER_PASSWORD);
			mailBean.setSubject("Default Password");
			mailBean.setReceiverMail(userDto.getEmail());

			bodyHtml.append("<html>\n");
			bodyHtml.append("<body>\n");
			bodyHtml.append("<p>Your one time Default password is  -  </p>" + otp);
			bodyHtml.append("</body>\n");
			bodyHtml.append("</html>");

			mailBean.setBodyHtml(bodyHtml.toString());
			statusCode = mailSender.sendHtmlMail(mailBean);

		} catch (Exception ex) {
			logger.info("Problem in Class - MailServiceImpl ~~ method-> sendOTPMail() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return statusCode;
	}

}
