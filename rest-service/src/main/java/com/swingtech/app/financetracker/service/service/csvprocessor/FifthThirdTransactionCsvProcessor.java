package com.swingtech.app.financetracker.service.service.csvprocessor;

import java.io.File;
import java.util.List;
import java.util.Map;

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
import com.swingtech.common.tools.csvprocessor.error.ParseCsvRecordExceptin;

public class FifthThirdTransactionCsvProcessor extends TransactionCsvProcessor {
	private final static int FIELD_NAME_DATE = 0;
	private final static int FIELD_NAME_DESCRIPTION = 1;
	private final static int FIELD_NAME_CHECK_NUMBER = 2;
	private final static int FIELD_NAME_AMOUNT = 3;

	FifthThirdTransactionCsvProcessor(File csvFile, TransactionSource transactionSource, CsvProcessorConfiguration csvProcessorCofiguration, ParseTransactionResults transactionResults) {
		super(csvFile, transactionSource, csvProcessorCofiguration, transactionResults);
	}

	@Override
	public FinancialTransactionRecord getTransactionFromCsvRecord(CsvLineRecord csvLineRecord) throws FinanceTrackerExcepton {
		FinancialTransactionRecord transaction = new FinancialTransactionRecord();
		Double transactionAmount = null;
		CategoryConfiguration matchingCategoryConfiguration = null;

		try {
			this.validateFields(csvLineRecord.getCsvLineFieldList());
			
			transaction.setCheckNumber(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_CHECK_NUMBER));
			transaction.setDescription(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DESCRIPTION));
			transaction.setDescriptionSearchText(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DESCRIPTION));
			
			matchingCategoryConfiguration = this.getCategoryConfigurationFromDescription(transaction);
			
			transaction.setMainCategory(matchingCategoryConfiguration.getMainCategory());
			transaction.setSubCategories(matchingCategoryConfiguration.getSubCategories());
	
			transactionAmount = Double.parseDouble(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_AMOUNT));
			
			transaction.setTransactionAmount(transactionAmount);
			
			if (transactionAmount > 0) {
				transaction.setTransactionCreditAmount(transactionAmount);
				transaction.setTransactionDebitAmount(0.0);
			} else {
				transaction.setTransactionDebitAmount(-transactionAmount);
				transaction.setTransactionCreditAmount(0.0);
			}
			
			transaction.setTransactionDateTime(LocalDateTime.parse(csvLineRecord.getCsvLineFieldList().get(FIELD_NAME_DATE), DateTimeFormat.forPattern("MM/dd/yyyy")));
			transaction.setCsvLineRecord(csvLineRecord);
	
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

	private boolean validateFields(List<String> csvFieldList) throws FinanceTrackerExcepton {
		if (isNullOrEmpty(csvFieldList.get(FIELD_NAME_DATE)) || 
				isNullOrEmpty(csvFieldList.get(FIELD_NAME_DESCRIPTION)) || 
				isNullOrEmpty(csvFieldList.get(FIELD_NAME_AMOUNT))) {
			throw new FinanceTrackerExcepton("Validation failed.  A required field from the csv was null or empty.  "
					+ "Here are the values from the row:  "
					        + "date='" + csvFieldList.get(FIELD_NAME_DATE) + "'.  "
							+ "description='" + csvFieldList.get(FIELD_NAME_DESCRIPTION) + "'.  "
							+ "amount='" + csvFieldList.get(FIELD_NAME_AMOUNT) + "'");
			
		}
		return true;
	}

}
