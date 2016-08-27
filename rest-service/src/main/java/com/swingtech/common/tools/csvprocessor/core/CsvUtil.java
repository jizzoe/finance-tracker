package com.swingtech.common.tools.csvprocessor.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.Timer;
import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParseSuccessRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public class CsvUtil {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    
    public static int getCsvLineCount(String cvsLin) {
    	return 1;
    }

	public static CsvParserResults parseCsvFile(File csvFile, CsvRecordProcessor csvRecordProcessor, boolean isNullReturnAllowed) throws FinanceTrackerExcepton, FileNotFoundException {
		return parseCsvFile(csvFile, csvRecordProcessor, isNullReturnAllowed, null);
	}
	
	public static CsvParserResults parseCsvFile(File csvFile, CsvRecordProcessor csvRecordProcessor, boolean isNullReturnAllowed, Map<String, Object> parseProcessContextMap) {
		Scanner scanner = null;
		CsvParserResults results = new CsvParserResults();
		Timer csvParseProcessTimer = new Timer();
		
		// only starting this timer in case there's an error getting the scanner.  If there's no error, irgnore the timer.
		csvParseProcessTimer.startTiming();

		try {
			scanner = CsvUtil.getScanner(csvFile);
		} 
		catch (Exception e) {
			String errorMessage = "Error trying to parse the follwoing CSV file:  '" + csvFile.getAbsolutePath() + "'.  Error Message:  " + ErrorUtil.getErrorMessageFromException(e);
			CsvUtil.handleParseProcessError(errorMessage, e, results, csvRecordProcessor, csvParseProcessTimer, parseProcessContextMap);
			return results;
		} 
		
		results = parseCsvFile(csvFile.getAbsolutePath(), scanner, csvRecordProcessor, isNullReturnAllowed, parseProcessContextMap);
		
		return results;
	}
	
	private static CsvParserResults parseCsvFile(String csvFileName, Scanner scanner, CsvRecordProcessor csvRecordProcessor, boolean isNullReturnAllowed, Map<String, Object> parseProcessContextMap) {
		String csvLineString = null;
		List<String> csvLineFields = null; 
		Object returnObject = null;
		CsvParserResults csvParserResults = new CsvParserResults();
		Map<String, Object> csvLineContextMap = null;
		boolean continueLParsing = true;
	    Timer csvParseProcessTimer = null;
		Timer csvParseLineTimer = null;

		try {
			csvParseProcessTimer = new Timer();
			
			csvParseProcessTimer.startTiming();
			
			csvParserResults.setCsvFileName(csvFileName);
			
			int lineNumber = 0;

			if (parseProcessContextMap == null) {
				parseProcessContextMap = new HashMap<String, Object>();
			}
            
			csvRecordProcessor.handleParseCsvPrerProcess(parseProcessContextMap);
			
	        while (scanner.hasNext()) {
	        	csvParseLineTimer = new Timer();
				
	        	csvParseLineTimer.startTiming();
				
	        	lineNumber++;
	        	
	        	csvLineString = scanner.nextLine();
	            csvLineFields = CsvUtil.parseLine(csvLineString);
	            
	            csvLineContextMap = new HashMap<String, Object>();
	            
	            CsvLineRecord csvLineRecord = CsvLineRecord.newCsvLineRecord(lineNumber, csvLineString, csvLineFields);
	            
	            String errorMessage = null;
	            
	            try {
	            	returnObject = csvRecordProcessor.handleParseCsvLine(csvLineRecord, csvLineContextMap, parseProcessContextMap);
	            } catch(Exception e) {
            		errorMessage = "Error trying to parse csv line";
            		continueLParsing = CsvUtil.handleError(csvLineRecord, returnObject, errorMessage, e, csvParserResults, csvRecordProcessor, csvParseLineTimer, parseProcessContextMap, csvLineContextMap);

            		if (continueLParsing) {
            			continue;
            		} else {
            			break;
            		}
	            }

            	if (returnObject == null && !isNullReturnAllowed) {
            		errorMessage = "Object returned from handleParseLine was null and the 'isNullReturnAllowed' flag was set to false.  Something wrong must have happened";
            		continueLParsing = CsvUtil.handleError(csvLineRecord, returnObject, errorMessage, null, csvParserResults, csvRecordProcessor, csvParseLineTimer, parseProcessContextMap, csvLineContextMap);
            		
            		if (continueLParsing) {
            			continue;
            		} else {
            			break;
            		}
            	}
            	
            	continueLParsing = CsvUtil.handleSuccess(csvLineRecord, returnObject, csvParserResults, csvRecordProcessor, csvParseLineTimer, parseProcessContextMap, csvLineContextMap);
        		if (continueLParsing) {
        			continue;
        		} else {
        			break;
        		}
	        }
		} 
		catch (Exception e) {
			String errorMessage = "Error trying to parse the follwoing CSV file:  '" + csvFileName + "'.  Error Message:  " + ErrorUtil.getErrorMessageFromException(e);
			
			return csvParserResults;
		} 
		finally {
			scanner.close();
		}

		CsvUtil.handleParseProcessSuccess(csvParserResults, csvRecordProcessor, csvParseProcessTimer, parseProcessContextMap);
		
		return csvParserResults;
	}
	
	private static void handleParseProcessSuccess(CsvParserResults csvParserResults, CsvRecordProcessor csvRecordProcessor, Timer csvParseProcessTimer, Map<String, Object> parseProcessContextMap) {
		csvParseProcessTimer.stopTiming();
		csvParserResults.setCsvParseProcessTimer(csvParseProcessTimer);
		csvParserResults.setPocessingCcomplete(true);
		
		csvRecordProcessor.handleParseCsvPostProcess(csvParserResults, parseProcessContextMap, true);
	}

	private static void handleParseProcessError(String errorMessage, Exception e, CsvParserResults csvParserResults, CsvRecordProcessor csvRecordProcessor, Timer csvParseProcessTimer, Map<String, Object> parseProcessContextMap) {
		CsvParseProcessError processError = CsvParseProcessError.getCsvParseError(errorMessage, e);
		
		csvParseProcessTimer.stopTiming();
		csvParserResults.setCsvParseError(processError);
		csvParserResults.setCsvParseProcessTimer(csvParseProcessTimer);
		csvParserResults.setPocessingCcomplete(true);
		
		csvRecordProcessor.handlePraseCsvProcessError(csvParserResults, processError, parseProcessContextMap);
		csvRecordProcessor.handleParseCsvPostProcess(csvParserResults, parseProcessContextMap, false);
	}
	
	private static boolean handleSuccess(CsvLineRecord csvLineRecord, Object returnObject, CsvParserResults csvParserResults, CsvRecordProcessor csvRecordProcessor, Timer csvParseLineTimer, Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) {
		CsvParseSuccessRecord success = CsvParseSuccessRecord.getCsvParseSuccess(csvLineRecord, returnObject);
		csvParseLineTimer.stopTiming();
		
		success.setProcessTimer(csvParseLineTimer);
		
		csvParserResults.addParseSuccess(success);
		
		boolean continueLParsing = true;
		try {
			continueLParsing = csvRecordProcessor.handleParseLineSuccess(success, csvParserResults, parseProcessContextMap, csvLineContextMap);
		} catch(Exception e) {
			return true;
		}
		
		return continueLParsing;
	}
	
	private static boolean handleError(CsvLineRecord csvLineRecord, Object returnObject, String errorMessage, Exception e, CsvParserResults csvParserResults, CsvRecordProcessor csvRecordProcessor, Timer csvParseLineTimer, Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) {
		CsvParseErrorRecord failureRecord = CsvParseErrorRecord.getCsvParseError(csvLineRecord, returnObject, errorMessage, null);
		csvParseLineTimer.stopTiming();
		failureRecord.setProcessTimer(csvParseLineTimer);
		csvParserResults.addParseError(failureRecord); 
		boolean continueLParsing = true;
		
		try {
			continueLParsing = csvRecordProcessor.handleParseLineError(failureRecord, csvParserResults, parseProcessContextMap, csvLineContextMap);
		}catch(Exception handleParseRrrorException) {
			handleParseRrrorException.printStackTrace();
			failureRecord.addAdditionalError("Error caling the handleParseLineError callback method:  Error:  " + ErrorUtil.getErrorMessageFromException(handleParseRrrorException), handleParseRrrorException);
			return true;
		}
		
		return continueLParsing;
	}
	
	public static Scanner getScanner(File csvFile) throws FileNotFoundException {
		return new Scanner(csvFile);
	}
	
    public static void main(String[] args) throws Exception {

        String csvFile = "/git/swingtech/swing-tech/sandbox/finance-tracker/rest-service/src/test/resources/test-data/category-configuration-fifththird.csv";

        Scanner scanner = new Scanner(new File(csvFile));
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
            System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + "]");
        }
        scanner.close();

    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
