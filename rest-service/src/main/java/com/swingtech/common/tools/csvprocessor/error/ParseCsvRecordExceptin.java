package com.swingtech.common.tools.csvprocessor.error;

import java.util.List;

import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;

public class ParseCsvRecordExceptin extends Exception {
	String csvRecordErrorMessage = null;
	Exception csvRecordException = null;
	private CsvParseErrorRecord csvParseErrorRecord = null;
	

	public ParseCsvRecordExceptin(CsvParseErrorRecord csvParseErrorRecord) {
		super( (csvParseErrorRecord.getMainException() != null) ? csvParseErrorRecord.getMainException().getMessage() :  csvParseErrorRecord.getErrorMessage(), csvParseErrorRecord.getMainException());
		this.setFieldsFromParseError(csvParseErrorRecord);
	}

	public ParseCsvRecordExceptin(String csvRecordErrorMessage, Exception csvRecordException) {
		super( (csvRecordException != null) ? csvRecordException.getMessage() :  csvRecordErrorMessage, csvRecordException);
		this.csvRecordErrorMessage = csvRecordErrorMessage;
		this.csvRecordException = csvRecordException;
	}
	
	private void setFieldsFromParseError(CsvParseErrorRecord csvParseErrorRecord) {
		this.csvRecordErrorMessage = csvParseErrorRecord.getErrorMessage();
		this.csvRecordException = csvParseErrorRecord.getMainException();
		this.csvParseErrorRecord = csvParseErrorRecord;
	}

	public String getCsvRecordErrorMessage() {
		return csvRecordErrorMessage;
	}

	public void setCsvRecordErrorMessage(String csvRecordErrorMessage) {
		this.csvRecordErrorMessage = csvRecordErrorMessage;
	}

	public Exception getCsvRecordException() {
		return csvRecordException;
	}

	public void setCsvRecordException(Exception csvRecordException) {
		this.csvRecordException = csvRecordException;
	}

	public CsvParseErrorRecord getCsvParseErrorRecord() {
		return csvParseErrorRecord;
	}

	public void setCsvParseErrorRecord(CsvParseErrorRecord csvParseErrorRecord) {
		this.csvParseErrorRecord = csvParseErrorRecord;
	}
}
