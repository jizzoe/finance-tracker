package com.swingtech.common.tools.reportbuilder.model.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleConfigurationTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleNodeTypeEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleTypeEnum;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportModuleType {
	ModuleTypeEnum 		moduleType();
	ModuleNodeTypeEnum 	moduleNodeType() default ModuleNodeTypeEnum.CHILD_NODE;
	boolean 			canAttachToRoot() default true;
	ModuleTypeEnum[] 	allowedChildren();
	ModuleTypeEnum[] 	allowedParents();
	ModuleConfigurationTypeEnum defaultConfigurationType();
	Class			    defaultConfigurationClass() default String.class;
}
