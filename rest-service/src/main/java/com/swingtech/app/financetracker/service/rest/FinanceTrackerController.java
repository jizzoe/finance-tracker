package com.swingtech.app.financetracker.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.core.model.TransactionSource;
import com.swingtech.app.financetracker.core.model.UploadFileInput;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.service.FinanceTrackerService;
import com.swingtech.app.financetracker.service.util.FinanceTrackerUtil;

@RestController
public class FinanceTrackerController {
	private static final String DUMMY_USER_NAME = "jizzoe";
	
	@Autowired
	private FinanceTrackerService financeTrackerService;

	@Value("${application.finance-data.dir.transaction-files.work}")
	public String financeDataTransactionsWorkDir;

    @RequestMapping("/transactions")
    public List<FinancialTransactionRecord> index() {
        return financeTrackerService.retrieveTransactions(null, null);
    }

    @RequestMapping(value="/parse-transactions", method=RequestMethod.POST)
    public ParseTransactionResults parseAndAddTransactions() throws FinanceTrackerExcepton {
    	ParseTransactionResults transactionResults = null;
    	transactionResults = financeTrackerService.parseAndAddTransactions(DUMMY_USER_NAME);
    	
    	return transactionResults;
    }

    @RequestMapping(value="/upload-transactions", method=RequestMethod.POST)
    public ParseTransactionResults uploadAndProcessTransactions(
    		@RequestParam("file") MultipartFile[] files,
    		@RequestParam(value="transactionSource", required = false) String[] transactionSourceNames,
    		@RequestParam(value="userName", required = false) String[] userNames,
    		@RequestParam(value="accountName", required = false) String[] accountNames,
    		@RequestParam(value="startDateRange", required = false) String[] startDateRangeStrings,
    		@RequestParam(value="endDateRange", required = false) String[] endDateRangeStrings,
    		@RequestParam(value="transactionSource", required = false) String[] fileNames
    		) throws FinanceTrackerExcepton 
    {
    	boolean getTransactionSourceFromFileNames;
    	List<UploadFileInput> uploadFileInputList = null;    	
    	ParseTransactionResults transactionResults = null;
    	
    	if (files == null || files.length <= 0) {
    		throw new FinanceTrackerExcepton("Invalid input.  There were no 'files' sent in to upload");
    	}
    	
    	getTransactionSourceFromFileNames = this.useFileNamesToGetTransactionSource(files, transactionSourceNames, userNames, accountNames, startDateRangeStrings, endDateRangeStrings, fileNames);
    	
    	uploadFileInputList = this.getUploadFilesInputList(files, transactionSourceNames, userNames, accountNames, startDateRangeStrings, endDateRangeStrings, fileNames, getTransactionSourceFromFileNames);
    	
    	transactionResults = financeTrackerService.processAndAddTransactionUploadFiles(DUMMY_USER_NAME, uploadFileInputList);
    	
    	return transactionResults;
    }

    private List<UploadFileInput> getUploadFilesInputList(
    		MultipartFile[] files,
    		String[] transactionSourceNames,
    		String[] userNames,
    		String[] accountNames,
    		String[] startDateRangeStrings,
    		String[] endDateRangeStrings,
    		String[] fileNames,
    		boolean getTransactionSourceFromFileNames) throws FinanceTrackerExcepton 
    {
    	List<UploadFileInput> uploadFileInputList = new ArrayList<UploadFileInput>();
    	String originalFileName = null;
    	byte[] fileCotnent = null;
    	UploadFileInput uploadFileInput = null;
    	TransactionSource transactionSource = null;
    	
    	for (int i = 0; i < files.length; i++) {
    		MultipartFile file = files[i];
    		uploadFileInput = new UploadFileInput();
    		
    		System.out.println("original file name:  " + file.getOriginalFilename());
    		
    		try {
	    		uploadFileInput.setOriginalFileName(file.getOriginalFilename());
	    		uploadFileInput.setFileContents(file.getBytes());
	    		uploadFileInput.setContentType(file.getContentType());
	    		uploadFileInput.setFileInputStream(file.getInputStream());
	    		uploadFileInput.setFileLength(file.getSize());
    		}
    		catch (Exception e) {
    			throw new FinanceTrackerExcepton("error trying to get uploadFileInput from uploaded file:  " + file.getOriginalFilename(), e);
    		}

    		if (getTransactionSourceFromFileNames) {
    			transactionSource = FinanceTrackerUtil.getTransactionSourceFromTransactionFileName(financeDataTransactionsWorkDir, file.getOriginalFilename());	
    		} else {
    			transactionSource = FinanceTrackerUtil.getTransactionSourceFromRequestParams(
    					financeDataTransactionsWorkDir,
    					transactionSourceNames[i], userNames[i], accountNames[i], 
    					startDateRangeStrings[i], endDateRangeStrings[i], originalFileName);
    		}
    		
    		uploadFileInput.setTransactionSource(transactionSource);
    		uploadFileInput.setFileName(transactionSource.getFileName());
    		uploadFileInput.setFile(transactionSource.getFile());;
    		
    		uploadFileInputList.add(uploadFileInput);
    	}
    	
    	return uploadFileInputList;
    }
    private boolean useFileNamesToGetTransactionSource(
    		MultipartFile[] files,
    		String[] transactionSourceNames,
    		String[] userNames,
    		String[] accountNames,
    		String[] startDateRangeStrings,
    		String[] endDateRangeStrings,
    		String[] fileNames) 
    {

    	if (transactionSourceNames == null || 
    			userNames == null || 
    			accountNames == null || 
    			startDateRangeStrings == null || 
    			endDateRangeStrings == null) 
    	{
    		return true;
    	}
    	
    	if (files.length != transactionSourceNames.length ||
    			files.length != userNames.length || 
    			files.length != accountNames.length || 
    			files.length != startDateRangeStrings.length || 
    			files.length != endDateRangeStrings.length) 
    	{
    		return true;
    	}
    	return false;
    }

}
