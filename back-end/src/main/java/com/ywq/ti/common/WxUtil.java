package com.ywq.ti.common;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WxUtil {

	private static Logger log = Logger.getLogger(WxUtil.class);

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public final static String wx_api_url = "https://api.weixin.qq.com/sns/jscode2session";
	public final static String app_id = "wxf3dda8e9ed75c98b";
	public final static String app_secret = "b6fd4536a1461fc4a316751609dda381";

	public static void main(String[] args) throws Exception {

		String code = "0236Whjf2Xal7C0ym5jf26ocjf26Whj8";
		Map<String, String> result = sessionKeyOf(code);
		// 非正常返回的话值是null
		log.info("session_key = " + result.get("session_key"));
		log.info("openid = " + result.get("openid"));
		String encryptedData = "MtlqeSTknGIz55s9R6MyNsEYxY6FygWahXWkYpii4S85zHjcMt9Mu4lJNVVy6pd6olVanvoBmWuwHD3ByTXLvsWCbnTKOu5SRQZVr+mo5v0zF5Ycdo3MRLKzzLYBFgtCLec3EPeC9QyhJ3x2yzBCmK7RWBydYBXzwd8QnAZJi7l8P3saHD9Ees2raTGkFOArS3GUwfGRY2ZbRtI+182BNQ==";
		String iv = "1furY3BgUvlCwdanLVxwew==";
		Map<String, String> s = decryptData(encryptedData, iv, result.get("session_key"));
		log.info("result = " + s);

	}

	/**
	 * 用wx.login获取的code换取session_key和openid
	 * @param code
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> sessionKeyOf(String code) throws IOException {

		Map<String, String> result = null;

		String _url = wx_api_url + "?appid=" + app_id + "&secret=" + app_secret + "&js_code=" + code
				+ "&grant_type=authorization_code";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(_url).build();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful()) {
			String resBody = response.body().string();
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(resBody, Map.class);
		}
		log.info("result = " + result);

		return result;
	}

	/**
	 * 解密敏感数据
	 * @param encryptedData
	 * @param sessionKey
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> decryptData(String encryptedData, String iv, String sessionKey) throws Exception {

		Map<String, String> result = null;

		byte[] dataByte = Base64.decodeBase64(encryptedData);// 加密数据
		byte[] keyByte = Base64.decodeBase64(sessionKey);// 加密秘钥
		byte[] ivByte = Base64.decodeBase64(iv);// 矢量移量

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
		parameters.init(new IvParameterSpec(ivByte));
		cipher.init(Cipher.DECRYPT_MODE, keySpec, parameters);// 初始化

		byte[] resultByte = cipher.doFinal(dataByte);
		if (null != resultByte && resultByte.length > 0) {
			String resBody = new String(resultByte, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(resBody, Map.class);
		}

		return result;
	}
}
