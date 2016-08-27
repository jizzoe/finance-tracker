package com.swingtech.common.core.util.introspect.model.config;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.model.config.annotation.IntrospectDescription;

@IntrospectDescription
(
	introspectType = IntrospectionTypeEnum.ANNOTATION,
	containsIntrospectionTypes = {},
	containedByIntrospectionTypes = {},
	javaIntrospectionClass = Annotation.class
)
public class SuperClassTypeFilter extends AbstractClassTestingTypeFilter {
	Class<?> classToCheck;
	SuperOrSubCheckTypeEnum superOrSubCheckTypeEnum = SuperOrSubCheckTypeEnum.CHECK_FOR_SUPER_CLASS;
	AssignableOrNotEnum assignableOrNotEnum = AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_ONLY;

	public SuperClassTypeFilter(Pattern pattern, Class<?> classToCheck, SuperOrSubCheckTypeEnum superOrSubCheckTypeEnum,
			AssignableOrNotEnum assignableOrNotEnum) {
		this.classToCheck = classToCheck;
		this.superOrSubCheckTypeEnum = superOrSubCheckTypeEnum;
		this.assignableOrNotEnum = assignableOrNotEnum;
	}

	public SuperClassTypeFilter(Class<?> classToCheck, SuperOrSubCheckTypeEnum superOrSubCheckTypeEnum,
			AssignableOrNotEnum assignableOrNotEnum) {
		this(null, classToCheck, superOrSubCheckTypeEnum, assignableOrNotEnum);
	}

	@Override
	protected boolean match(ClassMetadata metadata) {
		Class<?> metaDataClass;
		
		boolean match = false;
		boolean superMatches = false;
		boolean assignableMatches = false;
		
		try {
			metaDataClass = ClassUtility.forName(metadata.getClassName());
		} catch (ClassNotFoundException e) {
			return false;
		}

		Class<?> subClassToCheck = metaDataClass;
		Class<?> superClassToCheck = classToCheck;
		
		if (this.checkForSuperType()) {
			if (superOrSubCheckTypeEnum.equals(SuperOrSubCheckTypeEnum.CHECK_FOR_SUPER_CLASS)) {
				superMatches = ClassUtility.isSuperClassOf(subClassToCheck, superClassToCheck, false); 
			} else {
				superMatches = ClassUtility.isSubClassOf(subClassToCheck, superClassToCheck, false);
			}
		}

		if (this.checkForAssignable()) {
			assignableMatches = ClassUtility.isAssignable(subClassToCheck, superClassToCheck);
		}
		
		return this.didMatch(superMatches, assignableMatches);
	}
	
	private boolean didMatch(boolean superMatches, boolean assignableMatches) {
		if (assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_ONLY)) {
			return superMatches;
		}
		
		if (assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_ASSIGNABLE_ONLYY)) {
			return assignableMatches;
		}

		if (assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_AND_ASSIGNABLE)) {
			return assignableMatches && superMatches;
		}
		
		return false;
	}
	
	private boolean checkForSuperType() {
		if (assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_ONLY) 
				|| assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_AND_ASSIGNABLE)) 
		{
			return true;
		}
		
		return false;
	}
	

	private boolean checkForAssignable() {
		if (assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_ASSIGNABLE_ONLYY) 
				|| assignableOrNotEnum.equals(AssignableOrNotEnum.CHECK_FOR_SUPER_SUB_AND_ASSIGNABLE)) 
		{
			return true;
		}
		
		return false;
	}
	
	
	public enum SuperOrSubCheckTypeEnum {
		CHECK_FOR_SUPER_CLASS,
		CHECK_FOR_SUBLCLASS;
	}

	public enum AssignableOrNotEnum {
		CHECK_FOR_SUPER_SUB_ONLY,
		CHECK_FOR_ASSIGNABLE_ONLYY,
		CHECK_FOR_SUPER_SUB_AND_ASSIGNABLE;
	}
}
