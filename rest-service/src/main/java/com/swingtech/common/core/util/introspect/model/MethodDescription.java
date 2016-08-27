package com.swingtech.common.core.util.introspect.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.core.reader.IntrospectionException;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.METHOD,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)
public class MethodDescription extends BaseInfrospectionDescription {
	private List<ParameterDescription> parameters = new ArrayList<ParameterDescription>();
	private DescriptionCollectionMultiValue<String, AnnotationDescription> annotations = new DescriptionCollectionMultiValue<String, AnnotationDescription>();
	protected String methodName;
	protected String declaringClassName;
	protected String returnTypeName;
	protected ClassLoader classLoader;
	protected List<MethodAccessModifiersEnum> accessModifiers = null;

	public MethodDescription() {
		super();
	}

	public MethodDescription(RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, BaseInfrospectionDescription parentDescription, Object introspectionObject,
			Class<?> introspectedClass) {
		super(rootStaticConfiguration, staticConfiguration, runtimeConfiguration, introspectionType, baseClass,
				parentDescription, introspectionObject, introspectedClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ClassDescriptionBuilderConfiguration initializeStaticConfiguration(
			ClassDescriptionBuilderConfiguration staticConfigiuration) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addParameter(ParameterDescription parameter) {
		parameters.add(parameter);
	}

	public void visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDeclaringClassName() {
		return declaringClassName;
	}

	public void setDeclaringClassName(String declaringClassName) {
		this.declaringClassName = declaringClassName;
	}

	public String getReturnTypeName() {
		return returnTypeName;
	}

	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}

	public List<MethodAccessModifiersEnum> getAccessModifiers() {
		return accessModifiers;
	}

	public void setAccessModifiers(List<MethodAccessModifiersEnum> accessModifiers) {
		this.accessModifiers = accessModifiers;
	}

	public void setAccessModifiers(int access) {
		this.accessModifiers = MethodAccessModifiersEnum.getAccessModifiers(access);
	}

	public List<ParameterDescription> getParameters() {
		return parameters;
	}
	
	public void addAnnotation(Class<?> annotationClass, AnnotationDescription annotationDescription) {
		annotations.add(annotationClass.getName(), annotationDescription);
	}
	

	public AnnotationDescription newChildAnnotation() throws IntrospectionException {
		AnnotationDescription desc = this.newChildInstance(AnnotationDescription.class);
		return desc;
	}

}
