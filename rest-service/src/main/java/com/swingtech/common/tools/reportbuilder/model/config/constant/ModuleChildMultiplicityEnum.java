package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum ModuleChildMultiplicityEnum {
	SINGLE_FIELD_SINGLE_INSTANCE("", ""), 
	LIST_SINGLE_INSTANCE("", ""), 
	LIST_MULTIPLE_INTANCCE("", ""), 
	LIST_LIMITED_INSTANCES("", "");

	private String name;
	private String description;
	
	private ModuleChildMultiplicityEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
