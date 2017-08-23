package com.snsoft.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @copyright 神农大学生软件创新中心版权所有 © 2017
 * 
 * @author 慎伟康
 * 
 * @version 1.0
 * 
 * @date 2017年8月14日 下午6:17:37
 * 
 * @Description TODO
 *		编码工具类。MD5加密、base64位编码
 */
public final class EncodeUtils {
	
	private static String charset = "UTF-8";
	private static String[] arr = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"}; 
	
	
	/**
	 * base64加密
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeByBase64(String password){
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(password.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * base64解密
	 * @param password
	 * @return
	 * @throws IOException 
	 */
	public static String decodeByBase64(String password){
		try {
			BASE64Decoder decode = new BASE64Decoder();
			byte[] temp = decode.decodeBuffer(password);
			return new String(temp, charset);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * MD5加密
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static String encodeByMd5(String password){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md.digest(password.getBytes(charset)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * MD5加密。byte数组转为16进制字符串
	 * @param password
	 * @return
	 */
	public static String encodeByMd5Second(String password){
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] temp = md.digest(password.getBytes(charset));
			//将byte数组转为字符串
			for (byte b : temp) {
				result.append(byteToString(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 将每一位byte转发String，转为16进制
	 * @param b
	 * @return
	 */
	private static String byteToString(byte b) {
		int n = b;
		if(n < 0){
			n = 256 + n;
		}
		int index1 = n / 16;
		int index2 = n % 16;
		return arr[index1] + arr[index2];
	}

}
