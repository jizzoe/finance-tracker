package com.swingtech.common.core.util.introspect.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.springframework.util.ClassUtils;

public class SpringFileUtilsWrapper {
	private static final SpringFileUtilsWrapper instance = new SpringFileUtilsWrapper();
	
	public static SpringFileUtilsWrapper getInstanceinstance() {
		return instance;
	}
	
	public ClassLoader getDefaultClassLoader() {
		return ClassUtils.getDefaultClassLoader();
	}

	public ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
		return ClassUtils.overrideThreadContextClassLoader(classLoaderToUse);
	}

	public Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		return ClassUtils.forName(name, classLoader);
	}

	public Class<?> resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
		return ClassUtils.resolveClassName(className, classLoader);
	}

	public Class<?> resolvePrimitiveClassName(String name) {
		return ClassUtils.resolvePrimitiveClassName(name);
	}

	public boolean isPresent(String className, ClassLoader classLoader) {
		return ClassUtils.isPresent(className, classLoader);
	}

	public Class<?> getUserClass(Object instance) {
		return ClassUtils.getUserClass(instance);
	}

	public Class<?> getUserClass(Class<?> clazz) {
		return ClassUtils.getUserClass(clazz);
	}

	public boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.isCacheSafe(clazz, classLoader);
	}

	public String getShortName(String className) {
		return ClassUtils.getShortName(className);
	}

	public String getShortName(Class<?> clazz) {
		return ClassUtils.getShortName(clazz);
	}

	public String getShortNameAsProperty(Class<?> clazz) {
		return ClassUtils.getShortNameAsProperty(clazz);
	}

	public String getClassFileName(Class<?> clazz) {
		return ClassUtils.getClassFileName(clazz);
	}

	public String getPackageName(Class<?> clazz) {
		return ClassUtils.getPackageName(clazz);
	}

	public String getPackageName(String fqClassName) {
		return ClassUtils.getPackageName(fqClassName);
	}

	public String getQualifiedName(Class<?> clazz) {
		return ClassUtils.getQualifiedName(clazz);
	}

	public String getQualifiedMethodName(Method method) {
		return ClassUtils.getQualifiedMethodName(method);
	}

	public String getDescriptiveType(Object value) {
		return ClassUtils.getDescriptiveType(value);
	}

	public boolean matchesTypeName(Class<?> clazz, String typeName) {
		return ClassUtils.matchesTypeName(clazz, typeName);
	}

	public boolean hasConstructor(Class<?> clazz, Class<?>... paramTypes) {
		return ClassUtils.hasConstructor(clazz, paramTypes);
	}

	public <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
		return ClassUtils.getConstructorIfAvailable(clazz, paramTypes);
	}

	public boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.hasMethod(clazz, methodName, paramTypes);
	}

	public Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.getMethod(clazz, methodName, paramTypes);
	}

	public Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return ClassUtils.getMethodIfAvailable(clazz, methodName, paramTypes);
	}

	public int getMethodCountForName(Class<?> clazz, String methodName) {
		return ClassUtils.getMethodCountForName(clazz, methodName);
	}

	public boolean hasAtLeastOneMethodWithName(Class<?> clazz, String methodName) {
		return ClassUtils.hasAtLeastOneMethodWithName(clazz, methodName);
	}

	public Method getMostSpecificMethod(Method method, Class<?> targetClass) {
		return ClassUtils.getMostSpecificMethod(method, targetClass);
	}

	public boolean isUserLevelMethod(Method method) {
		return ClassUtils.isUserLevelMethod(method);
	}

	public Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... args) {
		return ClassUtils.getStaticMethod(clazz, methodName, args);
	}

	public boolean isPrimitiveWrapper(Class<?> clazz) {
		return ClassUtils.isPrimitiveWrapper(clazz);
	}

	public boolean isPrimitiveOrWrapper(Class<?> clazz) {
		return ClassUtils.isPrimitiveOrWrapper(clazz);
	}

	public boolean isPrimitiveArray(Class<?> clazz) {
		return ClassUtils.isPrimitiveArray(clazz);
	}

	public boolean isPrimitiveWrapperArray(Class<?> clazz) {
		return ClassUtils.isPrimitiveWrapperArray(clazz);
	}

	public Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
		return ClassUtils.resolvePrimitiveIfNecessary(clazz);
	}

	public boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
		return ClassUtils.isAssignable(lhsType, rhsType);
	}

	public boolean isAssignableValue(Class<?> type, Object value) {
		return ClassUtils.isAssignableValue(type, value);
	}

	public String convertResourcePathToClassName(String resourcePath) {
		return ClassUtils.convertResourcePathToClassName(resourcePath);
	}

	public String convertClassNameToResourcePath(String className) {
		return ClassUtils.convertClassNameToResourcePath(className);
	}

	public String addResourcePathToPackagePath(Class<?> clazz, String resourceName) {
		return ClassUtils.addResourcePathToPackagePath(clazz, resourceName);
	}

	public String classPackageAsResourcePath(Class<?> clazz) {
		return ClassUtils.classPackageAsResourcePath(clazz);
	}

	public String classNamesToString(Class<?>... classes) {
		return ClassUtils.classNamesToString(classes);
	}

	public String classNamesToString(Collection<Class<?>> classes) {
		return ClassUtils.classNamesToString(classes);
	}

	public Class<?>[] toClassArray(Collection<Class<?>> collection) {
		return ClassUtils.toClassArray(collection);
	}

	public Class<?>[] getAllInterfaces(Object instance) {
		return ClassUtils.getAllInterfaces(instance);
	}

	public Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
		return ClassUtils.getAllInterfacesForClass(clazz);
	}

	public Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.getAllInterfacesForClass(clazz, classLoader);
	}

	public Set<Class<?>> getAllInterfacesAsSet(Object instance) {
		return ClassUtils.getAllInterfacesAsSet(instance);
	}

	public Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz) {
		return ClassUtils.getAllInterfacesForClassAsSet(clazz);
	}

	public Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.getAllInterfacesForClassAsSet(clazz, classLoader);
	}

	public Class<?> createCompositeInterface(Class<?>[] interfaces, ClassLoader classLoader) {
		return ClassUtils.createCompositeInterface(interfaces, classLoader);
	}

	public Class<?> determineCommonAncestor(Class<?> clazz1, Class<?> clazz2) {
		return ClassUtils.determineCommonAncestor(clazz1, clazz2);
	}

	public boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
		return ClassUtils.isVisible(clazz, classLoader);
	}

	public boolean isCglibProxy(Object object) {
		return ClassUtils.isCglibProxy(object);
	}

	public boolean isCglibProxyClass(Class<?> clazz) {
		return ClassUtils.isCglibProxyClass(clazz);
	}

	public boolean isCglibProxyClassName(String className) {
		return ClassUtils.isCglibProxyClassName(className);
	}
}
