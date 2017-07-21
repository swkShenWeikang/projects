package com.snsoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 © 2017
 * 
 * @author 14信息慎伟康
 * 
 * @version 1.0
 * 
 * @date 2017年7月7日 下午9:01:43
 * 
 * @Description TODO
 *		jdbc工具类
 */
public class JDBCUtils {

	private static String className;
	private static String url;
	private static String username;
	private static String password;
	
	static {
		//配置文件必须放在src目录下
		className = ResourceBundle.getBundle("db").getString("className");
		url = ResourceBundle.getBundle("db").getString("url");
		password = ResourceBundle.getBundle("db").getString("password");
		username = ResourceBundle.getBundle("db").getString("username");
	}
	
	/**
	 * 加载驱动
	 */
	private static void loadDriver() {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("数据库驱动加载失败！");
		}
	}
	
	/**
	 * 获取数据库链接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		loadDriver();
		return DriverManager.getConnection(url,username,password);
	}
	
	/**
	 * 释放资源
	 * @param conn
	 * @throws SQLException
	 */
	public static void close(Connection conn) {
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	public static void close(Statement stat) {
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stat = null;
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}
	
	
	/**
	 * 处理结果集，获取列表
	 * @param rs
	 * @return
	 * @Description TODO
	 */
	public static List<HashMap<String, Object>> getList(ResultSet rs) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> temp = null;
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
			while(rs.next()){
				temp = new HashMap<String, Object>();
				for(int i = 1; i <= rsmd.getColumnCount(); i++){
					temp.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				result.add(temp);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(result == null || result.size() == 0){
			return null;
		}else{
			return result;
		}
	}
	
	/**
	 * 获取一条记录
	 * @param rs
	 * @return
	 */
	public static HashMap<String, Object> getHashMap(ResultSet rs) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		result = getList(rs);
		if(result == null || result.size() == 0){
			return null;
		}else{
			return result.get(0);
		}
	}
	
}
