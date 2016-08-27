package com.swingtech.common.core.util.introspect.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.springframework.util.ClassUtils;

public class ApacheClassUtilsWrapper  {
	private static final ApacheClassUtilsWrapper instance = new ApacheClassUtilsWrapper();
	
	public static ApacheClassUtilsWrapper getInstanceinstance() {
		return instance;
	}
	
	public static ClassLoader getDefaultClassLoader() {
		return ClassUtils.getDefaultClassLoader();
	}

	public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
		return ClassUtils.overrideThreadContextClassLoader(classLoaderToUse);
	}

	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		return ClassUtils.forName(name, classLoader);
	}

	public static Class<?> resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
		return ClassUtils.resolveClassName(className, classLoader);
	}

	public static Class<?> resolvePrimitiveClassName(String name) {
		return ClassUtils.resolvePrimitiveClassName(name);
	}

	public static boolean isPresent(String className, ClassLoader classLoader) {
		return ClassUtils.isPresent(className, classLoader);
	}

	public static Class<?> getUserClass(Object instance) {
		return ClassUtils.getUserClass(instance);
	}

	public static Class<?> getUserClass(Class<?> clazz) {
		return ClassUtils.getUserClass(clazz);
	}

	public static boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.isCacheSafe(clazz, classLoader);
	}

	public static String getShortName(String className) {
		return ClassUtils.getShortName(className);
	}

	public static String getShortName(Class<?> clazz) {
		return ClassUtils.getShortName(clazz);
	}

	public static String getShortNameAsProperty(Class<?> clazz) {
		return ClassUtils.getShortNameAsProperty(clazz);
	}

	public static String getClassFileName(Class<?> clazz) {
		return ClassUtils.getClassFileName(clazz);
	}

	public static String getPackageName(Class<?> clazz) {
		return ClassUtils.getPackageName(clazz);
	}

	public static String getPackageName(String fqClassName) {
		return ClassUtils.getPackageName(fqClassName);
	}

	public static String getQualifiedName(Class<?> clazz) {
		return ClassUtils.getQualifiedName(clazz);
	}

	public static String getQualifiedMethodName(Method method) {
		return ClassUtils.getQualifiedMethodName(method);
	}

	public static String getDescriptiveType(Object value) {
		return ClassUtils.getDescriptiveType(value);
	}

	public static boolean matchesTypeName(Class<?> clazz, String typeName) {
		return ClassUtils.matchesTypeName(clazz, typeName);
	}

	public static boolean hasConstructor(Class<?> clazz, Class<?>... paramTypes) {
		return ClassUtils.hasConstructor(clazz, paramTypes);
	}

	public static <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
		return ClassUtils.getConstructorIfAvailable(clazz, paramTypes);
	}

	public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.hasMethod(clazz, methodName, paramTypes);
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.getMethod(clazz, methodName, paramTypes);
	}

	public static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.getMethodIfAvailable(clazz, methodName, paramTypes);
	}

	public static int getMethodCountForName(Class<?> clazz, String methodName) {
		return ClassUtils.getMethodCountForName(clazz, methodName);
	}

	public static boolean hasAtLeastOneMethodWithName(Class<?> clazz, String methodName) {
		return ClassUtils.hasAtLeastOneMethodWithName(clazz, methodName);
	}

	public static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
		return ClassUtils.getMostSpecificMethod(method, targetClass);
	}

	public static boolean isUserLevelMethod(Method method) {
		return ClassUtils.isUserLevelMethod(method);
	}

	public static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... args) {
		return ClassUtils.getStaticMethod(clazz, methodName, args);
	}

	public static boolean isPrimitiveWrapper(Class<?> clazz) {
		return ClassUtils.isPrimitiveWrapper(clazz);
	}

	public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
		return ClassUtils.isPrimitiveOrWrapper(clazz);
	}

	public static boolean isPrimitiveArray(Class<?> clazz) {
		return ClassUtils.isPrimitiveArray(clazz);
	}

	public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
		return ClassUtils.isPrimitiveWrapperArray(clazz);
	}

	public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
		return ClassUtils.resolvePrimitiveIfNecessary(clazz);
	}

	public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
		return ClassUtils.isAssignable(lhsType, rhsType);
	}

	public static boolean isAssignableValue(Class<?> type, Object value) {
		return ClassUtils.isAssignableValue(type, value);
	}

	public static String convertResourcePathToClassName(String resourcePath) {
		return ClassUtils.convertResourcePathToClassName(resourcePath);
	}

	public static String convertClassNameToResourcePath(String className) {
		return ClassUtils.convertClassNameToResourcePath(className);
	}

	public static String addResourcePathToPackagePath(Class<?> clazz, String resourceName) {
		return ClassUtils.addResourcePathToPackagePath(clazz, resourceName);
	}

	public static String classPackageAsResourcePath(Class<?> clazz) {
		return ClassUtils.classPackageAsResourcePath(clazz);
	}

	public static String classNamesToString(Class<?>... classes) {
		return ClassUtils.classNamesToString(classes);
	}

	public static String classNamesToString(Collection<Class<?>> classes) {
		return ClassUtils.classNamesToString(classes);
	}

	public static Class<?>[] toClassArray(Collection<Class<?>> collection) {
		return ClassUtils.toClassArray(collection);
	}

	public static Class<?>[] getAllInterfaces(Object instance) {
		return ClassUtils.getAllInterfaces(instance);
	}

	public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
		return ClassUtils.getAllInterfacesForClass(clazz);
	}

	public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.getAllInterfacesForClass(clazz, classLoader);
	}

	public static Set<Class<?>> getAllInterfacesAsSet(Object instance) {
		return ClassUtils.getAllInterfacesAsSet(instance);
	}

	public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz) {
		return ClassUtils.getAllInterfacesForClassAsSet(clazz);
	}

	public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.getAllInterfacesForClassAsSet(clazz, classLoader);
	}

	public static Class<?> createCompositeInterface(Class<?>[] interfaces, ClassLoader classLoader) {
		return ClassUtils.createCompositeInterface(interfaces, classLoader);
	}

	public static Class<?> determineCommonAncestor(Class<?> clazz1, Class<?> clazz2) {
		return ClassUtils.determineCommonAncestor(clazz1, clazz2);
	}

	public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.isVisible(clazz, classLoader);
	}

	public static boolean isCglibProxy(Object object) {
		return ClassUtils.isCglibProxy(object);
	}

	public static boolean isCglibProxyClass(Class<?> clazz) {
		return ClassUtils.isCglibProxyClass(clazz);
	}

	public static boolean isCglibProxyClassName(String className) {
		return ClassUtils.isCglibProxyClassName(className);
	}
}