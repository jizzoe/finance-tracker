package com.swingtech.common.core.util.introspect.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.core.util.ErrorUtil;

public class ClassPathScanningINputs {
	private final List<String> basePackagesToScan = new ArrayList<String>();
	private final MatchTypeEnum DEFAULT_INCLUDE_MATCH_TYPE = MatchTypeEnum.OR_ALL_VALUES;
	private final List<Class<?>> includesMatchingBaseClasses = new ArrayList<Class<?>>();
	private final List<Class<?>> excludesMatchingBaseClasses = new ArrayList<Class<?>>();
	
	private final List<Class<?>> includesMatchinggSuperClasses = new ArrayList<Class<?>>();
	private final List<Class<?>> excludesMatchinggSuperClasses = new ArrayList<Class<?>>();

	private final List<Class<? extends Annotation>> includesMatchinggAnnotations = new ArrayList<Class<? extends Annotation>>();
	private final List<Class<? extends Annotation>> excludesMatchinggAnnotations = new ArrayList<Class<? extends Annotation>>();
	
	private MatchTypeEnum includeMatchType = DEFAULT_INCLUDE_MATCH_TYPE;
	
	public ClassPathScanningINputs() {
		
	}
	
	public void addBasePackage(String basePackages) {
		this.getBasepackagestoscan().add(basePackages);
	}

	public void addBasePackagse(List<String> basePackages) {
		this.getBasepackagestoscan().addAll(basePackages);
	}

	public void addBasePackagse(String... basePackages) {
		for (String  basePackage : basePackages) {
			this.getBasepackagestoscan().add(basePackage);
		}
	}
	
	
	public void addIncludeMatchingBaseClass(Class<?> classToAdd) {
		if (classToAdd != null) {
			includesMatchingBaseClasses.add(classToAdd);
		}
	}

	public void addExcludeMatchingBaseClass(Class<?> classToAdd) {
		if (classToAdd != null) {
			excludesMatchingBaseClasses.add(classToAdd);
		}
	}

	public void addIncludeMatchingSuperClass(Class<?> classToAdd) {
		if (classToAdd != null) {
			includesMatchinggSuperClasses.add(classToAdd);
		}
	}

	public void addExcludeMatchingSuperClass(Class<?> classToAdd) {
		if (classToAdd != null) {
			excludesMatchinggSuperClasses.add(classToAdd);
		}
	}

	public void addIncludeMatchingAnnotation(Class<? extends Annotation> classToAdd) {
		if (classToAdd != null) {
//			includesMatchinggAnnotations.add(classToAdd);
		}
	}

	public void addExcludeMatchingAnnotation(Class<? extends Annotation> classToAdd) {
		if (classToAdd != null) {
			excludesMatchinggAnnotations.add(classToAdd);
		}
	}

	public ClassPathScanningINputs(
			List<Class<?>> includesMatchingBaseClasses, List<Class<?>> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<Class<?>> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<Class<? extends Annotation>> excludesMatchinggAnnotations)
	{
		super();
		this.addAllListsWithClasses(
				includesMatchingBaseClasses, excludesMatchingBaseClasses, 
				includesMatchinggSuperClasses, excludesMatchinggSuperClasses, includesMatchinggAnnotations, excludesMatchinggAnnotations, false
				);
		
	}

	public ClassPathScanningINputs(
			List<Class<?>> includesMatchingBaseClasses, List<String> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<String> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<String> excludesMatchinggAnnotations, boolean why)
	{
		super();
		this.addAllListsWithStrings(includesMatchingBaseClasses, excludesMatchingBaseClasses, 
				includesMatchinggSuperClasses, excludesMatchinggSuperClasses, includesMatchinggAnnotations, excludesMatchinggAnnotations, false);
		
	}

	public void addAllListsWithClasses(
			List<Class<?>> includesMatchingBaseClasses, List<Class<?>> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<Class<?>> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<Class<? extends Annotation>> excludesMatchinggAnnotations,
			boolean resetLists)
	{
		this.addClassList(includesMatchingBaseClasses, excludesMatchingBaseClasses, resetLists);
		this.addClassList(includesMatchinggSuperClasses, excludesMatchinggSuperClasses, resetLists);
		this.addAnnotationList(includesMatchinggAnnotations, excludesMatchinggAnnotations, resetLists);	
	}
	
	public void addAllListsWithStrings(
			List<Class<?>> includesMatchingBaseClasses, List<String> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<String> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<String> excludesMatchinggAnnotations,
			boolean resetLists)
	{
		this.addClassWithStringNameList(includesMatchingBaseClasses, excludesMatchingBaseClasses, resetLists);
		this.addClassWithStringNameList(includesMatchinggSuperClasses, excludesMatchinggSuperClasses, resetLists);
		this.addAnnotationWithStringNameListList(includesMatchinggAnnotations, excludesMatchinggAnnotations, resetLists);
	}

	private void addClassWithStringNameList(List<Class<?>> listToAddTo, List<String> classNameListToLoad, boolean resetLists) {
		List<Class<?>> newList = this.getClassListFromStringNameList(classNameListToLoad);
		this.addClassList(listToAddTo, newList, resetLists);
	}

	private void addAnnotationWithStringNameListList(List<Class<? extends Annotation>> listToAddTo, List<String> classNameListToLoad, boolean resetLists) {
		List<Class<? extends Annotation>> newList = this.getAnnotationListFromStringNameList(classNameListToLoad);
		this.addAnnotationList(newList, newList, resetLists);
	}

	private List<Class<?>> getClassListFromStringNameList(List<String> classNameList) 
	{
		List<Class<?>> returnClassList = new ArrayList<Class<?>>();
		
		if (this.isNullOrEmpty(classNameList)) {
			return null;
		}
		
		for (String className : classNameList) {
			returnClassList.add(this.createClassFromClassName(className));
		}
		
		return returnClassList;
	}
	
	private Class<?> createClassFromClassName(String className) {
		Class<?> newClass = null;
		
		try {
			newClass = ClassUtility.forName(className);
		} catch (Exception e) {
			throw new IllegalArgumentException("There was an error trying to create a new class from this class name:  " + className + "  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		if (className == null) {
			throw new IllegalArgumentException("There was an error trying to create a new class from this class name:  " + className);
		}
		
		return newClass;
	}

	private Class<? extends Annotation> createAnnotationFromClassName(String className) {
		Class<? extends Annotation> newClass = null;
		
		try {
			newClass = (Class<? extends Annotation>) ClassUtility.forName(className);
		} catch (Exception e) {
			throw new IllegalArgumentException("There was an error trying to create a new class from this class name:  " + className + "  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		if (className == null) {
			throw new IllegalArgumentException("There was an error trying to create a new class from this class name:  " + className);
		}
		
		return newClass;
	}

	
	private List<Class<? extends Annotation>> getAnnotationListFromStringNameList(List<String> classNameList) {
		List<Class<? extends Annotation>> returnClassList = new ArrayList<Class<? extends Annotation>>();
		
		if (this.isNullOrEmpty(classNameList)) {
			return null;
		}
		
		for (String className : classNameList) {
			returnClassList.add(this.createAnnotationFromClassName(className));
		}
		
		return returnClassList;
	}

	private void addClassList(List<Class<?>> listToAddTo, List<Class<?>> newListToAdd, boolean resetLists) {
		if (this.isNullOrEmpty(listToAddTo) || this.isNullOrEmpty(newListToAdd)) {
			return;
		}
		
		if (resetLists) {
			this.resetList(listToAddTo);
		}
		
		listToAddTo.addAll(newListToAdd);
	}

	
	private void addAnnotationList(List<Class<? extends Annotation>> listToAddTo, List<Class<? extends Annotation>> newListToAdd, boolean resetLists) {
		if (this.isNullOrEmpty(listToAddTo) || this.isNullOrEmpty(newListToAdd)) {
			return;
		}
		
		if (resetLists) {
			this.resetList(listToAddTo);
		}
		
		listToAddTo.addAll(newListToAdd);
	}

	
	private void resetList(List<?> testList) {
		if (!isNullOrEmpty(testList)) {
			testList.clear();
		}
		
	}
	
	private boolean isNullOrEmpty(List<?> testList) {
		return testList == null || testList.isEmpty();
	}

	public List<Class<?>> getIncludesMatchingBaseClasses() {
		return includesMatchingBaseClasses;
	}

	public List<Class<?>> getExcludesMatchingBaseClasses() {
		return excludesMatchingBaseClasses;
	}

	public List<Class<?>> getIncludesMatchinggSuperClasses() {
		return includesMatchinggSuperClasses;
	}

	public List<Class<?>> getExcludesMatchinggSuperClasses() {
		return excludesMatchinggSuperClasses;
	}

	public List<Class<? extends Annotation>> getIncludesMatchinggAnnotations() {
		return includesMatchinggAnnotations;
	}

	public List<Class<? extends Annotation>> getExcludesMatchinggAnnotations() {
		return excludesMatchinggAnnotations;
	}
	

	public enum MatchTypeEnum {
		AND_ALL_VALUES,
		OR_ALL_VALUES;
	}


	public MatchTypeEnum getIncludeMatchType() {
		return includeMatchType;
	}

	public void setIncludeMatchType(MatchTypeEnum includeMatchType) {
		this.includeMatchType = includeMatchType;
	}

	public  MatchTypeEnum getDefaultIncludeMatchType() {
		return DEFAULT_INCLUDE_MATCH_TYPE;
	}

	public  List<String> getBasepackagestoscan() {
		return basePackagesToScan;
	}
}
