package com.swingtech.common.tools.reportbuilder.module.configi;

import com.swingtech.common.tools.reportbuilder.modules.ui.BaseReportModule;

public class BaseNodeReportConfiguration extends BaseReportDisplayConfiguration {

	public BaseNodeReportConfiguration(BaseReportDisplayConfiguration parentModuleConfiguration) {
		super(parentModuleConfiguration);
	}

	public static BaseNodeReportConfiguration newReportBodyDisplayConfiguration(BaseReportDisplayConfiguration parentModuleConfiguration) {
		return new BaseNodeReportConfiguration(parentModuleConfiguration);
	}
	
	public static BaseNodeReportConfiguration createNewModuleConfigurationFromParent(BaseReportModule parentReportModule) {
		BaseNodeReportConfiguration moduleConfiguration = new BaseNodeReportConfiguration(parentReportModule.getModuleConfiguration());
		
		return moduleConfiguration;
	}

	@Override
	public boolean isRootNode() {
		return false;
	}	

}
