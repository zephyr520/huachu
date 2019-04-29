package com.huachu.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @DATE 2018/8/30
 */
public class CollectionsUtil {

	/***
     * 判定集合中是否包含重复的元素
     * @param list
     * @return
     */
    public static boolean isDuplicate(List<? extends Object> list) {
        if (null == list) {
            return Boolean.FALSE;
        }
        return list.size() == new HashSet<>(list).size();
    }

    public static List<Integer> stringToIntegerList(List<String> list) {
        return list.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<String> removeDuplicate(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static <T> List<T> emptyDuplicate(List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }
}
