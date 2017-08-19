package com.alacriti.imdbportal.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;


public class ExceptionUtil {

	public static final Logger log= Logger.getLogger(ExceptionUtil.class);

	public static String getStackTrace(Throwable aThrowable) {
		log.debug("==============>>getStackTrace method in ExceptionUtil class");
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

}