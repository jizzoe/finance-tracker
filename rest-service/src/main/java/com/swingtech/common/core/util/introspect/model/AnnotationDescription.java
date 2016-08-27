package com.swingtech.common.core.util.introspect.model;

import java.lang.annotation.Annotation;

import org.springframework.core.annotation.AnnotationAttributes;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.ANNOTATION,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)

public class AnnotationDescription extends BaseInfrospectionDescription {
	private Annotation annotation = null;
	private Class<? extends Annotation> annotationType = null;
	private String annotationName = null;
	private boolean isVisible;
	private AnnotationAttributes annotationAtributes;

	protected AnnotationDescription() {
		super();
	}
	
	public AnnotationDescription(RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, BaseInfrospectionDescription parentDescription, Object introspectionObject,
			Class<?> introspectedClass) {
		super(rootStaticConfiguration, staticConfiguration, runtimeConfiguration, introspectionType, baseClass,
				parentDescription, introspectionObject, introspectedClass);
	}

	protected final DescriptionCollection<String, AnnotationFieldDecription> annotationFieldDescriptions = new DescriptionCollection<String, AnnotationFieldDecription>();

	@Override
	public ClassDescriptionBuilderConfiguration initializeStaticConfiguration(
			ClassDescriptionBuilderConfiguration staticConfigiuration) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
