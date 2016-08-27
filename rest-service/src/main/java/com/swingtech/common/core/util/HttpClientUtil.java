package com.swingtech.common.core.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientUtil {
	public static HttpResponse sendPostMessage(String url, String body) {
		return sendPostMessage(url, body, null);
	}

	public static HttpResponse sendPostMessage(String url, String body, Map<String, String> headers) {
		HttpEntity entity = getRequestHttpEntity(url, body);
		
		return sendPostMessage(url, entity, headers);
	}
	
	public static HttpEntity getRequestHttpEntity(String url, String requestBody) {
		HttpEntity entity = null;

		try {
			entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(
					"Error trying to send post Http.  To this URL:  " + url + ".  With this body:  " + requestBody);
		}

		return entity;
	}

	public static HttpEntity getFileUploadRequestHttpEntity(String url, List<FileUploadMetaData> filesToUpload) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();    
		String fileName = null;
		
//		builder.setContentType(ContentType.MULTIPART_FORM_DATA);

		for (FileUploadMetaData fileMetaData : filesToUpload) {
			if (fileMetaData.getUploadFileName() == null || fileMetaData.getUploadFileName().trim().isEmpty()) {
				fileName = fileMetaData.getUploadFile().getName();
			} else {
				fileName = fileMetaData.getUploadFileName();
			}
			
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			builder.addBinaryBody(fileMetaData.getUploadFileFieldName(), fileMetaData.getUploadFile(), fileMetaData.getContentType(), fileName);
			
			if (fileMetaData.getFormData() != null && !fileMetaData.getFormData().isEmpty()) {
				for (Entry<String, String>  entry : fileMetaData.getFormData().entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					builder.addTextBody(key, value, fileMetaData.getContentType());
				}
			}
		}
		
		return builder.build();
	}

	public static HttpResponse sendPostMessage(String url, HttpEntity entity) {
		return sendPostMessage(url, entity, null);
	}
	
	public static HttpResponse sendPostMessage(String url, HttpEntity entity, Map<String, String> headers) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = null;
		HttpResponse response = null;

		post = new HttpPost(url);
		
		try {
			post.setEntity(entity);
			
			if (headers != null && !headers.isEmpty()) {
				addHeadersToRequest(post, headers);
			}

			response = client.execute(post);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error trying to send post Http.  To this URL:  " + url + ".  Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}

		return response;
	}
	
	private static void addHeadersToRequest(HttpRequestBase request, Map<String, String> headers) {
		if (headers == null || headers.isEmpty()) {
			return;
		}
		
		for (Entry<String, String> header : headers.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}
	}
	public static HttpResponse sendGetMessage(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = null;
		HttpResponse response = null;
		HttpEntity entity = null;

		get = new HttpGet(url);
		
		try {
			response = client.execute(get);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error trying to send post get.  To this URL:  " + url);
		}

		return response;
	}

	public static HttpResponse sendPostUploadFiles(String url, List<FileUploadMetaData> filesToUpload) {
		return sendPostUploadFiles(url, filesToUpload, null);
	}
	
	public static HttpResponse sendPostUploadFiles(String url, List<FileUploadMetaData> filesToUpload, Map<String, String> headers) {
		HttpEntity entity = null;
		
		entity = getFileUploadRequestHttpEntity(url, filesToUpload);
		
		return sendPostMessage(url, entity, headers);
	}
	
	public static boolean isContentTypeBinary(ContentType contentType) {
		if (contentType.equals(ContentType.DEFAULT_BINARY) 
				|| contentType.equals(ContentType.APPLICATION_OCTET_STREAM)
				|| contentType.equals(ContentType.APPLICATION_SVG_XML)) 
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isContentTypeText(ContentType contentType) {
		if (contentType.equals(ContentType.DEFAULT_TEXT) 
				|| contentType.equals(ContentType.APPLICATION_JSON)
				|| contentType.equals(ContentType.APPLICATION_FORM_URLENCODED)
				|| contentType.equals(ContentType.APPLICATION_XHTML_XML)
				|| contentType.equals(ContentType.APPLICATION_XML)
				|| contentType.equals(ContentType.TEXT_HTML)
				|| contentType.equals(ContentType.TEXT_PLAIN)
				|| contentType.equals(ContentType.TEXT_XML)) 
		{
			return true;
		}
		
		return false;
	}
}
