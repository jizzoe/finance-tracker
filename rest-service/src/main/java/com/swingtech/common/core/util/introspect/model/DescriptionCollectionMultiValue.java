package com.swingtech.common.core.util.introspect.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DescriptionCollectionMultiValue <X, T> {
	private DescriptionCollection<X, List<T>> collection = new DescriptionCollection<X, List<T>>();

	public void add(X key, List<T> value) {
		collection.add(key, value);
	}

	public void add(X key, T value) {
		List<T> valueList = collection.get(key);
		
		if (valueList == null) {
			valueList = new ArrayList<T>();
			collection.add(key, valueList);
		}
		
		valueList.add(value);
	}

	public List<T> getValueList(X key) {
		return collection.get(key);
	}

	public T getValueSingle(X key) {
		if (collection.get(key) == null || collection.get(key).isEmpty()) {
			return null;
		}
		return collection.get(key).get(0);
	}

	public List<List<T>> getReturnList() {
		return collection.getReturnList();
	}

	public Map<X, List<T>> getReturnMap() {
		return collection.getReturnMap();
	}
	
}
