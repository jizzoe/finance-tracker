package com.swingtech.common.core.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;

public class FileUploadMetaData {
	private String uploadFileName = null;
	private String uploadFileFieldName = null;
	private File uploadFile = null;
	private InputStream uploadFileInputStream = null;
	private Map<String, String> formData = new HashMap<String, String>();
	private ContentType contentType = null;
	
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public Map<String, String> getFormData() {
		return formData;
	}
	public void setFormData(Map<String, String> formData) {
		this.formData = formData;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public InputStream getUploadFileInputStream() {
		return uploadFileInputStream;
	}
	public void setUploadFileInputStream(InputStream uploadFileInputStream) {
		this.uploadFileInputStream = uploadFileInputStream;
	}
	public ContentType getContentType() {
		return contentType;
	}
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	public String getUploadFileFieldName() {
		return uploadFileFieldName;
	}
	public void setUploadFileFieldName(String uploadFileFieldName) {
		this.uploadFileFieldName = uploadFileFieldName;
	}
}
