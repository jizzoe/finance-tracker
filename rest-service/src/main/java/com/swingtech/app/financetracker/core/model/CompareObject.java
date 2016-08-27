package com.swingtech.app.financetracker.core.model;

public class CompareObject {
	String compareName = null;
	Object compareValue;

	public CompareObject(String compareName, Object compoareValueFrom) {
		super();
		this.compareName = compareName;
		this.compareValue = compoareValueFrom;
	}
	
	public Object getCompareValue() {
		return compareValue;
	}
	public void setCompareValue(Object fromValue) {
		this.compareValue = fromValue;
	}
	public String getCompareName() {
		return compareName;
	}
	public void setCompareName(String compareName) {
		this.compareName = compareName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compareValue == null) ? 0 : compareValue.hashCode());
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
		CompareObject other = (CompareObject) obj;
		if (compareValue == null) {
			if (other.compareValue != null)
				return false;
		} else if (!compareValue.equals(other.compareValue))
			return false;
		return true;
	}
}
