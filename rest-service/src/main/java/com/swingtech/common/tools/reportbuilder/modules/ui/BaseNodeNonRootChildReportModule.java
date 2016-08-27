package com.swingtech.common.tools.reportbuilder.modules.ui;

import com.swingtech.common.tools.reportbuilder.module.configi.BaseNodeReportConfiguration;

public class BaseNodeNonRootChildReportModule extends BaseNodeReportModule {
	public BaseNodeNonRootChildReportModule(BaseNodeReportModule parentReportModule, BaseNodeReportConfiguration baseConfiuguration) {
		super(parentReportModule, createNewModuleConfigurationFromParent(parentReportModule));
	}
}
