package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

public class CsvProcessorConfigurationListHolder {
	List<CategoryConfiguration> configurationList = new ArrayList<CategoryConfiguration>();

	public List<CategoryConfiguration> getConfigurationList() {
		return configurationList;
	}

	public void setConfigurationList(List<CategoryConfiguration> configurationList) {
		this.configurationList = configurationList;
	}
}
