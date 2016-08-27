package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum ModuleConfigurationTypeEnum {
	HEADER_CONFIG("", "", ""), 
	FOOTER_CONFIG("", "", ""),
	CONTAINER_CONFIG("", "", ""),
	REPORT_BODY_CONFIG("", "", ""),
	SECTION_CONFIG("", "", ""),
	REPORT_ROOT_CONFIG("", "", "");

	private String name;
	private String description;
	private String defaultConfigKeyName;
	
	private ModuleConfigurationTypeEnum(String name, String description, String defaultConfigKeyName) {
		this.name = name;
		this.description = description;
		this.defaultConfigKeyName = defaultConfigKeyName;
	}

}
