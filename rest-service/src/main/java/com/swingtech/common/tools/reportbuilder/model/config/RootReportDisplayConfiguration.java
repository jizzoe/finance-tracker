package com.swingtech.common.tools.reportbuilder.model.config;

import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseRootReportDisplayConfiguration;

public class RootReportDisplayConfiguration  extends BaseRootReportDisplayConfiguration {

	public RootReportDisplayConfiguration() {
		super();
	}
	
	public RootReportDisplayConfiguration(ReportBuilder reportBuffer) {
		super(reportBuffer);
	}
	
	public static RootReportDisplayConfiguration newRootReportDisplayConfiguration(ReportBuilder reportBuffer) {
		return new RootReportDisplayConfiguration(reportBuffer);
	}
	public static RootReportDisplayConfiguration newRootReportDisplayConfiguration() {
		return new RootReportDisplayConfiguration(null);
	}

}
