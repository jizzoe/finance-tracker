package com.swingtech.common.core.util.introspect.model.config;

import java.util.ArrayList;
import java.util.List;

public enum IntrospectionTypeEnum {
	CLASS("class", "", "header-module", null, null),
	FIELD("field", "", "header-module", null, null), 
	METHOD("method", "", "footer-module", null, null),
	ANNOTATED_FIELD("method", "", "footer-module", null, null),
	ANNOTATION("annotation", "", "report-body-module", null, null),
	PARAMETER("parameter", "", "container-module", null, null),
	RETURN_TYPE("return_type", "", "modDULE-any", null, null);
	
	private IntrospectionTypeEnum(String name, String description, String defaultConfigKeyName, IntrospectionTypeEnum[] contains, IntrospectionTypeEnum[] isContainedBy) {
		this.name = name;
		this.description = description;
	}
	private String name;
	private String description;
	private String defaultConfigKeyName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDefaultConfigKeyName() {
		return defaultConfigKeyName;
	}
	public void setDefaultConfigKeyName(String defaultConfigKeyName) {
		this.defaultConfigKeyName = defaultConfigKeyName;
	}
}
