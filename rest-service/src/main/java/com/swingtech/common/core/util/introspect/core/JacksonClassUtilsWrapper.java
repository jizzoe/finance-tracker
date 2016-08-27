package com.swingtech.common.core.util.introspect.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.util.ClassUtil;

public class JacksonClassUtilsWrapper {

	private static final JacksonClassUtilsWrapper jacksonClassUtilsWrapper = new JacksonClassUtilsWrapper();
	
	public static JacksonClassUtilsWrapper getInstanceinstance() {
		return jacksonClassUtilsWrapper;
	}
	
	public static ClassLoader getDefaultClassLoader() {
		return ClassUtils.getDefaultClassLoader();
	}


	public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
		return ClassUtil.findSuperTypes(cls, endBefore);
	}

	public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
		return ClassUtil.findSuperTypes(cls, endBefore, result);
	}

	public static String canBeABeanType(Class<?> type) {
		return ClassUtil.canBeABeanType(type);
	}

	public static String isLocalType(Class<?> type, boolean allowNonStatic) {
		return ClassUtil.isLocalType(type, allowNonStatic);
	}

	public static Class<?> getOuterClass(Class<?> type) {
		return ClassUtil.getOuterClass(type);
	}

	public static boolean isProxyType(Class<?> type) {
		return ClassUtil.isProxyType(type);
	}

	public static boolean isConcrete(Class<?> type) {
		return ClassUtil.isConcrete(type);
	}

	public static boolean isConcrete(Member member) {
		return ClassUtil.isConcrete(member);
	}

	public static boolean isCollectionMapOrArray(Class<?> type) {
		return ClassUtil.isCollectionMapOrArray(type);
	}

	public static String getClassDescription(Object classOrInstance) {
		return ClassUtil.getClassDescription(classOrInstance);
	}

	public static Class<?> findClass(String className) throws ClassNotFoundException {
		return ClassUtil.findClass(className);
	}

	public static boolean hasGetterSignature(Method m) {
		return ClassUtil.hasGetterSignature(m);
	}

	public static Throwable getRootCause(Throwable t) {
		return ClassUtil.getRootCause(t);
	}

	public static void throwRootCause(Throwable t) throws Exception {
		ClassUtil.throwRootCause(t);
	}

	public static void throwAsIAE(Throwable t) {
		ClassUtil.throwAsIAE(t);
	}

	public static void throwAsIAE(Throwable t, String msg) {
		ClassUtil.throwAsIAE(t, msg);
	}

	public static void unwrapAndThrowAsIAE(Throwable t) {
		ClassUtil.unwrapAndThrowAsIAE(t);
	}

	public static void unwrapAndThrowAsIAE(Throwable t, String msg) {
		ClassUtil.unwrapAndThrowAsIAE(t, msg);
	}

	public static <T> T createInstance(Class<T> cls, boolean canFixAccess) throws IllegalArgumentException {
		return ClassUtil.createInstance(cls, canFixAccess);
	}

	public static <T> Constructor<T> findConstructor(Class<T> cls, boolean canFixAccess) throws IllegalArgumentException {
		return ClassUtil.findConstructor(cls, canFixAccess);
	}

	public static Object defaultValue(Class<?> cls) {
		return ClassUtil.defaultValue(cls);
	}

	public static Class<?> wrapperType(Class<?> primitiveType) {
		return ClassUtil.wrapperType(primitiveType);
	}

	public static void checkAndFixAccess(Member member) {
		ClassUtil.checkAndFixAccess(member);
	}

	public static Class<? extends Enum<?>> findEnumType(EnumSet<?> s) {
		return ClassUtil.findEnumType(s);
	}

	public static Class<? extends Enum<?>> findEnumType(EnumMap<?, ?> m) {
		return ClassUtil.findEnumType(m);
	}

	public static Class<? extends Enum<?>> findEnumType(Enum<?> en) {
		return ClassUtil.findEnumType(en);
	}

	public static Class<? extends Enum<?>> findEnumType(Class<?> cls) {
		return ClassUtil.findEnumType(cls);
	}

}
