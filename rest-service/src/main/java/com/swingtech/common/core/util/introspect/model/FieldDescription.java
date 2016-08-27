package com.swingtech.common.core.util.introspect.model;

import java.lang.annotation.Annotation;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.FIELD,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)
public class FieldDescription extends BaseInfrospectionDescription {
	
	public FieldDescription(RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, BaseInfrospectionDescription parentDescription, Object introspectionObject,
			Class<?> introspectedClass) {
		super(rootStaticConfiguration, staticConfiguration, runtimeConfiguration, introspectionType, baseClass,
				parentDescription, introspectionObject, introspectedClass);
	}

	@Override
	public ClassDescriptionBuilderConfiguration initializeStaticConfiguration(
			ClassDescriptionBuilderConfiguration staticConfigiuration) {
		// TODO Auto-generated method stub
		return null;
	}

}
