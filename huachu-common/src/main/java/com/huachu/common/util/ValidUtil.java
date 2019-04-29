package com.huachu.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

    public static final String MOBILE_REGEX = "^1\\d{10}$";
    public static final String EMAIL_REGEX = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    public static final String QQ_REGEX = "[1-9]([0-9]{5,11})";
    public static final String TELEPHONE_REGEX = "^((\\d{3,4})|\\d{3,4}-)?\\d{8}";

	/**
     * 是否有效手机号码，可以更改参数以只检查特定运营商的号段
     * @param mobile 号码
     * @return 是否合法手机号码
     */
    public static boolean isValidMobile(String mobile){
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(mobile); 
        
        return matcher.matches();
    }

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email); 
        
        return matcher.matches();
	}

	public static boolean isQQ(String qq) {
        Pattern pattern = Pattern.compile(QQ_REGEX);
        Matcher matcher = pattern.matcher(qq);
        return matcher.matches();
    }

    public static boolean isValidTel(String telephone) {
        Pattern pattern = Pattern.compile(TELEPHONE_REGEX);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }

//    public static void main(String[] args) {
//        System.out.println(isValidMobile("12222222222"));
//    }
}
