package com.swingtech.app.financetracker.core.model;

import java.io.File;

import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;

public class ParseTransactionSourceError extends BaseError {
	private File failedFile = null;
	private CsvParseProcessError csvParseRrror = null;

	public ParseTransactionSourceError() {
		super();
	}

	public ParseTransactionSourceError(TransactionSource transactionSource, Exception exception, String errorMessage,
			File failedFile) {
		super(transactionSource, exception, errorMessage);
		this.failedFile = failedFile;
	}

	public ParseTransactionSourceError(TransactionSource transactionSource, CsvParseProcessError csvParseRrror,
			File failedFile) {
		super(transactionSource, csvParseRrror.getMainException(), csvParseRrror.getErrorMessage());
		this.failedFile = failedFile;
		this.csvParseRrror = csvParseRrror;
	}

	public File getFailedFile() {
		return failedFile;
	}

	public void setFailedFile(File failedFile) {
		this.failedFile = failedFile;
	}
	
	public static ParseTransactionSourceError newParseTransactionSourceError(TransactionSource transactionSource, Exception exception, String errorMessage,
			File failedFile) {
		return new ParseTransactionSourceError(transactionSource, exception, errorMessage,
				failedFile);
	}

	public static ParseTransactionSourceError newParseTransactionSourceError(TransactionSource transactionSource, CsvParseProcessError csvParseProcessError, File failedFile) {
		return new ParseTransactionSourceError(transactionSource, csvParseProcessError,
				failedFile);
	}
	
	public ParseTransactionSourceError cloneMinimal() {
		return new ParseTransactionSourceError(this.getTransactionSource(), this.getException(), this.getErrorMessage(),
				this.failedFile);
	}

	public CsvParseProcessError getCsvParseRrror() {
		return csvParseRrror;
	}

	public void setCsvParseRrror(CsvParseProcessError csvParseRrror) {
		this.csvParseRrror = csvParseRrror.clone();
	}
}
