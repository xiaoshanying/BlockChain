package com.wmz.blockchain.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter4 {
    @Override
    protected boolean supports(Class<?> paramClass) {
        if (paramClass == String.class) {
            return false;
        }
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        super.setFastJsonConfig(fastJsonConfig);
        return super.supports(paramClass);
    }
}
