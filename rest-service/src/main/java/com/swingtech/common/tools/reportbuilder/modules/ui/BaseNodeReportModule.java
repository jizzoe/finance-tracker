package com.swingtech.common.tools.reportbuilder.modules.ui;

import com.swingtech.common.tools.reportbuilder.module.configi.BaseNodeReportConfiguration;

public class BaseNodeReportModule extends BaseReportModule {
	public BaseNodeReportModule(BaseReportModule parentReportModule, BaseNodeReportConfiguration baseConfiuguration) {
		super(parentReportModule, createNewModuleConfigurationFromParent(parentReportModule));
	}

	public static BaseNodeReportConfiguration createNewModuleConfigurationFromParent(BaseReportModule parentReportModule) {
		BaseNodeReportConfiguration moduleConfiguration = BaseNodeReportConfiguration.createNewModuleConfigurationFromParent(parentReportModule);
		
		return moduleConfiguration;
	}

	public boolean isRootNode() {
		return false;
	}
}
