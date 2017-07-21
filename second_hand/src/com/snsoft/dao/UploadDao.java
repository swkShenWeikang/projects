package com.snsoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * @date 2017年7月21日 下午3:59:11
 * 
 * @Description TODO
 *		图片上传接口相关的dao
 */
public class UploadDao {

	/**
	 * 添加图片记录
	 * @param params
	 * @return
	 */
	public boolean addAttach(HashMap<String, String> params){
		//返回结果
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstat = null;
		String sql = "INSERT INTO attach "
				+ "VALUES(?, ?, ?, NOW());";
		
		try {
			conn = JDBCUtils.getConnection();//取得连接
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, params.get("id"));//设置参数
			pstat.setString(2, params.get("parentId"));//设置参数
			pstat.setString(3, params.get("saveName"));//设置参数
			pstat.execute();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.close(pstat);
			JDBCUtils.close(conn);
		}
		return result;
	}
	
	/**
	 * 删除图片记录
	 * @param saveName
	 * @return
	 */
	public boolean deleteAttach(String saveName){
		//返回结果
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstat = null;
		String sql = "DELETE FROM attach WHERE saveName = ?;";
		
		try {
			conn = JDBCUtils.getConnection();//取得连接
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, saveName);//设置参数
			pstat.execute();
			result = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.close(pstat);
			JDBCUtils.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		UploadDao ad = new UploadDao();
		
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("id", "slaksl");
//		params.put("parentId", "111111");
//		params.put("saveName", "aa.jpg");
//		System.out.println(ad.addAttach(params));
		
		System.out.println(ad.deleteAttach("vv.jpg"));
	}
	
}
