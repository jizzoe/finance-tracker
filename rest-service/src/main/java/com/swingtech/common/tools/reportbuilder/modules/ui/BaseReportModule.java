package com.swingtech.common.tools.reportbuilder.modules.ui;

import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.tools.reportbuilder.model.config.RootReportDisplayConfiguration;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseNodeReportConfiguration;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseReportDisplayConfiguration;

public abstract class BaseReportModule {
	final BaseReportModule rootReportModule;
	final BaseReportModule parentReportModule;
	final BaseReportDisplayConfiguration moduleConfiguration;
	final List<BaseReportModule> children = new ArrayList<BaseReportModule>();

	public BaseReportModule(BaseReportModule parentReportModule) {
		this(parentReportModule, BaseReportModule.createNewModuleConfigurationFromParent(parentReportModule));
	}

	public BaseReportModule(BaseReportModule parentReportModule, BaseReportDisplayConfiguration moduleConfiguration) {
		super();
		if ((this instanceof BaseRootReportModule)) {
			this.parentReportModule = null;
			this.rootReportModule = this;
			if (moduleConfiguration != null) {
				this.moduleConfiguration = moduleConfiguration;
			} else {
				this.moduleConfiguration = RootReportDisplayConfiguration.newRootReportDisplayConfiguration(null);
			}
		} else {
			if (parentReportModule == null || moduleConfiguration == null ||  parentReportModule.getRootReportModule() == null) {
				throw new IllegalArgumentException("Could not initialize report module.  Someehow either the parent configuration is null or the ReportBuffer is null.");
			}
			
			this.parentReportModule = parentReportModule;	
			this.moduleConfiguration = moduleConfiguration;
			this.rootReportModule = parentReportModule.getRootReportModule();
		}
	}
	
	public static BaseNodeReportConfiguration createNewModuleConfigurationFromParent(BaseReportModule parentReportModule) {
		BaseNodeReportConfiguration moduleConfiguration = new BaseNodeReportConfiguration(parentReportModule.getModuleConfiguration());
		
		return moduleConfiguration;
	}

	
	public static BaseNodeReportConfiguration createRootConfiguration(BaseReportModule parentReportModule) {
		BaseNodeReportConfiguration moduleConfiguration = new BaseNodeReportConfiguration(parentReportModule.getModuleConfiguration());
		
		return moduleConfiguration;
	}

	public BaseReportModule getParentReportModule() {
		return parentReportModule;
	}

	public BaseReportDisplayConfiguration getModuleConfiguration() {
		return moduleConfiguration;
	}
	
	public abstract boolean isRootNode();

	public List<BaseReportModule> getChildren() {
		return children;
	}

	public BaseReportModule getRootReportModule() {
		return rootReportModule;
	}
	
	
}
