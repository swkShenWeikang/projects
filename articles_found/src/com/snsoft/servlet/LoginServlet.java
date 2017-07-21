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
import com.snsoft.util.AllConstant;
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
		//返回结果
		String result = "";
		//解析请求中的参数
		HashMap<String, String> param = JsonUtils.getRequestParams(request);
		
		//参数校验
		if(StringUtils.isEmpty(param.get("account"))){
			result = JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "账号不能为空！");
		}else if(StringUtils.isEmpty(param.get("password"))){
			result = JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "密码不能为空！");
		}else{
			try {
				//调用dao
				LoginDao ld = new LoginDao();
				HashMap<String, Object> res = ld.getUserInfo(param);
				if(res == null){
					result = JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "该账号不存在！");
				}else{
					if(param.get("password").equals(res.get("password"))){
						result = JsonUtils.jsonResponse(res, AllConstant.CODE_SUCCESS, "登录成功！");
					}else{
						result = JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "密码错误！");
					}
				}
			} catch (SQLException e) {
				result = JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, AllConstant.MSG_ERROR);
				e.printStackTrace();
			}
			
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
	}

}
