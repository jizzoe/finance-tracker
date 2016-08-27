package com.swingtech.common.tools.reportbuilder.module.configi;

import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;

public class BaseRootReportDisplayConfiguration extends BaseReportDisplayConfiguration {

	public BaseRootReportDisplayConfiguration() {
		super(null, null);
	}
	
	public BaseRootReportDisplayConfiguration(ReportBuilder reportBuffer) {
		super(null, reportBuffer);
	}

	public static BaseRootReportDisplayConfiguration createRootConfiguration(ReportBuilder reportBuffer) {
		return new BaseRootReportDisplayConfiguration(reportBuffer);
	}
	public static BaseRootReportDisplayConfiguration createRootConfiguration() {
		return new BaseRootReportDisplayConfiguration(null);
	}

	@Override
	public boolean isRootNode() {
		return true;
	}
}
