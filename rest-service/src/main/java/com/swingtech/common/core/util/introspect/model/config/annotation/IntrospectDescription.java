package com.swingtech.common.core.util.introspect.model.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IntrospectDescription {
	IntrospectionTypeEnum   introspectType();
	IntrospectionTypeEnum[] containsIntrospectionTypes();
	IntrospectionTypeEnum[] containedByIntrospectionTypes();
	Class 				    javaIntrospectionClass();
}
