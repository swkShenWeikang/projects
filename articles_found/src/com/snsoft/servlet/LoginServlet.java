package com.snsoft.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.snsoft.dao.LoginDao;
import com.snsoft.jwc.ConnectTimeOutException;
import com.snsoft.jwc.ConnectToJWC;
import com.snsoft.jwc.StudentDataBean;
import com.snsoft.util.AllConstant;
import com.snsoft.util.EncodeUtils;
import com.snsoft.util.JsonUtils;


/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 © 2017
 * 
 * @author 14信息慎伟康
 * 
 * @version 1.0
 * 
 * @date 2017年7月7日 下午8:39:04
 * 
 * @Description TODO
 *		登录接口
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * 非接口入口
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "请求异常！"));	
	}

	/**
	 * 登录
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		//参数校验
		HashMap<String, String> params = JsonUtils.getRequestParams(request);
		if (StringUtils.isEmpty(params.get("account"))) {
			response.getWriter().write(
					JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "账号不能为空！"));
			return;
		}
		if(StringUtils.isEmpty(params.get("password"))){
			response.getWriter().write(
					JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "密码不能为空！"));
			return;
		}
		
		//查询账号信息
		HashMap<String, Object> userInfo = null;
		LoginDao ld = new LoginDao();
		try {
			userInfo = ld.getUserInfo(params);//调用数据库，查询用户信息
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().write(
					JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, AllConstant.MSG_ERROR));
			return;
		}//try
		
		//若数据库中无账号信息，则抓教务处数据，校对密码，存入数据
		if(userInfo == null){
			ConnectToJWC conn = new ConnectToJWC(params.get("account"));
			boolean flag = conn.checkVPNAccount(params.get("account"), params.get("password"));
			if(flag){//账号密码正确
				//抓取教务处信息，添加到数据库
				StudentDataBean studentData = null;
				try {
					studentData = conn.getStudentData();//访问教务处
				} catch (ConnectTimeOutException e) {
					e.printStackTrace();
					response.getWriter().write(
							JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "连接教务处超时！"));
					return;
				}//try
				
				if(studentData != null){//用户信息添加到数据库
					params.put("username", studentData.getName());
					params.put("password", EncodeUtils.encodeByMd5(params.get("password")));//加密
					params.put("college", studentData.getXiSuo());
					params.put("clazz", studentData.getBanJi());
					boolean flag2 = ld.addOneUser(params);
					if(flag2){
						response.getWriter().write(
								JsonUtils.jsonResponse(null, AllConstant.CODE_SUCCESS, AllConstant.MSG_SUCCESS));
						return;
					}
				}else{
					response.getWriter().write(
							JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "账号信息异常！"));
					return;
				}//if3
				
			}else{//账号密码错误
				response.getWriter().write(
						JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "账号或密码错误"));
				return;
			}//if2
			
		}else{//数据库中有账号信息，则直接校验密码
			String password = EncodeUtils.encodeByMd5(params.get("password"));
			if(password.equals(userInfo.get("password"))){
				response.getWriter().write(
						JsonUtils.jsonResponse(null, AllConstant.CODE_SUCCESS, AllConstant.MSG_SUCCESS));
				return;
			}else{
				response.getWriter().write(
						JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "账号或密码错误"));
				return;
			}//if2
		}//if1
		
	}//post

}//class
