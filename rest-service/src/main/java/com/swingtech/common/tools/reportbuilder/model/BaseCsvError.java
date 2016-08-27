package com.swingtech.common.tools.reportbuilder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;

public class BaseCsvError extends BaseCsvProcessResult  {

	private String errorMessage;
	private Exception mainException;	
	private Map<String, Exception> additionalErrors = new HashMap<String, Exception>();

	public BaseCsvError() {
		super();
	}
	public BaseCsvError(CsvLineRecord csvLineRecord, Object returnObject) {
		super(csvLineRecord, returnObject);
	}

	public BaseCsvError(CsvLineRecord csvLineRecord, Object returnObject, String errorMessage,
			Exception exception) {	
		super(csvLineRecord, returnObject);
		this.errorMessage = errorMessage;
		this.mainException = exception;
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
}
