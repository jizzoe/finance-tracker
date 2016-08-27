package com.swingtech.app.financetracker.service.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.core.io.Resource;

import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.ParseTransactionSourceError;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.service.csvprocessor.CategoryConfigurationProcessor;
import com.swingtech.app.financetracker.service.service.csvprocessor.TransactionCsvProcessor;
import com.swingtech.app.financetracker.service.service.csvprocessor.TransactionCsvProcessorFactory;
import com.swingtech.common.core.util.FileUtil;
import com.swingtech.common.core.util.JsonUtil;
import com.swingtech.common.core.util.Timer;
import com.swingtech.common.tools.csvprocessor.error.ParseCsvProcessException;
import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public class FinanceTrackerUtil {
	private static final String CONFIG_FILE_PREFIX = "category_configuration";
	private static final String TRANSACTIONS_FILE_PREFIX = "transactions";

	public static Map<String, Resource> getCategoryResourceMap(String folderName) throws FinanceTrackerExcepton {
        Map<String, Resource> categoryFileMap = new HashMap<String, Resource>();
        List<Resource> configFileList = null;
        String fileNameWithoutExtension = null;
        String[] fileParts = null;
        String transactionSource = null;
        
        configFileList = getConfigurationResources(folderName);
        
        for (Resource file : configFileList) {
        	fileNameWithoutExtension = getFileNameWithoutExtension(file.getFilename());
        	
        	fileParts = fileNameWithoutExtension.split("-");
        	
        	transactionSource = fileParts[1];
        	
        	categoryFileMap.put(transactionSource, file);
        }
		
        return categoryFileMap;
	}

	public static CsvProcessorConfiguration getCsvProcessorConfiguration(String folderName) throws FinanceTrackerExcepton {
        CsvProcessorConfiguration csvProcessorConfiguration = null;
        
        Map<String, Resource> categoryFileMap = getCategoryResourceMap(folderName);
        
		CategoryConfigurationProcessor processor = new CategoryConfigurationProcessor();
		
		csvProcessorConfiguration = processor.parseConfigurationFiles(categoryFileMap);
		
		return csvProcessorConfiguration;
	}
	
	public static ParseTransactionResults parseFinancialTransactionRecords(CsvProcessorConfiguration csvProcessorConfiguration, File transactionFolder, ParseTransactionResults transactionResults) throws FinanceTrackerExcepton {
		List<FinancialTransactionRecord> fileFinancialTransactions = null;
		List<File> transactionFiles = null;
        TransactionCsvProcessor csvProcessor = null;
        TransactionSource transactionSource = null;
        Timer parseCsvFilesTimer = null;

		if (transactionResults == null) {
			transactionResults = new ParseTransactionResults();
		}

        System.out.println("csvProcessorConfiguration:  " + JsonUtil.marshalObjectToJson(csvProcessorConfiguration));
		
		transactionFiles = getTransactionFilesToProcess(transactionFolder);
		
		for (File transactionFile : transactionFiles) {
	        String errorMessage = "";
			
			try {
				transactionSource = getTransactionSourceFromTransactionFile(transactionFile);

				parseCsvFilesTimer = transactionSource.getProcessResults().getParseCsvFilesTimer();

				parseCsvFilesTimer.startTiming();
				
				if (transactionSource.getProcessResults().getFailToProcessError() != null) {
					errorMessage = "Error ";
					addParseTransactionSourceError(transactionSource, transactionFile, transactionSource.getProcessResults().getFailToProcessError(), transactionResults, null, parseCsvFilesTimer);
					continue;
				}
			} catch (Exception caughtException) {
				errorMessage = "Error tryng to get the TransactionSource from transaction file name:  " + transactionFile.getAbsolutePath() + "'.  Error:  " + caughtException.getClass() + " - "  + caughtException.getMessage();
				addParseTransactionSourceError(transactionSource, transactionFile, caughtException, errorMessage, transactionResults, csvProcessor, parseCsvFilesTimer);
				continue;
			}

			try {
				csvProcessor = TransactionCsvProcessorFactory.getTransactionCsvProcessor(transactionFile, transactionSource, csvProcessorConfiguration, transactionResults);
	
				transactionResults = csvProcessor.parseCsvFile(transactionResults);
			} catch (Exception caughtException) { 
				errorMessage = "Error tryng to get the TransactionSource from transaction file name:  " + transactionFile.getAbsolutePath() + "'.  Error:  " + caughtException.getClass() + " - "  + caughtException.getMessage();
				addParseTransactionSourceError(transactionSource, transactionFile, caughtException, errorMessage, transactionResults, csvProcessor, parseCsvFilesTimer);
				continue;
			}
			
			if (csvProcessor.didParseProcessFail()) {
				String errorMesage = "trhe csv process filed for some reason!";
				addParseTransactionSourceError(transactionSource, transactionFile, null, errorMessage, transactionResults, csvProcessor, parseCsvFilesTimer);
				continue;
			}
			
			addParseTransactionSourceSuccess(transactionSource, transactionResults, csvProcessor, parseCsvFilesTimer);
		}

		transactionResults.setDidParseCsvFiles(true);
		
		return transactionResults;
	}
	
	private static void addParseTransactionSourceError(TransactionSource transactionSource, File transactionFile, Exception caughtException, String message, ParseTransactionResults transactionResults, TransactionCsvProcessor csvProcessor, Timer parseCsvFilesTimer) {
		ParseTransactionSourceError error = null;

		if (transactionSource == null) {
			transactionSource = new TransactionSource();
			transactionSource.setFile(transactionFile);
		}
		
		error = transactionSource.getProcessResults().getFailToProcessError();
		
		if (error == null) {
			error = createNewParseTransactionSourceError(transactionSource, transactionFile, caughtException, message);
		}
		
		error.setErrorMessage(message);
		error.setException(caughtException);
		
		addParseTransactionSourceError(transactionSource, transactionFile, error, transactionResults, csvProcessor, parseCsvFilesTimer);
	}
	
	private static ParseTransactionSourceError createNewParseTransactionSourceError(TransactionSource transactionSource, File transactionFile, Exception caughtException, String message) {
		ParseTransactionSourceError error = null;
		
		error = ParseTransactionSourceError.newParseTransactionSourceError(transactionSource, caughtException, message, transactionFile);;
		transactionSource.getProcessResults().setFailToProcessError(error);
		
		return error;
	}
	
	private static CsvParseProcessError getCsvProcessError(TransactionCsvProcessor csvProcessor) {
		if (csvProcessor == null) {
			return null;
		} else {
			return csvProcessor.getCsvParseProcessError();
		}
		
	}

	private static void addParseTransactionSourceError(TransactionSource transactionSource, File transactionFile, ParseTransactionSourceError error, ParseTransactionResults transactionResults, TransactionCsvProcessor csvProcessor, Timer parseCsvFilesTimer) {
		if (transactionSource == null) {
			transactionSource = new TransactionSource();
			transactionSource.setFile(transactionFile);
		}

		error.setTransactionSource(transactionSource);
		
		if (csvProcessor != null) {
			CsvParseProcessError csvParseProcessError = csvProcessor.getCsvParseProcessError();
			CsvParserResults parseCsvFileResults = csvProcessor.getCsvParseResults();

			if (csvParseProcessError != null) {
				error.setCsvParseRrror(csvParseProcessError);
			}

			if (parseCsvFileResults != null) {
				transactionSource.getProcessResults().setParseCsvFileResults(parseCsvFileResults);
			}
			
		} 
		
		transactionSource.getProcessResults().setFailedToProcessTransactionSource(true);
		transactionSource.getProcessResults().setFailToProcessError(error);
		
		if (parseCsvFilesTimer != null) {
			parseCsvFilesTimer.stopTiming();
		}
		
		transactionResults.addParseTransactionSourceError(transactionSource);
	}
	
	private static void addParseTransactionSourceSuccess(TransactionSource transactionSource, ParseTransactionResults transactionResults, TransactionCsvProcessor csvProcessor, Timer parseCsvFilesTimer) {
		if (parseCsvFilesTimer != null) {
			parseCsvFilesTimer.stopTiming();
		}

		if (csvProcessor != null && csvProcessor.getCsvParseResults() != null) {
			transactionSource.getProcessResults().setParseCsvFileResults(csvProcessor.getCsvParseResults());
		}

		transactionResults.addParseTransactionSourceSuccess(transactionSource);
	}
	
	public static TransactionSource getTransactionSourceFromTransactionFileName(String folderName, String transactionFileName) throws FinanceTrackerExcepton {
		File transactionFile = null;
		String fileAbsouteName = folderName + "/" + transactionFileName;
		
		try {
			transactionFile = FileUtil.getFileFromFileName(fileAbsouteName);
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("error trying to get file from file name:  '" + fileAbsouteName + "'", e);
		}
		
		return getTransactionSourceFromTransactionFile(transactionFile);
	}

	public static TransactionSource getTransactionSourceFromTransactionFile(File transactionFile) {
		TransactionSource transactionSource = new TransactionSource();
		String transactionFileNameWithoutExtension = null;
		String transactionFileName = null;

    	transactionSource.setFile(transactionFile);
		
    	try {
			transactionFileName = transactionFile.getName();
			
			transactionFileNameWithoutExtension = getFileNameWithoutExtension(transactionFileName); 
			
	        String[] transactionSourceFields = null;
	
	    	transactionSourceFields = transactionFileNameWithoutExtension.split("-");
	    	
	    	if (validateGetTransactionSourceFromTransactionFile(transactionFileNameWithoutExtension)) {
		    	transactionSource.setTransactionSourceName(transactionSourceFields[1]);
		    	transactionSource.setUserName(transactionSourceFields[2]);
		    	transactionSource.setAccountName(transactionSourceFields[3]);
		    	transactionSource.setDateRangeString(transactionSourceFields[4] + " - " + transactionSourceFields[5]);
		    	transactionSource.setStartDateRange(LocalDateTime.parse(transactionSourceFields[4], DateTimeFormat.forPattern("MM.dd.yyyy")));
		    	transactionSource.setEndDateRange(LocalDateTime.parse(transactionSourceFields[5], DateTimeFormat.forPattern("MM.dd.yyyy")));
		    	transactionSource.setFileName(transactionFileName);
	    	} else {
	    		ParseTransactionSourceError error = createNewParseTransactionSourceError(transactionSource, transactionFile, null, "Invalid transaction file name.  Could not build a transactionSource from the transaction file name:  '" + transactionFileNameWithoutExtension + "'");
	    		transactionSource.getProcessResults().setFailToProcessError(error);
	    	}
    	} catch(Exception e) {
    		ParseTransactionSourceError error = createNewParseTransactionSourceError(transactionSource, transactionFile, e, "Error trying to build a transactionSource from the transaction file name:  '" + transactionFileNameWithoutExtension + "'.  Error:  " + e.getClass().getName() + "' - " + e.getMessage() + "'");
    		transactionSource.getProcessResults().setFailToProcessError(error);
    	}
    	
		return transactionSource;
	}
	
	public static boolean validateGetTransactionSourceFromTransactionFile(String transactionFileNameWithoutExtension) {
        String[] transactionSourceFields = null;

    	transactionSourceFields = transactionFileNameWithoutExtension.split("-");
    	
		return transactionSourceFields != null && transactionSourceFields.length == 6;
	}
	
	public static TransactionSource getTransactionSourceFromRequestParams(
			String folderName, 
    		String transactionSourceName,
    		String userName,
    		String accountName,
    		String startDateRangeString,
    		String endDateRangeString,
    		String originalFileName) throws FinanceTrackerExcepton 
	{
		File transactionFile = null;
		String fileAbsouteName = null;
		TransactionSource transactionSource = new TransactionSource();
		String dateRangeString = startDateRangeString + "-" + endDateRangeString;
		String transactionFileName = "transactions-" + transactionSourceName + "-" + userName + "-" + accountName + "-" + startDateRangeString + "-" + endDateRangeString + ".csv";
		
		transactionSource.setAccountName(accountName);
		transactionSource.setDateRangeString(dateRangeString);
		transactionSource.setEndDateRange(LocalDateTime.parse(endDateRangeString, DateTimeFormat.forPattern("MM.dd.yyyy")));
		transactionSource.setFileName(originalFileName);
		transactionSource.setStartDateRange(LocalDateTime.parse(startDateRangeString, DateTimeFormat.forPattern("MM.dd.yyyy")));
		transactionSource.setTransactionSourceName(transactionSourceName);
		transactionSource.setUserName(userName);
		transactionSource.setFileName(transactionFileName);
		
		fileAbsouteName = folderName + "/" + transactionFileName;

		try {
			transactionFile = FileUtil.getFileFromFileName(fileAbsouteName);
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("error trying to get file from file name:  '" + fileAbsouteName + "'", e);
		}
		
		transactionSource.setFile(transactionFile);
		
		return transactionSource;
	}

	public static String getFileNameWithoutExtension(File file) {
		return getFileNameWithoutExtension(file.getName());
	}

	public static String getFileNameWithoutExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		
		if (index < 0) {
			return fileName;
		} else {
			return fileName.substring(0, index);
		}
	}

	public static String getFileNameExtension(File file) {
		int index = file.getName().lastIndexOf(".");
		
		if (index < 0) {
			return null;
		} else {
			return file.getName().substring(index + 1);
		}
	}
	
	public static List<File> getFilesForPrefix(String filePrefix, File folderFile) throws FinanceTrackerExcepton {
		List<File> configFileList = new ArrayList<File>();
		File[] configFileArr = null;
		
		validateFinanceTrackerFilesFolder(folderFile);
		
		configFileArr = folderFile.listFiles();
		
		for (File file : configFileArr) {
			if (file.isFile() && file.getName().startsWith(filePrefix)) {
				configFileList.add(file);
			}
		}
		
		return configFileList;
	}

	public static List<Resource> getConfigurationResources(String folderName) throws FinanceTrackerExcepton {
		Resource folderResource = null;
		List<Resource> configResourceList = null;
		List<Resource> returnConfigResourceList = new ArrayList<Resource>();
		File[] configFileArr = null;
		String fileSearchString = folderName + "/*.json";
		
		validateFinanceTrackerFolder(folderName);
		
		try {
			configResourceList = FileUtil.getResourceListForParentFolder(fileSearchString);
		} catch (IOException e) {
			throw new FinanceTrackerExcepton("error trying to get category config files for this folder search string:  '" + fileSearchString + "'", e);
		}
		
		validateFinanceTrackerFilesFolder(folderName, configResourceList);
		
		for (Resource file : configResourceList) {
			if (file.exists() && file.getFilename().startsWith(CONFIG_FILE_PREFIX)) {
				returnConfigResourceList.add(file);
			}
		}
		
		return returnConfigResourceList;
	}

	public static List<File> getTransactionFilesToProcess(File folderName) throws FinanceTrackerExcepton {
		return getFilesForPrefix(TRANSACTIONS_FILE_PREFIX, folderName);
	}

	public static boolean validateFinanceTrackerFilesFolder(String folderFile, List<Resource> configResourceList) throws FinanceTrackerExcepton {
		if (configResourceList == null || configResourceList.isEmpty()) {
			throw new FinanceTrackerExcepton("folderFile does not have any files:  '" + folderFile + "'");
		}
		
		return true;
	}

	public static boolean validateFinanceTrackerFilesFolder(File folderFile) throws FinanceTrackerExcepton {
		File[] configFileArr = null;

		if (!folderFile.exists()) {
			throw new FinanceTrackerExcepton("folderFile does not exist:  '" + folderFile.getAbsolutePath() + "'");
		}

		if (!folderFile.isDirectory()) {
			throw new FinanceTrackerExcepton("folderFile is not a directory:  '" + folderFile.getAbsolutePath() + "'");
		}
		
		configFileArr = folderFile.listFiles();
		
		if (configFileArr == null || configFileArr.length < 1) {
			throw new FinanceTrackerExcepton("folderFile does not have any files:  '" + folderFile.getAbsolutePath() + "'");
		}
		
		return true;
	}

	public static boolean validateFinanceTrackerFolder(String folderName) throws FinanceTrackerExcepton {
		File[] configFileArr = null;
		Resource folderResource = FileUtil.getResourceFromFileName(folderName);

		if (!folderResource.exists()) {
			throw new FinanceTrackerExcepton("folderFile does not exist:  '" + folderResource.getFilename() + "'");
		}
		
		return true;
	}
}
