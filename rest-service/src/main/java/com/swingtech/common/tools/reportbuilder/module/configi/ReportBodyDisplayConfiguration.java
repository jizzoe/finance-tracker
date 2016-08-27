package com.swingtech.common.tools.reportbuilder.module.configi;

public class ReportBodyDisplayConfiguration extends BaseNodeNonRootChildConfiguration {


	protected ReportBodyDisplayConfiguration(BaseNodeReportConfiguration parentModuleConfiguration) {
		super(parentModuleConfiguration);
		// TODO Auto-generated constructor stub
	}

	public static ReportBodyDisplayConfiguration createNewModuleConfigurationFromRoot(BaseNodeReportConfiguration parentReportModule) {
		ReportBodyDisplayConfiguration moduleConfiguration = new ReportBodyDisplayConfiguration(parentReportModule);
		
		return moduleConfiguration;
	}

}
