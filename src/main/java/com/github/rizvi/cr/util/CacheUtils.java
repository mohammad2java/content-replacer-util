package com.github.rizvi.cr.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {
	private static Map<String, String> props = new HashMap<>();

	@Autowired
	private Environment evn;

	public String getPropValue(String key, String defaultVal) {
		String ret = defaultVal;
		ret = StringUtils.isNotBlank(props.get(key))?props.get(key):StringUtils.isNotBlank(evn.getProperty(key))?evn.getProperty(key):ret;
		return ret;
	}

	public void put(String key,String val) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(val)) {
			props.put(key, val);	
		}
		else {
			throw new RuntimeException("key-val blank exception-");
		}
	}
	
	public void putAll(Map<String,String> prop) {
		props.putAll(prop);
	}
}

