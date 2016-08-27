package com.swingtech.common.tools.reportbuilder.module.configi;

public class ReportDisplayConfiguration extends BaseNodeReportConfiguration {

	protected ReportDisplayConfiguration(BaseNodeReportConfiguration parentModuleConfiguration) {
		super(parentModuleConfiguration);
	}
	
	public BaseReportDisplayConfiguration createChildConfiguration() {
		return new ReportDisplayConfiguration(this);
	}
	
	protected static ReportDisplayConfiguration newReportBodyDisplayConfiguration(BaseNodeReportConfiguration parentModuleConfiguration) {
		return new ReportDisplayConfiguration(parentModuleConfiguration);
	}
}
