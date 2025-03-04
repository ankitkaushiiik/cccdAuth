package com.ccd.constantReader;

import java.util.ResourceBundle;

public class ConstantReader {

	private static ResourceBundle resource = ResourceBundle.getBundle("Constant");

	public static final String FRONTEND_URL = resource.getString("FRONTEND_URL");
}
