package com.swingtech.app.financetracker.service.csvprocessor;

import java.io.File;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.FinancialTransactionRecord;
import com.swingtech.app.financetracker.core.model.ParseTransactionResults;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.util.FinanceTrackerUtil;
import com.swingtech.common.core.util.FileUtil;
import com.swingtech.common.core.util.JsonUtil;
import com.swingtech.common.core.util.Timer;
import com.swingtech.common.core.util.bean.BeanUtility;
import com.swingtech.common.core.util.introspect.core.ClassUtility;
import com.swingtech.common.core.util.introspect.core.reader.ObjectClassMetaDataReader;
import com.swingtech.common.core.util.introspect.model.ClassDescription;
import com.swingtech.common.core.util.introspect.model.DescriptionCollection;
import com.swingtech.common.tools.reportbuilder.modules.ui.BaseReportModule;

public class CategoryConfigurationTest {

	// @Test
	public void testFinanceTrackerUtil() throws FinanceTrackerExcepton {
		File dataFolder = new File(FinanceTrackerUtil.class.getResource("/test-data").getFile());
		Map<String, Resource> configFileMap = FinanceTrackerUtil.getCategoryResourceMap("/test-data");
		List<File> transactionFileList = FinanceTrackerUtil.getTransactionFilesToProcess(dataFolder);

		System.out.println("configFileMap:  " + configFileMap);
		System.out.println("transactionFileList:  " + transactionFileList);
	}

	// @Test
	public void testGetCategoryConfigurationListFromCsvFile() throws Exception {
		List<FinancialTransactionRecord> transactionRecords = null;
		ParseTransactionResults transactionResults = new ParseTransactionResults();
		CsvProcessorConfiguration csvProcessorConfiguration = null;
		String resultsJson = null;
		String resultsJsonPrettyPrint = null;
		String folderNameToWriteJson = "/git/swingtech/swing-tech/sandbox/finance-tracker/rest-service/files/test-output";
		String outputFileName = folderNameToWriteJson + "/parse-transactions-results-local.json";

		File configurationFolderName = new File(FinanceTrackerUtil.class.getResource("/test-data").getFile());
		File transactionFolderName = new File(FinanceTrackerUtil.class.getResource("/test-data").getFile());

		csvProcessorConfiguration = FinanceTrackerUtil.getCsvProcessorConfiguration("/test-data");

		transactionResults = FinanceTrackerUtil.parseFinancialTransactionRecords(csvProcessorConfiguration,
				transactionFolderName, transactionResults);

		transactionRecords = transactionResults.getAllFinancialTransactions();

		System.out.println("Transaction Records:  " + transactionRecords);

		transactionResults.finalizeResults();

		int i = 0;

		for (FinancialTransactionRecord record : transactionRecords) {
			System.out.println("Printng record " + i);
			// System.out.println(" Printng record " + i record.getd);
		}

		resultsJson = JsonUtil.marshalObjectToJson(transactionResults);
		resultsJsonPrettyPrint = JsonUtil.prettyPrintJson(resultsJson);

		System.out.println("start wrtiting results to json");
		File responseJsonFile = FileUtil.getFileFromFileName(outputFileName, false);
		FileUtil.writeContentsToFile(responseJsonFile, resultsJsonPrettyPrint);

		Map<Class, String> stringMap = new HashMap<Class, String>();
		stringMap.put(Timer.class, "durationString");

		this.startSTop(transactionResults.getAddTransactionTimer());
		this.startSTop(transactionResults.getParseCsvFilesTimer());
		this.startSTop(transactionResults.getTotalProcessTimer());

		Map<String, Object> propMap = BeanUtility.getPropertiesForObject(transactionResults, stringMap, true, true);

		for (Entry<String, Object> record : propMap.entrySet()) {
			System.out.println("  --->Property Name:  " + record.getKey() + " = " + record.getValue());
		}

		System.out.println("end wrtiting results to json");
	}
	
	@Test
	public void testStringUtil() {
//		String obj = null;
//		ReportHeaderModule obj = new ReportHeaderModule();
//		ReportBuilderUtil.isRootModule(obj);
//		
//		List<BaseReportModule> baseModules = new ArrayList<BaseReportModule>();
//		List<ModuleChild> moduleChilds = new ArrayList<ModuleChild>();
		List<String> objects = new ArrayList<String>();
		
		printClassStuff(objects);
	}
	
	void printClassStuff(Object object) {
		Class<BaseReportModule> clas = BaseReportModule.class;
		DescriptionCollection<String, String> coll = new DescriptionCollection<String, String>(); 
		TypeVariable<?>[] typeParameters = object.getClass().getTypeParameters();
		
		System.out.println("# of types:  " + typeParameters.length);
		
		for (TypeVariable<?> type : typeParameters) {
			System.out.println("  type:  " + type.getName());
		}
	}

//	@Test
	public void testClassUtil() throws Exception {
		String basePackage = null;
		Class<?> baseClassToCheckFor = null;

//		// basePackage = "com.swingtech.common.core.util.introspect.model";
//		// baseClassToCheckFor = BaseInfrospectionDescription.class;
//		basePackage = "com.swingtech.common.tools.reportbuilder.modules.ui";
//		baseClassToCheckFor = BaseReportModule.class;
//
//		Map<Class<?>, AnnotationMetadata> subClassList = null;
//
//		subClassList = ClassUtility.getAllParentClasses(baseClassToCheckFor, basePackage);
//
//		System.out.println("subclasses:  " + subClassList);
//
//		for (Entry<Class<?>, AnnotationMetadata> record : subClassList.entrySet()) {
//			System.out.println("   subclass:  " + record.getKey());
//		}
//
//		subClassList = ClassUtility.getAllClassesWithTheAnnotation(ReportModuleType.class, basePackage);
//
//		System.out.println("CLASS:  " + subClassList);
//
//		for (Entry<Class<?>, AnnotationMetadata> record : subClassList.entrySet()) {
//			System.out.println("   subclass:  " + record.getKey());
//		}
		
//		RootDescriptionBuildConfiguration rootConfig= RootIntrospectionDescriptionConfigurationFactory.createAndInitializeRootConfiguration();
//
//		System.out.println("description:  " + rootConfig);
		
		ClassDescription classDescription = null;
		try {
			ObjectClassMetaDataReader reader = new ObjectClassMetaDataReader();
			
			classDescription = reader.getClassMetaDataForClass(ClassDescription.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("  classDescription: " + classDescription.getMethods().size());

		System.out.println("  path for class: " + ClassUtility.getResourceFromClass(ParseTransactionResults.class));

	}

	public void startSTop(Timer timer) throws Exception {
		timer.startTiming();
		Thread.sleep(30);
		timer.stopTiming();
	}
}
