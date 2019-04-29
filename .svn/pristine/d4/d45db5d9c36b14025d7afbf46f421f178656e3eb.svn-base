package com.huachu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class BeanUtil {

    private final static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * bean 转化为实体
     * @param bean
     * @return
     */
    public static Map<String,Object> beanToMap(Object bean){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(null == bean){
            return map;
        }
        Class<?> clazz = bean.getClass();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor descriptor : descriptors){
            String propertyName = descriptor.getName();
            if(!"class".equals(propertyName)){
                Method method = descriptor.getReadMethod();
                Object result;
                try {
                    result = method.invoke(bean);
                    if(null != result){
                        map.put(propertyName, result);
                    }else{
                        map.put(propertyName, "");
                    }
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return map;
    }



    /**
     * map 转化为 bean
     * @param clazz
     * @param map
     * @return
     */
    public static <T> T mapToBean(Class<T> clazz,Map map){
        T object = null;
        try {
            object = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors){
                String propertyName = descriptor.getName();
                if(map.containsKey(propertyName)){
                    Object value = map.get(propertyName);
                    descriptor.getWriteMethod().invoke(object, value);
                }
            }

        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }


    /**
     * 复制集合
     *
     * @param <E>
     * @param source
     * @param destinationClass
     * @return
     */
    public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) {
        try {
            if (source.size() == 0) return Collections.emptyList();
            List<E> res = new ArrayList<E>(source.size());
            for (Object o : source) {
                E e = destinationClass.newInstance();
                org.apache.commons.beanutils.BeanUtils.copyProperties(e, o);
                res.add(e);
            }
            return res;
        } catch (InstantiationException e) {
            logger.error("BeanUtil-exception", e);
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }

    public static <S, D> D copyBean(S source, Class<D> destinationClazz) {
        if (null == source)
            return null;

        try {
            D d = destinationClazz.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(d, source);
            return d;
        } catch (InstantiationException e) {
            logger.error("BeanUtil-exception", e);
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }

    /**
     * 复制到已存在的bean
     *
     * @param source
     * @param dest
     * @return
     */
    public static <S, D> D copyToExistBean(S source, D dest) {
        if (null == source || null == dest)
            return null;

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
            return dest;
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }



    public static LinkedHashMap<String, Object> beanToLinkedHashMap(Object obj) {
        if (obj == null) {
            return null;
        }
        LinkedHashMap<String, Object> map =new  LinkedHashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;

    }


    /**
     * 使用反射方式转,不会改变元素的位置
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static <T>T mapToBeanByReflect(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }

        T obj = null;
        try {
            obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return obj;
    }



    /**
     * 使用反射方式转,不会改变元素的位置
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> beanToMapReflect(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = null;
        try {
            map = new HashMap<>(16);

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }
    
    

	/**
	 * Copy List对象
	 * @param list
	 * @param clazz
	 * @return
	 */
    public <T> List<T> copyList(@SuppressWarnings("rawtypes") List list, Class<T> clazz){
		List<T> respList = new ArrayList<>();
		for(Object o : list) {
			respList.add(copy(o, clazz));
		}
		
		return respList;
	}
	
	/**
	 * Copy Object对象
	 * @param ori
	 * @param clazz
	 * @return
	 */
	public static <T> T copy(Object ori, Class<T> clazz){
		T t = null;
		try {
			t = clazz.newInstance();
			BeanUtils.copyProperties(ori, t);
		} catch (Exception e) {
			logger.error("异常",e);
		} 
		
		return t;
	}
	
	public static <T> T copy(Object ori, T target){
		if(target == null) {
			throw new NullPointerException("Target copy object is null.");
		}
			
		try {
			BeanUtils.copyProperties(ori, target);
		} catch (Exception e) {
			logger.error("异常",e);
		} 
		return target;
	}
}
