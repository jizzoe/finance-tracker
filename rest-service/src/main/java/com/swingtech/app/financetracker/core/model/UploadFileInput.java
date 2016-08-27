package com.swingtech.app.financetracker.core.model;

import java.io.File;
import java.io.InputStream;

public class UploadFileInput {
	private TransactionSource transactionSource = null;
	private String originalFileName = null;
	private byte[] fileContents = null;
	private InputStream fileInputStream = null;
	private long fileLength = -1;
	private String fileName = null;
	private String contentType = null;
	private File file = null;
	
	public TransactionSource getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(TransactionSource transactionSource) {
		this.transactionSource = transactionSource;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public byte[] getFileContents() {
		return fileContents;
	}
	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String getFileName) {
		this.fileName = getFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
