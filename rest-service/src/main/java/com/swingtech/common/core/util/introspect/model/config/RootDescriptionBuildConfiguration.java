package com.swingtech.common.core.util.introspect.model.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.MultiValueMap;

import com.swingtech.common.core.util.introspect.model.BaseInfrospectionDescription;

public class RootDescriptionBuildConfiguration extends ClassDescriptionBuilderConfiguration {
	private final Map<IntrospectionTypeEnum, ClassDescriptionBuilderConfiguration> staticConfigurationMap;

	public RootDescriptionBuildConfiguration() {
		super(null, null, null);
		// TODO Auto-generated constructor stub
		this.staticConfigurationMap = new HashMap<IntrospectionTypeEnum, ClassDescriptionBuilderConfiguration>();
	}

	List<String> basePackagesToScan = new ArrayList<String>();

	public List<String> getBasePackagesToScan() {
		return basePackagesToScan;
	}

	public void setBasePackagesToScan(List<String> basePackagesToScan) {
		this.basePackagesToScan = basePackagesToScan;
	}

	public void addBasePackagesToScan(List<String> basePackagesToScan) {
		this.basePackagesToScan.addAll(basePackagesToScan);
	}

	public void addBasePackagesToScan(String basePackagesToScan) {
		this.basePackagesToScan.add(basePackagesToScan);
	}

	public Map<IntrospectionTypeEnum, ClassDescriptionBuilderConfiguration> getStaticConfigiurationMap() {
		return staticConfigurationMap;
	}
	
	public ClassDescriptionBuilderConfiguration getonfigurationIntrospectionType(IntrospectionTypeEnum introspectType) {
		if (introspectType == null) {
			return null;
		}
		
		return staticConfigurationMap.get(introspectType);
	}
	
	public ClassDescriptionBuilderConfiguration getConfigurationForIntrospectionTypeAndDescriptionClass(IntrospectionTypeEnum introspectType, Class<? extends BaseInfrospectionDescription> descClass) {
		ClassDescriptionBuilderConfiguration descConfig = null;
		ClassDescriptionBuilderConfiguration childDescConfig = null;
		
		if (introspectType == null || descClass == null) {
			return null;
		}
		
		descConfig = staticConfigurationMap.get(introspectType);
		
		if (descConfig == null) {
			return null;
		}
		
		childDescConfig = descConfig.getConfigurationForDescriptionClass(descClass);
		
		return childDescConfig;
	}

	public ClassDescriptionBuilderConfiguration getConfigurationForIntrospectionType(IntrospectionTypeEnum introspectType) {
		ClassDescriptionBuilderConfiguration descConfig = null;
		ClassDescriptionBuilderConfiguration childDescConfig = null;
		
		if (introspectType == null) {
			return null;
		}
		
		return staticConfigurationMap.get(introspectType);
	}

	public ClassDescriptionBuilderConfiguration getConfigurationForDescriptionClass(Class<? extends BaseInfrospectionDescription> descClass) {
		ClassDescriptionBuilderConfiguration returnConfig = null;
		for (Entry<IntrospectionTypeEnum, ClassDescriptionBuilderConfiguration> entry : staticConfigurationMap.entrySet()) {
			returnConfig = entry.getValue().getConfigurationForDescriptionClass(descClass);
			
			if (returnConfig != null) {
				return returnConfig;
			}
		}
		
		return null;
	}
	
	public boolean doesConfigurationExistForIntrospectionTypeAndClass(IntrospectionTypeEnum introspectType, Class<? extends BaseInfrospectionDescription> descClass) {
		return this.getConfigurationForIntrospectionTypeAndDescriptionClass(introspectType, descClass) != null;
	}

	public boolean doesConfigurationExistForIntrospectionType(IntrospectionTypeEnum introspectType) {
		return this.getConfigurationForIntrospectionType(introspectType) != null;
	}

	public void addNewConfiguration(IntrospectionTypeEnum introspectionType, Class<? extends BaseInfrospectionDescription> descClass, MultiValueMap<String, Object> childAnnotationFieldMap) {
		ClassDescriptionBuilderConfiguration existingConfig = null;
		ClassDescriptionBuilderConfiguration newConfig = null;
		
		if (this.doesConfigurationExistForIntrospectionTypeAndClass(introspectionType, descClass)) {
			return;
		}
		
		existingConfig = this.getConfigurationForIntrospectionTypeAndDescriptionClass(introspectionType, descClass);
		
		if (existingConfig != null) {
			existingConfig.addAdditionalSubConfigurationForClass(descClass, childAnnotationFieldMap);
		}
		
		ClassDescriptionBuilderConfiguration newConfiguration = new ClassDescriptionBuilderConfiguration(introspectionType, descClass, childAnnotationFieldMap);
		
		this.getStaticConfigiurationMap().put(introspectionType, newConfiguration);
	}

	@Override
	public String toString() {
		if (staticConfigurationMap == null || staticConfigurationMap.isEmpty()) {
			return "StringBuffer is empty";
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("staticConfigurationMap:  \n");
		
		for (Entry<IntrospectionTypeEnum, ClassDescriptionBuilderConfiguration> entry : staticConfigurationMap.entrySet()) {
			sb.append("   " + entry.getKey() + " = " + entry.getValue().toString() + "\n");
		}
		
		return sb.toString();
		
//		return "RootDescriptionBuildConfiguration [basePackagesToScan=" + basePackagesToScan
//				+ ", getBasePackagesToScan()=" + getBasePackagesToScan() + ", getStaticConfigiurationMap()="
//				+ getStaticConfigiurationMap() + ", getIntrospectionType()=" + getIntrospectionType()
//				+ ", getDescriptionClassType()=" + getDescriptionClassType() + ", getClassConfigurationMape()="
//				+ getClassConfigurationMape() + ", getAnnotationFieldMap()=" + getAnnotationFieldMap()
//				+ ", getJavaClassBeingInspected()=" + getJavaClassBeingInspected() + ", getClass()=" + getClass()
//				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
