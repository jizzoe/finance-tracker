package com.swingtech.app.financetracker.service.service.csvprocessor;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;

import com.swingtech.app.financetracker.core.model.CategoryConfiguration;
import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfigurationListHolder;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseCsvRecordError;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionCategoryTag;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.common.tools.csvprocessor.core.CsvRecordProcessor;
import com.swingtech.common.tools.csvprocessor.core.CsvUtil;
import com.swingtech.common.tools.csvprocessor.error.ParseCsvProcessException;
import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParseSuccessRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public abstract class TransactionCsvProcessor implements CsvRecordProcessor {
	private final static CategoryConfiguration DEFAULT_CATEGORY_CONFIGURATION = new CategoryConfiguration();

	private CsvParserResults csvParseResults = null;

	private final File csvFile;
	private final CsvProcessorConfiguration csvProcessorCofiguration;
	private ParseTransactionResults transactionResults;
	private final TransactionSource transactionSource;

	public boolean didParseProcessFail() {
		return csvParseResults != null && csvParseResults.didParseProcessFail();
	}
	
	public CsvParseProcessError getCsvParseProcessError() {
		if (csvParseResults == null || csvParseResults.getCsvParseProcessError() == null) {
			return null;
		} else {
			return csvParseResults.getCsvParseProcessError();
		}
	}
	
	static {
		TransactionCategoryTag defaultTransactionCategory = new TransactionCategoryTag();
		defaultTransactionCategory.setFullName("<uncategorized>");
		defaultTransactionCategory.setName("<uncategorized>");

		DEFAULT_CATEGORY_CONFIGURATION.setMainCategory(defaultTransactionCategory);

		TransactionCategoryTag defaultTransactionSubCategory = new TransactionCategoryTag();
		defaultTransactionCategory.setFullName("UNCATEGORIZED");
		defaultTransactionCategory.setName("UNCATEGORIZED");

		DEFAULT_CATEGORY_CONFIGURATION.getSubCategories().add(defaultTransactionSubCategory);
	}

	TransactionCsvProcessor(File csvFile, TransactionSource transactionSource, CsvProcessorConfiguration csvProcessorCofiguration, ParseTransactionResults transactionResults) {
		this.csvFile = csvFile;
		this.csvProcessorCofiguration = csvProcessorCofiguration;
		this.transactionSource = transactionSource;
		this.transactionResults = transactionResults;
	}

	public ParseTransactionResults parseCsvFile(ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		if (transactionResults != null) {
			transactionResults = new ParseTransactionResults();
		}

		this.transactionResults = transactionResults;

		try {
			csvParseResults = CsvUtil.parseCsvFile(csvFile, this, false);
		}
		catch (Exception e) {
			throw new FinanceTrackerExcepton("Error trying to et Category Configuration list from csv file:  '" + csvFile + "'", e);
		} 
		
		return this.transactionResults;
	}
	
	public void addCsvProcessSuccess(FinancialTransactionRecord transaction, int transactionRecordIndex, ParseTransactionResults transactionResults) {
		transactionResults.addCsvProcessSuccess(transactionSource, transaction);
	}

	public void addParseCsvRecordError(CsvParseErrorRecord errorRecordtransaction) {
		this.addParseCsvRecordError(errorRecordtransaction, null);
	}
	
	public void addParseCsvRecordError(CsvParseErrorRecord errorRecord,  FinancialTransactionRecord transaction) {
		ParseCsvRecordError error = ParseCsvRecordError.bewParseCsvRecordError(transaction, this.getTransactionSource(), errorRecord);

		transactionResults.addParseCsvRecordError(transactionSource, transaction, error);
	}

	CategoryConfiguration getCategoryConfigurationFromDescription(FinancialTransactionRecord transactionRecord) {
		CsvProcessorConfigurationListHolder categoryConfigurationList = null;

		categoryConfigurationList = csvProcessorCofiguration.getConfigurationMap()
				.get(this.getTransactionSource().getTransactionSourceName());

		if (categoryConfigurationList == null || categoryConfigurationList.getConfigurationList() == null || categoryConfigurationList.getConfigurationList().isEmpty()) {
			return null;
		}

		for (CategoryConfiguration categoryConfiguration : categoryConfigurationList.getConfigurationList()) {
			CategoryConfiguration cloneCategoryConfiguration = null;

			for (TransactionCategoryTag transactionSubCategory : categoryConfiguration.getSubCategories()) {

				if (transactionRecord.getDescription().contains(transactionSubCategory.getFullName().trim())) {
					if (cloneCategoryConfiguration == null) {
						cloneCategoryConfiguration = categoryConfiguration.clone();
						cloneCategoryConfiguration.getSubCategories().clear();
					}

					cloneCategoryConfiguration.getSubCategories().add(transactionSubCategory);
				}

			}

			if (cloneCategoryConfiguration != null) {
				return cloneCategoryConfiguration;
			}
		}

		return DEFAULT_CATEGORY_CONFIGURATION;
	}

	protected String getTransactionId(FinancialTransactionRecord transaction) {
		StringBuffer transactionIdBuf = new StringBuffer();
		
		transactionIdBuf.append(transaction.getTransactionDateTime().toString(DateTimeFormat.forPattern("MMddyyyy")));
		transactionIdBuf.append(transaction.getDescription().replace(" ", ""));
		transactionIdBuf.append(Double.toString(transaction.getTransactionAmount()));
		if (transaction.getEndingBalance() != null) {
			transactionIdBuf.append(Double.toString(transaction.getEndingBalance()));
		}	
		
		return transactionIdBuf.toString().replace(".", "").replace(",", "").replace(" ", "").replace("-", "").replace(":", "").replace("/", "").replace("#", "").replace("*", "");
	}

	public abstract FinancialTransactionRecord getTransactionFromCsvRecord(CsvLineRecord csvLineRecord)
			throws FinanceTrackerExcepton;

	public File getCsvFile() {
		return csvFile;
	}

	public List<ParseCsvRecordError> getErrorList() {
		return transactionResults.getParseCsvRecordErrorList();
	}

	boolean isNullOrEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	public CsvProcessorConfiguration getCsvProcessorCofiguration() {
		return csvProcessorCofiguration;
	}

	public TransactionSource getTransactionSource() {
		return transactionSource;
	}

	public ParseTransactionResults getTransactionResults() {
		return transactionResults;
	}

	@Override
	public void handleParseCsvPrerProcess(Map<String, Object> parseProcessContextMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object handleParseCsvLine(CsvLineRecord csvLineRecord,
			Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) throws Exception 
	{
		FinancialTransactionRecord transaction = null;
		transaction = this.getTransactionFromCsvRecord(csvLineRecord);

		return transaction;
	}

	@Override
	public boolean handleParseLineSuccess(CsvParseSuccessRecord successRecord, CsvParserResults csvParseResults,
			Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) 
	{
		FinancialTransactionRecord transaction = (FinancialTransactionRecord) successRecord.getReturnObject();
		
		this.addCsvProcessSuccess(transaction, successRecord.getLineNumber(), this.transactionResults);
		
		return true;
	}

	@Override
	public boolean handleParseLineError(CsvParseErrorRecord errorRecord, CsvParserResults csvParseResults,
			Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) {

		FinancialTransactionRecord transaction = null;
		
		if (errorRecord.getReturnObject() != null) {
			transaction = (FinancialTransactionRecord) errorRecord.getReturnObject();
		}
	
		this.addParseCsvRecordError(errorRecord, transaction);
		
		return true;
	}

	@Override
	public void handleParseCsvPostProcess(CsvParserResults csvParseResults, Map<String, Object> parseProcessContextMap,
			boolean didParseProcessFail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePraseCsvProcessError(CsvParserResults csvParseResults, CsvParseProcessError csvParseProcessError,
			Map<String, Object> parseProcessContextMap) 
	{
		
	}

	public CsvParserResults getCsvParseResults() {
		return csvParseResults;
	}
	
}
