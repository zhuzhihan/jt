package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	public static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 方法说明: 根据API讲对象转化为JSON,同时将JSON转化为对象
	 */

	public static String toJSON(Object obj) {
		String result = null;
		try {
			result = MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}

	public static <T> T toObject(String json, Class<T> cls) {
		T obj = null;
		try {
			obj = MAPPER.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return obj;
	}

}
