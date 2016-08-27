package com.swingtech.app.financetracker.core.model;

public class BaseTransactionRecordError extends BaseError {
	private FinancialTransactionRecord transactionRecord = null;

	public BaseTransactionRecordError() {
		super();
	}

	public BaseTransactionRecordError(TransactionSource transactionSource, Exception exception, String errorMessage,
			FinancialTransactionRecord transactionRecord) {
		super(transactionSource, exception, errorMessage);
		this.transactionRecord = transactionRecord;
	}

	public FinancialTransactionRecord getTransactionRecord() {
		return transactionRecord;
	}

	public void setTransactionRecord(FinancialTransactionRecord transactionRecord) {
		if (transactionRecord != null) {
			this.transactionRecord = transactionRecord.cloneMinimal();
		} else {
			this.transactionRecord = null;
		}
	}

}
