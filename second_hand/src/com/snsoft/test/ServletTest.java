package com.snsoft.test;

import java.io.InputStream;
import org.junit.Test;
import com.snsoft.util.HttpUtils;


public class ServletTest {
	
	@Test
	public void servletPostTest(){
		String url = "http://localhost:8080/second_hand/login";
		InputStream is = HttpUtils.sendPost(url, "{account:'189140086',password:'syau199500',identity:'1'}");
		String result = HttpUtils.getString(is);
		System.out.println(result);
	}
	
	
	
	
	
}
