package com.github.hwhaocool.yellow.validate;

public class ValidateTool {
	
	private static final ValidateTool  DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ValidateTool();
    }

	public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
      }
    
    private Builder toBuilder() {
        return new Builder();
    }

    public static final class Builder {
    	private boolean required = false;
    	
    	private int minLength = 0;
    	
    	private int maxLength = 0;
    	
    	private String regexPattern = null;
    	
    	public Builder required() {
    		return required(true);
    	}
    	
    	public Builder required(boolean value) {
    		this.required = value;
    		return this;
    	}
    	
    	public Builder regex(String value) {
    		this.regexPattern = value;
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
    	public Builder length(String description) {
    		return this;
    	}
    	
    	public void validate(RuntimeException ex) {
    		throw ex;
    	}
    	
    	public void validate(String msg) {
    		validate(new IllegalArgumentException(msg));
    	}
    	
    	public void validate() {
    		validate(new IllegalArgumentException());
    	}
    }
}
