package com.swingtech.common.core.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class FileUtil {
	private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	public static boolean copyFileResource(ResourceLoader resourceLoader, String fileNameFrom, String fileNameTo)
			throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(fileNameFrom);

		InputStream inputStream = classPathResource.getInputStream();
		File toFile = new File(fileNameTo);

		try {
			FileUtils.copyInputStreamToFile(inputStream, toFile);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

		return false;
	}

	public static List<Resource> getResourceListForParentFolder(String parentFolderName) throws IOException {
		List<Resource> resourceList = new ArrayList<Resource>();

		Resource[] resources = resourcePatternResolver.getResources(parentFolderName);

		for (Resource resource : resources) {
			resourceList.add(resource);
		}

		return resourceList;
	}

	public static List<File> getFileListForParentFolder(String parentFolderName) throws IOException {
		List<File> resourceList = new ArrayList<File>();

		Resource[] resources = resourcePatternResolver.getResources(parentFolderName);

		if (resources == null || resources.length <= 0) {
			return null;
		}
		
		for (Resource resource : resources) {
			resourceList.add(resource.getFile());
		}

		return resourceList;
	}
	
	public static Resource getResourceFromFileName(String fileName) {
		Resource resource = resourceLoader.getResource(fileName);
		
		if ((resource == null || !resource.exists()) && fileName.indexOf(":") < 0) {
			resource = resourceLoader.getResource("file:" + fileName);

			if ((resource == null || !resource.exists())) {
				resource = resourceLoader.getResource("classpath:" + fileName);
			}
			
		}
		
		return resource;
	}
	public static File getFileFromFileName(String fileName) throws IOException {
		return getFileFromFileName(fileName, true);
	}
	public static File getFileFromFileName(String fileName, boolean useResourceLoader) throws IOException {
		if (useResourceLoader) {
			return resourceLoader.getResource(fileName).getFile();
		} else {
			return new File(fileName);
		}
	}

	public static void writeContentsToFile(File newFile, String contents) throws IOException {
		FileUtils.write(newFile, contents);
	}
	public static void writeContentsToFile(String fileName, String contents) throws IOException {
		File file = FileUtil.getFileFromFileName(fileName);
		FileUtil.writeContentsToFile(file, contents);
	}
	public static void writeContentsToFile(String fileName, InputStream contents) throws IOException {
		File file = FileUtil.getFileFromFileName(fileName);
        writeContentsToFile(file, contents);
	}
	public static void writeContentsToFile(File file, InputStream contents) throws IOException {
        OutputStream out = new FileOutputStream(file);
		IOUtils.copy(contents, out);
	}
	public static void writeContentsToFile(File newFile, byte[] bytes) throws IOException {
		System.out.println("about to create new file:  " + newFile.getAbsolutePath());
		
		if (!newFile.exists()) {
			boolean success = newFile.createNewFile();
			if (!success) {
				throw new RuntimeException("Could not create new file:  '" + newFile.getAbsolutePath() + "'");
			}
		}
		
        BufferedOutputStream buffStream = 
                new BufferedOutputStream(new FileOutputStream(newFile));
        buffStream.write(bytes);
        buffStream.close();
	}

	public static boolean createNewDirectoryIfNotExist(File file) throws IOException {
		if (!file.exists()) {
			boolean fileCreated = file.mkdirs();
			return fileCreated;
		}

		return true;
	}

	public static boolean moveFileToFolder(File fromFile, File toFolder) {
		File newFile = new File(toFolder.getAbsolutePath() + fromFile.getName());
		
		return fromFile.renameTo(newFile);
	}
	
	public static String readFileContents(String fileName) throws IOException {
		Resource resource = getResourceFromFileName(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);
        
		return IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
	}
}
