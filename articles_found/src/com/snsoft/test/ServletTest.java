package com.snsoft.test;

import java.io.InputStream;
import org.junit.Test;
import com.snsoft.util.HttpUtils;


public class ServletTest {
	
	@Test
	public void servletPostTest(){
		String url = "http://localhost:8080/articles_found/login";
		InputStream is = HttpUtils.sendPost(url, "{account:'189140086',password:'syau1995'}");
		String result = HttpUtils.getString(is);
		System.out.println(result);
	}
	
	@Test
	public void Test22(){
		String url = "http://localhost:8080/articles_found/Test2";
		InputStream is = HttpUtils.sendPost(url, "{saveName:'7/20/0ea05e8f4f4bb1303a374b97f7c9.jpg'}");
		String result = HttpUtils.getString(is);
		System.out.println(result);
	}
	
	@Test
	public void ViewServletPostTest(){
		String url = "http://localhost:8080/articles_found/view";
		InputStream is = HttpUtils.sendPost(url, "{account:'111111',id:'111111'}");
		String result = HttpUtils.getString(is);
		System.out.println(result);
	}
	
}
