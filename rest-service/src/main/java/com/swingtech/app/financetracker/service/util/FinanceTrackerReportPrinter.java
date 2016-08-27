package com.swingtech.app.financetracker.service.util;

import java.util.HashMap;
import java.util.Map;

import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.Timer;
import com.swingtech.common.core.util.bean.BeanUtility;
import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;

public class FinanceTrackerReportPrinter {

	public void printResults(ParseTransactionResults parseResults) throws FinanceTrackerExcepton { 
		ReportBuilder reportBuilder = new ReportBuilder();
		StringBuffer sb = new StringBuffer();
		Map<Class, String> stringMap = new HashMap<Class, String>();
		stringMap.put(Timer.class, "durationString");
		Map<String, Object> beanPropsMap = null;
		
		try {
			beanPropsMap = BeanUtility.getPropertiesForObject(parseResults, stringMap, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new FinanceTrackerExcepton("Error trying to get bean properties map for parseResults.  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		reportBuilder.appendHeader("Results from Parse", beanPropsMap);
		
		System.out.print(reportBuilder.toString());
	}
	
}
