package com.epam.hw1.dao.impl;

import com.epam.hw1.model.DefaultBeanHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yevhen_Larikov
 */
public class SqlParameterSourceImpl implements SqlParameterSource{
    private static final Logger LOG = Logger.getLogger(SqlParameterSourceImpl.class);

    private Map<String, Object> params = new HashMap<>();
    private DefaultBeanHolder defaultBeanHolder;

    public SqlParameterSourceImpl(DefaultBeanHolder defaultBeanHolder) {
        this.defaultBeanHolder = defaultBeanHolder;
    }

    public SqlParameterSourceImpl addValue(String paramName, Object value) {
        if(paramName==null && value==null){
            LOG.warn("Passed parameter was null.");
            return this;
        }
        if("eventId".equals(paramName) && defaultBeanHolder.getDefaultEvent()!=null){
            params.put(paramName, defaultBeanHolder.getDefaultEvent().getId());
            return this;
        }
        if("userId".equals(paramName) && defaultBeanHolder.getDefaultUser()!=null){
            params.put(paramName, defaultBeanHolder.getDefaultUser().getId());
            return this;
        }
        params.put(paramName, value);
        return this;
    }

    @Override
    public boolean hasValue(String paramName) {
        return params.containsValue(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return params.get(paramName);
    }

    @Override
    public int getSqlType(String paramName) {
        return TYPE_UNKNOWN;
    }

    @Override
    public String getTypeName(String paramName) {
        return null;
    }
}
