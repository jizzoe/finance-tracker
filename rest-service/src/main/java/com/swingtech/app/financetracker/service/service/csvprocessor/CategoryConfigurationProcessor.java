package com.swingtech.app.financetracker.service.service.csvprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.Resource;

import com.swingtech.app.financetracker.core.model.CategoryConfiguration;
import com.swingtech.app.financetracker.core.model.CsvLineRecord;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfiguration;
import com.swingtech.app.financetracker.core.model.CsvProcessorConfigurationListHolder;
import com.swingtech.app.financetracker.core.model.TransactionCategoryTag;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.common.core.util.JsonUtil;
import com.swingtech.common.tools.csvprocessor.core.CsvRecordProcessor;
import com.swingtech.common.tools.csvprocessor.core.CsvUtil;
import com.swingtech.common.tools.reportbuilder.model.CsvParseErrorRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParseProcessError;
import com.swingtech.common.tools.reportbuilder.model.CsvParseSuccessRecord;
import com.swingtech.common.tools.reportbuilder.model.CsvParserResults;

public class CategoryConfigurationProcessor implements CsvRecordProcessor {
	private final String CONTEXT_MAP_KEY_CSV_PROCESSOR_CONFIG = "CsvProcessorConfigurationListHolder";
	private final String CONTEXT_MAP_KEY_CATEGORY_CONFIGURATION = "CsvProcessorConfigurationListHolder";

	private Map<String, Object> parseProcessContextMap = null;

	public CsvProcessorConfiguration parseConfigurationFiles(Map<String, Resource> configurationResourceMap)
			throws FinanceTrackerExcepton {
		CsvProcessorConfiguration csvProcessorConfiguration = new CsvProcessorConfiguration();
		CategoryConfiguration categoryConfiguration = null;
		CsvProcessorConfigurationListHolder categoryList = null;

		for (Entry<String, Resource> configFileEntry : configurationResourceMap.entrySet()) {
			categoryConfiguration = new CategoryConfiguration();
			Resource configurationResource = configFileEntry.getValue();
			String transactionSource = configFileEntry.getKey();

			// categoryList =
			// this.getCategoryConfigurationListFromCsvFile((File)
			// configFileEntry.getValue());

			try {
				categoryList = JsonUtil.unmarshalJsonToObject(configurationResource.getInputStream(),
						CsvProcessorConfigurationListHolder.class);
			} catch (IOException e) {
				throw new FinanceTrackerExcepton(
						"Error trying to unmarshal the resource json into a CsvProcessorConfigurationListHolder object.  Resource:  '"
								+ configurationResource.getFilename() + "'");
			}

			csvProcessorConfiguration.getConfigurationMap().put(transactionSource, categoryList);
		}

		return csvProcessorConfiguration;
	}

	public CsvProcessorConfigurationListHolder getCategoryConfigurationListFromCsvFile(File csvFile)
			throws FinanceTrackerExcepton {
		CsvProcessorConfigurationListHolder categoryList = new CsvProcessorConfigurationListHolder();
		Map<String, Object> parseProcessContextMap = new HashMap<String, Object>();

		parseProcessContextMap.put(CONTEXT_MAP_KEY_CSV_PROCESSOR_CONFIG, categoryList);
		CsvParserResults csvParseResults = null;

		try {
			csvParseResults = CsvUtil.parseCsvFile(csvFile, this, false);
		} catch (Exception e) {
			throw new FinanceTrackerExcepton(
					"Error trying to et Category Configuration list from csv file:  '" + csvFile + "'", e);
		}

		categoryList = (CsvProcessorConfigurationListHolder) parseProcessContextMap
				.get(CONTEXT_MAP_KEY_CSV_PROCESSOR_CONFIG);
		return categoryList;
	}

	public CategoryConfiguration parseCategoryConfigurationFromCsvLine(List<String> csvLine) {
		CategoryConfiguration configuration = new CategoryConfiguration();
		String field = null;
		TransactionCategoryTag mainCategoryTag = null;
		List<TransactionCategoryTag> subCategories = null;

		for (int i = 0; i < csvLine.size(); i++) {
			field = csvLine.get(i);

			if (i == 0) {
				mainCategoryTag = this.getMainCategory(csvLine);
				configuration.setMainCategory(mainCategoryTag);
			} else {
				if (field == null || field.trim().isEmpty()) {
					continue;
				}

				subCategories = this.getSubCategories(csvLine);
				configuration.setSubCategories(subCategories);
			}
		}

		return configuration;
	}

	public TransactionCategoryTag getMainCategory(List<String> csvLine) {
		TransactionCategoryTag category = new TransactionCategoryTag();
		String categoryFullName = csvLine.get(0);

		category.setFullName(categoryFullName);

		if (categoryFullName.contains("-")) {
			category.setName(categoryFullName.substring(categoryFullName.lastIndexOf("-") + 1));
			this.getChildCategory(categoryFullName, category);
		} else {
			category.setName(categoryFullName);
		}

		return category;
	}

	public void getChildCategory(String categoryFullName, TransactionCategoryTag parentCategory) {
		int lastDashIndex = -1;
		String lastCategoryName = null;
		String remainingCategoryName = null;
		TransactionCategoryTag childCategory = new TransactionCategoryTag();

		lastDashIndex = categoryFullName.lastIndexOf("-");
		lastCategoryName = categoryFullName.substring(lastDashIndex + 1).trim();
		remainingCategoryName = categoryFullName.substring(0, lastDashIndex).trim();

		childCategory.setFullName(remainingCategoryName);
		childCategory.setName(lastCategoryName);

		parentCategory.setChildCategory(childCategory);

		if (remainingCategoryName.contains("-")) {
			this.getChildCategory(remainingCategoryName, childCategory);
		}

	}

	public List<TransactionCategoryTag> getSubCategories(List<String> csvLine) {
		List<TransactionCategoryTag> subCategories = new ArrayList<TransactionCategoryTag>();
		TransactionCategoryTag subCategory = null;
		String field = null;

		if (csvLine.size() < 1) {
			return subCategories;
		}

		for (int i = 1; i < csvLine.size(); i++) {
			field = csvLine.get(i);

			if (field != null && !field.isEmpty()) {
				subCategory = new TransactionCategoryTag();
				subCategory.setFullName(field);
				subCategory.setName(field);
				subCategories.add(subCategory);
			}
		}

		return subCategories;
	}

	@Override
	public void handleParseCsvPrerProcess(Map<String, Object> parseProcessContextMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object handleParseCsvLine(CsvLineRecord csvLineRecord, Map<String, Object> parseProcessContextMap,
			Map<String, Object> csvLineContextMap) {
		CategoryConfiguration categoryConfiguration = null;
		categoryConfiguration = this.parseCategoryConfigurationFromCsvLine(csvLineRecord.getCsvLineFieldList());

		csvLineContextMap.put(CONTEXT_MAP_KEY_CATEGORY_CONFIGURATION, categoryConfiguration);

		return true;
	}

	@Override
	public boolean handleParseLineSuccess(CsvParseSuccessRecord successRecord, CsvParserResults csvParseResults,
			Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) {
		CsvProcessorConfigurationListHolder categoryList = (CsvProcessorConfigurationListHolder) parseProcessContextMap
				.get(CONTEXT_MAP_KEY_CSV_PROCESSOR_CONFIG);
		CategoryConfiguration categoryConfiguration = (CategoryConfiguration) csvLineContextMap
				.get(CONTEXT_MAP_KEY_CSV_PROCESSOR_CONFIG);
		categoryList.getConfigurationList().add(categoryConfiguration);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean handleParseLineError(CsvParseErrorRecord errorRecord, CsvParserResults csvParseResults,
			Map<String, Object> parseProcessContextMap, Map<String, Object> csvLineContextMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handlePraseCsvProcessError(CsvParserResults csvParseResults, CsvParseProcessError csvParseProcessError,
			Map<String, Object> parseProcessContextMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleParseCsvPostProcess(CsvParserResults csvParseResults, Map<String, Object> parseProcessContextMap,
			boolean didParseProcessFail) {
		// TODO Auto-generated method stub

	}

}
