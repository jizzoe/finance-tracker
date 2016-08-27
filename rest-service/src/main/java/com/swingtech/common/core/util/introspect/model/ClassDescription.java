package com.swingtech.common.core.util.introspect.model;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.core.reader.IntrospectionException;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.CLASS,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)
public class ClassDescription extends BaseRootIntrospectionDescription {
	private final List<FieldDescription> fields = new ArrayList<FieldDescription>();
	private final List<MethodDescription> methods = new ArrayList<MethodDescription>();
	private final List<AnnotationDescription> annotations = new ArrayList<AnnotationDescription>();
	private Class<?> classType = null;
	private Resource classLocation = null;
	private Package packageObj = null;
	private String packageName = null;
	
	private ClassDescription() {
		super();
	}

	public ClassDescription(RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration,
			Object introspectionObject) {
		this(runtimeConfiguration, introspectionObject, null);
	}
	public ClassDescription(RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration,
			Class<?> introspectedClass) {
		this(runtimeConfiguration, null, introspectedClass);
	}
	
	public ClassDescription(RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration,
			Object introspectionObject, Class<?> introspectedClass) {
		super(runtimeConfiguration, introspectionObject, introspectedClass);
		
		this.validateAndSetIntrospectedObjectAndClass(introspectionObject, introspectedClass);
		
		try {
			this.setClassLocation(ClassUtility.getResourceFromClass(getClassType()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		this.setPackageR(this.getClass().getPackage());
		this.setPackageName(this.getPackageObj().getName());
	}

	public void validateAndSetIntrospectedObjectAndClass(Object introspectionObject, Class<?> introspectedClass) {
		if (introspectionObject == null && introspectedClass == null) {
			throw new IllegalArgumentException("Could not create class description  both introspectionObject and introspectedClass were null");
		}
		
		if (introspectionObject != null && introspectedClass != null) {
			if (introspectionObject.getClass() != introspectedClass || !ClassUtility.isAssignable(introspectedClass, introspectionObject.getClass())) {
				throw new IllegalArgumentException("Could not create class description  both introspectionObject and introspectedClass were passed in, but the class in not a subclass of the object");
			}
		} else {
			if (introspectionObject != null) {
				this.setClassType(introspectionObject.getClass());
			} else {
				this.setClassType(introspectedClass.getClass());
			}
		}
	}
	
	@Override
	public ClassDescriptionBuilderConfiguration initializeStaticConfiguration(
			ClassDescriptionBuilderConfiguration staticConfigiuration) {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}

	public Resource getClassLocation() {
		return classLocation;
	}

	public void setClassLocation(Resource classLocation) {
		this.classLocation = classLocation;
	}

	public Package getPackageObj() {
		return packageObj;
	}

	public void setPackageR(Package packageR) {
		this.packageObj = packageR;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<FieldDescription> getFields() {
		return fields;
	}

	public List<MethodDescription> getMethods() {
		return methods;
	}

	public void addMethod(MethodDescription method) {
		methods.add(method);
	}

	public List<AnnotationDescription> getAnnotations() {
		return annotations;
	}

	public void addAnnotation(AnnotationDescription method) {
		annotations.add(method);
	}

	public FieldDescription newChildField() throws IntrospectionException {
		FieldDescription desc = this.newChildInstance(FieldDescription.class);
		fields.add(desc);
		return desc;
	}
	
	public AnnotationDescription newChildAnnotation() throws IntrospectionException {
		AnnotationDescription desc = this.newChildInstance(AnnotationDescription.class);
		annotations.add(desc);
		return desc;
	}
	
	public MethodDescription newChildMethod() throws IntrospectionException {
		MethodDescription desc = this.newChildInstance(MethodDescription.class);
		methods.add(desc);
		return desc;
	}
}
