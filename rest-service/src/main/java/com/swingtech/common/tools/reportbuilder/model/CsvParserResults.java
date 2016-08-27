package com.swingtech.common.tools.reportbuilder.model;

import java.util.ArrayList;
import java.util.List;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.common.core.util.Timer;

public class CsvParserResults {
	public List<CsvParseSuccessRecord> parseSuccessRecords = new ArrayList<CsvParseSuccessRecord>();
	public List<CsvParseErrorRecord> parseErrorRecords = new ArrayList<CsvParseErrorRecord>();
	public int numOfRecordsProcessed = 0;
	public int numofSuccessRecords = 0;
	public int numbOfFailedRecords = 0;
	private CsvParseProcessError csvParseProcessError = null;
	private Timer csvParseProcessTimer = new Timer();
	private String csvFileName = null;
	private boolean isPocessingCcomplete = false;
	
	public void addParseSuccess(CsvLineRecord csvLineRecord, Object resultObject) {
		CsvParseSuccessRecord csvParseSuccess = CsvParseSuccessRecord.getCsvParseSuccess(csvLineRecord, resultObject);
		this.addParseSuccess(csvParseSuccess);
	}

	public void addParseSuccess(CsvParseSuccessRecord csvParseSuccess) {
		parseSuccessRecords.add(csvParseSuccess);
		
		numOfRecordsProcessed++;
		numofSuccessRecords++;
	}

	public void addParseError(CsvParseErrorRecord csvParseError) {
		parseErrorRecords.add(csvParseError);
		
		numOfRecordsProcessed++;
		numbOfFailedRecords++;
	}

	public List<CsvParseSuccessRecord> getParseSuccessRecords() {
		return parseSuccessRecords;
	}

	public List<CsvParseErrorRecord> getParseErrorRecords() {
		return parseErrorRecords;
	}

	public int getNumOfRecordsProcessed() {
		return numOfRecordsProcessed;
	}

	public int getNumofSuccessRecords() {
		return numofSuccessRecords;
	}

	public int getNumbOfFailedRecords() {
		return numbOfFailedRecords;
	}

	public CsvParseProcessError getCsvParseProcessError() {
		return csvParseProcessError;
	}

	public void setCsvParseError(CsvParseProcessError csvParseError) {
		this.csvParseProcessError = csvParseError;
	}

	public void setParseSuccessRecords(List<CsvParseSuccessRecord> parseSuccessRecords) {
		this.parseSuccessRecords = parseSuccessRecords;
	}

	public void setParseErrorRecords(List<CsvParseErrorRecord> parseErrorRecords) {
		this.parseErrorRecords = parseErrorRecords;
	}

	public void setNumOfRecordsProcessed(int numOfRecordsProcessed) {
		this.numOfRecordsProcessed = numOfRecordsProcessed;
	}

	public void setNumofSuccessRecords(int numofSuccessRecords) {
		this.numofSuccessRecords = numofSuccessRecords;
	}
	
	public boolean didParseProcessFail() {
		return csvParseProcessError != null;
	}

	public Timer getCsvParseProcessTimer() {
		return csvParseProcessTimer;
	}

	public void setCsvParseProcessTimer(Timer csvParseProcessTimer) {
		this.csvParseProcessTimer = csvParseProcessTimer;
	}

	public void setCsvParseProcessError(CsvParseProcessError csvParseProcessError) {
		this.csvParseProcessError = csvParseProcessError;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	public boolean isPocessingCcomplete() {
		return isPocessingCcomplete;
	}

	public void setPocessingCcomplete(boolean isPocessingCcomplete) {
		this.isPocessingCcomplete = isPocessingCcomplete;
	}
}
