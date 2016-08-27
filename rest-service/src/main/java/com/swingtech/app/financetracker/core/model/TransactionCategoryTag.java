package com.swingtech.app.financetracker.core.model;

public class TransactionCategoryTag {
	String name = null;
	String fullName = null;
	TransactionCategoryTag childCategory = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public TransactionCategoryTag getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(TransactionCategoryTag childCategory) {
		this.childCategory = childCategory;
	}
	@Override
	public String toString() {
		return "TransactionCategoryTag [name=" + name + ", fullName=" + fullName + ", childCategory=" + childCategory
				+ "]";
	}
	
	public TransactionCategoryTag clone() {
		TransactionCategoryTag category = new TransactionCategoryTag();
		
		category.setFullName(getFullName());
		category.setName(getName());
		
		if (childCategory != null) {
			category.setChildCategory(childCategory.clone());
		}
		
		return category;
	}
}
