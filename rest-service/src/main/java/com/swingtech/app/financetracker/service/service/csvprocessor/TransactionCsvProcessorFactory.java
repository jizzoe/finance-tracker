package com.swingtech.app.financetracker.service.service.csvprocessor;

import java.io.File;

import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;

public class TransactionCsvProcessorFactory {
	public final static String TRANSACTION_SOURCE_SUNTRUST="suntrust";
	public final static String TRANSACTION_SOURCE_FIFTH_THIRD="fifththird";
	public final static String TRANSACTION_SOURCE_FSNB="fsnb";
	public final static String TRANSACTION_SOURCE_WALMART="walmart";

	public static TransactionCsvProcessor getTransactionCsvProcessor(File csvFile, TransactionSource transactionSource, CsvProcessorConfiguration csvProcessorCofiguration, ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		if (transactionSource.getTransactionSourceName().trim().equalsIgnoreCase(TRANSACTION_SOURCE_FIFTH_THIRD)) {
			return new FifthThirdTransactionCsvProcessor(csvFile, transactionSource, csvProcessorCofiguration, transactionResults);
		}

		if (transactionSource.getTransactionSourceName().trim().equalsIgnoreCase(TRANSACTION_SOURCE_SUNTRUST)) {
			return new SunTrustTransactionCsvProcessor(csvFile, transactionSource, csvProcessorCofiguration, transactionResults);
		}
		
		throw new FinanceTrackerExcepton("Could not find a csvProcessor for this transaction source:  '" + transactionSource + "'");
	}
}
