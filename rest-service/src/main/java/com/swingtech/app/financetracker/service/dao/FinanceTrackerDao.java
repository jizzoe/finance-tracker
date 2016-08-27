package com.swingtech.app.financetracker.service.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.swingtech.app.financetracker.core.model.AddTransactionRecordError;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionCategoryTag;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.util.FinanceTrackerUtil;
import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.FileUtil;
import com.swingtech.common.core.util.HttpClientUtil;
import com.swingtech.common.core.util.JsonUtil;

@Service
public class FinanceTrackerDao {
	public static final String FINANCE_TRACKER_TRANSACTION_RECORDS_DOCUMENT_TYPE_INDEX_NAME = "st_financetracker";
	public static final String FINANCE_TRACKER_TRANSACTION_RECORDS_DOCUMENT_TYPE = "finance_transaction_record";

	public static final String FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE_INDEX_NAME = "st_financetracker_configuration";
	public static final String FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE = "category_configuration";
	public static final String FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_ID = "categoryConfiguration";
	
	@Value("${elasticsearch.connection.host}")
	public String elasticSearhUrlHost;
	
	@Value("${elasticsearch.connection.port}")
	public int elasticSearchPort;

	@Value("${application.finance-data.dir.transaction-files.work}")
	public String financeDataTransactionsWorkDir;
	
    @Autowired
    private ResourceLoader resourceLoader;
    
    CsvProcessorConfiguration cachedCsvProcessorConfiguration = null;

	
	public ParseTransactionResults parseTransactionRecords(ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		List<FinancialTransactionRecord> transactions = null;
		File transactionFolderFile = getFileFromString(financeDataTransactionsWorkDir);
		CsvProcessorConfiguration csvProcessorConfiguration = null;
		
		csvProcessorConfiguration = this.retrieveCsvProcessorConfiguration();
		
		if (csvProcessorConfiguration == null) {
			throw new FinanceTrackerExcepton("Could not parse transaction records.  Invalid state.  a CsvProcessorConfiguration does not exist in data store.");
		}
		
		System.out.println("transactionFolderFile:  " + transactionFolderFile.getAbsolutePath());

		transactionResults = FinanceTrackerUtil.parseFinancialTransactionRecords(csvProcessorConfiguration, transactionFolderFile, transactionResults);
		
		return transactionResults;
	}
	
	private File getFileFromString(String fileName) throws FinanceTrackerExcepton {
		File returnFile = null;
		
		try {
			returnFile = FileUtil.getFileFromFileName(fileName, true);
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("Error trying to get a file from this file name:  " + fileName + ".  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		return returnFile;
	}
	
	public ParseTransactionResults saveTransactions(String userName, ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		List<FinancialTransactionRecord> transactions = null;
		
		if (transactionResults == null || transactionResults.getAllRransactionSources().isEmpty()) {
			throw new FinanceTrackerExcepton("Invalid request.  transactionResults sent in was null or empty");
		}
		
		for (TransactionSource transactionSource : transactionResults.getAllRransactionSources()) {
			transactions = transactionSource.getProcessResults().getAllFinancialTransactions();
			
			if (transactions == null || transactions.isEmpty()) {
				continue;
			}

			transactionSource.getProcessResults().getAddTransactionTimer().startTiming();
			
			try {
				int transactionRecordIndex = 0;
				
				for (FinancialTransactionRecord transaction : transactions) {
					transactionRecordIndex++;
					
					try {
						this.saveTransaction(userName, transactionSource, transaction, transactionRecordIndex, transactionResults);
					} catch(Exception e) {
						// if an exception is thrown just continue the loop.  The saveTransaction() method will correctly handle exceptions and log errors.
						// just skip to the next record.
						continue;
					}
				}
			} finally {
				transactionSource.getProcessResults().getAddTransactionTimer().stopTiming();
			}
			
			transactionResults.setDidAddTransactions(true);
		}
		
		return transactionResults;
	}

	public void saveTransaction(String userName, TransactionSource transactionSource, FinancialTransactionRecord transaction, int transactionRecordIndex, ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		String url = "http://" + elasticSearhUrlHost + ":" + elasticSearchPort + "/" + FINANCE_TRACKER_TRANSACTION_RECORDS_DOCUMENT_TYPE_INDEX_NAME + "/" + FINANCE_TRACKER_TRANSACTION_RECORDS_DOCUMENT_TYPE + "/" + transaction.getTransactionId();
		String requestBody = null;
		HttpResponse response = null;
		String responseBody = null;
		
		try {
			
			requestBody = JsonUtil.marshalObjectToJson(transaction);
			
			System.out.println("\n\nSending POST Request for addTransaction:  ");
			System.out.println("   url:  " + url);
			System.out.println("   request length:  " + requestBody.length());
			System.out.println("   request body:  " + requestBody);
			
			try {
				response = HttpClientUtil.sendPostMessage(url, requestBody);
		        responseBody = EntityUtils.toString(response.getEntity());
			} 
			catch (Exception e) {
				String errorMessage = "error sending the add transaction request to elastic search for transactionId = '" + transaction.getTransactionId() + "'. Error:  " + ErrorUtil.getErrorMessageFromException(e);
				this.addAddTransactionFailToResults(transactionSource, transaction, transactionResults, transactionRecordIndex, null, errorMessage, url, requestBody, null, null);
				
				throw new FinanceTrackerExcepton(errorMessage, e);
			}
	
			System.out.println("\n\nRecieved POST Response for addTransaction:  ");
			System.out.println("   Status Code:  " + response.getStatusLine().getStatusCode());
			System.out.println("   response length:  " + response.getEntity().getContentLength());
			System.out.println("   response body:  " + responseBody);
			
			if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299) {
				String errorMessage = "Error trying to add the transaction id:  " + transaction.getTransactionId() + ".  The index call to elastic search failed with response code:  '" + response.getStatusLine().getStatusCode() + " '";
				this.addAddTransactionFailToResults(transactionSource, transaction, transactionResults, transactionRecordIndex, null, errorMessage, url, requestBody, responseBody, response.getStatusLine().getStatusCode());
				
				throw new FinanceTrackerExcepton(errorMessage + ".  The index call to elastic search failed.  Status code:  " + response.getStatusLine().getStatusCode() + ".  URL sent to:  '" + url + "'.  Request Body sent:  '" + requestBody + "'.  Response Body returned:  '" + responseBody + "'");
			}
		}
		catch (Exception e) {
			String errorMessage = "unkown error trying to save transaction request to elastic search for transactionId = '" + transaction.getTransactionId() + "'. Error:  " + ErrorUtil.getErrorMessageFromException(e);
			this.addAddTransactionFailToResults(transactionSource, transaction, transactionResults, transactionRecordIndex, null, errorMessage, url, requestBody, null, null);
			
			throw new FinanceTrackerExcepton(errorMessage, e);
		}
		
		// if that succeeded, then log it as a success:
		this.addAddTransactionSuccessToResults(transactionSource, transaction, transactionRecordIndex, transactionResults);
	}
	
	public boolean saveCsvProcessorConfiguration(CsvProcessorConfiguration csvProcessorConfiguration) throws FinanceTrackerExcepton {
		String url = "http://" + elasticSearhUrlHost + ":" + elasticSearchPort + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE_INDEX_NAME + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_ID;
		String requestBody = null;
		HttpResponse response = null;
		String responseBody = null;
		
		requestBody = JsonUtil.marshalObjectToJson(csvProcessorConfiguration);
		
		System.out.println("\n\nSending POST Request for addTransaction:  ");
		System.out.println("   url:  " + url);
		System.out.println("   request length:  " + requestBody.length());
		System.out.println("   request body:  " + requestBody);
		
		try {
			response = HttpClientUtil.sendPostMessage(url, requestBody);
	        responseBody = EntityUtils.toString(response.getEntity());
		} 
		catch (Exception e) {
			String errorMessage = "error sending the add transaction request to elastic search for csvProcessorConfiguration = '" + csvProcessorConfiguration;
			
			throw new FinanceTrackerExcepton(errorMessage, e);
		}

		System.out.println("\n\nRecieved POST Response for addTransaction:  ");
		System.out.println("   Status Code:  " + response.getStatusLine().getStatusCode());
		System.out.println("   response length:  " + response.getEntity().getContentLength());
		System.out.println("   response body:  " + responseBody);
		
		if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299) {
			String errorMessage = "Error trying to add the csvProcessorConfiguration id:  " + csvProcessorConfiguration + ".  The index call to elastic search failed with response code:  '" + response.getStatusLine().getStatusCode() + " '";
			
			throw new FinanceTrackerExcepton(errorMessage + ".  The index call to elastic search failed.  Status code:  " + response.getStatusLine().getStatusCode() + ".  URL sent to:  '" + url + "'.  Request Body sent:  '" + requestBody + "'.  Response Body returned:  '" + responseBody + "'");
		}
		
		cachedCsvProcessorConfiguration = csvProcessorConfiguration;
		
		return true;
	}

	public CsvProcessorConfiguration retrieveCsvProcessorConfiguration() throws FinanceTrackerExcepton {
		CsvProcessorConfiguration csvProcessorConfiguration = null;
		String url = null;
		HttpResponse response = null;
		String responseBody = null;
		String esConfigResponseJsonPath = "$._source";
		Object parsedResultJsonDocument = null;
		
		if (cachedCsvProcessorConfiguration != null) {
			return cachedCsvProcessorConfiguration;
		}
		
		url = "http://" + elasticSearhUrlHost + ":" + elasticSearchPort + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE_INDEX_NAME + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_TYPE + "/" + FINANCE_TRACKER_CATEGORY_CONFIGURATION_DOCUMENT_ID;

		System.out.println("\n\nSending GET Request for retrieveCsvProcessorConfiguration:  ");
		System.out.println("   url:  " + url);
		
		try {
			response = HttpClientUtil.sendGetMessage(url);
	        responseBody = EntityUtils.toString(response.getEntity());
		} 
		catch (Exception e) {
			throw new FinanceTrackerExcepton("Error trying to get CsvProcessorConfiguration from this url:  " + url, e);
		}

		System.out.println("\n\nRecieved GET Response for get retrieveCsvProcessorConfiguration:  ");
		System.out.println("   Status Code:  " + response.getStatusLine().getStatusCode());
		System.out.println("   Response length:  " + response.getEntity().getContentLength());
		System.out.println("   Response body:  " + responseBody);
		
		if (response.getStatusLine().getStatusCode() == 404) {
			// 404.  Not found.  Return null to indicate document does not exist
			return null;
		}
		
		// first check if we got success http status
		if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299) {
			throw new FinanceTrackerExcepton("Error trying to get CsvProcessorConfiguration from elastic search:  The get call to elastic search failed.  Status code:  " + response.getStatusLine().getStatusCode() + ".  URL sent to:  '" + url + "'.  Response Body returned:  '" + responseBody + "'");
		}
		
		// parse the json document since we'll be making 2 jsonPath calls and don't want to re-parse everytime.
		parsedResultJsonDocument = JsonUtil.parseJson(responseBody);
		
		// Ok, we're good.  Now we can get the CsvProcessorConfiguration from the result.
		csvProcessorConfiguration = JsonUtil.unmarshalJsonToObjectUsingJsonPath(parsedResultJsonDocument, esConfigResponseJsonPath, CsvProcessorConfiguration.class);
        
		return csvProcessorConfiguration;
	}
	
	private void addAddTransactionSuccessToResults(TransactionSource transactionSource, FinancialTransactionRecord transaction, int transactionRecordIndex, ParseTransactionResults transactionResults) {
		transactionResults.addAddTransactionSuccessToResults(transactionSource, transaction);
	}
	

	private void addAddTransactionFailToResults(TransactionSource transactionSource, FinancialTransactionRecord transaction, ParseTransactionResults transactionResults, int transactionRecordIndex, Exception e, String errorMessage, String url, String requestBody, String responseBody, Integer responseStatusCode) {
		AddTransactionRecordError error = AddTransactionRecordError.newAddTransactionRecordError(transactionSource, e, errorMessage, transaction, transactionRecordIndex, url, requestBody, responseStatusCode, responseBody);

		transactionResults.addAddTransactionError(transactionSource, transaction, error);
	}
	
	public List<FinancialTransactionRecord> retrieveTransactions(LocalDateTime startDate, LocalDateTime endDate) {
		List<FinancialTransactionRecord> transactionList = new ArrayList<FinancialTransactionRecord>();
		FinancialTransactionRecord transaction = null;
		String description = null;
		
		transaction = new FinancialTransactionRecord();
		
		description = "WALT DISNEY PA   EDI PYMNTS - " + elasticSearchPort;
		
		transaction.setDescription(description);
		transaction.setDescriptionSearchText(description);
		transaction.setEndingBalance(100.00);
		transaction.setMainCategory(this.getTransactionCategoryFromDescription(description));
		transaction.setTransactionAmount(-233.00);
		transaction.setTransactionCreditAmount(0.0);
		transaction.setTransactionDateTime(new LocalDateTime());
		transaction.setTransactionDebitAmount(200.00);
		
		transactionList.add(transaction);

		
		transaction = new FinancialTransactionRecord();
		
		description = "WALT DISNEY PA   EDI PYMNTS";
		
		transaction.setDescription(description);
		transaction.setDescriptionSearchText(description);
		transaction.setEndingBalance(100.00);
		transaction.setMainCategory(this.getTransactionCategoryFromDescription(description));
		transaction.setTransactionAmount(-233.00);
		transaction.setTransactionCreditAmount(0.0);
		transaction.setTransactionDateTime(new LocalDateTime());
		transaction.setTransactionDebitAmount(200.00);
		
		transactionList.add(transaction);

		
		transaction = new FinancialTransactionRecord();
		
		description = "WALT DISNEY PA   EDI PYMNTS";
		
		transaction.setDescription(description);
		transaction.setDescriptionSearchText(description);
		transaction.setEndingBalance(100.00);
		transaction.setMainCategory(this.getTransactionCategoryFromDescription(description));
		transaction.setTransactionAmount(-233.00);
		transaction.setTransactionCreditAmount(0.0);
		transaction.setTransactionDateTime(new LocalDateTime());
		transaction.setTransactionDebitAmount(200.00);
		
		transactionList.add(transaction);
		
		return transactionList;
	}
	
	public TransactionCategoryTag getTransactionCategoryFromDescription(String descrption) {
		TransactionCategoryTag category = new TransactionCategoryTag();
		
		category.setName("Eat Out");
		category.setFullName("Eat Out - Restaurant - Big");
		
		return category;
	}
}
