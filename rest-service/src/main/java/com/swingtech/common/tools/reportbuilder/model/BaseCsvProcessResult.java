package com.swingtech.common.tools.reportbuilder.model;

import java.util.List;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.common.core.util.Timer;

public class BaseCsvProcessResult {
	CsvLineRecord csvLineRecord = null;
	private Object returnObject;
	private Timer csvParseLineTimer = new Timer();
	
	public BaseCsvProcessResult(CsvLineRecord csvLineRecord, Object returnObject) {
		super();
		this.csvLineRecord = csvLineRecord;
		this.returnObject = returnObject;
	}
	public BaseCsvProcessResult(CsvLineRecord csvLineRecord) {
		super();
		this.csvLineRecord = csvLineRecord;
	}
	public BaseCsvProcessResult() {
		super();
	}
	public int getLineNumber() {
		return csvLineRecord.getCsvLineNumber();
	}
	public String getCsvLineString() {
		return csvLineRecord.getCsvLineString();
	}
	public List<String> getCsvFieldList() {
		return csvLineRecord.getCsvLineFieldList();
	}
	public Timer getCsvParseLineTimer() {
		return csvParseLineTimer;
	}
	public void setProcessTimer(Timer processTimer) {
		this.csvParseLineTimer = processTimer;
	}
	public Object getReturnObject() {
		return returnObject;
	}
	public CsvLineRecord getCsvLineRecord() {
		return csvLineRecord;
	}
}
