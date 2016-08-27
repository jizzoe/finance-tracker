package com.swingtech.app.financetracker.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvProcessorConfiguration {
	private Map<String, CsvProcessorConfigurationListHolder> categoryConfigurationMap = new HashMap<String, CsvProcessorConfigurationListHolder>();

	public Map<String, CsvProcessorConfigurationListHolder> getConfigurationMap() {
		return categoryConfigurationMap;
	}

	public void setConfigurationMap(Map<String, CsvProcessorConfigurationListHolder> configurationMap) {
		this.categoryConfigurationMap = configurationMap;
	}

	@Override
	public String toString() {
		return "CsvProcessorConfiguration [categoryConfigurationMap=" + categoryConfigurationMap + "]";
	}
	
}
