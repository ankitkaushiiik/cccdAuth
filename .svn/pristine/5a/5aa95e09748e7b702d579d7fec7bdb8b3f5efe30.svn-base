package com.ccd.mailReader;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class MailConstantReader {

	private static ResourceBundle resource = ResourceBundle.getBundle("mail");
	private static Properties props;

	public static Properties getMailProperties() {
		Enumeration<String> keys = resource.getKeys();
		props = new Properties();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			props.put(key, resource.getString(key));
			// System.out.println("next");
		}
		return props;
	}

}
