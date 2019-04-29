package com.huachu.common.util;

import java.lang.reflect.Field;
import java.util.Date;

import com.huachu.common.annotation.Column;

public class CommonUtil {

	/**
     * 数据修改对比统计
     * @param oldT  修改前
     * @param newT  修改后
     * @param className 类名
     * @param <T>
     * @return
     */
    public static <T> String getModifyAfterMsg(T oldT, T newT, String className) {
        StringBuffer builder = new StringBuffer();
        try {
            Class<?> clazz = Class.forName(className);
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                // 过滤掉 static, final, private static final 字段
                if (8 == f.getModifiers()
                        || 16 == f.getModifiers()
                        || 26 == f.getModifiers()) {
                    continue;
                }
                Column column = f.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                Object oldV = ReflectionUtils.getFieldValue(oldT, f.getName());
                Object newV = ReflectionUtils.getFieldValue(newT, f.getName());
                // 新值和旧值进行比较
                if (newV != null && !newV.equals(oldV) && StringUtils.isNotBlank(newV.toString())) {
                    if (Date.class.equals(f.getType())) {
                        oldV = LDTUtil.toLocalDate((Date)oldV);
                        newV = LDTUtil.toLocalDate((Date)newV);
                    }
                    builder.append(column.desc())
                            .append("从(")
                            .append(oldV)
                            .append(")修改为(")
                            .append(newV)
                            .append(");");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
