package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

public class BaseResultsValidationError {
	private String errorMessage = null;
	private List<CompareObject> compareValueList = new ArrayList<CompareObject>();

	public BaseResultsValidationError(String errorMessage, List<CompareObject> compareValueList) {
		super();
		this.errorMessage = errorMessage;
		this.compareValueList = compareValueList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	public List<CompareObject> getCompareValueList() {
		return compareValueList;
	}

	public void setCompareValueList(List<CompareObject> compareValueMap) {
		compareValueList = compareValueMap;
	}
	
	public void addCompareObject(String compareName, Object compareValueFrom) {
		CompareObject compareObject = new CompareObject(compareName, compareValueFrom);
		compareValueList.add(compareObject);
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("ResultsValidationError:\n");
		buff.append("  errorMessage: + '" + errorMessage +"', \n");
		buff.append("  compareValueList=\n");
		
		int index = 0;
		
		for (CompareObject compareObject : compareValueList) {
			buff.append("    " + compareObject.getCompareName() + " = '" + compareObject.getCompareValue().toString() + "\n");
		}
		
		return buff.toString();
	}
	
}
