package com.swingtech.common.tools.reportbuilder.util.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.swingtech.common.tools.reportbuilder.model.config.annotation.ModuleChild;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ModuleChildType;
import com.swingtech.common.tools.reportbuilder.model.config.annotation.ReportModuleType;
import com.swingtech.common.tools.reportbuilder.modules.ui.BaseReportModule;

public class ChildModuleFieldConfigHolder {
	private ReportModuleType parentModuleTypeAnnotation;
	private Class<?> parentModuleClass;
	private BaseReportModule parentModuleObject;
	private Field childField;
	private Class<?> childFieldType;
	private BaseReportModule childFieldValue;
	private ModuleChild childFieldConfigurationAnnotation;
	private ModuleChildType childFieldTypeAnnotation;
	private ReportModuleType childReportModuleTypeAnnotation;
	private List<ChildModuleFieldConfigHolder> childrenOfChildField = new ArrayList<ChildModuleFieldConfigHolder>();
	
	public Field getChildField() {
		return childField;
	}
	public void setChildField(Field childField) {
		this.childField = childField;
	}
	public Class<?> getChildFieldType() {
		return childFieldType;
	}
	public void setChildFieldType(Class<?> childFieldType) {
		this.childFieldType = childFieldType;
	}
	public ModuleChild getChildFieldConfigurationAnnotation() {
		return childFieldConfigurationAnnotation;
	}
	public void setChildFieldConfigurationAnnotation(ModuleChild childFieldConfigurationAnnotation) {
		this.childFieldConfigurationAnnotation = childFieldConfigurationAnnotation;
	}
	public ModuleChildType getChildFieldTypeAnnotation() {
		return childFieldTypeAnnotation;
	}
	public void setChildFieldTypeAnnotation(ModuleChildType childFieldTypeAnnotation) {
		this.childFieldTypeAnnotation = childFieldTypeAnnotation;
	}
	public ReportModuleType getChildReportModuleTypeAnnotation() {
		return childReportModuleTypeAnnotation;
	}
	public void setChildReportModuleTypeAnnotation(ReportModuleType childReportModuleTypeAnnotation) {
		this.childReportModuleTypeAnnotation = childReportModuleTypeAnnotation;
	}
	public ReportModuleType getParentModuleTypeAnnotation() {
		return parentModuleTypeAnnotation;
	}
	public void setParentModuleType(ReportModuleType parentModuleType) {
		this.parentModuleTypeAnnotation = parentModuleType;
	}
	public Class<?> getParentModuleClass() {
		return parentModuleClass;
	}
	public void setParentModuleClass(Class<?> parentModuleClass) {
		this.parentModuleClass = parentModuleClass;
	}
	public List<ChildModuleFieldConfigHolder> getChildrenOfChildField() {
		return childrenOfChildField;
	}
	public void setChildrenOfChildField(List<ChildModuleFieldConfigHolder> childrenOfChildField) {
		this.childrenOfChildField = childrenOfChildField;
	}
	public BaseReportModule getParentModuleObject() {
		return parentModuleObject;
	}
	public void setParentModuleObject(BaseReportModule parentModuleObject) {
		this.parentModuleObject = parentModuleObject;
	}
	public BaseReportModule getChildFieldValue() {
		return childFieldValue;
	}
	public void setChildFieldValue(BaseReportModule childFieldValue) {
		this.childFieldValue = childFieldValue;
	}
}
