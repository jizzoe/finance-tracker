package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

public class ParseTransactionResults extends BaseResults {
	private final List<TransactionSource> allRransactionSources = new ArrayList<TransactionSource>();
	private final List<TransactionSource> transactionSourcesThatFailedToProcess = new ArrayList<TransactionSource>();
	private final List<TransactionSource> transactionSourcesWithFailedTransactionRecordsFull = new ArrayList<TransactionSource>();
	private final List<TransactionSource> transactionSourcesWithFailedTransactionRecordsPartial = new ArrayList<TransactionSource>();
	private final List<TransactionSource> transactionSourcesWithAllSuccessTransactionRecords = new ArrayList<TransactionSource>();
	private final List<BaseResultsValidationError> transactionSourcesValidationErrors = new ArrayList<BaseResultsValidationError>();
	private boolean didParseCsvFiles = false;
	private boolean didAddTransactions = false;

	@Override
	public String toString() {
		return "ParseTransactionResults [totalNumOfRecordsProcessed=" + totalNumOfRecordsProcessed
				+ ", totalNumOfSuccess=" + totalNumOfSuccess + ", totalNumOfFail=" + totalNumOfFail
				+ ", parseNumOfRecordsProcessed=" + parseNumOfRecordsProcessed + ", parseNumOfFail=" + parseNumOfFail
				+ ", parseNumOfSuccess=" + parseNumOfSuccess + ", addNumOfRecordsProcessed=" + addNumOfRecordsProcessed
				+ ", addNumOfFail=" + addNumOfFail + ", addNumOfSuccess=" + addNumOfSuccess + "]";
	}
	public List<TransactionSource> getAllRransactionSources() {
		return allRransactionSources;
	}
	
	public boolean hasValidationErrors(boolean runValidation) {
		if (runValidation) {
			this.validateResults();
		}
		
		return this.getValidationErrors().isEmpty();
	}
	
	public List<BaseResultsValidationError> validateResults() {
		List<BaseResultsValidationError> allValidationErrors = null;
		List<BaseResultsValidationError> methodValidationErrors = new ArrayList<BaseResultsValidationError>();
		
		allValidationErrors = super.validateResults();
		
		methodValidationErrors = this.validateTransactionSourceTotals(allValidationErrors);
		
		return allValidationErrors;
	}
	
	public List<BaseResultsValidationError> validateTransactionSourceTotals(List<BaseResultsValidationError> validationErrors) {
		BaseResultsValidationError validationError = null;
		List<CompareObject> compareObjectList = null;
		BaseResultsValidationError error = null;
		List<BaseResultsValidationError> returnValidationErrorList = new ArrayList<BaseResultsValidationError>();
		
		int transactionSourceCount = 0;
		
		// validate # of add Transaction Failures 
		compareObjectList = this.createNewCompareObjectAndAddToList("count of list of all transaction sources", this.getAllRransactionSources().size());
		
		transactionSourceCount += this.getTransactionSourcesThatFailedToProcess().size();
		transactionSourceCount += this.getTransactionSourcesWithAllSuccessTransactionRecords().size();
		transactionSourceCount += this.getTransactionSourcesWithFailedTransactionRecordsFull().size();
		transactionSourceCount += this.getTransactionSourcesWithFailedTransactionRecordsPartial().size();
		
		compareObjectList = this.createNewCompareObjectAndAddToList("count of all the other transaction lists combined (faieldToProcess, AllSuccess, FailureFull, FailurePartisl)", transactionSourceCount, compareObjectList);
		
		validationError = this.validateMatchingValues("# of Add Transaction Failures don't match", compareObjectList, returnValidationErrorList, validationErrors);
		
		if (returnValidationErrorList.isEmpty()) {
			return null;
		}
		
		return returnValidationErrorList;
	}
	
	
	public void addTransactionRecord(TransactionSource transactionSource, FinancialTransactionRecord transaction) {
		this.addTransactionRecord(transaction);
		transactionSource.getProcessResults().addTransactionRecord(transaction);
	}
	
	public void addTransactionSource(TransactionSource transactionSource) {
		this.getAllRransactionSources().add(transactionSource);
	}

	public void addCsvProcessSuccess(TransactionSource transactionSource, FinancialTransactionRecord transaction) {
		this.addCsvProcessSuccess(transaction);
		transactionSource.getProcessResults().addCsvProcessSuccess(transaction);
	}

	public void addAddTransactionSuccessToResults(TransactionSource transactionSource, FinancialTransactionRecord transaction) {
		this.addAddTransactionSuccessToResults(transaction);
		transactionSource.getProcessResults().addAddTransactionSuccessToResults(transaction);
	}
	
	public void addParseCsvRecordError(TransactionSource transactionSource, FinancialTransactionRecord transaction, ParseCsvRecordError error) {
		this.addParseCsvRecordError(transaction, error);
		transactionSource.getProcessResults().addParseCsvRecordError(transaction, error);
	}
	
	public void addAddTransactionError(TransactionSource transactionSource, FinancialTransactionRecord transaction, AddTransactionRecordError error) {
		this.addAddTransactionRecordError(transaction, error);
		transactionSource.getProcessResults().addAddTransactionRecordError(transaction, error);
	}
	
	public void addParseTransactionSourceError(TransactionSource transactionSource) {		
		transactionSource.getProcessResults().setFailedToProcessTransactionSource(true);
		
		this.addTransactionSource(transactionSource);
	}
	
	public void finalizeResults() {
		List<BaseResultsValidationError> tansactionSourceValidationErrors = null;
		
		this.addAllTransactionSourceToRightBucket();
		
		this.validateResults();
		
		this.validateAllTransactionSouurces();
	}
	
	public List<BaseResultsValidationError> validateAllTransactionSouurces() {
		List<BaseResultsValidationError> tansactionSourceValidationErrors = null;
		
		this.getTransactionSourcesValidationErrors().clear();
		
		for (TransactionSource transactionSource : this.getAllRransactionSources()) {
			// then loop throught he transaction sources and do 2 things.  1) run validation on each source and 2) put the source in the right bucket.
			tansactionSourceValidationErrors = transactionSource.getProcessResults().validateResults();
			
			if (tansactionSourceValidationErrors != null && !tansactionSourceValidationErrors.isEmpty()) {
				this.getTransactionSourcesValidationErrors().addAll(tansactionSourceValidationErrors);
			}
		}
		
		return this.getTransactionSourcesValidationErrors();
	}
	
	public void addAllTransactionSourceToRightBucket () {
		this.clearAllTransactionSourceBuckets();

		for (TransactionSource transactionSource : this.getAllRransactionSources()) {
			this.addTransactionSourceToRightBucket(transactionSource);
		}
	}
	
	
	public void clearAllTransactionSourceBuckets() {
		this.getTransactionSourcesThatFailedToProcess().clear();
		this.getTransactionSourcesWithFailedTransactionRecordsFull().clear();
		this.getTransactionSourcesWithFailedTransactionRecordsPartial().clear();
		this.getTransactionSourcesWithAllSuccessTransactionRecords().clear();
	}

	public void addParseTransactionSourceSuccess(TransactionSource transactionSource) {
		this.addTransactionSource(transactionSource);
	}

	public void addTransactionSourceToRightBucket(TransactionSource transactionSource) {
		if (transactionSource.getProcessResults().getDidTransactionSourceFailToProcess()) {
			this.getTransactionSourcesThatFailedToProcess().add(transactionSource);	
		} else if (transactionSource.getProcessResults().getDidAllRecordsFail()) {
			this.getTransactionSourcesWithFailedTransactionRecordsFull().add(transactionSource);	
		} else if (transactionSource.getProcessResults().getDidPartialRecordsFail()) {
			this.getTransactionSourcesWithFailedTransactionRecordsPartial().add(transactionSource);	
		}else if (transactionSource.getProcessResults().getDidAllRecordsSucceed()) {
			this.getTransactionSourcesWithAllSuccessTransactionRecords().add(transactionSource);	
		}
	}
	
	public List<TransactionSource> getTransactionSourcesThatFailedToProcess() {
		return transactionSourcesThatFailedToProcess;
	}
	public List<BaseResultsValidationError> getTransactionSourcesValidationErrors() {
		return transactionSourcesValidationErrors;
	}
	public List<TransactionSource> getTransactionSourcesWithFailedTransactionRecordsFull() {
		return transactionSourcesWithFailedTransactionRecordsFull;
	}
	public List<TransactionSource> getTransactionSourcesWithFailedTransactionRecordsPartial() {
		return transactionSourcesWithFailedTransactionRecordsPartial;
	}
	public List<TransactionSource> getTransactionSourcesWithAllSuccessTransactionRecords() {
		return transactionSourcesWithAllSuccessTransactionRecords;
	}
	public boolean isDidParseCsvFiles() {
		return didParseCsvFiles;
	}
	public void setDidParseCsvFiles(boolean didParseCsvFiles) {
		this.didParseCsvFiles = didParseCsvFiles;
	}
	public boolean isDidAddTransactions() {
		return didAddTransactions;
	}
	public void setDidAddTransactions(boolean didAddTransactions) {
		this.didAddTransactions = didAddTransactions;
	}

	public StringBuffer printResultsToBuffer(int offSet, StringBuffer buf) {
		buf = super.printResultsToBuffer(offSet, buf);
		return buf;
	}
	
	public String printResults() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("");
		
		return buf.toString();
	}
}
