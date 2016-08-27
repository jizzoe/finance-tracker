package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum LifeCycleEnum {
	TIED_TO_PARENT("", ""),
	REUSED_ACROSS_INVOCATIONS("", ""),
	DESTROYED_AFTER_EACH_USE("", "");

	private String name;
	private String description;
	
	private LifeCycleEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
