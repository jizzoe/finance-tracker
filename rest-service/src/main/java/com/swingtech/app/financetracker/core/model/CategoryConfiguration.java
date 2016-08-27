package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryConfiguration {
	TransactionCategoryTag mainCategory = null;
	List<TransactionCategoryTag> subCategories = new ArrayList<TransactionCategoryTag>();
	
	public TransactionCategoryTag getMainCategory() {
		return mainCategory;
	}
	public void setMainCategory(TransactionCategoryTag mainCategory) {
		this.mainCategory = mainCategory;
	}
	public List<TransactionCategoryTag> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<TransactionCategoryTag> subCategories) {
		this.subCategories = subCategories;
	}
	
	public CategoryConfiguration clone() {
		CategoryConfiguration cloneCategoryConfiguration = new CategoryConfiguration();
		
		cloneCategoryConfiguration.setMainCategory(getMainCategory().clone());
		
		for (TransactionCategoryTag subCategory : subCategories) {
			cloneCategoryConfiguration.getSubCategories().add(subCategory.clone());
		}
		
		return cloneCategoryConfiguration;
	}
	
	@Override
	public String toString() {
		return "CategoryConfiguration [mainCategory=" + mainCategory + ", subCategories=" + subCategories + "]";
	}
}
