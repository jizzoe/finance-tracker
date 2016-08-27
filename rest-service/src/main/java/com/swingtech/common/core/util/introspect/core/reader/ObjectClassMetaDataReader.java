package com.swingtech.common.core.util.introspect.core.reader;

import java.io.IOException;

import org.springframework.core.type.AnnotationMetadata;

import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.model.ClassDescription;

public class ObjectClassMetaDataReader {
	public ClassDescription getClassMetaDataForObject(Object object) {
		RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration = new RuntimeClassDescriptionBuilderConfiguration();
		ClassDescription classDescription = new ClassDescription(runtimeConfiguration, object);
		
		IntrospectionMetaDataReader reader = null;
		
		try {
			reader = new IntrospectionMetaDataReader(object, ClassUtility.getCurrentClassLoader(), runtimeConfiguration, classDescription);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnnotationMetadata annotatedMetaData = reader.getAnnotationMetadata();
		
		return classDescription;
	}
	
	public ClassDescription getClassMetaDataForClass(Class<?> classToInspect) {
		RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration = new RuntimeClassDescriptionBuilderConfiguration();
		ClassDescription classDescription = new ClassDescription(runtimeConfiguration, classToInspect);
		
		IntrospectionMetaDataReader reader = null;
		
		try {
			reader = new IntrospectionMetaDataReader(classToInspect, ClassUtility.getCurrentClassLoader(), runtimeConfiguration, classDescription);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnnotationMetadata annotatedMetaData = reader.getAnnotationMetadata();
		
		return classDescription;
	}
}
