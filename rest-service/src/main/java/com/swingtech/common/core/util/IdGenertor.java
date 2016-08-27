package com.swingtech.common.core.util;

import org.joda.time.LocalDateTime;

public class IdGenertor {
	public static String generateNewId() {
		LocalDateTime now = new LocalDateTime();
		
		return Long.toString(now.toDateTime().getMillis());
	}
}
