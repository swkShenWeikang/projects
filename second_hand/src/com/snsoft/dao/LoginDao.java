package com.snsoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.snsoft.util.JDBCUtils;

/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 © 2017
 * 
 * @author 14信息慎伟康
 * 
 * @version 1.0
 * 
 * @date 2017年7月7日 下午11:05:25
 * 
 * @Description TODO
 *		访问数据库，查看用户信息
 */
public class LoginDao {

	/**
	 * 根据账号查询用户信息
	 * @param params
	 * @throws SQLException
	 */
	public HashMap<String, Object> getUserInfo(HashMap<String, String> params) throws SQLException {
		//返回结果
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM user_info WHERE account = ? AND identity = ?";
		
		try {
			conn = JDBCUtils.getConnection();//取得连接
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, params.get("account"));//设置参数
			pstat.setString(2, params.get("identity"));//设置参数
			rs = pstat.executeQuery();//查询
			result = JDBCUtils.getHashMap(rs);//获取处理后的结果集
		} catch (SQLException e) {
			throw e;
			//e.printStackTrace();
		}finally{//关闭资源
			JDBCUtils.close(rs);
			JDBCUtils.close(pstat);
			JDBCUtils.close(conn);
		}
		
		return result;
	}
	
	
	/**
	 * 添加一个用户
	 * @param params
	 * @return
	 */
	public boolean addOneUser(HashMap<String, String> params){
		boolean result = false;//返回结果
		
		Connection conn = null;
		PreparedStatement pstat = null;
		String sql = "INSERT INTO user_info(account, `password`, username, identity) "
				+ "VALUES(?, ?, ?, '1')";
		
		try {
			conn = JDBCUtils.getConnection();//取得连接
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, params.get("account"));//设置参数
			pstat.setString(2, params.get("password"));//设置参数
			pstat.setString(3, params.get("username"));//设置参数
			pstat.execute();//执行SQL
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 测试
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		LoginDao ld = new LoginDao();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("account", "1111");
		param.put("password", "1111");
		param.put("username", "王五");
		param.put("identity", "0");
		
		HashMap<String, Object> res = null;
		res = ld.getUserInfo(param);
		System.out.println(res);
		
//		System.out.println(ld.addOneUser(param));
	}

}
