package com.snsoft.test;

import java.io.InputStream;
import org.junit.Test;
import com.snsoft.util.HttpUtils;


public class ServletTest {
	
	@Test
	public void servletPostTest(){
		String url = "http://localhost:8080/second_hand/login";
		InputStream is = HttpUtils.sendPost(url, "{account:'111111',password:'111111'}");
		String result = HttpUtils.getString(is);
		System.out.println(result);
	}
	
	
	
	
	
}
