package com.swingtech.common.tools.reportbuilder.modules.ui;

import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseReportDisplayConfiguration;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseRootReportDisplayConfiguration;

public abstract class BaseRootReportModule extends BaseReportModule {
	BaseRootReportModule() {
		super(null, BaseRootReportModule.createRootConfiguration());
	}
	
	BaseRootReportModule(ReportBuilder reportBuffer) {
		super(null, BaseRootReportModule.createRootConfiguration(reportBuffer));
	}

	public BaseRootReportModule(BaseReportDisplayConfiguration moduleConfiguration) {
		super(null, moduleConfiguration);
	}

	public boolean isRootNode() {
		return true;
	}

	protected static BaseRootReportDisplayConfiguration createRootConfiguration(ReportBuilder reportBuffer) {
		BaseRootReportDisplayConfiguration moduleConfiguration = BaseRootReportDisplayConfiguration.createRootConfiguration(reportBuffer);
		
		return moduleConfiguration;
	}

	protected static BaseRootReportDisplayConfiguration createRootConfiguration() {
		BaseRootReportDisplayConfiguration moduleConfiguration = BaseRootReportDisplayConfiguration.createRootConfiguration();

		return moduleConfiguration;
	}
}
