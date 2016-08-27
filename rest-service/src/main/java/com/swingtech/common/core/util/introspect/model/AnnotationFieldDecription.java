package com.swingtech.common.core.util.introspect.model;

import java.lang.annotation.Annotation;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.ANNOTATED_FIELD,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)
public class AnnotationFieldDecription extends BaseInfrospectionDescription 
{	
	public AnnotationFieldDecription(RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, BaseInfrospectionDescription parentDescription, Object introspectionObject,
			Class<?> introspectedClass) {
		super(rootStaticConfiguration, staticConfiguration, runtimeConfiguration, introspectionType, baseClass,
				parentDescription, introspectionObject, introspectedClass);
		// TODO Auto-generated constructor stub
	}
	private String fieldName = null;
	private Object declaredValue = null;
	private Object runtimeFieldValue = null;
	
	private FieldDescription annotatedFieldDescritpion = null;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Object declaredValue) {
		this.declaredValue = declaredValue;
	}
	public Object getRuntimeFieldValue() {
		return runtimeFieldValue;
	}
	public void setRuntimeFieldValue(Object runtimeFieldValue) {
		this.runtimeFieldValue = runtimeFieldValue;
	}
	public FieldDescription getAnnotatedFieldDescritpion() {
		return annotatedFieldDescritpion;
	}
	public void setAnnotatedFieldDescritpion(FieldDescription annotatedFieldDescritpion) {
		this.annotatedFieldDescritpion = annotatedFieldDescritpion;
	}
	@Override
	public ClassDescriptionBuilderConfiguration initializeStaticConfiguration(
			ClassDescriptionBuilderConfiguration staticConfigiuration) {
		// TODO Auto-generated method stub
		return null;
	}
}
