package com.swingtech.common.tools.reportbuilder.model.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.swingtech.common.tools.reportbuilder.model.config.constant.LifeCycleEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleChildMultiplicityEnum;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleTypeEnum;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleChild {
	ModuleTypeEnum childModuleType();
	ModuleChildMultiplicityEnum childMultiplicity();
	boolean isChildTiedToParentsLifeCycle() default  true;
	LifeCycleEnum lifeCycleEnum() default LifeCycleEnum.TIED_TO_PARENT;
}
