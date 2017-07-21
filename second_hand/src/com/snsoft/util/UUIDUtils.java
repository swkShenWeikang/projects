package com.snsoft.util;

import java.util.UUID;

/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 © 2016
 * 
 * @author 14信息慎伟康
 * 
 * @version 1.0
 * 
 * @date 2016年11月4日 下午9:58:39
 * 
 * @Description TODO
 *    生成UUID工具类
 */
public final class UUIDUtils {

	public UUIDUtils() {
		super();
	}
	
	/**
	 * 获取UUID（带'-'）
	 * @return
	 * @Description TODO
	 */
	public static String uuid() {
		String s = UUID.randomUUID().toString();
		return s;
	}
	
	/**
	 * 获得一个UUID (去除'-')
	 * @return
	 * @Description TODO
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	
	/**
	 * 获得指定数目的UUID(生成多个UUID)
	 * @param number  需要获得的UUID数量
	 * @return  String[] UUID数组
	 * @Description TODO
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
}
