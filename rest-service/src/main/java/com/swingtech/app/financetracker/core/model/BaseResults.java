package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.core.util.Timer;

public class BaseResults {
	protected int totalNumOfRecordsProcessed = 0;
	protected int totalNumOfSuccess = 0;
	protected int totalNumOfFail = 0;
	protected Timer totalProcessTimer = new Timer();
	protected int parseNumOfRecordsProcessed = 0;
	protected int parseNumOfFail = 0;
	protected int parseNumOfSuccess = 0;
	protected int addNumOfRecordsProcessed = 0;
	protected int addNumOfFail = 0;
	protected int addNumOfSuccess = 0;
	protected Timer parseCsvFilesTimer = new Timer();
	protected Timer addTransactionTimer = new Timer();
	protected final List<FinancialTransactionRecord> allFinancialTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> successfulFinancialTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> successfulParseCsvFinancialTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> successfulAddTransactionFinancialTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> failedFinancialTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> failedParseCsvTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<FinancialTransactionRecord> failedAddTransactionTransactions = new ArrayList<FinancialTransactionRecord>();
	protected final List<ParseCsvRecordError> parseCsvRecordErrorList = new ArrayList<ParseCsvRecordError>();
	protected final List<AddTransactionRecordError> addTransactionRecordErrorList = new ArrayList<AddTransactionRecordError>();
	protected final List<BaseResultsValidationError> validationErrors = new ArrayList<BaseResultsValidationError>();
	
	public int getTotalNumOfRecordsProcessed() {
		return totalNumOfRecordsProcessed;
	}
	public void setTotalNumOfRecordsProcessed(int totalNumOfRecordsProcessed) {
		this.totalNumOfRecordsProcessed = totalNumOfRecordsProcessed;
	}
	public int getTotalNumOfSuccess() {
		return totalNumOfSuccess;
	}
	public void setTotalNumOfSuccess(int totalNumOfSuccess) {
		this.totalNumOfSuccess = totalNumOfSuccess;
	}
	public int getTotalNumOfFail() {
		return totalNumOfFail;
	}
	public void setTotalNumOfFail(int totalNumOfFail) {
		this.totalNumOfFail = totalNumOfFail;
	}
	public Timer getTotalProcessTimer() {
		return totalProcessTimer;
	}
	public void setTotalProcessTimer(Timer totalProcessTimer) {
		this.totalProcessTimer = totalProcessTimer;
	}
	public int getParseNumOfRecordsProcessed() {
		return parseNumOfRecordsProcessed;
	}
	public void setParseNumOfRecordsProcessed(int parseNumOfRecordsProcessed) {
		this.parseNumOfRecordsProcessed = parseNumOfRecordsProcessed;
	}
	public int getParseNumOfFail() {
		return parseNumOfFail;
	}
	public void setParseNumOfFail(int parseNumOfFail) {
		this.parseNumOfFail = parseNumOfFail;
	}
	public int getParseNumOfSuccess() {
		return parseNumOfSuccess;
	}
	public void setParseNumOfSuccess(int parseNumOfSuccess) {
		this.parseNumOfSuccess = parseNumOfSuccess;
	}
	public int getAddNumOfRecordsProcessed() {
		return addNumOfRecordsProcessed;
	}
	public void setAddNumOfRecordsProcessed(int addNumOfRecordsProcessed) {
		this.addNumOfRecordsProcessed = addNumOfRecordsProcessed;
	}
	public int getAddNumOfFail() {
		return addNumOfFail;
	}
	public void setAddNumOfFail(int addNumOfFail) {
		this.addNumOfFail = addNumOfFail;
	}
	public int getAddNumOfSuccess() {
		return addNumOfSuccess;
	}
	public void setAddNumOfSuccess(int addNumOfSuccess) {
		this.addNumOfSuccess = addNumOfSuccess;
	}
	public Timer getParseCsvFilesTimer() {
		return parseCsvFilesTimer;
	}
	public void setParseCsvFilesTimer(Timer parseCsvFilesTimer) {
		this.parseCsvFilesTimer = parseCsvFilesTimer;
	}
	public Timer getAddTransactionTimer() {
		return addTransactionTimer;
	}
	public void setAddTransactionTimer(Timer addTransactionTimer) {
		this.addTransactionTimer = addTransactionTimer;
	}
	public List<FinancialTransactionRecord> getAllFinancialTransactions() {
		return allFinancialTransactions;
	}
	public List<ParseCsvRecordError> getParseCsvRecordErrorList() {
		return parseCsvRecordErrorList;
	}
	public List<AddTransactionRecordError> getAddTransactionRecordErrorList() {
		return addTransactionRecordErrorList;
	}
	
	public void addTransactionRecord(FinancialTransactionRecord transactionRecord) {
		this.getAllFinancialTransactions().add(transactionRecord);
	}

	public void addCsvProcessSuccess(FinancialTransactionRecord transaction) {
		this.addTransactionRecord(transaction); 
		
		this.getSuccessfulParseCsvFinancialTransactions().add(transaction);

		this.setParseNumOfSuccess(this.getParseNumOfSuccess() + 1);
		this.setParseNumOfRecordsProcessed(this.getParseNumOfRecordsProcessed() + 1);

		this.setTotalNumOfSuccess(this.getTotalNumOfSuccess() + 1);
		this.setTotalNumOfRecordsProcessed(this.getTotalNumOfRecordsProcessed() + 1);
	}
	
	public void addAddTransactionSuccessToResults(FinancialTransactionRecord transaction) {
		this.setAddNumOfSuccess(this.getAddNumOfSuccess() + 1);
		this.setAddNumOfRecordsProcessed(this.getAddNumOfRecordsProcessed() + 1);
		
		this.getSuccessfulFinancialTransactions().add(transaction);
		this.getSuccessfulAddTransactionFinancialTransactions().add(transaction);
	}
	
	public void addParseCsvRecordError(FinancialTransactionRecord transaction, ParseCsvRecordError error) {
		if (transaction == null) {
			transaction = new FinancialTransactionRecord();
		}

		transaction.setParseCsvError(error);
		transaction.setCsvLineRecord(error.getCsvLineRecord());
		
		this.getParseCsvRecordErrorList().add(error);
		this.getFailedFinancialTransactions().add(transaction);
		this.getFailedParseCsvTransactions().add(transaction);
		
		this.setParseNumOfFail(this.getParseNumOfFail() + 1);
		this.setParseNumOfRecordsProcessed(this.getParseNumOfRecordsProcessed() + 1);
		
		this.setTotalNumOfFail(this.getTotalNumOfFail() + 1);
		this.setTotalNumOfRecordsProcessed(this.getTotalNumOfRecordsProcessed() + 1);
	}

	public void addAddTransactionRecordError(FinancialTransactionRecord transaction, AddTransactionRecordError error) {
		if (transaction == null) {
			transaction = new FinancialTransactionRecord();
		}

		transaction.setAddTransactionError(error);
		
		this.getAddTransactionRecordErrorList().add(error);
		this.getFailedFinancialTransactions().add(transaction);
		this.getFailedAddTransactionTransactions().add(transaction);
		
		this.setAddNumOfFail(this.getAddNumOfFail() + 1);
		this.setAddNumOfRecordsProcessed(this.getAddNumOfRecordsProcessed() + 1);
		
		this.setTotalNumOfFail(this.getTotalNumOfFail() + 1);
	}
	
	public List<FinancialTransactionRecord> getSuccessfulFinancialTransactions() {
		return successfulFinancialTransactions;
	}
	
	public List<FinancialTransactionRecord> getSuccessfulFinancialTransactions(boolean walkTheListOfAllTransactions) {
		List<FinancialTransactionRecord> returnSuccsfulFinanancialTrancactions  = new ArrayList<FinancialTransactionRecord>();
		
		if (!walkTheListOfAllTransactions) {
			return this.getSuccessfulFinancialTransactions();
		}
		
		for (FinancialTransactionRecord transactionRecord : this.getSuccessfulFinancialTransactions()) {
			if (!transactionRecord.hasError()) {
				returnSuccsfulFinanancialTrancactions.add(transactionRecord);
			}
		}
		
		return returnSuccsfulFinanancialTrancactions;
	}
	public List<FinancialTransactionRecord> getFailedFinancialTransactions() {
		return failedFinancialTransactions;
	}

	public boolean hasFailures() {
		return allFailureListsEmpty();
	}

	public boolean hasFailureRecords() {
		return allFailureListsEmpty();
	}
	
	private boolean allFailureListsEmpty() {
		if (this.getAddNumOfFail() > 0 
				|| this.getParseNumOfFail() > 0 
				|| this.getTotalNumOfFail() > 0) {
			return true;
		}
		
		return !this.getFailedFinancialTransactions().isEmpty() && !this.getFailedAddTransactionTransactions().isEmpty() && !this.getFailedParseCsvTransactions().isEmpty();
	}
	
	public Boolean getDidAllRecordsFail() {
		if (this.getTotalNumOfRecordsProcessed() == 0 
				|| this.getTotalNumOfRecordsProcessed() == this.getTotalNumOfFail()) {
			return true;
		}

		return !this.getFailedFinancialTransactions().isEmpty() && this.getNumberOfFailureRecords() == this.getNumberOfAllRecords();
	}

	public Boolean getDidPartialRecordsFail() {
		if (this.hasFailures() 
				&& !this.getDidAllRecordsFail()) {
			return true;
		}

		return !this.getFailedFinancialTransactions().isEmpty() && this.getNumberOfFailureRecords() != this.getNumberOfAllRecords();
	}

	public Boolean getDidAllRecordsSucceed() {
		return !this.hasFailures();
	}
	
	public List<BaseResultsValidationError> validateResults() {
		// Always reset the list
		this.getValidationErrors().clear();
		
		List<BaseResultsValidationError> methodValidationErrors = new ArrayList<BaseResultsValidationError>();
		
		methodValidationErrors = this.validateNumberOfFailedRecords(this.getValidationErrors());
		methodValidationErrors = this.validateNumberOfSuccessRecords(this.getValidationErrors());
		methodValidationErrors = this.validateNumberOfProccessedRecords(this.getValidationErrors());
		
		return this.getValidationErrors();
	}
	
	public List<BaseResultsValidationError> validateNumberOfFailedRecords(List<BaseResultsValidationError> validationErrors) {
		BaseResultsValidationError validationError = null;
		List<CompareObject> compareObjectList = null;
		BaseResultsValidationError error = null;
		List<BaseResultsValidationError> returnValidationErrorList = new ArrayList<BaseResultsValidationError>();
		
		// validate # of add Transaction Failures 
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAddNumOfFail()", this.getAddNumOfFail());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAddTransactionRecordErrorList().size()", this.getAddTransactionRecordErrorList().size(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getFailedAddTransactionTransactions().size()", this.getFailedAddTransactionTransactions().size(), compareObjectList);
		
		validationError = this.validateMatchingValues("# of Add Transaction Failures don't match", compareObjectList, returnValidationErrorList, validationErrors);
		

		// validate # of parse csv Failures
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getParseNumOfFail()", this.getParseNumOfFail());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getParseCsvRecordErrorList().size()", this.getParseCsvRecordErrorList().size(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getFailedParseCsvTransactions().size()", this.getFailedParseCsvTransactions().size(), compareObjectList);
		
		validationError = this.validateMatchingValues("# of Parse Csv Failures don't match", compareObjectList, returnValidationErrorList, validationErrors);

		// validate # of totsl # Failures
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getTotalNumOfFail()", this.getTotalNumOfFail());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAddNumOfFail() + results.getParseNumOfFail()", this.getAddNumOfFail() + this.getParseNumOfFail());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getFailedFinancialTransactions().size()", this.getFailedFinancialTransactions().size());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getParseCsvRecordErrorList().size() + results.getAddTransactionRecordErrorList().size()", this.getParseCsvRecordErrorList().size() + this.getAddTransactionRecordErrorList().size(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getFailedAddTransactionTransactions().size() + results.getFailedParseCsvTransactions().size()", this.getFailedAddTransactionTransactions().size() + this.getFailedParseCsvTransactions().size(), compareObjectList);
		
		validationError = this.validateMatchingValues("Total # of Failures don't match", compareObjectList, returnValidationErrorList, validationErrors);
		
		return returnValidationErrorList;
	}
	

	public List<BaseResultsValidationError> validateNumberOfProccessedRecords(List<BaseResultsValidationError> validationErrors) {
		BaseResultsValidationError validationError = null;
		List<CompareObject> compareObjectList = null;
		BaseResultsValidationError error = null;
		List<BaseResultsValidationError> returnValidationErrorList = new ArrayList<BaseResultsValidationError>();
		
		// validate Totl # of records processed
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getTotalNumOfRecordsProcessed()", this.getTotalNumOfRecordsProcessed());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAllFinancialTransactions().size()", this.getAllFinancialTransactions().size(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getFailedFinancialTransactions().size() + results.getSuccessfulAddTransactionFinancialTransactions().size()", this.getFailedFinancialTransactions().size() + this.getSuccessfulAddTransactionFinancialTransactions().size());
		
		validationError = this.validateMatchingValues("# of Total Transactions Processed don't match", compareObjectList, returnValidationErrorList, validationErrors);
		
		return validationErrors;
	}


	public List<BaseResultsValidationError> validateNumberOfSuccessRecords(List<BaseResultsValidationError> validationErrors) {
		BaseResultsValidationError validationError = null;
		List<CompareObject> compareObjectList = null;
		BaseResultsValidationError error = null;
		List<BaseResultsValidationError> returnValidationErrorList = new ArrayList<BaseResultsValidationError>();
		
		// validate # of add Transaction successes 
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAddNumOfSuccess()", this.getAddNumOfSuccess());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getSuccessfulAddTransactionFinancialTransactions().size()", this.getSuccessfulAddTransactionFinancialTransactions().size(), compareObjectList);
		
		validationError = this.validateMatchingValues("# of Add Transaction Successes don't match", compareObjectList, returnValidationErrorList, validationErrors);
		
		// validate # of parse csv successes
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getParseNumOfSuccess()", this.getParseNumOfSuccess());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getSuccessfulParseCsvFinancialTransactions().size()", this.getSuccessfulParseCsvFinancialTransactions().size(), compareObjectList);
		
		
		validationError = this.validateMatchingValues("# of Parse Csv Successes don't match", compareObjectList, returnValidationErrorList, validationErrors);

		// validate # of totsl # successes
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getTotalNumOfSuccess()", this.getTotalNumOfSuccess());
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getAddNumOfSuccess()", this.getAddNumOfSuccess(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getSuccessfulFinancialTransactions().size()", this.getSuccessfulFinancialTransactions().size(), compareObjectList);
		compareObjectList = this.createNewCompareObjectAndAddToList("results.getSuccessfulAddTransactionFinancialTransactions().size()", this.getSuccessfulAddTransactionFinancialTransactions().size(), compareObjectList);
		
		validationError = this.validateMatchingValues("Total # of Successes don't match", compareObjectList, returnValidationErrorList, validationErrors);
		
		return validationErrors;
	}

	public List<CompareObject> createNewCompareObjectAndAddToList(String compareName, Object compoareValueFrom) {
		return this.createNewCompareObjectAndAddToList(compareName, compoareValueFrom, null);
	}
	
	public List<CompareObject> createNewCompareObjectAndAddToList(String compareName, Object compareValue, List<CompareObject> compareValueList) {
		if (compareValueList == null) {
			compareValueList = new ArrayList<CompareObject>();
		}
		
		CompareObject compareObject =  new CompareObject(compareName, compareValue);
		compareValueList.add(compareObject);
		
		return compareValueList;
	}
	
	public BaseResultsValidationError validateMatchingValues(String message, List<CompareObject> compareValueList, List<BaseResultsValidationError> validationErrors, List<BaseResultsValidationError> allVValidationErrors) {
		BaseResultsValidationError validationError = null;
		CompareObject  baseObjectToCompare = null;
		
		for (CompareObject compareObject : compareValueList) {
			if (baseObjectToCompare == null) {
				baseObjectToCompare = compareObject;
			}
			
			if (!compareObject.equals(baseObjectToCompare)) {
				validationError = new BaseResultsValidationError(message, compareValueList);
				validationErrors.add(validationError);
				allVValidationErrors.add(validationError);
			}
		}
		
		
		return  validationError;
	}

	public boolean hasValidationErrors(boolean runValidation) {
		if (runValidation) {
			this.validateResults();
		}
		
		return this.getValidationErrors().isEmpty();
	}
	
	public int getNumberOfFailureRecords() {
		return this.getFailedFinancialTransactions().size();
	}

	public int getNumberOfAllRecords() {
		return getAllFinancialTransactions().size();
	}
	
	public int getNumberOfSuccessfulRecords() {
		return getAllFinancialTransactions().size();
	}
	public List<FinancialTransactionRecord> getSuccessfulParseCsvFinancialTransactions() {
		return successfulParseCsvFinancialTransactions;
	}
	public List<FinancialTransactionRecord> getSuccessfulAddTransactionFinancialTransactions() {
		return successfulAddTransactionFinancialTransactions;
	}
	public List<FinancialTransactionRecord> getFailedParseCsvTransactions() {
		return failedParseCsvTransactions;
	}
	public List<FinancialTransactionRecord> getFailedAddTransactionTransactions() {
		return failedAddTransactionTransactions;
	}
	public List<BaseResultsValidationError> getValidationErrors() {
		return validationErrors;
	}
	
	public void cloneMinimal(BaseResults baseResults) {
		
	}
	
	public StringBuffer printResultsToBuffer(int offSet, StringBuffer buf) {
		return buf;
	}
}
