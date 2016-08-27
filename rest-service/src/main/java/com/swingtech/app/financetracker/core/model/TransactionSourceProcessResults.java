package com.swingtech.app.financetracker.core.model;

import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public class TransactionSourceProcessResults extends BaseResults {
	public CsvParserResults parseCsvFileResults = null;
	public Boolean didTransactionSourceFailToProcess = false;
	private ParseTransactionSourceError failToProcessError = null;
	
	public Boolean getDidTransactionSourceFailToProcess() {
		return didTransactionSourceFailToProcess;
	}

	public void setFailedToProcessTransactionSource(Boolean failedToProcessTransactionSource) {
		this.didTransactionSourceFailToProcess = failedToProcessTransactionSource;
	}
	
	public boolean hasFailToProcessError() {
		return this.getFailToProcessError() != null;
	}

	public ParseTransactionSourceError getFailToProcessError() {
		return failToProcessError;
	}

	public void setFailToProcessError(ParseTransactionSourceError failToProcessError) {
		if (failToProcessError != null) {
			this.failToProcessError = failToProcessError.cloneMinimal();
		} else {
			this.failToProcessError = null;
		}
	}

	public Boolean getDidPartialRecordsFail() {
		if (this.getDidTransactionSourceFailToProcess()) {
			return false;
		}
		
		return super.getDidPartialRecordsFail();
	}

	public boolean hasFailureRecords() {
		// IF the entire transaction source failed, there will be no failure record because nothing was processed.
		if (this.getDidTransactionSourceFailToProcess()) {
			return false;
		}
		
		return super.hasFailureRecords();
	}

	public boolean hasFailures() {
		// In this case, we just want ot know if there were any kind of failures.  
		if (this.getDidTransactionSourceFailToProcess()) {
			return true;
		}
		
		return super.hasFailures();
	}
	
	public Boolean getDidAllRecordsFail() {
		// this one is tough.  If no records were processed, did they all fail?  Gotta go with semantics
		if (this.getDidTransactionSourceFailToProcess()) {
			return false;
		}
		
		return super.getDidAllRecordsFail();
	}

	public CsvParserResults getParseCsvFileResults() {
		return parseCsvFileResults;
	}

	public void setParseCsvFileResults(CsvParserResults parseCsvFileResults) {
		this.parseCsvFileResults = parseCsvFileResults;
	}

	
}
