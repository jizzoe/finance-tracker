package com.swingtech.common.core.util.introspect.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DescriptionCollection <X, T> {
	private final List<T> internalList = new ArrayList<T>();
	private final Map<X, T> internalMap = new HashMap<X, T>();
	private final List<T> returnList = Collections.unmodifiableList(internalList);
	private final Map<X, T> returnMap = Collections.unmodifiableMap(internalMap);
	
	public List<T> getList() {
		if (returnList.isEmpty()) {
			return null;
		}
		return returnList;
	}
	
	public void add(X key, T value) {
		internalList.add(value);
		internalMap.put(key, value);
	}
	
	public T get(X key) {
		return internalMap.get(key);
	}

	public List<T> getReturnList() {
		return returnList;
	}

	public Map<X, T> getReturnMap() {
		return returnMap;
	}
}
