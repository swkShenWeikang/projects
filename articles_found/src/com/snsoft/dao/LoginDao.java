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
		String sql = "SELECT * FROM user_info WHERE account = ?";
		
		try {
			conn = JDBCUtils.getConnection();//取得连接
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, params.get("account"));//设置参数
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
	 * 测试
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		LoginDao ld = new LoginDao();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("account", "111111");
		
		HashMap<String, Object> res = null;
		res = ld.getUserInfo(param);
		System.out.println(res);
	}

}
