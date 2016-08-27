package com.swingtech.common.tools.reportbuilder.model.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleConfigurationTypeEnum;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleConfiguration {
	ModuleConfigurationTypeEnum configurationType();	
	boolean inheritCompileTypeConfigurations() default true;
	boolean inheritRntimeConfigurations() default true;
	ModuleConfigurationTypeEnum inhertisFromTypes();
	String configurationKeyName();
	Class configurationClasType();
}
