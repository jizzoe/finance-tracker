package com.swingtech.common.core.util.introspect.model.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import com.swingtech.common.core.util.introspect.core.ClassPathScanningINputs;
import com.swingtech.common.core.util.introspect.core.ClassPathScanningINputs.MatchTypeEnum;
import com.swingtech.common.core.util.introspect.model.BaseInfrospectionDescription;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;
import com.swingtech.common.core.util.introspect.core.ClassUtility;

public class RootIntrospectionDescriptionConfigurationFactory {
	private static final String DEFAULT_BASE_PACKAGE_FOR_DESCRIPTIONS = "com.swingtech.common.core.util.introspect.model";

	public static RootDescriptionBuildConfiguration createAndInitializeRootConfiguration() throws Exception {
		return createAndInitializeRootConfiguration(null, null, false);
	}

	public static RootDescriptionBuildConfiguration createAndInitializeRootConfiguration(RootDescriptionBuildConfiguration existingRootConfig, List<String> additionalBasePackages, boolean overrideBasePackages) throws Exception {
		RootDescriptionBuildConfiguration rootConfig = getOrCreateRootConfiguration(existingRootConfig);
		initializeRootConfiguration(rootConfig, additionalBasePackages, overrideBasePackages);
		initiaizeChildIntrospectionConfigurations(rootConfig);
		
		return rootConfig;
	}
	
	private static RootDescriptionBuildConfiguration getOrCreateRootConfiguration(RootDescriptionBuildConfiguration newRootStaticConfiguration) {
		if (newRootStaticConfiguration != null) {
			return newRootStaticConfiguration;
		}
		else {
			return new RootDescriptionBuildConfiguration();
		}
	}
	private static void initializeRootConfiguration(RootDescriptionBuildConfiguration rootConfig, List<String> additionalBasePackages, boolean overrideBasePackages) {
		rootConfig.addBasePackagesToScan(DEFAULT_BASE_PACKAGE_FOR_DESCRIPTIONS);
		
		if (additionalBasePackages == null || additionalBasePackages.isEmpty()) {
			return;
		}
		
		if (overrideBasePackages) {
			rootConfig.getBasePackagesToScan().clear();
			rootConfig.setBasePackagesToScan(additionalBasePackages);
		} else {
			rootConfig.addBasePackagesToScan(additionalBasePackages);
		}
	}
	private static void initiaizeChildIntrospectionConfigurations(RootDescriptionBuildConfiguration rootConfig) throws Exception {
		ClassPathScanningINputs inputs = new ClassPathScanningINputs();


		inputs.addIncludeMatchingBaseClass(BaseInfrospectionDescription.class);
		inputs.addIncludeMatchingAnnotation(IntrospectDescription.class);
		inputs.setIncludeMatchType(MatchTypeEnum.AND_ALL_VALUES);
		inputs.addBasePackagse(rootConfig.getBasePackagesToScan());
		
		 Map<Class<?>, AnnotationMetadata> subClassList = null;

		 subClassList = ClassUtility.scanForClassesOnClasspath(inputs);
		 
		 int index = 0;

		for(Entry<Class<?>, AnnotationMetadata> entry : subClassList.entrySet()) {
			index++;
			AnnotationMetadata classMetaData = entry.getValue();
			Class<?> clazz = entry.getKey();
			
			System.out.println(ClassUtility.isAssignable(BaseInfrospectionDescription.class, clazz));
			
			System.out.println(rootConfig.toString());
			
			if (entry.getKey() == null || 
					!ClassUtility.isAssignable(BaseInfrospectionDescription.class, clazz) 
					|| classMetaData == null || classMetaData.getAnnotationTypes() == null || classMetaData.getAnnotationTypes().isEmpty()) {
				continue;
			}
			
			Class<? extends BaseInfrospectionDescription> descriptionClass = (Class<? extends BaseInfrospectionDescription>) entry.getKey();
			
			for (String annotationClassName : classMetaData.getAnnotationTypes()) {
				
				if (!ClassUtility.isAnnotation(annotationClassName) || !ClassUtility.isClassOfType(annotationClassName, IntrospectDescription.class)) {
					continue;
				}
				
				MultiValueMap<String, Object> annotationFieldMap = classMetaData.getAllAnnotationAttributes(annotationClassName);

				if (annotationFieldMap == null) {
					continue;
				}
								
				List<Object> introTypeValues = annotationFieldMap.get("introspectType");

				if (annotationFieldMap == null) {
					continue;
				}
				
				IntrospectionTypeEnum introspectType = (IntrospectionTypeEnum) introTypeValues.get(0);
				
				rootConfig.addNewConfiguration(introspectType, descriptionClass, annotationFieldMap);
			}
			
		}   
	}
	
}
