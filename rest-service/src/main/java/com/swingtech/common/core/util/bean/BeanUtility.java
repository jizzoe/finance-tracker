package com.swingtech.common.core.util.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.model.AnnotationDescription;

public class BeanUtility {

	public static List<AnnotationDescription> getAllMethodAnnotationsForMethod(Method method, Class<? extends Annotation> annotationTypeToCheck) {
		List<AnnotationDescription> annotations = new ArrayList<AnnotationDescription>();

		return annotations;
	}
	public static Map<String, Object> getPropertiesForObject(Object object) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getPropertiesForObject(object, null, true, true);
	}
	
//	public static Object get
	
	public static Map<String, Object> getPropertiesForObject(Object object, Map<Class, String> stringMap, boolean includeLists, boolean includeMaps) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    	String propertyName = null;
    	Object propValue = null;
    	
	    Map<String, Object> result = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(object.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null) {
	        	propertyName = pd.getName();
	        	propValue = reader.invoke(object);
	        	if (stringMap != null && !stringMap.isEmpty()) {
	        		Map<String, Object> newValueMap = getPropertyNameToGetIfThereIsAMatch(propertyName, propValue, stringMap);
	        		
	        		if (newValueMap == null || newValueMap.isEmpty()) {
	        			continue;
	        		}
	        		
	        		propertyName = newValueMap.entrySet().iterator().next().getKey();
	        		propValue = newValueMap.entrySet().iterator().next().getValue();
	        	}
	        	
	        	if (!includeLists && ClassUtility.isCollection(propValue)) {
	        		continue;
	        	}

	        	if (!includeMaps && ClassUtility.isMap(propValue)) {
	        		continue;
	        	}
	        	
        		result.put(propertyName, propValue);
	        }
	    }
	    return result;
	}

	public static Map<String, Object> getPropertyNameToGetIfThereIsAMatch(String propertyName, Object propertyValue, Map<Class, String> stringMap) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, IntrospectionException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		for (Entry<Class, String> entry : stringMap.entrySet()) {
			Class classToCompare = entry.getKey();
			String propNameToGet = entry.getValue();
			
			String newKeyName = "";
			Object newValue = null;
			
			if (propertyValue.getClass().isAssignableFrom(classToCompare)) {
				Map<String, Object> propValueProperties = BeanUtility.getSimpleList(propertyValue);
				newValue = BeanUtils.getProperty(propertyValue, propNameToGet);

				newKeyName = propertyName + "->" + propNameToGet;
				
				if (newValue == null) {
					newKeyName = propertyName + "->" + propNameToGet;
					result.put(newKeyName, "null");
					return result;
				}
				
				System.out.println("     --->Match!:  Properties for this property:  " + propertyName + " of this type:  " + propertyValue.getClass().getName());
				
				for (Entry<String, Object> record : propValueProperties.entrySet()) {
					System.out.println("        -->Property Name:  " + record.getKey() + " = " + record.getValue());
				}

				if (newValue != null) {
					result.put(newKeyName, newValue);
					return result;
				}
			}
		}
		
		result.put(propertyName, propertyValue);
		
		return result;
	}

	public static Map<String, Object> getSimpleList(Object object) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    	String propertyName = null;
    	Object propValue = null;
    	
	    Map<String, Object> result = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(object.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null) {
	        	propertyName = pd.getName();
	        	propValue = reader.invoke(object);
	        	
        		result.put(propertyName, propValue);
	        }
	    }
	    return result;
	}
}
