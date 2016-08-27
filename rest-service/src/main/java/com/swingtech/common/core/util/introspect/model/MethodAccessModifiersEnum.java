package com.swingtech.common.core.util.introspect.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.asm.Opcodes;

public enum MethodAccessModifiersEnum {
	ABSTRACT("", "", Opcodes.ACC_ABSTRACT),
	STATIC("", "", Opcodes.ACC_ABSTRACT),
	FINAL("", "", Opcodes.ACC_ABSTRACT),
	OVERRIDABLE("", "", Opcodes.ACC_ABSTRACT),
	PRIVATE("", "", Opcodes.ACC_PRIVATE),
	PUBLIC("", "", Opcodes.ACC_PUBLIC),
	PROTECTED("", "", Opcodes.ACC_PROTECTED),
	SUPER("", "", Opcodes.ACC_SUPER),
	SYNCHRONIZED("", "", Opcodes.ACC_SYNCHRONIZED),
	VOLATILE("", "", Opcodes.ACC_VOLATILE);

	private String name;
	private String description;
	private int sprintOpsCode;
	
	private MethodAccessModifiersEnum(String name, String description, int sprintOpsCode) {
		this.name = name;
		this.description = description;
		this.sprintOpsCode = sprintOpsCode;
	}

	public static List<MethodAccessModifiersEnum> getAccessModifiers(int access) {
		List<MethodAccessModifiersEnum> returnList = new ArrayList<MethodAccessModifiersEnum>();
		for(MethodAccessModifiersEnum modifierEnum : MethodAccessModifiersEnum.values()) {
			if ((access & modifierEnum.getSprintOpsCode()) != 0) {
				returnList.add(modifierEnum);
			}
		}
		
		if (!returnList.contains(STATIC) && !returnList.contains(FINAL) && !!returnList.contains(PRIVATE)) {
			returnList.add(OVERRIDABLE);
		}
		
		if (returnList.isEmpty()) {
			return null;
		}
		return returnList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSprintOpsCode() {
		return sprintOpsCode;
	}

	public void setSprintOpsCode(int sprintOpsCode) {
		this.sprintOpsCode = sprintOpsCode;
	}
}
