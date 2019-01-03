package com.github.hwhaocool.yellow.string;

import java.util.Optional;

public class YellowStringBuilder {


    private static final YellowStringBuilder  DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new YellowStringBuilder();
    }
    
    /**
     * <br> 占位符
     */
    public static final String TOKEN = "#";
    
    /**
     * <br>日期格式化
     */
//    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
    
    private YellowStringBuilder() {
    }
    
    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
      }
    
    private Builder toBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private StringBuilder sb;
        private boolean isFirst = true;
        
        private Builder() {
            sb = new StringBuilder(128);
            sb.append("{");
        }
        
        public String build() {
            return sb.append("}").toString();
        }
        
        public Builder append(Object value) {
        	return null;
        }
        
        public Builder appendIfPresent(Object value) {
//        	Optional.ofNullable(value).ifPresent(consumer);
        	return null;
        }
        
        public Builder appendIfPresent(String value) {
        	return null;
        }
        
        public Builder addField(String query) {
            if (isFirst) {
                sb.append(query);
                isFirst = false;
            } else {
                sb.append(",").append(query);
            }

            return this;
        }
        
        private boolean isParamHaveNullValue(Object... parameters) {
            for(Object obj: parameters) {
                if (null == obj) {
                    return true;
                }
            }
            
            return false;
        }
    }
    
    
}