package com.swingtech.common.tools.reportbuilder.model.config.constant;

public enum ModuleTypeEnum {
	HEADER_MODULE("", "", "header-module"),
	REPORT_ROOT_MODULE("", "", "header-module"), 
	FOOTER_MODULE("", "", "footer-module"),
	REPORT_BODY_MODULE("", "", "report-body-module"),
	REPORT_CONTAINER_MODULE("", "", "container-module"),
	NOME("", "", "modDULE-any"),
	OTHER("", "", "module-other"),
	ANY("", "", "module=-anyk"),
	ALL("", "", "module-all");

	private ModuleTypeEnum(String name, String description, String defaultConfigKeyName) {
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
