package com.swingtech.app.financetracker.core.model;

public class AddTransactionRecordError extends BaseTransactionRecordError {
	public AddTransactionRecordError(TransactionSource transactionSource, Exception exception, String errorMessage,
			FinancialTransactionRecord transactionRecord, int transactionRecordNumber, String requestUrl,
			String requestPostBody, Integer responseStatusCode, String responseBody) {
		super(transactionSource, exception, errorMessage, transactionRecord);
		this.transactionRecordNumber = transactionRecordNumber;
		this.requestUrl = requestUrl;
		this.requestPostBody = requestPostBody;
		this.responseStatusCode = responseStatusCode;
		this.responseBody = responseBody;
	}

	public AddTransactionRecordError() {
		super();
	}

	private int transactionRecordNumber = -1;
	private String requestUrl = "";
	private String requestPostBody = "";
	private Integer responseStatusCode = -1;
	private String responseBody = "";

	public int getTransactionRecordNumber() {
		return transactionRecordNumber;
	}

	public void setTransactionRecordNumber(int transactionRecordNumber) {
		this.transactionRecordNumber = transactionRecordNumber;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestPostBody() {
		return requestPostBody;
	}

	public void setRequestPostBody(String requestPostBody) {
		this.requestPostBody = requestPostBody;
	}

	public int getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(Integer responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public AddTransactionRecordError cloneMinimal() {
		return new AddTransactionRecordError(this.getTransactionSource(), this.getException(), this.getErrorMessage(),
				this.getTransactionRecord(), transactionRecordNumber, requestUrl, requestPostBody, responseStatusCode,
				responseBody);
	}

	public static AddTransactionRecordError newAddTransactionRecordError(TransactionSource transactionSource, Exception exception, String errorMessage,
			FinancialTransactionRecord transactionRecord, int transactionRecordNumber, String requestUrl,
			String requestPostBody, Integer responseStatusCode, String responseBody) 
	{
		return new AddTransactionRecordError(transactionSource, exception, errorMessage,
				transactionRecord, transactionRecordNumber, requestUrl,
				requestPostBody, responseStatusCode, responseBody);
	}
}
