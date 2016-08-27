package com.swingtech.app.financetracker.service.csvprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;

import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.service.util.FinanceTrackerReportPrinter;
import com.swingtech.common.core.util.FileUploadMetaData;
import com.swingtech.common.core.util.FileUtil;
import com.swingtech.common.core.util.HttpClientUtil;
import com.swingtech.common.core.util.JsonUtil;
import com.swingtech.common.core.util.Timer;

public class FinanceTrackerHttpClientTest {
//	@Test
	public void testHttp() throws Exception {
		String url = "http://localhost:8080/upload-transactions";
		String uploadFileDirectory = "classpath:/test-data/scenarios/valid-files";
		String uploadFileDirectorySearchPattern = uploadFileDirectory + "/*.csv";
		String folderNameToWriteJson = "/git/swingtech/swing-tech/sandbox/finance-tracker/rest-service/files/test-output";
		String outputFileName = folderNameToWriteJson + "/parse-transactions-results-server.json";
		List<File> uploadFiles = null;
		Resource resource = null;
		List<FileUploadMetaData> filesToUpload = new ArrayList<FileUploadMetaData>();
		FileUploadMetaData fileUploadMetadata = null;
		Map<String, String> headers = new HashMap<String, String>();
		HttpResponse response = null;
		String responseBodyPrintyPrint = null;
		String responseBody = null;
		long contentLength = -1;
		File responseJsonFile = null;
		Timer httpSendTimer = Timer.getTimer();
		Timer prettyPrintTimer = Timer.getTimer();
		Timer getBodyTimer = Timer.getTimer();
		Timer marshalTimer = Timer.getTimer();
		Timer fileWriteTimer = Timer.getTimer();
		
		resource = FileUtil.getResourceFromFileName(uploadFileDirectory);
		
		System.out.println(resource);;
	
		
		uploadFiles = FileUtil.getFileListForParentFolder(uploadFileDirectorySearchPattern);
		
		for (File file : uploadFiles) {
			fileUploadMetadata = new FileUploadMetaData();
			fileUploadMetadata.setContentType(ContentType.DEFAULT_BINARY);
			fileUploadMetadata.setUploadFile(file);
			fileUploadMetadata.setUploadFileFieldName("file");
			fileUploadMetadata.setUploadFileName(file.getName());
			
			filesToUpload.add(fileUploadMetadata);
		}
		
		headers.put("Accept", "application/json");
//		headers.put("Content-Type", "multipart/form-data");
		
		System.out.println(uploadFiles);
		
		httpSendTimer.startTiming();
		response = HttpClientUtil.sendPostUploadFiles(url, filesToUpload, headers);
		httpSendTimer.stopTiming();
		
		getBodyTimer.startTiming();
		responseBody = EntityUtils.toString(response.getEntity());
		getBodyTimer.stopTiming();
		
		contentLength = responseBody.length();
		
		prettyPrintTimer.startTiming();
		responseBodyPrintyPrint = JsonUtil.prettyPrintJson(responseBody);
		prettyPrintTimer.stopTiming();
		
		System.out.println("START - marchalling json response(" + contentLength + " bytes)");
		marshalTimer.startTiming();;
		ParseTransactionResults results = JsonUtil.unmarshalJsonToObject(responseBody, ParseTransactionResults.class);
		marshalTimer.stopTiming();
		System.out.println("END - marchalling json response(" + contentLength + " bytes)");
		
//		responseBody = EntityUtils.toString(response.getEntity());

//		System.out.println("responseBody (" + responseBody.length() + " bytes):  " + responseBody);
		fileWriteTimer.startTiming();
		responseJsonFile = FileUtil.getFileFromFileName(outputFileName, false);
		FileUtil.writeContentsToFile(responseJsonFile,responseBodyPrintyPrint);
		fileWriteTimer.stopTiming();
		
		System.out.println("done writing file (" + contentLength + " bytes)");
		
		System.out.println("\n\n**********************************************");
		System.out.println("  Timers.  Time it took to:");
		System.out.println("     send http request:  " + httpSendTimer.getDurationString() + " (" + httpSendTimer.getDurationMilis() + " ms) ");
		System.out.println("     get the body from the response:  " + getBodyTimer.getDurationString() + " (" + getBodyTimer.getDurationMilis() + " ms) ");
		System.out.println("     pretty print the response body:  " + prettyPrintTimer.getDurationString() + " (" + prettyPrintTimer.getDurationMilis() + " ms) ");
		System.out.println("     marshall the response to json:  " + marshalTimer.getDurationString() + " (" + marshalTimer.getDurationMilis() + " ms) ");
		System.out.println("     write the response to file:  " + fileWriteTimer.getDurationString() + " (" + fileWriteTimer.getDurationMilis() + " ms) ");
		System.out.println("");
		System.out.println("  Request info:");
		System.out.println("     Files Sent:  ");
		int i = 0;
		for (FileUploadMetaData uploadMeta : filesToUpload) {

			System.out.println("       File # " + ++i +":  " );
			System.out.println("          Local Name # " + uploadMeta.getUploadFile().getName());
			System.out.println("          upload File Name # " + uploadMeta.getUploadFile().getAbsolutePath());
			System.out.println("          Full file Path # " + uploadMeta.getUploadFile().getAbsolutePath());
			System.out.println("          File field name # " + uploadMeta.getUploadFileFieldName());
			if (!uploadMeta.getFormData().isEmpty()) {
				System.out.println("          Form Data:");
				for (Entry<String, String> entry : uploadMeta.getFormData().entrySet()) {
					System.out.println("             " + entry.getKey() + " = " + entry.getValue());	
				}
			}
		}
		System.out.println("");
		System.out.println("  Response info:");
		System.out.println("     Status Code:  " + response.getStatusLine().getStatusCode());
		System.out.println("     Content Lenth:  " + contentLength);
		System.out.println("     ResponseHeaders:  " + response.getAllHeaders().toString());
		System.out.println("**********************************************\n\n");
	}
	
	@Test
	public void testPrintResults() throws Exception {
		String parseResultsJsonFile = "/git/swingtech/swing-tech/sandbox/finance-tracker/rest-service/files/test-output/parse-transactions-results-server.json";
		String parseResultsJson = FileUtil.readFileContents(parseResultsJsonFile);
		
		System.out.println(parseResultsJson.length());
		
		ParseTransactionResults results = JsonUtil.unmarshalJsonToObject(parseResultsJson, ParseTransactionResults.class);
		
		FinanceTrackerReportPrinter printer = new FinanceTrackerReportPrinter();
		
		printer.printResults(results);
		
	}
}
