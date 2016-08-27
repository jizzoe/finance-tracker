package com.swingtech.common.core.util.introspect.model;

import java.util.List;

import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.core.reader.IntrospectionException;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;
import com.swingtech.common.core.util.introspect.model.config.RootIntrospectionDescriptionConfigurationFactory;

public abstract class BaseInfrospectionDescription {
	private final static RootDescriptionBuildConfiguration rootStaticConfiguration;
	private ClassDescriptionBuilderConfiguration staticConfiguration;

	protected RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration;
	protected IntrospectionTypeEnum introspectionType;
	protected ClassDescription rootClassDescription;
	protected BaseInfrospectionDescription parentDescription;
	protected Object introspectedObject;
	protected Class<?> introspectedClass;
	
	static {
		RootDescriptionBuildConfiguration rootConfig = null;
		
		try {
			rootConfig = RootIntrospectionDescriptionConfigurationFactory.createAndInitializeRootConfiguration(null, null, false);
		} catch (Exception e) {
			throw new RuntimeException("Could not initialize root configuration.  Error trying to inialize.  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}

		rootStaticConfiguration = rootConfig;
	}
	
	protected BaseInfrospectionDescription() {
		
	}
	
	public BaseInfrospectionDescription(
			RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, 
			IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, 
			BaseInfrospectionDescription parentDescription, 
			Object introspectionObject,
			Class<?> introspectedClass) {
		super();
		
		this.staticConfiguration = staticConfiguration;
		this.runtimeConfiguration = runtimeConfiguration;
		this.introspectionType = introspectionType;
		this.rootClassDescription = baseClass;
		this.parentDescription = parentDescription;
		this.introspectedObject = introspectionObject;
		this.introspectedClass = introspectedClass;
		
		if (this instanceof BaseInfrospectionDescription) {
			return;
		}
	}
	
	public Object getIntrospectedObject() {
		return introspectedObject;
	}

	public ClassDescription getRootClassDescription() {
		return rootClassDescription;
	}

	public BaseInfrospectionDescription getParentDescription() {
		return parentDescription;
	}
	
	public static boolean shouldCreateDescriptionType(List<IntrospectionTypeEnum> introspectionTypesToReturn, boolean createSubLevels) {
		return false;
	}
	
	public abstract ClassDescriptionBuilderConfiguration initializeStaticConfiguration(ClassDescriptionBuilderConfiguration staticConfigiuration);

	public RootDescriptionBuildConfiguration getRootStaticConfiguration() {
		return rootStaticConfiguration;
	}

	public ClassDescriptionBuilderConfiguration getStaticConfiguration() {
		return staticConfiguration;
	}

	public RuntimeClassDescriptionBuilderConfiguration getRuntimeConfiguration() {
		return runtimeConfiguration;
	}

	public static RootDescriptionBuildConfiguration getRootstaticconfiguration() {
		return rootStaticConfiguration;
	}
	
	public RuntimeClassDescriptionBuilderConfiguration getRootRuntimeConfiguration() {
		return rootClassDescription.getRuntimeConfiguration();
	}
	
	public BaseInfrospectionDescription newInstance(IntrospectionTypeEnum introspectionTypeEnum) throws IntrospectionException {
		return this.newChildInstance(this.getRootStaticConfiguration().getConfigurationForIntrospectionType(introspectionTypeEnum).getDescriptionClassType());
	}

	public <T> T newChildInstance(Class<T> newDescriptionClass) throws IntrospectionException {
		BaseInfrospectionDescription newDescriptionInstance;
		Class<? extends BaseInfrospectionDescription> baseINtroClassType;
		
		if (!ClassUtility.isAssignable(BaseInfrospectionDescription.class, newDescriptionClass)) {
			throw new IllegalArgumentException("Could not create new instance.  Class type passed was not a subclass of BaseInfrospectionDescription.  Class type possed in:  " + newDescriptionClass);
		}
		
		try {
			newDescriptionInstance = (BaseInfrospectionDescription) newDescriptionClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IntrospectionException("Error trying to create new description.  error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}

		Class<? extends BaseInfrospectionDescription> newDescriptionClassBase = (Class<? extends BaseInfrospectionDescription>) newDescriptionClass;
		
		System.out.println("\n\n----> Config Map:\n" + this.getRootStaticConfiguration().toString() + "\n\n");
		
		ClassDescriptionBuilderConfiguration configForNewDescription = this.getRootStaticConfiguration().getConfigurationForDescriptionClass(newDescriptionClassBase);
		
		newDescriptionInstance.staticConfiguration = configForNewDescription;;
		newDescriptionInstance.runtimeConfiguration = new RuntimeClassDescriptionBuilderConfiguration(this.getRuntimeConfiguration());
		newDescriptionInstance.introspectionType = configForNewDescription.getIntrospectionType();
		newDescriptionInstance.rootClassDescription = rootClassDescription;
		newDescriptionInstance.parentDescription = this;
		newDescriptionInstance.introspectedObject = this.introspectedObject;
		newDescriptionInstance.introspectedClass = this.introspectedClass;
		
		return (T) newDescriptionInstance;
	}

	public IntrospectionTypeEnum getIntrospectionType() {
		return introspectionType;
	}

	public Class<?> getIntrospectedClass() {
		return introspectedClass;
	}
}
