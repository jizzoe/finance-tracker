package com.swingtech.common.tools.reportbuilder.model.config.annotation;

import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleConfigurationTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleNodeTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleTypeEnum;

public @interface ModuleConfigurationType {
	ModuleConfigurationTypeEnum configurationType();
	ModuleTypeEnum 		moduleType();
	ModuleNodeTypeEnum 	moduleNodeType() default ModuleNodeTypeEnum.CHILD_NODE;
	boolean 			canAttachToRoot() default true;
	ModuleTypeEnum[] 	allowedChildren();
	ModuleTypeEnum[] 	allowedParents();
	ModuleConfigurationTypeEnum defaultConfigurationType();
	Class			    defaultConfigurationClass() default String.class;
}
