package com.swingtech.common.tools.reportbuilder.modules.ui;

import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.tools.reportbuilder.model.config.ReportSectionCConfiguration;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ModuleChild;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ReportModuleType;
import com.swingtech.common.tools.reportbuilder.model.config.constant.LifeCycleEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleChildMultiplicityEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleConfigurationTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleNodeTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleTypeEnum;
import com.swingtech.common.tools.reportbuilder.module.configi.BaseNodeReportConfiguration;
import com.swingtech.common.tools.reportbuilder.module.configi.ReportBodyDisplayConfiguration; 

@ReportModuleType (
		moduleType = 	    ModuleTypeEnum.REPORT_ROOT_MODULE,
		moduleNodeType = 	ModuleNodeTypeEnum.ROOT_NODE,
		canAttachToRoot = 	false,
		allowedChildren  =  {ModuleTypeEnum.REPORT_CONTAINER_MODULE, 
						     ModuleTypeEnum.OTHER},
	    allowedParents = {ModuleTypeEnum.NOME,},
	    defaultConfigurationType =		ModuleConfigurationTypeEnum.REPORT_ROOT_CONFIG
)
public class ReportBody extends BaseNodeNonRootChildReportModule {
	@ModuleChild (
			childModuleType = 	    ModuleTypeEnum.REPORT_BODY_MODULE,
		    childMultiplicity = 	ModuleChildMultiplicityEnum.LIST_MULTIPLE_INTANCCE,
			isChildTiedToParentsLifeCycle = 	false,
			lifeCycleEnum = LifeCycleEnum.TIED_TO_PARENT
	)
	private final List<ReportSectionCConfiguration> reportSections = new ArrayList<ReportSectionCConfiguration>();
	
	public ReportBody(BaseNodeReportModule parentReportModule, BaseNodeReportConfiguration baseConfiuguration) {
		super(parentReportModule, BaseNodeReportModule.createNewModuleConfigurationFromParent(parentReportModule));
	}

	public static ReportBodyDisplayConfiguration createNewModuleConfigurationFromParent(BaseNodeReportConfiguration parentReportModule) {
		ReportBodyDisplayConfiguration moduleConfiguration = ReportBodyDisplayConfiguration.createNewModuleConfigurationFromRoot(parentReportModule);
		
		return moduleConfiguration;
	}

	public void addReportSection(ReportSectionCConfiguration reportSection) {
		this.reportSections.add(reportSection);
		
	}
	
	public ReportBodyDisplayConfiguration getModuleConfiguration() {
		return (ReportBodyDisplayConfiguration) this.getModuleConfiguration();
	}

}
