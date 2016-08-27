package com.swingtech.common.tools.reportbuilder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParseProcessError  {
	private String errorMessage;
	private Exception mainException;	
	private Map<String, Exception> additionalErrors = new HashMap<String, Exception>();
	
	public CsvParseProcessError(String errorMessage, Exception mainException) {
		super();
		this.errorMessage = errorMessage;
		this.mainException = mainException;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Exception getMainException() {
		return mainException;
	}
	public void setException(Exception exception) {
		this.mainException = exception;
	}
	
	public void addAdditionalError(String errorMessage, Exception e) {
		additionalErrors.put(errorMessage, e);
	}
	
	public static CsvParseProcessError getCsvParseError(String errorMessage, Exception e) {
		return new CsvParseProcessError(errorMessage, e);
	}
	
	public CsvParseProcessError clone() {
		return new CsvParseProcessError(this.errorMessage, this.mainException);
	}
	
}
