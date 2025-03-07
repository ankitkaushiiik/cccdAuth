package com.ccd.common.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PersonalUtility {

	static Logger logger = LogManager.getLogger(PersonalUtility.class);

	public String passwordGenerator() {

		String token = null;
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(25);
		try {

			for (int i = 0; i < 5; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());
				sb.append(AlphaNumericString.charAt(index));
			}

			token = sb.toString();

		} catch (Exception ex) {
			logger.info("Problem in Class - PersonalUtility ~~ method- passwordGenerator() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return token;
	}
	
	
	public String tokenGenerator() {

		String token = null;
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(25);
		try {

			for (int i = 0; i < 15; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());
				sb.append(AlphaNumericString.charAt(index));
			}

			token = sb.toString();

		} catch (Exception ex) {
			logger.info("Problem in Class - PersonalUtility ~~ method- passwordGenerator() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return token;
	}

	public boolean validateEmail(String email) {

		String patternStr = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.\\w]+)*(\\.[a-z]{2,})$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();

	}
}
