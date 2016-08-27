package com.swingtech.app.financetracker.core.model;

public class BaseError {
	private TransactionSource transactionSource = null;
	private Exception exception = null;
	private String errorMessage = null;

	public BaseError() {
		super();
	}
	public BaseError(TransactionSource transactionSource, Exception exception, String errorMessage) {
		super();
		this.setTransactionSource(transactionSource);
		this.setException(exception);
		this.setErrorMessage(errorMessage);
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public TransactionSource getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(TransactionSource transactionSource) {
		if (transactionSource != null) {
			this.transactionSource = transactionSource.cloneMinimal();
		} else {
			this.transactionSource = null;
		}
	}
}
