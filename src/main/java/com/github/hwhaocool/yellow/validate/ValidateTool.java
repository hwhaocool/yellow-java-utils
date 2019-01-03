package com.github.hwhaocool.yellow.validate;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidateTool {
	
	private static final ValidateTool  DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ValidateTool();
    }

	public static Condition newCondition() {
        return DEFAULT_INSTANCE.toCondition();
      }
    
    private Condition toCondition() {
        return new Condition();
    }
    
    public static final class Condition {
    	private Builder validateData;
    	
    	public Condition() {
    		this.validateData = new Builder();
    	}
    	
    	public Condition required() {
    		return required(true);
    	}
    	
    	public Condition required(boolean value) {
    		validateData.required = value;
    		return this;
    	}
    	
    	public Condition regex(String value) {
    		if (StringUtils.isEmpty(value)) {
				throw new IllegalArgumentException("regex pattern can't be empty");
			}
    		
    		validateData.regexPattern = value;
    		return this;
    	}
    	
    	/**
    	 * if (2 <= x < 5), you can set description to "[2, 5)"
    	 * <br>if (2 <= x ), you can set description to "[2, 0)" or "[2, 0]"
    	 * <br>if ( x < 5 ), you can set description to "(0, 5)" or "[0, 5)"
    	 * 
    	 * @param description
    	 * @author hwhaocool
    	 * @return
    	 */
    	public Condition length(String description) {
    		if (StringUtils.isEmpty(description)) {
				throw new IllegalArgumentException("length description can't be empty");
			}
    		
    		validateData.lengthDesc = description;
    		return this;
    	}
    	
    	public Validate from(String value) {
    		return fromValue(value);
    	}
    	
    	@SuppressWarnings("rawtypes")
		public Validate from(Collection value) {
    		return fromValue(value);
    	}
    	
    	@SuppressWarnings("rawtypes")
		public Validate from(Map value) {
    		return fromValue(value);
    	}
    	
    	private Validate fromValue(Object value) {
    		return new Validate(validateData, value);
    	}
    }

    @SuppressWarnings("unused")
    private static final class Builder {
    	
		private boolean required = false;
    	
    	private String lengthDesc;
    	
    	private Length maxLength;
    	
    	private String regexPattern = null;
    }
    
    public static final class Validate {
    	private Builder validateData;
    	
    	private Object value;
    	
    	private Pattern lengthAnalysisPattern = Pattern.compile("");
    	
    	public Validate(Builder builder, Object value) {
			this.validateData = builder;
			this.value = value;
		}
    	
    	public void validate(RuntimeException ex) {
    		boolean pass = validateValue(value);
    		if (! pass) {
    			throw ex;
			} else {
				//do nothing
			}
    	}
    	
    	public void validate(String msg) {
    		validate(new IllegalArgumentException(msg));
    	}
    	
    	public void validate() {
    		validate(new IllegalArgumentException());
    	}
    	
    	private boolean validateValue(Object value) {
    		if (null == value) {
				if (validateData.required) {
					return false;
				}
				return true;
			}
    		
    		//analysis length description
    		
    		
    		if (value instanceof String) {
				return validateString((String) value);
			}
    		return true;
    	}
    	
    	private boolean validateString(String str) {
    		if (true) {
				
			}
    		return true;
    	}
    }
    
    private static final class Length {
    	private int value;
    	private boolean canEquals;
    	
    	public Length(int value, boolean canEquals) {
    		this.value = value;
    		this.canEquals= canEquals;
    	}

		@Override
		public String toString() {
			return "Length [value=" + value + ", canEquals=" + canEquals + "]";
		}
    	
    }
}
