package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum InstantiationType {
	AT_REPORT_STARTUP("", "", ""), 
	WHEN_PARENT_IS_CREATED("", "", ""),
	WHEN_REQUESTED("", "", ""),
	REPORT_BODY_CONFIG("", "", ""),
	SECTION_CONFIG("", "", ""),
	REPORT_ROOT_CONFIG("", "", "");

	private String name;
	private String description;
	private String defaultConfigKeyName;
	
	private InstantiationType(String name, String description, String defaultConfigKeyName) {
		this.name = name;
		this.description = description;
		this.defaultConfigKeyName = defaultConfigKeyName;
	}


}
