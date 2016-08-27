package com.swingtech.common.tools.csvprocessor.error;

import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public class ParseCsvProcessException extends Exception {
	CsvParserResults csvParserResults = null;
	CsvParseProcessError csvParseProcessError = null;

	public ParseCsvProcessException(CsvParseProcessError csvParseProcessError, CsvParserResults csvParserResults) {
		super( (csvParseProcessError.getMainException() != null) ? csvParseProcessError.getMainException().getMessage() :  csvParseProcessError.getErrorMessage(), csvParseProcessError.getMainException());
		this.csvParserResults = csvParserResults; 
		this.csvParseProcessError = csvParseProcessError;
	}

	public CsvParserResults getCsvParserResults() {
		return csvParserResults;
	}

	public void setCsvParserResults(CsvParserResults csvParserResults) {
		this.csvParserResults = csvParserResults;
	}

	public CsvParseProcessError getCsvParseProcessError() {
		return csvParseProcessError;
	}

	public void setCsvParseProcessError(CsvParseProcessError csvParseProcessError) {
		this.csvParseProcessError = csvParseProcessError;
	}

}
