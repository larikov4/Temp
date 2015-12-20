package com.epam.hw1.dao.parameter;

import com.epam.hw1.model.DefaultBeanHolder;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom <code>SqlParameterSource</code> provides setting default entities.
 * They are used to replace passed parameters.
 *
 * @author Yevhen_Larikov
 */
public class SqlParameterSourceImpl implements SqlParameterSource{
    private static final Logger LOG = Logger.getLogger(SqlParameterSourceImpl.class);

    private Map<String, Object> params = new HashMap<>();
    private DefaultBeanHolder defaultBeanHolder;

    /**
     * Injects <code>DefaultBeanHolder</code>.
     *
     * @param defaultBeanHolder bean with default entities
     */
    public SqlParameterSourceImpl(DefaultBeanHolder defaultBeanHolder) {
        this.defaultBeanHolder = defaultBeanHolder;
    }

    /**
     * Puts passed value with passed name into inner map. When default entity id
     * is defined replace passed id with default ones.
     *
     * @param name the parameter name
     * @param value the parameter value
     * @return the object itself
     */
    public SqlParameterSourceImpl addValue(String name, Object value) {
        if(name==null && value==null){
            LOG.warn("Passed parameter was null.");
            return this;
        }
        if("eventId".equals(name) && defaultBeanHolder.getDefaultEvent()!=null){
            params.put(name, defaultBeanHolder.getDefaultEvent().getId());
            return this;
        }
        if("userId".equals(name) && defaultBeanHolder.getDefaultUser()!=null){
            params.put(name, defaultBeanHolder.getDefaultUser().getId());
            return this;
        }
        params.put(name, value);
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
