/**
 * CastorDateFieldHandler.java
 * 
 * 深圳凯莱特科技股份有限公司版权所有
 * Copyright 2011 Shenzhen KNet Technology Co., Ltd. All rights reserved.
 */
package com.dahuangit.iots.app.android.util;

import java.util.Date;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * 自定义的Castor XML日期解析器
 *
 * @author 吴林洪 
 * 
 * 创建于 2011-6-21 上午10:23:50 
 */
public class CastorDateFieldHandler extends GeneralizedFieldHandler {

    @Override
    public Object convertUponGet(Object value) {
        if (value == null) {
            return null;
        }
        return DateUtils.format((Date) value);
    }

    @Override
    public Object convertUponSet(Object value) {
        return DateUtils.parse((String) value);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Class getFieldType() {
        return Date.class;
    }

}
