package com.snsoft.jwc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import net.sf.json.JSONObject;



/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 (c) 2016
 * 
 * @author Mr. Soldier
 * 
 * @version 1.0
 * 
 * @date 2016年7月9日 下午12:54:54
 * 
 * @Description TODO
 *	连接到教务处网站
 */
public class ConnectToJWC {
	
	public static final int STUDENT_ACCOUNT = 2;  //学生账号
	public static final int TEACHER_ACCOUNT = 1;  //教师账号
	public static final int OTHER_ACCOUNT = 3;	//其他类型账号
	public static final int ERROR_ACCOUNT = -1;	//错误账号
	
	private Map<String, String> cookies ;
	private String account;
	
	public ConnectToJWC(String account){
		cookies = login(account);  //登录后的cookies
//		System.out.println(cookies);
		this.account = account;
	}
	


	/**
	 * 获取账号身份
	 * @return
	 */
	public static int getAccountStatus(String account){
		if(StringUtils.isBlank(account))
			return ERROR_ACCOUNT;
		int length = account.length();
		if(length == "1999500023".length())
			return TEACHER_ACCOUNT;
		else 
			return STUDENT_ACCOUNT;
	}
	
	/**
	 * 登录教务处，并返回成功后的cookie，其中包含jsonid
	 * @param account
	 * @return
	 */
	public Map<String,String> login(String account){
		try {
			LoginUtils tools = new LoginUtils();
			String weburl = "http://"+ ConstantValue.JWC_SERVER_IP_AND_PORT +"/loginAction.do?";
			String href = weburl + "zjh=" + account + "&loginFlag=" + tools.doEncryptURP(account);
			return Jsoup.connect(href).execute().cookies();
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * 验证账号和密码是否正确
	 * @param account
	 * @param password
	 * @return
	 * @throws WrongPasswordException 
	 */
	public boolean checkVPNAccount(String account, String password) {
		String url = "http://snsoft.syau.edu.cn/checkvpn/";
		HashMap<String, String> map = new HashMap<>();
		map.put("verifyCode", "snsoft2016");
		map.put("account", account);
		map.put("password", password);
		int flag = 0;
		try {
			Response response = Jsoup.connect(url).ignoreContentType(true).method(Method.POST).data(map).execute();
			String result = response.body();
			if(!StringUtils.isEmpty(result)){
				JSONObject jsonObject = JSONObject.fromObject(result);
				int code = Integer.parseInt((String)jsonObject.getJSONObject("oelement").get("errorcode"));
				if(code == 0){
					String type = (String) jsonObject.getJSONObject("element").get("type");
					if("teacher".equals(type)){
						flag =  TEACHER_ACCOUNT;
					} else if("student".equals(type)){
						flag =  STUDENT_ACCOUNT;
					} 
				} else if(code == 1){
					flag =  0;
				}
			}
		} catch (Exception e) {
			flag = 0;
		}
		if(flag == STUDENT_ACCOUNT || flag == TEACHER_ACCOUNT)
			return true;
		else 
			return false;
		
	}
	
	/**
	 * 获取学生基本信息
	 * @return
	 * @throws ConnectTimeOutException
	 * @throws NotLoginException
	 */
	public StudentDataBean getStudentData() 
			throws ConnectTimeOutException{
		String basedataUrl = "http://"+ConstantValue.JWC_SERVER_IP_AND_PORT+"/xjInfoAction.do?oper=xjxx";
		StudentDataBean bean = null;
		try {
			String body = Jsoup.connect(basedataUrl).cookies(cookies).execute().body();
			bean = HtmlParser.parserStudentBaseData(body);
		}  catch (IOException e) {
			throw new ConnectTimeOutException();
		}
		return bean;
	}
	
	
	
	
	
	public static void main(String[] args) throws ConnectTimeOutException {
		String account = "189140086";
		ConnectToJWC conn = new ConnectToJWC(account);
		
		StudentDataBean studentData = conn.getStudentData();
		System.out.println(studentData);
		
	}
	
	
	
	
}
