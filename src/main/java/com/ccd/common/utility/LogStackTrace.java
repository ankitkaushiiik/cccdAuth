package com.ccd.common.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LogStackTrace implements Consumer<Exception> {

	static Logger logger = LogManager.getLogger(LogStackTrace.class);

	@Override
	public void accept(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String sStackTrace = sw.toString();
		logger.error(sStackTrace);
	}

}
