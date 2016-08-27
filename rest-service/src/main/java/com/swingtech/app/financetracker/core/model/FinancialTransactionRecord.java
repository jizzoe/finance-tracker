package com.swingtech.app.financetracker.core.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

public class FinancialTransactionRecord {
	String transactionId = null;
	String description = null;
	String descriptionSearchText = null;
	String checkNumber = null;
	LocalDateTime  transactionDateTime = null;
	Double transactionAmount = null;
	Double transactionDebitAmount = null;
	Double transactionCreditAmount = null;
	Double endingBalance = null;
	TransactionCategoryTag mainCategory = null;
	List<TransactionCategoryTag> subCategories = new ArrayList<TransactionCategoryTag>();
	AddTransactionRecordError addTransactionError;
	ParseCsvRecordError parseCsvError;
	CsvLineRecord csvLineRecord = null;
	TransactionSource transactionSource = null;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionSearchText() {
		return descriptionSearchText;
	}
	public void setDescriptionSearchText(String descriptionSearchText) {
		this.descriptionSearchText = descriptionSearchText;
	}
	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Double getTransactionDebitAmount() {
		return transactionDebitAmount;
	}
	public void setTransactionDebitAmount(Double transactionDebitAmount) {
		this.transactionDebitAmount = transactionDebitAmount;
	}
	public Double getTransactionCreditAmount() {
		return transactionCreditAmount;
	}
	public void setTransactionCreditAmount(Double transactionCreditAmount) {
		this.transactionCreditAmount = transactionCreditAmount;
	}
	public Double getEndingBalance() {
		return endingBalance;
	}
	public void setEndingBalance(Double endingBalance) {
		this.endingBalance = endingBalance;
	}
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
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public AddTransactionRecordError getAddTransactionError() {
		return addTransactionError;
	}
	public void setAddTransactionError(AddTransactionRecordError addTransactionError) {
		if (addTransactionError != null) {
			this.addTransactionError = addTransactionError.cloneMinimal();
		} else {
			this.addTransactionError = null;
		}
	}
	
	public boolean hasError() {
		return this.hasAddTransactionError() || this.hasParseCsvRecordError();
	}
	
	public boolean hasAddTransactionError() {
		return this.addTransactionError != null;
	}
	public boolean hasParseCsvRecordError() {
		return this.parseCsvError != null;
	}
	public ParseCsvRecordError getParseCsvError() {
		return parseCsvError;
	}
	public void setParseCsvError(ParseCsvRecordError parseCsvError) {
		if (parseCsvError != null) {
			this.parseCsvError = parseCsvError.cloneMinimal();
		} else {
			this.parseCsvError = null;
		}
	}
	public TransactionSource getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(TransactionSource transactionSource) {
		if (transactionSource != null) {
			this.transactionSource = transactionSource.cloneMinimal();
		} else {
			this.transactionSource = null;
		}
	}
	public CsvLineRecord getCsvLineRecord() {
		return csvLineRecord;
	}
	public void setCsvLineRecord(CsvLineRecord csvLineRecord) {
		this.csvLineRecord = csvLineRecord;
	}
	
	public FinancialTransactionRecord cloneMinimal() {
		FinancialTransactionRecord cloneTransaction = new FinancialTransactionRecord();
		
		cloneTransaction.setCsvLineRecord(this.getCsvLineRecord().cloneMinimal());
		cloneTransaction.setTransactionId(this.getTransactionId());
		cloneTransaction.setDescription(this.getDescription());
		
		return cloneTransaction;
	}
}
