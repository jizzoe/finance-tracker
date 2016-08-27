package com.swingtech.common.tools.reportbuilder.model;

import java.util.List;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;

public class CsvParseSuccessRecord extends BaseCsvProcessResult {
	public CsvParseSuccessRecord() {
		super();
	}
	public CsvParseSuccessRecord(CsvLineRecord csvLineRecord, Object returnObject) {
		super(csvLineRecord, returnObject);
	}

	public static CsvParseSuccessRecord getCsvParseSuccess(CsvLineRecord csvLineRecord, Object returnObject) {
		return new CsvParseSuccessRecord(csvLineRecord, returnObject);
	}

}
