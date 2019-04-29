package com.huachu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象和Map的转换工具类
 * 
 * @author jiwen2c
 * @date 2017年6月29日下午8:15:31
 */
public class ObjMapUtil {

	private static final Logger logger = LoggerFactory.getLogger(ObjMapUtil.class);

	/**
	 * 通用对象转Map方法
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> objToMap(T obj) {
		if(obj == null) {
			return null;
		}
		
		Map<String, Object> map = new HashMap<>();  
		BeanMap beanMap = BeanMap.create(obj);  
        for (Object key : beanMap.keySet()) {  
            map.put(key.toString(), beanMap.get(key));  
        }  
	    return map;
	}

	/**
	 * 通用Map转对象方法
	 * @param map
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> T mapToObj(Map<String, Object> map, T obj) {
		BeanMap beanMap = BeanMap.create(obj);  
	    beanMap.putAll(map);  
	    return obj; 
	}

	public static Map<String, Object> objConvertMap(Object obj) {

		Map<String, Object> map = new HashMap<>();
		try {
			for (Field f : obj.getClass().getDeclaredFields()) {
				if (!f.getName().equals("serialVersionUID")) {
					f.setAccessible(true);
					if (f.get(obj) != null) {
						map.put(f.getName(), f.get(obj));
					}

				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
		 T obj = clazz.newInstance();
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 属性名
				String propertyName = entry.getKey();
				Object value = entry.getValue();
				String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				Field field = getClassField(clazz, propertyName);
				if (field == null)
					continue;
				Class<?> fieldTypeClass = field.getType();
				value = convertValType(value, fieldTypeClass);
				try {
					clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 * 
	 * @param clazz
	 *            指定的class
	 * @param fieldName
	 *            字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class<?> clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		Class<?> superClass = clazz.getSuperclass();
		// 简单的递归一下
		if (superClass != null) {
			return getClassField(superClass, fieldName);
		}
		return null;
	}

	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 * 
	 * @param value
	 *            Object对象值
	 * @param fieldTypeClass
	 *            属性的类型
	 * @return 转换后的值
	 */
	private static Object convertValType(Object value, Class<?> fieldTypeClass) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldTypeClass.getName()) || long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value.toString());
		} else if (Integer.class.getName().equals(fieldTypeClass.getName()) || int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value.toString());
		} else if (Float.class.getName().equals(fieldTypeClass.getName()) || float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value.toString());
		} else if (Double.class.getName().equals(fieldTypeClass.getName()) || double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value.toString());
		} else if(BigDecimal.class.getName().equals(fieldTypeClass.getName())) {
			retVal = new BigDecimal(value.toString());
		}else{
			retVal=value;
		}
		return retVal;
	}
}
