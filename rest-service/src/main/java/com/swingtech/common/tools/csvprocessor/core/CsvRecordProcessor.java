package com.swingtech.common.tools.csvprocessor.core;

import java.util.List;
import java.util.Map;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParseSuccessRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public interface CsvRecordProcessor {
	public void handleParseCsvPrerProcess(Map<String, Object> parseProcessContextMap);
	
	public Object handleParseCsvLine(CsvLineRecord csvLineRecord, Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) throws Exception;
	
	public boolean handleParseLineSuccess(CsvParseSuccessRecord successRecord, CsvParserResults csvParseResults, Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap);
	public boolean handleParseLineError(CsvParseErrorRecord errorRecord, CsvParserResults csvParseResults, Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap);
	
	public void handleParseCsvPostProcess(CsvParserResults csvParseResults, Map<String, Object> parseProcessContextMap, boolean didParseProcessFail);
	
	public void handlePraseCsvProcessError(CsvParserResults csvParseResults, CsvParseProcessError csvParseProcessError, Map<String, Object> parseProcessContextMap);	
}
