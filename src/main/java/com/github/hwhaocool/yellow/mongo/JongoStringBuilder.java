package com.github.hwhaocool.yellow.mongo;

import java.text.SimpleDateFormat;

public class JongoStringBuilder {
    
    private static final JongoStringBuilder  DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new JongoStringBuilder();
    }
    
    /**
     * <br> 占位符
     */
    public static final String TOKEN = "#";
    
    /**
     * <br>日期格式化
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
    
    private JongoStringBuilder() {
        
    }
    
    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
      }
    
    public Builder toBuilder() {
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
        
        /**
         * <br>添加一个 过滤项，agentId: #
         *
         * @param query 带# 的过滤语句
         * @param parameters 参数
         * @return
         * @author YellowTail
         * @since 2018-12-11
         */
        public Builder addField(String query, Object... parameters) {
            if (parameters == null) {
                return this;
            }
            
            if (isParamHaveNullValue(parameters)) {
                //参数有null值，不允许
                return this;
            }
            
            String process = process(query, parameters);
            
            if (isFirst) {
                sb.append(process);
                isFirst = false;
            } else {
                sb.append(",").append(process);
            }

            return this;
        }
        
        /**
         * <br>会自动把 参数转为 ObjectID
         *
         * @param query
         * @param id
         * @return
         * @author YellowTail
         * @since 2018-12-03
         */
        public Builder addFieldWithObjectId(String query, String id) {
            if (StringUtils.isBlank(id)) {
                return this;
            }
            
            if (! ObjectId.isValid(id)) {
                throw new NullPointerException("id is invalid ObjectId, it's " + id);
            }
            

            return addField(query, new ObjectId(id));
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
        
        private String process(String query, Object... parameters) {
            StringBuilder sb = new StringBuilder();
            
            int paramPos = 0;       // current position in the parameter list
            int start = 0;          // start of the current string segment
            int pos;                // position of the last token found
            
            while ((pos = query.indexOf(TOKEN, start)) != -1) {
                if (paramPos >= parameters.length) {
                    throw new IllegalArgumentException("Not enough parameters passed to query: " + query);
                }

                // Insert chars before the token
                sb.append(query, start, pos);
                
                sb.append( getString( parameters[paramPos]));

                paramPos++;
                start = pos + TOKEN.length();
            }

            // Add remaining chars
            sb.append(query, start, query.length());

            if (paramPos < parameters.length) {
                throw new IllegalArgumentException("Too many parameters passed to query: " + query);
            }
            
            return sb.toString();
        }
        
        private boolean isParamHaveNullValue(Object... parameters) {
            for(Object obj: parameters) {
                if (null == obj) {
                    return true;
                }
            }
            
            return false;
        }
        
        private String getString(Object obj) {
            if (obj instanceof String) {
                return "'" +  obj.toString() + "'";
            }
            
            if (obj instanceof Integer || obj instanceof Double) {
                return obj.toString();
            }
            
            if (obj instanceof Boolean) {
                return obj.toString();
            }
            
            if (obj instanceof Date) {
                return "{\"$date\": \""+ DATE_FORMAT.format(obj) + "\"}";
            }
            
            if (obj instanceof ObjectId) {
                return  "{\"$oid\": \"" + obj.toString() + "\"}";
            }
            
//            if (obj instanceof Object[]) {
//                StringBuilder tempSb = new StringBuilder();
//                
//                for (Object a: (Object[])obj) {
//                    
//                }
//            }
            
            return  null;
        }
        
    }
    
    
}