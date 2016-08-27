package com.swingtech.common.core.util.introspect.model.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.MultiValueMap;

import com.swingtech.common.core.util.introspect.model.BaseInfrospectionDescription;

public class ClassDescriptionBuilderConfiguration {
	private final IntrospectionTypeEnum introspectionType;
	private final Class<? extends BaseInfrospectionDescription> descriptionClassType;
	private final Map<Class<? extends BaseInfrospectionDescription>, ClassDescriptionBuilderConfiguration>  classConfigurationMape = new HashMap<Class<? extends BaseInfrospectionDescription>, ClassDescriptionBuilderConfiguration>();
	private final MultiValueMap<String, Object> annotationFieldMap;
	private final Class<?> javaClassBeingInspected;
	
	private final List<IntrospectionTypeEnum> containsIntrospectionTypes = new ArrayList<IntrospectionTypeEnum>();
	private final List<IntrospectionTypeEnum> containedByIntrospectionTypes = new ArrayList<IntrospectionTypeEnum>();

	public ClassDescriptionBuilderConfiguration(IntrospectionTypeEnum introspectionType, Class<? extends BaseInfrospectionDescription> descriptionClassType, MultiValueMap<String, Object> annotationFieldMap) {
		super();
		this.introspectionType = introspectionType;
		this.descriptionClassType = descriptionClassType;
		this.annotationFieldMap = annotationFieldMap;

		if (this instanceof RootDescriptionBuildConfiguration) {
			this.javaClassBeingInspected = null;
			return;
		}		
		
 		if (annotationFieldMap.get("containsIntrospectionTypes") != null && (!annotationFieldMap.get("containsIntrospectionTypes").isEmpty())) {
 			IntrospectionTypeEnum[] containsTypes = (IntrospectionTypeEnum[]) annotationFieldMap.get("containsIntrospectionTypes").get(0);
 			CollectionUtils.addAll(containsIntrospectionTypes, containsTypes);
 		} 

 		if (annotationFieldMap.get("containedByIntrospectionTypes") != null && (!annotationFieldMap.get("containedByIntrospectionTypes").isEmpty())) {
 			IntrospectionTypeEnum[] containByTypes = (IntrospectionTypeEnum[]) annotationFieldMap.get("containedByIntrospectionTypes").get(0);
 			CollectionUtils.addAll(containedByIntrospectionTypes, containByTypes);
 		}

 		if (annotationFieldMap.get("javaIntrospectionClass") != null && !annotationFieldMap.get("javaIntrospectionClass").isEmpty()) {
 			javaClassBeingInspected = (Class<?>) annotationFieldMap.get("javaIntrospectionClass").get(0);
 		} else {
 			javaClassBeingInspected = null;
 		}
	}
	
	public List<IntrospectionTypeEnum> getContainsintrospectiontypes() {
		return containsIntrospectionTypes;
	}
	
	public List<IntrospectionTypeEnum> getContainedbyintrospectiontypes() {
		return containedByIntrospectionTypes;
	}
	
	protected void addAdditionalSubConfigurationForClass(Class<? extends BaseInfrospectionDescription> descriptionClassType, MultiValueMap<String, Object> childAnnotationFieldMap) {
		if (classConfigurationMape.containsKey(descriptionClassType)) {
			return;
		}

		ClassDescriptionBuilderConfiguration newConfiguration = new ClassDescriptionBuilderConfiguration(introspectionType, descriptionClassType, childAnnotationFieldMap);
		classConfigurationMape.put(descriptionClassType, newConfiguration);
	}

	public IntrospectionTypeEnum getIntrospectionType() {
		return introspectionType;
	}

	public Class<? extends BaseInfrospectionDescription> getDescriptionClassType() {
		return descriptionClassType;
	}

	public Map<Class<? extends BaseInfrospectionDescription>, ClassDescriptionBuilderConfiguration> getClassConfigurationMape() {
		return classConfigurationMape;
	}

	public MultiValueMap<String, Object> getAnnotationFieldMap() {
		return annotationFieldMap;
	}

	public Class<?> getJavaClassBeingInspected() {
		return javaClassBeingInspected;
	}
	
	public ClassDescriptionBuilderConfiguration getConfigurationForDescriptionClass(Class<? extends BaseInfrospectionDescription> descClass) {
		ClassDescriptionBuilderConfiguration descConfig = null;
		
		if (descClass == null) {
			return null;
		}
		
		if (descClass == this.getDescriptionClassType()) {
			return this;
		}
		
		descConfig = getClassConfigurationMape().get(descConfig);
		
		if (descConfig == null) {
			return null;
		}
		
		return descConfig;
	}

	public boolean doesConfigurationExistForDescriptionClass(Class<? extends BaseInfrospectionDescription> descClass) {
		return this.getConfigurationForDescriptionClass(descClass) != null;
	}

	@Override
	public String toString() {
		return "ClassDescriptionBuilderConfiguration [descriptionClassType=" + descriptionClassType + "]";
//		return "ClassDescriptionBuilderConfiguration [introspectionType=" + introspectionType
//				+ ", \n       descriptionClassType=" + descriptionClassType + ", \n       classConfigurationMape="
//				+ classConfigurationMape + ", \n       annotationFieldMap=" + annotationFieldMap + ", \n       javaClassBeingInspected="
//				+ javaClassBeingInspected + ", \n       getIntrospectionType()=" + getIntrospectionType()
//				+ ", \n       getDescriptionClassType()=" + getDescriptionClassType() + ", \n       getClassConfigurationMape()="
//				+ getClassConfigurationMape() + ", \n       getAnnotationFieldMap()=" + getAnnotationFieldMap()
//				+ ", \n       getJavaClassBeingInspected()=" + getJavaClassBeingInspected() + ", \n       getClass()=" + getClass()
//				+ ", \n       hashCode()=" + hashCode() + ", \n       toString()=" + super.toString() + "]";
	}
}
