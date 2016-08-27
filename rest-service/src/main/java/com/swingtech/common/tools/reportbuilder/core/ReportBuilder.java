package com.swingtech.common.tools.reportbuilder.core;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import com.swingtech.common.core.util.StringUtil;

public class ReportBuilder {
	protected final static String LINE_SEPARATOR = "\n";
	protected final static Integer DEFAULT_OFFSET_INCREMENT_INCREASE = 4;
	protected final static Integer DEFAULT_OFFSET_DECREMENT_DECREASE = 4;
	protected final static Integer DEFAULT_OFFSET_PRINT_INCREASE = 4;
	protected final static CharSequence DEFAULT_PAD_CHARACTER = " ";
	protected final static Boolean DEFAULT_IGNORE_CURRENT_OFFSET = false;
	protected final static Boolean DEFAULT_AUTO_DECREMENT = false;
	protected final static Boolean DEFAULT_DO_OFFSET = false;
	
	protected StringBuffer reportBuffer = new StringBuffer();
	protected Integer currentOffSet = 0;
	protected Integer lastSetIncreaseValue = 0;
	protected Stack<Integer> lastSetIncreaseValueStack = new Stack<Integer>();
	protected Boolean globalAutoDecrement = false;
	protected Boolean useDefaultsGlobally = false;
	
	public static ReportBuilder newReportBuilder() {
		return new ReportBuilder();
	}
	
	public ReportBuilder appendln(CharSequence line) {
		return this.appendln(null, line);
	}

	public ReportBuilder appendln(Integer offset, CharSequence line) {
		return this.appendln(offset, line, null, null, null);
	}

	public ReportBuilder appendln(Integer offset, CharSequence line, Boolean ignoreurrentOffset, Boolean doNotOffset, String padCharacter) {
		Inputs inputs = this.createNewInputs(offset, ignoreurrentOffset, doNotOffset, padCharacter);
		
		return this.appendln(line, inputs);
	}

	public ReportBuilder appendln(CharSequence line, Inputs inputs) {
		String padString = this.getPadString(inputs);
		reportBuffer.append(padString + line + LINE_SEPARATOR);
		return this;
	}
	
	private Integer getPadNumber(Inputs inputs) {
		if (inputs.doNotOffsetv != null && !inputs.doNotOffsetv) {
			return 0;
		}
		if (inputs.doNotOffsetv != null && inputs.ignoreurrentOffset) {
			return inputs.getPrintOffsetIncrease();
		} else {
			return currentOffSet + inputs.getPrintOffsetIncrease();
		}
	}
	
	private String getPadString(Inputs inputs) {
		StringBuffer padStringBuf = new StringBuffer();
		Integer padNumber = this.getPadNumber(inputs);
		
		return StringUtil.getPadString(padNumber, (String) inputs.getPadCharacter());
	}

	@Override
	public String toString() {
		return reportBuffer.toString();
	}
	
	public <X, T> ReportBuilder appendHeader(String headerTitle, Map<X, T> valuesToPrint) {

		int maxLength = StringUtil.getLengthOFLongestStringInMap(valuesToPrint, true);

		String longestString = StringUtil.getStringWithLongestLengthInMap(valuesToPrint, true);
		
		System.out.println(" longest string:  " + longestString + ".  length:  " + maxLength);
		
		this.appendln("****************************************************************************");
		this.appendln("*  " + headerTitle);
		this.appendln("*");
		this.appendln("*  Values:");
		for (Entry<X, T> entry : valuesToPrint.entrySet()) {
			this.appendln(10, StringUtil.padStringPost(entry.getKey().toString(), maxLength + 2, " ", true) + " = " + entry.getValue().toString());
		}
		this.appendln("****************************************************************************");
		
		return this;
	}

	public Inputs createNewInputs(Integer offset, Boolean ignoreurrentOffset, Boolean doNotOffsetv, CharSequence padCharacter) {
		return new Inputs(offset, ignoreurrentOffset, doNotOffsetv, padCharacter);
	}
	
	public ReportBuilder increaseCurrentOffSet() {
		return this.increaseOffSet(DEFAULT_OFFSET_INCREMENT_INCREASE);
	}

	public ReportBuilder increaseOffSet(Integer increaseOffset) {
		Inputs inputs = new Inputs();
		
		inputs.setIncrementOffset(increaseOffset);
		
		return this.increaseOffSet(inputs);
	}

	public ReportBuilder increaseOffSet(Inputs inputs) {
		Integer incrementValue = inputs.getIncrementOffset();
		
		// push the increase value on the stack
		lastSetIncreaseValueStack.push(incrementValue);
		
		currentOffSet = currentOffSet + incrementValue;
		
		return this;
	}

	public ReportBuilder decrementOffSet() {
		return this.decrementOffSet(null, null);
	}
	
	public ReportBuilder decrementOffSet(Integer decreaseOffset, Boolean autoDecrement) {
		Inputs inputs = new Inputs();
		
		inputs.setAutoDecrement(autoDecrement);
		inputs.setDecreaseOffset(decreaseOffset);
		
		return decrementOffSet(inputs);
	}
	
	public Integer getDecrementyValue(Inputs inputs, Integer decrementFromStack) {
		Integer decrementValue = null;
		
		// if we are set to auto decrement, then use the las increment value in the stack and decreent by same amount.
		if (this.isGlobalAutoDecrement() || inputs.autoDecrement) {
			return decrementFromStack;
		}
		
		// Otherwise, use the input value.  If that value is null, it'll return default.  So, we'll always end up with a decrement amount

		return inputs.getDecreaseOffset();
	}

	public ReportBuilder decrementOffSet(Inputs inputs) {
		// Always get the value from the stack, even if we ignore it.
		Integer decrementFromStack = lastSetIncreaseValueStack.pop();
		Integer decrementValue = this.getDecrementyValue(inputs, decrementFromStack);

		currentOffSet = currentOffSet + decrementValue;

		return this;
	}
	
	public boolean useAllDefaultsGlobally() {
		return useDefaultsGlobally != null && useDefaultsGlobally.booleanValue() == true;
	}
	

	public class Inputs {
		private Integer printOffsetIncrease = null; 
		private Boolean ignoreurrentOffset = null; 
		private Boolean doNotOffsetv = null;
		private CharSequence padCharacter = null;
		private Integer incrementOffset = null;
		private Integer decreaseOffset = null;
		Boolean autoDecrement = null;
		Boolean useAllDefaults = false;

		public Inputs(Integer offset, Boolean ignoreurrentOffset, Boolean doNotOffsetv, CharSequence padCharacter) {
			super();
			this.printOffsetIncrease = offset;
			this.ignoreurrentOffset = ignoreurrentOffset;
			this.doNotOffsetv = doNotOffsetv;
			this.padCharacter = padCharacter;
		}

		public boolean useAllDefaultsLocally() {
			return useAllDefaults != null && useAllDefaults.booleanValue() == true;
		}
		
		public boolean useAllDefaults() {
			return useAllDefaultsLocally() || useAllDefaultsGlobally();
		}
		
		public Inputs() {
			useDefaultsGlobally.booleanValue();
		}
		public Integer getPrintOffsetIncrease() {
			if (useAllDefaults()) {
				return DEFAULT_OFFSET_PRINT_INCREASE;
			}
			return (printOffsetIncrease != null) ? printOffsetIncrease : DEFAULT_OFFSET_PRINT_INCREASE;
		}
		public void setOffset(Integer offset) {
			this.printOffsetIncrease = offset;
		}
		public Boolean getIgnoreurrentOffset() {
			if (useAllDefaults()) {
				return DEFAULT_IGNORE_CURRENT_OFFSET;
			}
			return (ignoreurrentOffset != null) ? ignoreurrentOffset : DEFAULT_IGNORE_CURRENT_OFFSET;
		}
		public void setIgnoreurrentOffset(Boolean ignoreurrentOffset) {
			this.ignoreurrentOffset = ignoreurrentOffset;
		}
		public Boolean getDoNotOffsetv() {
			if (useAllDefaults()) {
				return DEFAULT_DO_OFFSET;
			}
			return (doNotOffsetv != null) ? doNotOffsetv : DEFAULT_DO_OFFSET;
		}
		public void setDoNotOffsetv(Boolean doNotOffsetv) {
			this.doNotOffsetv = doNotOffsetv;
		}
		public CharSequence getPadCharacter() {
			if (useAllDefaults()) {
				return DEFAULT_PAD_CHARACTER;
			}
			return (padCharacter != null) ? padCharacter : DEFAULT_PAD_CHARACTER;
		}
		public void setPadCharacter(CharSequence padCharacter) {
			this.padCharacter = padCharacter;
		}

		public Integer getIncrementOffset() {
			if (useAllDefaults()) {
				return DEFAULT_OFFSET_INCREMENT_INCREASE;
			}
			return (incrementOffset != null) ? incrementOffset :DEFAULT_OFFSET_INCREMENT_INCREASE;
		}

		public void setIncrementOffset(Integer incrementOffset) {
			this.incrementOffset = incrementOffset;
		}

		public Integer getDecreaseOffset() {
			if (useAllDefaults()) {
				return DEFAULT_OFFSET_DECREMENT_DECREASE;
			}
			return (decreaseOffset != null) ? decreaseOffset :DEFAULT_OFFSET_DECREMENT_DECREASE;
		}

		public void setDecreaseOffset(Integer decreaseOffset) {
			this.decreaseOffset = decreaseOffset;
		}

		public Boolean getAutoDecrement() {
			if (useAllDefaults()) {
				return DEFAULT_AUTO_DECREMENT;
			}
			return (autoDecrement != null) ? autoDecrement : DEFAULT_AUTO_DECREMENT;
		}

		public void setAutoDecrement(Boolean autoDecrement) {
			this.autoDecrement = autoDecrement;
		}
	}

	public boolean isGlobalAutoDecrement() {
		return (globalAutoDecrement != null && globalAutoDecrement == true);
	}

	public void setGlobalAutoDecrement(Boolean globalAutoDecrement) {
		this.globalAutoDecrement = globalAutoDecrement;
	}
}
