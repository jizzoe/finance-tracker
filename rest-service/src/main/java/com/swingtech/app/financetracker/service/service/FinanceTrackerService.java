package com.swingtech.app.financetracker.service.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.core.model.UploadFileInput;
import com.swingtech.app.financetracker.service.dao.FinanceTrackerDao;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.util.FinanceTrackerUtil;
import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.FileUtil;
import com.swingtech.common.core.util.JsonUtil;
import com.swingtech.common.core.util.Timer;
import com.swingtech.common.core.util.bean.BeanUtility;

@Service
public class FinanceTrackerService {
	public final static Logger logger = LoggerFactory.getLogger(FinanceTrackerService.class);;
	public final static String PROCESSED_REPORT_NAME_SUCCESS = "";
	public final static String PROCESSED_REPORT_NAME_FAIL_ALL = "";
	public final static String PROCESSED_REPORT_NAME_FAIL_PARTIAL = "";

	@Value("${application.finance-data.dir.initial-configuration-files}")
	public String financeDataInitConfigurationDir;

	@Value("${application.finance-data.dir.transaction-files.upload}")
	public String financeDataTransactionsUploadDir;

	@Value("${application.finance-data.dir.transaction-files.work}")
	public String financeDataTransactionsWorkDir;

	@Value("${application.finance-data.dir.transaction-files.processed-success}")
	public String financeDataTransactionsProcssedSuccesDir;

	@Value("${application.finance-data.dir.transaction-files.processed-failed-all}")
	public String financeDataTransactionsProcessedFaileAlldDir;

	@Value("${application.finance-data.dir.transaction-files.processed-failed-partial}")
	public String financeDataTransactionsProcessedFailedPartialDir;

	@Value("${application.finance-data.dir.transaction-files.reports}")
	public String financeDataTransactionsReportsDir;

	@Autowired
	public FinanceTrackerDao financeTrackerDao;
	
	public ParseTransactionResults processAndAddTransactionUploadFiles(String userName, List<UploadFileInput> uploadFileInputs) throws FinanceTrackerExcepton {
		ParseTransactionResults parseTransactionResults = null;
		
		for (UploadFileInput uploadFileInput : uploadFileInputs) {
			this.createNewTransactionFile(uploadFileInput);
		}
		
		parseTransactionResults = this.parseAndAddTransactions(userName);
		
		this.moveTransactionFilesToProcessed(parseTransactionResults);
		
		return parseTransactionResults;
	}

	private void moveTransactionFilesToProcessed(ParseTransactionResults parseTransactionResults) throws FinanceTrackerExcepton {
		for (TransactionSource transactionSource : parseTransactionResults.getAllRransactionSources()) {
			try {
				if (transactionSource.getProcessResults().getDidTransactionSourceFailToProcess() || 
						transactionSource.getProcessResults().getDidAllRecordsFail()) 
				{
					this.moveFileFromFolderToFolder(transactionSource.getFile(), financeDataTransactionsProcessedFaileAlldDir);
				}
				else if (transactionSource.getProcessResults().getDidPartialRecordsFail()) 
				{
					this.moveFileFromFolderToFolder(transactionSource.getFile(), financeDataTransactionsProcessedFailedPartialDir);
				}
				else {
					this.moveFileFromFolderToFolder(transactionSource.getFile(), financeDataTransactionsProcssedSuccesDir);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void moveFileFromFolderToFolder(File fromFileNameName, String toFolderName) throws FinanceTrackerExcepton {
		File toFolderFile = null;
		
		try {
			toFolderFile = FileUtil.getFileFromFileName(toFolderName);
			
			FileUtil.moveFileToFolder(fromFileNameName, toFolderFile);
		} catch (Exception e) {
			throw new FinanceTrackerExcepton("Error trying to move from file name:  '" + fromFileNameName.getAbsolutePath() + "' to folder:  '" + toFolderName + "'", e);
		}
	}
	
	private File createNewTransactionFile(UploadFileInput uploadFileInput) throws FinanceTrackerExcepton {
		File newFile = uploadFileInput.getFile();
		
		try {
			FileUtil.writeContentsToFile(newFile, uploadFileInput.getFileContents());
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("Error trying to create new file:  " + newFile.getAbsolutePath() + ".  Error Message:  " + e.getClass().getName() + ":  " + e.getMessage(), e);
		}
		
		return newFile;
	}

	public List<FinancialTransactionRecord> retrieveTransactions(LocalDateTime startDate, LocalDateTime endDate) {
		return financeTrackerDao.retrieveTransactions(startDate, endDate);
	}

	public boolean initFinanceTracker() throws FinanceTrackerExcepton {
		CsvProcessorConfiguration csvProcessorConfiguration = null;

		try {
			csvProcessorConfiguration = financeTrackerDao.retrieveCsvProcessorConfiguration();
	
			if (csvProcessorConfiguration == null) {
				System.out.println(
						"Just got csvProcessorConfiguration from data store and it is NULL.  Going to get one and Adding a new one");
			} else {
				System.out.println(
						"Just got csvProcessorConfiguration from data store and it is NOT null.  Success!  Printing below");
				System.out.println(JsonUtil.marshalObjectToJson(csvProcessorConfiguration));
			}
	
			if (csvProcessorConfiguration == null) {
				// if the csvProcessorConfiguration was null, then let's get one from
				// the file system and add it to data store
				csvProcessorConfiguration = FinanceTrackerUtil.getCsvProcessorConfiguration(financeDataInitConfigurationDir);
	
				if (csvProcessorConfiguration == null) {
					System.out.println("Just got csvProcessorConfiguration from file system and it is NULL.  Didn't get one from data store.  Couldn't load one from file.  I give up.  Error oute");
					throw new FinanceTrackerExcepton("Could not load csvProcessorConfiguration from config dir:  '" + financeDataInitConfigurationDir + "'.  This method returned null:  FinanceTrackerUtil.getCsvProcessorConfiguration(financeDataInitConfigurationDir);");
				} else {
					System.out.println(
							"Just got csvProcessorConfiguration from file system and it is NOT null.  Success!  Printing below");
					System.out.println(JsonUtil.marshalObjectToJson(csvProcessorConfiguration));
				}
	
				financeTrackerDao.saveCsvProcessorConfiguration(csvProcessorConfiguration);
			}
	
			System.out.println("\n\n\n************************* START - creating files ***************************************");
	
			// Now create the working directories if not already created.
			this.createNewDirectoryIfNotExist(financeDataTransactionsUploadDir);
			this.createNewDirectoryIfNotExist(financeDataTransactionsWorkDir);
			this.createNewDirectoryIfNotExist(financeDataTransactionsProcssedSuccesDir);
			this.createNewDirectoryIfNotExist(financeDataTransactionsProcessedFaileAlldDir);
			this.createNewDirectoryIfNotExist(financeDataTransactionsProcessedFailedPartialDir);
			this.createNewDirectoryIfNotExist(financeDataTransactionsReportsDir);
			
			System.out.println("************************* START - creating files ***************************************\n\n\n");
		}
		catch (FinanceTrackerExcepton fe) {
			throw fe;
		}
		catch (Exception e) {
			throw new FinanceTrackerExcepton("Error load csvProcessorConfiguration from config dir:  '" + financeDataInitConfigurationDir + "'.  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		return true;
	}

	private void createNewDirectoryIfNotExist(String fileName) throws FinanceTrackerExcepton {
		File file = this.getFileFromString(fileName);
		System.out.println("   creating new dir:  " + file.getAbsolutePath());
		this.createNewDirectoryIfNotExist(file);
	}

	private void createNewDirectoryIfNotExist(File file) throws FinanceTrackerExcepton {
		try {
			boolean fileCreated = FileUtil.createNewDirectoryIfNotExist(file);

			if (!fileCreated) {
				throw new FinanceTrackerExcepton(
						"Could not initialize FinanceTracker.  Could not create the transaction file folder:  "
								+ file.getAbsolutePath() + ".");
			}
		} catch (IOException e) {
			throw new FinanceTrackerExcepton(
					"Could not initialize FinanceTracker.  Error trying to create the transaction file folder:  "
							+ file.getAbsolutePath() + ".  Error:  " + e.getClass().getName() + " - " + e.getMessage(),
					e);
		}
	}

	private File getFileFromString(String fileName) throws FinanceTrackerExcepton {
		File returnFile = null;

		try {
			returnFile = FileUtil.getFileFromFileName(fileName);
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("Error trying to get a file from this file name:  " + fileName
					+ ".  Error:  " + e.getClass().getName() + " - " + e.getMessage(), e);
		}

		return returnFile;
	}

	public ParseTransactionResults parseAndAddTransactions(String userName) throws FinanceTrackerExcepton {
		ParseTransactionResults transactionResults = new ParseTransactionResults();
		Timer totalProcessTimer = new Timer();
		Timer parseCsvFilesTimer = new Timer();
		Timer addTransactionTimer = new Timer();

		totalProcessTimer.startTiming();

		parseCsvFilesTimer.startTiming();
		transactionResults = financeTrackerDao.parseTransactionRecords(transactionResults);

		parseCsvFilesTimer.stopTiming();
		transactionResults.setParseCsvFilesTimer(parseCsvFilesTimer);

		addTransactionTimer.startTiming();
		transactionResults = financeTrackerDao.saveTransactions(userName, transactionResults);
		addTransactionTimer.stopTiming();
		
		transactionResults.setAddTransactionTimer(addTransactionTimer);

		totalProcessTimer.stopTiming();

		transactionResults.setTotalProcessTimer(totalProcessTimer);
		

		transactionResults.finalizeResults();
		
		this.printParseFinancialTransactionResults(transactionResults);

		return transactionResults;
	}
	
	public void printParseFinancialTransactionResults(ParseTransactionResults transactionResults) {
		System.out.println("\n\n\n------->START - parseFinancialTransactions results:");
		System.out.println("   " + JsonUtil.marshalObjectToJson(transactionResults));
		System.out.println("<--------END - parseFinancialTransactions results:\n\n\n");
	}
}
