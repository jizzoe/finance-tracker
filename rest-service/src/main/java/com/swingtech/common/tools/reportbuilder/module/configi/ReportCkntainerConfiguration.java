package com.swingtech.common.tools.reportbuilder.module.configi;

import com.swingtech.common.tools.reportbuilder.modules.ui.ReportBody;
import com.swingtech.common.tools.reportbuilder.modules.ui.ReportBodyHolder;
import com.swingtech.common.tools.reportbuilder.modules.ui.ReportFooterModule;
import com.swingtech.common.tools.reportbuilder.modules.ui.ReportHeaderModule;

public class ReportCkntainerConfiguration  extends BaseNodeRootChildReportConfiguration implements ReportBodyHolder {
	private ReportBody reportBody;
	private ReportHeaderModule reportHeader;
	private ReportFooterModule reportFooter;
	
	protected ReportCkntainerConfiguration(BaseRootReportDisplayConfiguration parentModuleConfiguration) {
		super(ReportCkntainerConfiguration.createNewModuleConfigurationFromRoot(parentModuleConfiguration));
	}
	
	protected ReportCkntainerConfiguration(BaseNodeRootChildReportConfiguration parentModuleConfiguration) {
		super(ReportCkntainerConfiguration.createNewModuleConfigurationFromParent(parentModuleConfiguration));
	}

	public static ReportCkntainerConfiguration createNewModuleConfigurationFromParent(BaseNodeRootChildReportConfiguration parentReportModule) {
		ReportCkntainerConfiguration moduleConfiguration = new ReportCkntainerConfiguration(parentReportModule);
		
		return moduleConfiguration;
	}

	public static ReportCkntainerConfiguration createNewModuleConfigurationFromRoot(BaseRootReportDisplayConfiguration parentReportModule) {
		ReportCkntainerConfiguration moduleConfiguration = new ReportCkntainerConfiguration(parentReportModule);
		
		return moduleConfiguration;
	}


	public ReportBody getReportBody() {
		return reportBody;
	}

	public void setReportBody(ReportBody reportBody) {
		this.reportBody = reportBody;
	}

	public ReportHeaderModule getReportHeader() {
		return reportHeader;
	}

	public void setReportHeader(ReportHeaderModule reportHeader) {
		this.reportHeader = reportHeader;
	}

	public ReportFooterModule getReportFooter() {
		return reportFooter;
	}

	public void setReportFooter(ReportFooterModule reportFooter) {
		this.reportFooter = reportFooter;
	}
}
