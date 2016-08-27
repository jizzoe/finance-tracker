package com.swingtech.common.tools.reportbuilder.modules.ui;

import com.swingtech.common.tools.reportbuilder.core.ReportBuilder;
import com.swingtech.common.tools.reportbuilder.model.config.RootReportDisplayConfiguration;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ModuleChild;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ReportModuleType;
import com.swingtech.common.tools.reportbuilder.model.config.constant.LifeCycleEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleChildMultiplicityEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleConfigurationTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleNodeTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleTypeEnum;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseRootReportDisplayConfiguration;

@ReportModuleType (
		moduleType = 	    ModuleTypeEnum.REPORT_ROOT_MODULE,
		moduleNodeType = 	ModuleNodeTypeEnum.ROOT_NODE,
		canAttachToRoot = 	false,
		allowedChildren  =  {ModuleTypeEnum.REPORT_CONTAINER_MODULE, 
						     ModuleTypeEnum.OTHER},
	    allowedParents = {ModuleTypeEnum.NOME,},
	    defaultConfigurationType =		ModuleConfigurationTypeEnum.REPORT_ROOT_CONFIG
)
public class ReportRootModule extends BaseRootReportModule implements ReportBodyHolder 

{
	@ModuleChild (
			childModuleType = 	    ModuleTypeEnum.REPORT_BODY_MODULE,
		    childMultiplicity = 	ModuleChildMultiplicityEnum.SINGLE_FIELD_SINGLE_INSTANCE,
			isChildTiedToParentsLifeCycle = 	false,
			lifeCycleEnum = LifeCycleEnum.TIED_TO_PARENT
	)
	private ReportBody reportBody;
	@ModuleChild (
			childModuleType = 	    ModuleTypeEnum.REPORT_BODY_MODULE,
		    childMultiplicity = 	ModuleChildMultiplicityEnum.SINGLE_FIELD_SINGLE_INSTANCE,
			isChildTiedToParentsLifeCycle = 	false,
			lifeCycleEnum = LifeCycleEnum.TIED_TO_PARENT
	)
	private ReportHeaderModule reportHeader;
	@ModuleChild (
			childModuleType = 	    ModuleTypeEnum.REPORT_BODY_MODULE,
		    childMultiplicity = 	ModuleChildMultiplicityEnum.SINGLE_FIELD_SINGLE_INSTANCE,
			isChildTiedToParentsLifeCycle = 	false,
			lifeCycleEnum = LifeCycleEnum.TIED_TO_PARENT
	)
	private ReportFooterModule reportFooter;
	
	ReportRootModule() {
		super(ReportRootModule.createRootConfiguration());
	}
	
	ReportRootModule(ReportBuilder reportBuffer) {
		super(ReportRootModule.createRootConfiguration(reportBuffer));
	}
	
	public static BaseRootReportDisplayConfiguration createRootConfiguration(ReportBuilder reportBuffer) {
		return RootReportDisplayConfiguration.newRootReportDisplayConfiguration(reportBuffer);
	}
	
	public static BaseRootReportDisplayConfiguration createRootConfiguration() {
		return  RootReportDisplayConfiguration.newRootReportDisplayConfiguration();
	}

	public boolean isRootNode() {	
		return true;
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
