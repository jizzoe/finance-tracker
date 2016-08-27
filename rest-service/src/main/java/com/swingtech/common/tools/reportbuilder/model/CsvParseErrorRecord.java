package com.swingtech.common.tools.reportbuilder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;

public class CsvParseErrorRecord extends BaseCsvError {
	public CsvParseErrorRecord() {
		super();
	}
	public CsvParseErrorRecord(CsvLineRecord csvLineRecord, Object returnObject) {
		super(csvLineRecord, returnObject);
		// TODO Auto-generated constructor stub
	}

	public CsvParseErrorRecord(CsvLineRecord csvLineRecord, Object returnObject, String errorMessage,
			Exception exception) {	
		super(csvLineRecord, returnObject, errorMessage, exception);
	}

	public static CsvParseErrorRecord getCsvParseError(CsvLineRecord csvLineRecord, Object returnObject, String errorMessage, Exception e) {
		return new CsvParseErrorRecord(csvLineRecord, returnObject, errorMessage, e);
	}
}
