package com.swingtech.app.financetracker.core.model;

import java.io.File;

import org.joda.time.LocalDateTime;

public class TransactionSource {
	private String transactionSourceName = null;
	private String userName = null;
	private String accountName = null;
	private String dateRangeString = null;
	private LocalDateTime startDateRange = null;
	private LocalDateTime endDateRange = null;
	private String fileName = null;
	private File file = null;
	final TransactionSourceProcessResults processResults = new TransactionSourceProcessResults();
	
	public TransactionSource cloneMinimal() {
		TransactionSource transactionSource = new TransactionSource();
		transactionSource.setTransactionSourceName(this.getTransactionSourceName());
		transactionSource.setFileName(this.getFileName());
		
		return transactionSource;
	}
	
	public String getTransactionSourceName() {
		return transactionSourceName;
	}
	public void setTransactionSourceName(String transactionSource) {
		this.transactionSourceName = transactionSource;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getDateRangeString() {
		return dateRangeString;
	}
	public void setDateRangeString(String dateRangeString) {
		this.dateRangeString = dateRangeString;
	}
	public LocalDateTime getStartDateRange() {
		return startDateRange;
	}
	public void setStartDateRange(LocalDateTime startDateRange) {
		this.startDateRange = startDateRange;
	}
	public LocalDateTime getEndDateRange() {
		return endDateRange;
	}
	public void setEndDateRange(LocalDateTime endDateRange) {
		this.endDateRange = endDateRange;
	}
	@Override
	public String toString() {
		return "TransactionSource [transactionSourceName=" + transactionSourceName + ", userName=" + userName + ", accountName="
				+ accountName + ", dateRangeString=" + dateRangeString + ", startDateRange=" + startDateRange
				+ ", endDateRange=" + endDateRange + ", fileName=" + fileName + "]";
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((transactionSourceName == null) ? 0 : transactionSourceName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionSource other = (TransactionSource) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (transactionSourceName == null) {
			if (other.transactionSourceName != null)
				return false;
		} else if (!transactionSourceName.equals(other.transactionSourceName))
			return false;
		return true;
	}
	public TransactionSourceProcessResults getProcessResults() {
		return processResults;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
