package com.swingtech.common.tools.reportbuilder.module.configi;

import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;
import com.swingtech.common.tools.reportbuilder.modules.ui.BaseReportModule;

public abstract class BaseReportDisplayConfiguration {
	private final static ReportBuilder DEFAULT_REPORT_BUILDER;

	private final BaseRootReportDisplayConfiguration rootModuleConfiguration;
	private final BaseReportDisplayConfiguration parentModuleConfiguration;
	private final ReportBuilder reportBuffer;

	static {
		DEFAULT_REPORT_BUILDER = new ReportBuilder();
		// add any default configurations here.s
	}

	protected BaseReportDisplayConfiguration(BaseReportModule parentReportModule) {
		this(parentReportModule.getModuleConfiguration());
	}

	protected BaseReportDisplayConfiguration(BaseReportDisplayConfiguration parentModuleConfiguration) {
		this(parentModuleConfiguration, parentModuleConfiguration.getReportBuffer());
	}
	
	public abstract boolean isRootNode();

	protected BaseReportDisplayConfiguration(BaseReportDisplayConfiguration parentModuleConfiguration, ReportBuilder reportBuffer) {
		if ((this instanceof BaseRootReportDisplayConfiguration)) {
			this.parentModuleConfiguration = null;
			this.rootModuleConfiguration = (BaseRootReportDisplayConfiguration)this;
			if (reportBuffer != null) {
				this.reportBuffer = reportBuffer;
			} else {
				this.reportBuffer = DEFAULT_REPORT_BUILDER;
			}
		} else {
			if (parentModuleConfiguration == null || reportBuffer == null || parentModuleConfiguration.getRootModuleConfiguration() == null) {
				throw new IllegalArgumentException("Could not initialize report module.  Someehow either the parent configuration is null or the ReportBuffer is null.");
			}
			
			this.reportBuffer = parentModuleConfiguration.reportBuffer;		
			this.parentModuleConfiguration = parentModuleConfiguration;
			this.rootModuleConfiguration = parentModuleConfiguration.getRootModuleConfiguration();
		}
	}
	
	public BaseReportDisplayConfiguration getParentModuleConfiguration() {
		return parentModuleConfiguration;
	}

	public ReportBuilder getReportBuffer() {
		return reportBuffer;
	}

	public static ReportBuilder getDefaultReportBuilder() {
		return DEFAULT_REPORT_BUILDER;
	}

	public BaseRootReportDisplayConfiguration getRootModuleConfiguration() {
		return rootModuleConfiguration;
	}
}
