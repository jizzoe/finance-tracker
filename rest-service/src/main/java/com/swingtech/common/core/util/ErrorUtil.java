package com.swingtech.common.core.util;

public class ErrorUtil {
	public static String getErrorMessageFromException(Exception e) {
		return e.getClass().getName() + " - " + e.getMessage();
	}
}
