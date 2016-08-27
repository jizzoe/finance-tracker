package com.swingtech.common.tools.reportbuilder.modules.ui;

import com.swingtech.common.tools.reportbuilder.module.configi.BaseNodeReportConfiguration;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseRootReportDisplayConfiguration;

public abstract class BaseNodeRootChildReportModule extends BaseNodeReportModule {
	
	public BaseNodeRootChildReportModule(BaseReportModule parentReportModule, BaseRootReportDisplayConfiguration baseConfiuguration) {
		super(parentReportModule, createNewModuleConfigurationFromParent(parentReportModule));
	}

	public BaseNodeRootChildReportModule(BaseNodeReportModule parentReportModule, BaseNodeReportConfiguration baseConfiuguration) {
		super(parentReportModule, createNewModuleConfigurationFromParent(parentReportModule));
	}

}
