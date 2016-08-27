package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum ModuleNodeTypeEnum {
	ROOT_NODE("", ""), 
	CHILD_NODE("", ""),
	CHILD_AND_ROOT_NODE("", "");

	private String name;
	private String description;

	ModuleNodeTypeEnum(String name, String descrptionF) {

	}
}
