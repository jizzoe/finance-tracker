package com.swingtech.common.core.util.introspect.config;

import java.util.HashMap;
import java.util.Map;

public class RuntimeClassDescriptionBuilderConfiguration {
	public final RuntimeClassDescriptionBuilderConfiguration parentRuntimeConfiguration;
	public final Map<String, Object> runtimeProperties = new HashMap<String, Object>();

	public RuntimeClassDescriptionBuilderConfiguration() {
		this(null);
	}

	public RuntimeClassDescriptionBuilderConfiguration(
			RuntimeClassDescriptionBuilderConfiguration parentRuntimeConfiguration) {
		super();
		this.parentRuntimeConfiguration = parentRuntimeConfiguration;
	}

	public RuntimeClassDescriptionBuilderConfiguration getParentRuntimeConfiguration() {
		return parentRuntimeConfiguration;
	}

	public Map<String, Object> getRuntimeProperties() {
		return runtimeProperties;
	}
	
	public void addRuntimeObject(String key, Object value) {
		runtimeProperties.put(key, value);
	}
}
