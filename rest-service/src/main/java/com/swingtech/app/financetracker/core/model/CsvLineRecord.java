package com.swingtech.app.financetracker.core.model;

import java.util.List;

public class CsvLineRecord {
	private int csvLineNumber = -1; 
	private String csvLineString = null;
	private List<String> csvLineFieldList = null;

	public CsvLineRecord() {
		super();
	}
	public CsvLineRecord(int csvLineNumber, String csvLineString, List<String> csvLineFieldList) {
		super();
		this.csvLineNumber = csvLineNumber;
		this.csvLineString = csvLineString;
		this.csvLineFieldList = csvLineFieldList;
	}
	public int getCsvLineNumber() {
		return csvLineNumber;
	}
	public void setCsvLineNumber(int csvLineNumber) {
		this.csvLineNumber = csvLineNumber;
	}
	public String getCsvLineString() {
		return csvLineString;
	}
	public void setCsvLineString(String csvLineString) {
		this.csvLineString = csvLineString;
	}
	public List<String> getCsvLineFieldList() {
		return csvLineFieldList;
	}
	public void setCsvLineFieldList(List<String> csvLineFieldList) {
		this.csvLineFieldList = csvLineFieldList;
	}
	
	public static CsvLineRecord newCsvLineRecord(int csvLineNumber, String csvLineString, List<String> csvLineFieldList) {
		return new CsvLineRecord(csvLineNumber, csvLineString, csvLineFieldList);
	}
	
	public CsvLineRecord cloneMinimal() {
		return new CsvLineRecord(csvLineNumber, csvLineString, csvLineFieldList);
	}
}
