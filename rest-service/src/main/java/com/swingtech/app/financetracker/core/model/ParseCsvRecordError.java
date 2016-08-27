package com.swingtech.app.financetracker.core.model;

import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;

public class ParseCsvRecordError extends BaseTransactionRecordError {
	private CsvLineRecord csvLineRecord = null;
	private CsvParseErrorRecord csvParseErrorRecord = null;
	
	
	public ParseCsvRecordError() {
		super();
	}

	public ParseCsvRecordError(TransactionSource transactionSource, Exception exception, String errorMessage,
			FinancialTransactionRecord transactionRecord, CsvLineRecord csvLineRecord,
			CsvParseErrorRecord csvParseErrorRecord) {
		super(transactionSource, exception, errorMessage, transactionRecord);
		this.csvLineRecord = csvLineRecord;
		this.csvParseErrorRecord = csvParseErrorRecord;
	}

	public ParseCsvRecordError(FinancialTransactionRecord transactionRecord, TransactionSource transactionSource, CsvParseErrorRecord oarseCsvErrorRecord) {
		super(transactionSource, oarseCsvErrorRecord.getMainException(), oarseCsvErrorRecord.getErrorMessage(), transactionRecord);

		this.setParseCsvErrorRecord(oarseCsvErrorRecord);
		this.setTransactionRecord(transactionRecord);
		this.csvLineRecord = oarseCsvErrorRecord.getCsvLineRecord();
	}

	public ParseCsvRecordError(FinancialTransactionRecord transactionRecord, TransactionSource transactionSource, String errorMessage, Exception e, CsvLineRecord csvLineRecord) {
		super(transactionSource, e, errorMessage, transactionRecord);
		
		CsvParseErrorRecord oarseCsvErrorRecord = CsvParseErrorRecord.getCsvParseError(csvLineRecord, transactionRecord, errorMessage, e);
		
		this.setParseCsvErrorRecord(oarseCsvErrorRecord);
		this.setTransactionRecord(transactionRecord);
		this.csvLineRecord = csvLineRecord;
	}
	
	@Override
	public String toString() {
		return "CerrorMessage=" + this.getErrorMessage() + ", lineIndex=" + csvLineRecord.getCsvLineNumber()
				+ ", line=" + csvLineRecord.getCsvLineString() + "]";
	}
	public CsvParseErrorRecord getCsvParseErrorRecord() {
		return csvParseErrorRecord;
	}
	public void setParseCsvErrorRecord(CsvParseErrorRecord parseCsvErrorRecord) {
		this.csvParseErrorRecord = parseCsvErrorRecord;
	}
	
	public static ParseCsvRecordError newParseCsvRecordError(FinancialTransactionRecord transactionRecord, TransactionSource transactionSource, String errorMessage, Exception e, CsvLineRecord csvLineRecord) {
		return new ParseCsvRecordError(transactionRecord, transactionSource, errorMessage, e, csvLineRecord);
	}
	
	public static ParseCsvRecordError bewParseCsvRecordError(FinancialTransactionRecord transactionRecord, TransactionSource transaction, CsvParseErrorRecord oarseCsvErrorRecord) {
		return new ParseCsvRecordError(transactionRecord, transaction, oarseCsvErrorRecord);
	}
	public CsvLineRecord getCsvLineRecord() {
		return csvLineRecord;
	}
	public void setCsvLineRecord(CsvLineRecord csvLineRecord) {
		this.csvLineRecord = csvLineRecord;
	}
	public void setCsvParseErrorRecord(CsvParseErrorRecord csvParseErrorRecord) {
		this.csvParseErrorRecord = csvParseErrorRecord;
	}
	
	public ParseCsvRecordError cloneMinimal() {
		return new ParseCsvRecordError(this.getTransactionRecord(), this.getTransactionSource(), this.getErrorMessage(), this.getException(), this.csvLineRecord.cloneMinimal());
	}
}
