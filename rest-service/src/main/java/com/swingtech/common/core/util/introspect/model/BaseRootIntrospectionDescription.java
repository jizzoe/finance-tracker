package com.swingtech.common.core.util.introspect.model;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.ClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.config.IntrospectionTypeEnum;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;

public abstract class BaseRootIntrospectionDescription extends BaseInfrospectionDescription {
	
	protected BaseRootIntrospectionDescription() {
		super();
	}

	public BaseRootIntrospectionDescription(RootDescriptionBuildConfiguration rootStaticConfiguration,
			ClassDescriptionBuilderConfiguration staticConfiguration,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, IntrospectionTypeEnum introspectionType,
			ClassDescription baseClass, BaseInfrospectionDescription parentDescription, Object introspectionObject,
			Class<?> introspectedClass) {
		super(rootStaticConfiguration, staticConfiguration, runtimeConfiguration, introspectionType, baseClass,
				parentDescription, introspectionObject, introspectedClass);
		// TODO Auto-generated constructor stub
	}

	public BaseRootIntrospectionDescription(RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, Object introspectionObject,
			Class<?> introspectedClass) {
		super(null, null, runtimeConfiguration, null, null,
				null, introspectionObject, introspectedClass);
	}

}
