package com.swingtech.common.core.util.introspect.core;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import com.swingtech.common.core.util.introspect.model.config.SuperClassTypeFilter;
import com.swingtech.common.core.util.introspect.model.config.SuperClassTypeFilter.AssignableOrNotEnum;
import com.swingtech.common.core.util.introspect.model.config.SuperClassTypeFilter.SuperOrSubCheckTypeEnum;

public class SimpleClassPathScanningFactory {
	public static ClassPathBeanDefinitionScanner createClassPathScanner(ClassPathScanningINputs inputs) 
	{
		return createClassPathScanner(inputs, null);
	}

	public static ClassPathBeanDefinitionScanner createClassPathScanner(ClassPathScanningINputs inputs,  BeanDefinitionRegistry beanRegistry) 
	{
		ClassPathBeanDefinitionScanner scanner = null;

		if (beanRegistry == null) {
			beanRegistry = getBeanRegistry();
		}
		
		scanner = new ClassPathBeanDefinitionScanner(beanRegistry, false);
		
		configureScanner(scanner, inputs);
		
		return scanner;
	}
	
	public static void configureScanner(ClassPathBeanDefinitionScanner scanner, ClassPathScanningINputs inputs) {
		addFilterTypes(scanner,  inputs);
	}

	public static void addFilterTypes(ClassPathBeanDefinitionScanner scanner, ClassPathScanningINputs inputs) {
		if (inputs == null) {
			return;
		}

		addSubSuperClassFilterTypes(scanner, inputs.getExcludesMatchingBaseClasses(), false, false);
		addSubSuperClassFilterTypes(scanner, inputs.getExcludesMatchinggSuperClasses(), false, true);
		addSubSuperClassFilterTypes(scanner, inputs.getIncludesMatchingBaseClasses(), true, false);
		addSubSuperClassFilterTypes(scanner, inputs.getIncludesMatchinggSuperClasses(), true, true);
		addAnnotationClassFilterTypes(scanner, inputs.getExcludesMatchinggAnnotations(), false, false);
		addAnnotationClassFilterTypes(scanner, inputs.getIncludesMatchinggAnnotations(), false, false);
	}

	private static void addAnnotationClassFilterTypes(ClassPathBeanDefinitionScanner scanner,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, boolean include, boolean checkForSuper) 
	{
		if (includesMatchinggAnnotations == null || includesMatchinggAnnotations.isEmpty()) {
			return;
		}
		
		for (Class<? extends Annotation> classToAdd : includesMatchinggAnnotations) {
			addSubSuperClassFilterTypes(scanner, classToAdd, include, checkForSuper);
		}		
	}

	public static void addSubSuperClassFilterTypes(ClassPathBeanDefinitionScanner scanner, List<Class<?>> classesToAdd, boolean include, boolean checkForSuper) {
		if (classesToAdd == null || classesToAdd.isEmpty()) {
			return;
		}
		
		for (Class<?> classToAdd : classesToAdd) {
			addSubSuperClassFilterTypes(scanner, classToAdd, include, checkForSuper);
		}
	}
	public static void addSubSuperClassFilterTypes(ClassPathBeanDefinitionScanner scanner, Class<?> classToAdd, boolean include, boolean checkForSuper) {
		TypeFilter typeFilter = null;
		Class<? extends Annotation> annotation = Annotation.class;
		
		if (classToAdd == null) {
			return;
		}
		
		if (classToAdd.isAssignableFrom(annotation)) {
			typeFilter = new AnnotationTypeFilter((Class<? extends Annotation>) classToAdd);
		}
		else {
			if (checkForSuper) {
				typeFilter = new SuperClassTypeFilter(classToAdd, SuperOrSubCheckTypeEnum.CHECK_FOR_SUPER_CLASS, AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_ONLY);
			} else {
				typeFilter = new SuperClassTypeFilter(classToAdd, SuperOrSubCheckTypeEnum.CHECK_FOR_SUBLCLASS, AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_ONLY);
			}
		}
		
		if (include) {
			scanner.addIncludeFilter(typeFilter);
		} else {
			scanner.addExcludeFilter(typeFilter);
		}
	}
	
	public static BeanDefinitionRegistry getBeanRegistry()  {
		return new SimpleBeanDefinitionRegistry();
	}
	
	public static ClassPathBeanDefinitionScanner newScanner(
			List<Class<?>> includesMatchingBaseClasses, List<Class<?>> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<Class<?>> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<Class<? extends Annotation>> excludesMatchinggAnnotations)
	{
		ClassPathScanningINputs inputs = new ClassPathScanningINputs(
				includesMatchingBaseClasses, excludesMatchingBaseClasses, 
				includesMatchinggSuperClasses, excludesMatchinggSuperClasses, includesMatchinggAnnotations, excludesMatchinggAnnotations);
		
		return createClassPathScanner(inputs);
	}

	public static ClassPathBeanDefinitionScanner newScanner(
			List<Class<?>> includesMatchingBaseClasses, List<String> excludesMatchingBaseClasses, 
			List<Class<?>> includesMatchinggSuperClasses, List<String> excludesMatchinggSuperClasses,
			List<Class<? extends Annotation>> includesMatchinggAnnotations, List<String> excludesMatchinggAnnotations, boolean why)
	{
		ClassPathScanningINputs inputs = new ClassPathScanningINputs(includesMatchingBaseClasses, excludesMatchingBaseClasses, includesMatchinggSuperClasses, excludesMatchinggSuperClasses, includesMatchinggAnnotations, excludesMatchinggAnnotations, false);
		
		return createClassPathScanner(inputs);
	}
}
