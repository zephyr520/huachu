package com.huachu.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.huachu.common.constants.ClientType;
import com.huachu.common.dto.UserClient;
import com.huachu.common.exception.BOException;
import com.huachu.common.web.ApiResultCode;

public class TokenUtil {
	
	private static final String SEC_KEY = "@^_^123aBcZ*";
	private static final String ACCESS_TOKEN_KEY_PREFIX = "AT";
	private static final String REFRESH_TOKEN_KEY_PREFIX = "RT";

	private static final Pattern PTN = Pattern.compile("^([\\d]+)[\\|]([a-zA-Z]+)[\\|]([\\d]+)$");
	
	public static String createAccessToken(Integer userid, ClientType role) {
		return createToken(userid, role, ACCESS_TOKEN_KEY_PREFIX+SEC_KEY);
	}
	
	public static String createRefreshToken(Integer userid, ClientType role) {
		return createToken(userid, role, REFRESH_TOKEN_KEY_PREFIX+SEC_KEY);
	}
	
	private static String createToken(Integer userid, ClientType role, String key) {
		StringBuilder content = new StringBuilder();
		content.append(userid).append("|");
		content.append(role.name()).append("|");
		content.append(System.currentTimeMillis());
		return AESUtil.encryptString(content.toString(), key);
	}
	
	public static UserClient getUserIdAndClientTypeFromAccessToken(String accessToken) {
		return getUserIdAndClientTypeFromAccessToken(accessToken, ACCESS_TOKEN_KEY_PREFIX+SEC_KEY);
	}
	
	private static UserClient getUserIdAndClientTypeFromAccessToken(String token, String key) {
		UserClient uc = null;
		try {
			String userId = null;
			String clientType = null;
			String genTokenTime = null;
			String content = AESUtil.decryptString(token, key);
			if(StringUtils.isNotBlank(content)) {
				Matcher m = PTN.matcher(content);
				if(m.find()) {
					uc = new UserClient();
					userId = m.group(1);
					clientType = m.group(2);
					genTokenTime = m.group(3);
					uc.setUserId(Integer.valueOf(userId));
					uc.setClientType(ClientType.getClientType(clientType));
					uc.setGenTime(Long.valueOf(genTokenTime));
					return uc;
				}
			}
			
		}catch(Exception e) {
			throw new BOException(ApiResultCode.TOKEN_INVALID);
		}
		return uc;
	}
}
