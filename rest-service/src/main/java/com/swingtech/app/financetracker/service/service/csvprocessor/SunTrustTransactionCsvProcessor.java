package com.swingtech.app.financetracker.service.service.csvprocessor;

import java.io.File;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import com.swingtech.app.financetracker.core.model.CategoryConfiguration;
import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseCsvRecordError;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.common.core.util.ErrorUtil;

public class SunTrustTransactionCsvProcessor extends TransactionCsvProcessor {
	private final static int FIELD_NAME_DATE = 0;
	private final static int FIELD_NAME_DESCRIPTION = 1;
	private final static int FIELD_NAME_CHECK_NUMBER = 2;
	private final static int FIELD_NAME_DEBIT = 3;
	private final static int FIELD_NAME_CREDIT = 4;
	private final static int FIELD_NAME_RUNNING_BALANCE = 5;

	SunTrustTransactionCsvProcessor(File csvFile, TransactionSource transactionSource, CsvProcessorConfiguration csvProcessorCofiguration, ParseTransactionResults transactionResults) {
		super(csvFile, transactionSource, csvProcessorCofiguration, transactionResults);
	}

	@Override 
	public FinancialTransactionRecord getTransactionFromCsvRecord(CsvLineRecord csvLineRecord) throws FinanceTrackerExcepton {
		FinancialTransactionRecord transaction = new FinancialTransactionRecord();
		Double debitAmount = null;
		Double creditAmount = null;
		Double transactionAmount = null;
		Double runningBalance = null;
		CategoryConfiguration matchingCategoryConfiguration = null;
		
		try {
			this.validateFields(csvLineRecord.getCsvLineFieldList());
			
			transaction.setCheckNumber(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_CHECK_NUMBER));
			transaction.setDescription(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DESCRIPTION));
			transaction.setDescriptionSearchText(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DESCRIPTION));
			
			matchingCategoryConfiguration = this.getCategoryConfigurationFromDescription(transaction);
			
			transaction.setMainCategory(matchingCategoryConfiguration.getMainCategory());
			transaction.setSubCategories(matchingCategoryConfiguration.getSubCategories());
	
			debitAmount = Double.parseDouble(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DEBIT));
			creditAmount = Double.parseDouble(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_CREDIT));
			runningBalance = Double.parseDouble(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_RUNNING_BALANCE));
			
			if (debitAmount > 0.0) {
				transactionAmount = -debitAmount;
			} else if (creditAmount > 0) {
				transactionAmount = creditAmount;
			} else {
				transactionAmount = 0.0;
			}
			
			transaction.setTransactionAmount(transactionAmount);
			transaction.setTransactionCreditAmount(creditAmount);
			transaction.setTransactionDebitAmount(debitAmount);
			transaction.setEndingBalance(runningBalance);
			transaction.setCsvLineRecord(csvLineRecord);
			
			transaction.setTransactionDateTime(LocalDateTime.parse(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DATE), DateTimeFormat.forPattern("MM/dd/yyyy")));
	
			transaction.setTransactionId(this.getTransactionId(transaction));
		}
		catch (Exception e) {
			String errorMessage = "Error trying to getTransaction from csvSourceLine.  An excption was throown:  " + csvLineRecord.getCsvLineString() + ".  Error:  " + ErrorUtil.getErrorMessageFromException(e);
			throw new FinanceTrackerExcepton(errorMessage, e);
		}
		

		if (transaction.getAddTransactionError() != null) {
			String errorMessage = "Error trying to getTransaction from csvSourceLine.  The transaction returned was null:  " + csvLineRecord.getCsvLineString();
			throw new FinanceTrackerExcepton(errorMessage);
		}
		
		return transaction;
	}

	private boolean validateFields(List<String> csvLineFieldList) throws FinanceTrackerExcepton {
		if (isNullOrEmpty(csvLineFieldList.get(FIELD_NAME_DATE)) || 
				isNullOrEmpty(csvLineFieldList.get(FIELD_NAME_DESCRIPTION)) || 
				isNullOrEmpty(csvLineFieldList.get(FIELD_NAME_DEBIT))|| 
				isNullOrEmpty(csvLineFieldList.get(FIELD_NAME_CREDIT))) {
			throw new FinanceTrackerExcepton("Validation failed.  A required field from the csv was null or empty.  "
					+ "Here are the values from the row:  "
					        + "date='" + csvLineFieldList.get(FIELD_NAME_DATE) + "'.  "
							+ "description='" + csvLineFieldList.get(FIELD_NAME_DESCRIPTION) + "'.  "
							+ "debit='" + csvLineFieldList.get(FIELD_NAME_DEBIT) + "'.  "
							+ "credit='" + csvLineFieldList.get(FIELD_NAME_CREDIT) + "'");
			
		}
		return true;
	}
	
}
