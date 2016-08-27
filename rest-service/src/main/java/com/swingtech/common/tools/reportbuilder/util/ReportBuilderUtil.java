package com.swingtech.common.tools.reportbuilder.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.tools.reportbuilder.error.ReportBuilderException;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ModuleChild;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ReportModuleType;
import com.swingtech.common.tools.reportbuilder.model.config.constant.ModuleNodeTypeEnum;
import com.swingtech.common.tools.reportbuilder.modules.ui.BaseReportModule;
import com.swingtech.common.tools.reportbuilder.util.model.ChildModuleFieldConfigHolder;

public class ReportBuilderUtil {
	public static <T extends BaseReportModule> boolean isRootModule(T reportModule) {
		Class<?> moduleClass = reportModule.getClass();

		if (!moduleClass.isAnnotationPresent(ReportModuleType.class)) {
			return false;
		}
		
		ReportModuleType moduleTypeAnnotation = moduleClass.getAnnotation(ReportModuleType.class);
		
		return moduleTypeAnnotation.moduleNodeType().equals(ModuleNodeTypeEnum.ROOT_NODE) ||
				moduleTypeAnnotation.moduleNodeType().equals(ModuleNodeTypeEnum.CHILD_AND_ROOT_NODE);
	}
	
	public static List<ChildModuleFieldConfigHolder> getReportModuleChildren(Class<?> reportModuleClass) throws ReportBuilderException {
		return getReportModuleChildren(null, reportModuleClass, null, 0, 3);
	}

	public static List<ChildModuleFieldConfigHolder> getReportModuleChildren(BaseReportModule reportModule, ChildModuleFieldConfigHolder parentModuleHolder) throws ReportBuilderException {
		return getReportModuleChildren(reportModule, reportModule.getClass(), parentModuleHolder, 0, 3);
	}
	public static List<ChildModuleFieldConfigHolder> getReportModuleChildren(BaseReportModule reportModule, Class<?> moduleClass, ChildModuleFieldConfigHolder parentModuleHolder, int maxChildLevels, int currentLevel) throws ReportBuilderException {
		List<ChildModuleFieldConfigHolder> returnFieldChildren = new ArrayList<ChildModuleFieldConfigHolder>();
		ChildModuleFieldConfigHolder childFieldHolder = null;
	
		List<Field> moduleChildFields = null;

		if (!moduleClass.isAnnotationPresent(ReportModuleType.class)) {
			return null;
		}
		
		ReportModuleType moduleTypeAnnotation = moduleClass.getAnnotation(ReportModuleType.class);
		
		moduleChildFields = ClassUtility.getDeclaredFieldsWithAnnotationAndBaseClass(moduleClass, ModuleChild.class, BaseReportModule.class);
		
		if (moduleChildFields == null || moduleChildFields.isEmpty()) {
			return null;
		}
		
		try {
			for (Field childField : moduleChildFields) {
				ModuleChild childConfgAnnotation = childField.getAnnotation(ModuleChild.class);
				BaseReportModule childFieldValue = null;
				
				if (childConfgAnnotation != null && BaseReportModule.class.isAssignableFrom(childField.getType())) {
					if (reportModule != null) {
						childFieldValue = (BaseReportModule) PropertyUtils.getProperty(reportModule, childField.getName());
					}
					
					childFieldHolder = new ChildModuleFieldConfigHolder();
					childFieldHolder.setParentModuleObject(reportModule);
					childFieldHolder.setParentModuleClass(moduleClass);
					childFieldHolder.setParentModuleType(moduleTypeAnnotation);
					childFieldHolder.setChildField(childField);
					childFieldHolder.setChildFieldConfigurationAnnotation(childField.getAnnotation(ModuleChild.class));
					childFieldHolder.setChildFieldType(childField.getType());
//				childFieldHolder.setChildFieldTypeAnnotation(childConfgAnnotation);
					childFieldHolder.setChildFieldConfigurationAnnotation(childConfgAnnotation);
					childFieldHolder.setChildFieldValue(childFieldValue);
					childFieldHolder.setChildReportModuleTypeAnnotation(getModuleTypeAnnotation(childField.getType()));
					
					if (!ClassUtility.hasFieldsWithAnnotationAndBaseClass(childField.getType(), ModuleChild.class, BaseReportModule.class)
							|| currentLevel >= maxChildLevels) {
						continue;
					}
					
					getReportModuleChildren(childFieldValue, moduleClass, childFieldHolder, maxChildLevels, currentLevel++);
					
					currentLevel = 0;
					
//				childFieldHolder.setChildrenOfChildField(childrenOfChildField);
				}
			}
		} catch (Exception e) {
			throw new ReportBuilderException("Error trying to get children.  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		return returnFieldChildren;
	}
	
	public static ReportModuleType getModuleTypeAnnotation(Class<?> moduleClass) {
		if (!moduleClass.isAnnotationPresent(ReportModuleType.class)) {
			return null;
		}
		
		return moduleClass.getAnnotation(ReportModuleType.class);
	}

	public static ReportModuleType getModuleTypeAnnotation(BaseReportModule reportModule) {
		Class<?> moduleClass = reportModule.getClass();

		if (!moduleClass.isAnnotationPresent(ReportModuleType.class)) {
			return null;
		}
		
		return moduleClass.getAnnotation(ReportModuleType.class);
	}
}
