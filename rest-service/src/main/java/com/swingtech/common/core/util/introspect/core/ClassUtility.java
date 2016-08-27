package com.swingtech.common.core.util.introspect.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.util.ClassUtil;

public class ClassUtility {
	public static com.fasterxml.jackson.databind.util.ClassUtil providerSpring;
	public static ClassUtils providerJackson;
//	public static org.apache.commons.lang.ClassUtils providerApache;
	
	public static final SpringFileUtilsWrapper springWrapperOnlyForReferenc = SpringFileUtilsWrapper.getInstanceinstance();
	public static final JacksonClassUtilsWrapper jacksonWrapperOnlyForReferenc = JacksonClassUtilsWrapper.getInstanceinstance();
	public static final ApacheClassUtilsWrapper apacheWrapperOnlyForReferenc = ApacheClassUtilsWrapper.getInstanceinstance(); 
	
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	public static ClassLoader getCurrentClassLoader() {
		return ClassUtility.class.getClassLoader();
	}

	public static Class<?> getClassFromString(String fileName) throws ClassNotFoundException {
		ClassLoader loader = ClassUtility.getCurrentClassLoader();
		return Class.forName(fileName, false, loader);
	}
	
	public static boolean classIsSuperClass(Class<?> testClass, Class<?> superClass) {
		return true;
	}

	public static Map<Class<?>, AnnotationMetadata> getAllParentClasses(Class<?> superClass, String... basePackages) throws ClassNotFoundException, LinkageError {
		ClassPathScanningINputs inputs = new ClassPathScanningINputs();
		inputs.addIncludeMatchingBaseClass(superClass);

		inputs.addBasePackagse(basePackages);

		return scanForClassesOnClasspath(inputs);
	}

	public static Map<Class<?>, AnnotationMetadata> getAllChildClasses(Class<?> superClass, String... basePackages) throws ClassNotFoundException, LinkageError {
		ClassPathScanningINputs inputs = new ClassPathScanningINputs();
		inputs.addIncludeMatchingBaseClass(superClass);
		
		inputs.addBasePackagse(basePackages);
			
		return scanForClassesOnClasspath(inputs);
	}

	public static boolean isAnnotation(Class<?> classToCheck) {
		return isAssignable(Annotation.class, classToCheck);
	}

	public static boolean isAnnotation(String className) throws ClassNotFoundException, LinkageError {
		Class<?> clazz = forName(className); 
		return isAnnotation(clazz);
	}
	
	public static <T> boolean isClassOfType(String annotationClassName, Class<T> annotationTypeToCheck) throws ClassNotFoundException, LinkageError {
		Class<?> classToCheck = forName(annotationClassName);
		
		return classToCheck == annotationTypeToCheck;
	}

	public static Resource getResourceFromClass(Class clazz) throws IOException {
		return getResourceFromClass(clazz.getName());
	}
	
	public static Resource getResourceFromClass(String className) throws IOException {

		String resourcePath = ClassUtils.convertClassNameToResourcePath(className);
		
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
				resourcePath + ".class";
		
		System.out.println("packageSearchPath:  " + packageSearchPath);
		
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
				getCurrentClassLoader());
		Resource[] resources = resolver.getResources(packageSearchPath);
		
		if (resources == null || resources.length <= 0) {
			return null;
		}
		
		return resources[0];
	}
	
	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}

	public static boolean isMap(Object obj) {
		return obj instanceof Map;
	}

	public static boolean isCollectionOrMap(Object obj) {
		return obj instanceof Collection || obj instanceof Map;
	}

	public static <T extends Annotation> boolean hasFieldsWithAnnotation(Class<?> testClass, Class<T> annotationClass) {
		return hasFieldsWithAnnotationAndBaseClass(testClass, null, annotationClass);
	}

	public static <T extends Annotation> boolean hasFieldsWithBaseClass(Class<?> testClass, Class<T> baseClassToCheck) {
		return hasFieldsWithAnnotationAndBaseClass(testClass, baseClassToCheck, null);
	}

	public static boolean hasFieldsWithAnnotationAndBaseClass(Class<?> testClass, Class<? extends Annotation> annotationClassToCheck, Class<?> baseClassToCheck) {
		List<Field> fieldList = getDeclaredFieldsWithAnnotationAndBaseClass(testClass, annotationClassToCheck, baseClassToCheck);
		
		return fieldList != null && !fieldList.isEmpty();

	}
	
	
	
	public static <T extends Annotation> List<Field> getDeclaredFieldsWithAnnotation(Class<?> testClass, Class<T> annotationClass) {
		return getDeclaredFieldsWithAnnotationAndBaseClass(testClass, null, annotationClass);
	}

	public static <T extends Annotation> List<Field> getDeclaredFieldsWithBaseClass(Class<?> testClass, Class<T> baseClassToCheck) {
		return getDeclaredFieldsWithAnnotationAndBaseClass(testClass, baseClassToCheck, null);
	}
	
	public static List<Field> getDeclaredFieldsWithAnnotationAndBaseClass(Class<?> testClass, Class<? extends Annotation> annotationClassToCheck, Class<?> baseClassToCheck) {
		List<Field> returnFieldList = new ArrayList<Field>();
		Field[] fieldArray = null;
		boolean addField = false;
		
		if (testClass == null || (annotationClassToCheck == null && baseClassToCheck == null)) {
			return null;
		}

		fieldArray = testClass.getDeclaredFields();
		
		if (fieldArray == null || fieldArray.length <= 0) {
			return null;
		}

		for (Field childField : fieldArray) {
			Annotation fieldAnnotation = childField.getAnnotation(annotationClassToCheck);
			
			if (annotationClassToCheck != null && baseClassToCheck != null) {
				addField = fieldAnnotation != null && baseClassToCheck.isAssignableFrom(childField.getType()); 
			} else {
				if (annotationClassToCheck != null) {
					addField = fieldAnnotation != null;
				}
				else if (baseClassToCheck != null) {
					addField = baseClassToCheck.isAssignableFrom(childField.getType());
				}
			}
			
			if (addField) {
				returnFieldList.add(childField);
			}
		}
		
		if (returnFieldList.isEmpty()) {
			return null;
		}
		
		return returnFieldList;
	}
	
	public static Class<?> getCollectionElementClass(Object obj, String fieldName) throws NoSuchFieldException, SecurityException {
		Field stringListField = obj.getClass().getDeclaredField(fieldName);
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> collectionElementClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        System.out.println(collectionElementClass); // class java.lang.String.
        
        return collectionElementClass;
	}

	public static <T> boolean isClassOfType(Class<?> classToChecke, Class<T> annotationTypeToCheck) throws ClassNotFoundException, LinkageError {
		return classToChecke == annotationTypeToCheck;
	}
	
	public static Map<Class<?>, AnnotationMetadata> getAllClassesWithTheAnnotation(Class<? extends Annotation> classToAdd, String... basePackages) throws ClassNotFoundException, LinkageError {
		ClassPathScanningINputs inputs = new ClassPathScanningINputs();
		inputs.addBasePackagse(basePackages);
		inputs.addIncludeMatchingAnnotation(classToAdd);
			
		return scanForClassesOnClasspath(inputs);
	}

	public static Map<Class<?>, AnnotationMetadata> scanForClassesOnClasspath(ClassPathScanningINputs inputs) throws ClassNotFoundException, LinkageError {
		Map<Class<?>, AnnotationMetadata> classList = new HashMap<Class<?>, AnnotationMetadata>(); 
		Class<?> resultClass =  null;

		ClassPathBeanDefinitionScanner classPathScanner = SimpleClassPathScanningFactory.createClassPathScanner(inputs);
		
		classPathScanner.scan(inputs.getBasepackagestoscan().toArray(new String[inputs.getBasepackagestoscan().size()]));
		
		String[] beanNames = classPathScanner.getRegistry().getBeanDefinitionNames();
		
		BeanDefinition beanDefinition = null;
		
		for(String beanName : beanNames) {
			beanDefinition = classPathScanner.getRegistry().getBeanDefinition(beanName);
			
			if (beanDefinition.getBeanClassName().startsWith("org.spring")) {
				continue;
			}
			
			if (beanDefinition instanceof ScannedGenericBeanDefinition) {
				ScannedGenericBeanDefinition scannedBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
				resultClass = forName(scannedBeanDefinition.getMetadata().getClassName());
				
				AnnotationMetadata metaData = scannedBeanDefinition.getMetadata();
				
				Set<String> annotations = metaData.getAnnotationTypes();
				
				System.out.println("Printing all the annotation info for this bean class:  " + resultClass);
				
				for(String type : annotations) {
					System.out.println("   Annotation type:  " + type);
					System.out.println("   Annotation property Values:  " + type);
					
					for (Entry<String, List<Object>> entry : metaData.getAllAnnotationAttributes(type).entrySet()) {
						System.out.println("      property key:  " + entry.getKey());
						System.out.println("      property values:  ");
						for (Object value : entry.getValue()) {
							System.out.println("         " + value + " - " + value.getClass());
						}
					}
				}

				System.out.println("Printing all the annotated methods info for this bean class:  " + resultClass);
				
				for(String type : annotations) {
					System.out.println("   Annotation type:  " + type);
					System.out.println("   Annotation property Values:  " + type);
					
					for (Entry<String, List<Object>> entry : metaData.getAllAnnotationAttributes(type).entrySet()) {
						System.out.println("      property key:  " + entry.getKey());
						System.out.println("      property values:  ");
						for (Object value : entry.getValue()) {
							System.out.println("         " + value + " - " + value.getClass());
						}
					}
				}

				classList.put(resultClass, metaData);
			} else {
				resultClass = forName(beanDefinition.getBeanClassName());
				classList.put(resultClass, null);
			}
			
			beanDefinition.getClass();
		}
		
		return classList;
	}

	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		return ClassUtils.forName(name, classLoader);
	}
	
	public static Class<?> forName(String name) throws ClassNotFoundException, LinkageError {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		return ClassUtils.forName(name, classLoader);
	}

	public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
		return ClassUtils.isAssignable(lhsType, rhsType);
	}

	public boolean isAssignableValue(Class<?> type, Object value) {
		return ClassUtils.isAssignableValue(type, value);
	}

	public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
		return ClassUtil.findSuperTypes(cls, endBefore);
	}

	public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
		return ClassUtil.findSuperTypes(cls, endBefore, result);
	}
	
	public static boolean isSuperClassOf (Class<?> lhsType, Class<?> rhsType, boolean checkOnlyForTopLevelSuperType) {
		return isSuperClassOrSubclassOf(lhsType, rhsType, true, checkOnlyForTopLevelSuperType);
	}

	public static boolean isSubClassOf (Class<?> lhsType, Class<?> rhsType, boolean checkOnlyForTopLevelSuperType) {
		return isSuperClassOrSubclassOf(lhsType, rhsType, false, checkOnlyForTopLevelSuperType);
	}

	private static boolean isSuperClassOrSubclassOf (Class<?> lhsType, Class<?> rhsType, boolean checkForSuperClass, boolean checkOnlyForTopLevelSuperType) {
		Class<?> classToCheckForSuper = null;
		Class<?> classToCheckForSub = null;
		
		// to check fo super or sub just switch the ordwer around
		if (checkForSuperClass) {
			classToCheckForSuper = lhsType;
			classToCheckForSub = rhsType;
		} else {
			classToCheckForSuper = rhsType;
			classToCheckForSub = lhsType;
		}
		
		List<Class<?>> superTypes = ClassUtil.findSuperTypes(classToCheckForSub, Object.class);
		
		if (superTypes == null || superTypes.isEmpty()) {
			return false;
		}
		
		if (checkOnlyForTopLevelSuperType) {
			return classToCheckForSuper == superTypes.get(0);
		}
		
		for (Class<?> clazz : superTypes) {
			if (classToCheckForSuper == clazz) {
				return true;
			}
		}
		
		return false;
	}
}
